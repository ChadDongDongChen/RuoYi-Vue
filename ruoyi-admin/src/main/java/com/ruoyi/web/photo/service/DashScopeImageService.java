package com.ruoyi.web.photo.service;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.FileOutputStream;
import java.net.URL;
import java.util.Base64;
import javax.imageio.ImageIO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.ruoyi.common.config.RuoYiConfig;
import com.ruoyi.web.photo.config.PhotoAiProperties;

@Service
public class DashScopeImageService
{
    private static final Logger log = LoggerFactory.getLogger(DashScopeImageService.class);

    private static final RestTemplate restTemplate = new RestTemplate();

    @Autowired
    private PhotoAiProperties photoAiProperties;

    /**
     * 证件照生成主流程：人像分割 → 换背景 → 尺寸裁剪
     */
    public String generateIdPhoto(String originalImageUrl, String background, int widthPx, int heightPx)
    {
        try
        {
            String base64Image = downloadAndEncodeBase64(originalImageUrl);
            if (base64Image == null)
            {
                log.error("failed to download original image: {}", originalImageUrl);
                return null;
            }

            // Step 1: 人像分割（获取mask）— 失败时降级使用原图
            String maskBase64 = callSegmentation(base64Image);
            if (maskBase64 == null)
            {
                log.warn("segmentation failed, fallback to original image");
                maskBase64 = base64Image; // 降级：使用原图
            }

            // Step 2: 换背景色 + 尺寸裁剪
            String resultBase64 = compositeWithBackground(base64Image, maskBase64, background, widthPx, heightPx);
            if (resultBase64 == null)
            {
                log.error("composite failed");
                return null;
            }

            // Step 3: 保存结果
            String savedPath = saveBase64Image(resultBase64);
            log.info("id photo generated: {}", savedPath);
            return savedPath;
        }
        catch (Exception e)
        {
            log.error("generate id photo error", e);
            return null;
        }
    }

    /**
     * 下载图片并转为 base64（Java 8 兼容）
     */
    private String downloadAndEncodeBase64(String imageUrl)
    {
        try
        {
            URL url = new URL(imageUrl);
            try (InputStream is = url.openStream())
            {
                ByteArrayOutputStream buffer = new ByteArrayOutputStream();
                byte[] data = new byte[4096];
                int nRead;
                while ((nRead = is.read(data, 0, data.length)) != -1)
                {
                    buffer.write(data, 0, nRead);
                }
                buffer.flush();
                byte[] bytes = buffer.toByteArray();
                return Base64.getEncoder().encodeToString(bytes);
            }
        }
        catch (Exception e)
        {
            log.error("download image failed: {}", imageUrl, e);
            return null;
        }
    }

    /**
     * 调用阿里百炼人像分割 API
     * DashScope 人像分割: 使用 wanx2.1-imageedit 的 async 接口
     */
    private String callSegmentation(String base64Image)
    {
        // 使用 DashScope wanx2.1-imageedit 异步接口
        String url = "https://dashscope.aliyuncs.com/api/v1/services/aigc/image2image/image-synthesis";

        JSONObject body = new JSONObject();
        body.put("model", "wanx2.1-imageedit");

        JSONObject input = new JSONObject();
        // DashScope 要求 base64 不带 data URI 前缀
        input.put("image", base64Image);
        body.put("input", input);

        // 背景移除/透明化
        JSONObject parameters = new JSONObject();
        parameters.put("style", "background_change");
        body.put("parameters", parameters);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + photoAiProperties.getAi().getApiKey());
        headers.set("X-DashScope-Async", "enable");

        HttpEntity<String> entity = new HttpEntity<>(body.toJSONString(), headers);

        log.info("calling DashScope segmentation API: model=wanx2.1-imageedit, url={}", url);

        try
        {
            ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);
            log.info("segmentation response: {}", response.getBody());
            JSONObject result = JSONObject.parseObject(response.getBody());

            // 异步任务返回 task_id
            JSONObject output = result.getJSONObject("output");
            if (output != null)
            {
                String taskId = output.getString("task_id");
                if (taskId != null)
                {
                    log.info("segmentation task created: {}", taskId);
                    return pollTaskResult(taskId);
                }
            }

            // 检查错误
            String code = result.getString("code");
            String message = result.getString("message");
            if (code != null)
            {
                log.error("segmentation error: code={}, message={}", code, message);
                return null;
            }

            log.error("segmentation no task_id: {}", response.getBody());
            return null;
        }
        catch (Exception e)
        {
            log.error("segmentation API call failed", e);
            return null;
        }
    }

    /**
     * 轮询 DashScope 异步任务结果
     */
    private String pollTaskResult(String taskId)
    {
        String url = "https://dashscope.aliyuncs.com/api/v1/tasks/" + taskId;
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + photoAiProperties.getAi().getApiKey());
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        for (int i = 0; i < 30; i++)
        {
            try
            {
                Thread.sleep(2000);
                ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
                JSONObject result = JSONObject.parseObject(response.getBody());

                // 兼容模式格式: {"data": [{"url": "..."}], "status": "SUCCEEDED"}
                JSONArray data = result.getJSONArray("data");
                String status = result.getString("status");

                if ("SUCCEEDED".equals(status) && data != null && !data.isEmpty())
                {
                    String resultUrl = data.getJSONObject(0).getString("url");
                    if (resultUrl != null)
                    {
                        return downloadAndEncodeBase64(resultUrl);
                    }
                }

                // 旧格式: {"output": {"task_status": "SUCCEEDED", "result_url": "..."}}
                JSONObject output = result.getJSONObject("output");
                if (output == null)
                {
                    continue;
                }

                String taskStatus = output.getString("task_status");

                if ("SUCCEEDED".equals(taskStatus))
                {
                    // 从 results 数组获取
                    JSONArray results = output.getJSONArray("results");
                    if (results != null && !results.isEmpty())
                    {
                        String resultUrl = results.getJSONObject(0).getString("url");
                        return downloadAndEncodeBase64(resultUrl);
                    }
                    // 或者直接从 result_url
                    String resultUrl = output.getString("result_url");
                    if (resultUrl != null)
                    {
                        return downloadAndEncodeBase64(resultUrl);
                    }
                    log.error("no result url in task output");
                    return null;
                }
                else if ("FAILED".equals(taskStatus))
                {
                    log.error("segmentation task failed: {}", result.getString("message"));
                    return null;
                }
                // PENDING/RUNNING: continue polling
            }
            catch (Exception e)
            {
                log.error("poll task error", e);
            }
        }
        log.error("segmentation task timeout");
        return null;
    }

    /**
     * 将人像与背景色合成 + 尺寸裁剪
     * 使用分割后的mask提取人像区域
     * @param personBase64 原始图片 base64
     * @param maskBase64 分割mask base64（失败时与原图相同）
     */
    private String compositeWithBackground(String personBase64, String maskBase64, String background, int width, int height)
    {
        try
        {
            byte[] personBytes = Base64.getDecoder().decode(personBase64);
            BufferedImage personImg = ImageIO.read(new ByteArrayInputStream(personBytes));

            if (personImg == null)
            {
                return null;
            }

            boolean hasValidMask = !maskBase64.equals(personBase64);
            BufferedImage maskImg = null;
            if (hasValidMask)
            {
                maskImg = ImageIO.read(new ByteArrayInputStream(Base64.getDecoder().decode(maskBase64)));
            }

            // 创建目标尺寸的图片
            BufferedImage result = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

            // 设置背景色
            java.awt.Color bgColor = parseBackground(background);
            java.awt.Graphics2D g = result.createGraphics();
            g.setColor(bgColor);
            g.fillRect(0, 0, width, height);

            // 按比例缩放并居中放置人像（证件照通常头部占70%）
            int srcW = (maskImg != null) ? maskImg.getWidth() : personImg.getWidth();
            int srcH = (maskImg != null) ? maskImg.getHeight() : personImg.getHeight();
            double scale = Math.min((double) width / srcW, (double) height / srcH) * 0.85;
            int newW = (int) (personImg.getWidth() * scale);
            int newH = (int) (personImg.getHeight() * scale);
            int offsetX = (width - newW) / 2;
            int offsetY = (height - newH) / 2;

            g.setRenderingHint(java.awt.RenderingHints.KEY_INTERPOLATION, java.awt.RenderingHints.VALUE_INTERPOLATION_BILINEAR);

            if (hasValidMask && maskImg != null)
            {
                // 有有效mask：逐像素合成（只绘制人像区域）
                // 先缩放person和mask到目标尺寸
                BufferedImage scaledPerson = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_RGB);
                java.awt.Graphics2D pg = scaledPerson.createGraphics();
                pg.setRenderingHint(java.awt.RenderingHints.KEY_INTERPOLATION, java.awt.RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                pg.drawImage(personImg, 0, 0, newW, newH, null);
                pg.dispose();

                BufferedImage scaledMask = new BufferedImage(newW, newH, BufferedImage.TYPE_BYTE_GRAY);
                java.awt.Graphics2D mg = scaledMask.createGraphics();
                mg.setRenderingHint(java.awt.RenderingHints.KEY_INTERPOLATION, java.awt.RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                mg.drawImage(maskImg, 0, 0, newW, newH, null);
                mg.dispose();

                // 逐像素合成
                for (int y = 0; y < newH; y++)
                {
                    for (int x = 0; x < newW; x++)
                    {
                        int maskVal = scaledMask.getRGB(x, y) & 0xFF;
                        if (maskVal > 128)
                        {
                            int px = Math.min(x + offsetX, width - 1);
                            int py = Math.min(y + offsetY, height - 1);
                            result.setRGB(px, py, scaledPerson.getRGB(x, y));
                        }
                    }
                }
            }
            else
            {
                // 降级模式：直接绘制原图
                g.drawImage(personImg, offsetX, offsetY, newW, newH, null);
            }
            g.dispose();

            // 编码为 base64
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(result, "jpg", baos);
            return Base64.getEncoder().encodeToString(baos.toByteArray());
        }
        catch (Exception e)
        {
            log.error("composite error", e);
            return null;
        }
    }

    /**
     * 解析背景色
     */
    private java.awt.Color parseBackground(String background)
    {
        switch (background)
        {
            case "蓝底": return new java.awt.Color(67, 142, 219);
            case "红底": return new java.awt.Color(207, 37, 51);
            case "白底":
            default: return java.awt.Color.WHITE;
        }
    }

    /**
     * 保存 base64 图片到本地
     */
    private String saveBase64Image(String base64)
    {
        FileOutputStream fos = null;
        try
        {
            byte[] bytes = Base64.getDecoder().decode(base64);
            String fileName = "idphoto_" + System.currentTimeMillis() + ".jpg";
            String filePath = RuoYiConfig.getUploadPath() + "/" + fileName;

            java.io.File dir = new java.io.File(RuoYiConfig.getUploadPath());
            if (!dir.exists())
            {
                dir.mkdirs();
            }

            fos = new FileOutputStream(filePath);
            fos.write(bytes);
            return "/profile/upload/" + fileName;
        }
        catch (Exception e)
        {
            log.error("save image error", e);
            return null;
        }
        finally
        {
            if (fos != null)
            {
                try { fos.close(); } catch (Exception ignored) {}
            }
        }
    }
}

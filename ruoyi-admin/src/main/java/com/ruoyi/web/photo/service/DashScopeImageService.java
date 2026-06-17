package com.ruoyi.web.photo.service;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.FileOutputStream;
import java.io.File;
import java.net.URL;
import java.util.Base64;
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
     * 证件照生成：质量校验 + 大模型分析 + 生成
     * 1. 质量校验：检测照片是否清晰、是否有完整面部
     * 2. qwen-vl-plus 分析自拍，提取人物特征
     * 3. wan2.6-t2i 根据特征生成证件照
     */
    public IdPhotoResult generateIdPhoto(String originalImageUrl, String background, int widthPx, int heightPx,
                                         String beauty, String suit)
    {
        try
        {
            // Step 0: 质量校验
            String qualityError = checkImageQuality(originalImageUrl);
            if (qualityError != null)
            {
                log.warn("image quality check failed: {}", qualityError);
                return IdPhotoResult.error(qualityError);
            }
            log.info("image quality check passed");

            // Step 1: 用 qwen-vl-plus 分析自拍
            String personDesc = analyzeSelfie(originalImageUrl, beauty, suit);
            if (personDesc == null)
            {
                log.error("failed to analyze selfie");
                return IdPhotoResult.error("照片分析失败，请换一张重试");
            }
            log.info("analyzed selfie: {}", personDesc);

            // Step 2: 构建生成提示词
            String prompt = buildGeneratePrompt(personDesc, background, beauty, suit);
            log.info("generation prompt: {}", prompt);

            // Step 3: 调用 wanx-v1 图生图（保留面部特征）
            String resultUrl = generateImage(prompt, originalImageUrl);
            if (resultUrl == null)
            {
                log.error("image generation failed");
                return IdPhotoResult.error("AI 生成失败，请重试");
            }
            log.info("generated image url: {}", resultUrl);

            // Step 4: 下载并保存
            String savedPath = downloadAndSaveImage(resultUrl);
            log.info("id photo saved: {}", savedPath);
            return new IdPhotoResult(savedPath);
        }
        catch (Exception e)
        {
            log.error("generate id photo error", e);
            return IdPhotoResult.error("生成过程出错: " + e.getMessage());
        }
    }

    /**
     * 照片质量校验：检测是否清晰、是否有完整面部
     */
    private String checkImageQuality(String imageUrl)
    {
        String apiKey = photoAiProperties.getAi().getApiKey();
        String baseUrl = photoAiProperties.getAi().getBaseUrl();
        String url = baseUrl + "/chat/completions";

        String qualityPrompt = "请检查这张照片是否适合制作证件照。只检查以下两点：\n"
            + "1. 照片是否清晰（不能模糊、过暗、过曝、严重噪点）\n"
            + "2. 是否能看到完整的面部（正脸，五官清晰可见，不能被遮挡、戴口罩、墨镜）\n"
            + "如果符合要求，只返回 OK 两个英文字母，不要任何其他内容。\n"
            + "如果不符合，用一句话说明原因，比如\"照片太模糊\"或\"面部被遮挡\"。";

        String imageDataUri = resolveImageUrl(imageUrl);
        if (imageDataUri == null)
        {
            return "图片加载失败";
        }

        JSONObject body = new JSONObject();
        body.put("model", "qwen-vl-plus");

        JSONArray messages = new JSONArray();
        JSONObject userMsg = new JSONObject();
        userMsg.put("role", "user");

        JSONArray content = new JSONArray();

        JSONObject imgContent = new JSONObject();
        imgContent.put("type", "image_url");
        JSONObject imgUrl = new JSONObject();
        imgUrl.put("url", imageDataUri);
        imgContent.put("image_url", imgUrl);
        content.add(imgContent);

        JSONObject textContent = new JSONObject();
        textContent.put("type", "text");
        textContent.put("text", qualityPrompt);
        content.add(textContent);

        userMsg.put("content", content);
        messages.add(userMsg);
        body.put("messages", messages);
        body.put("max_tokens", 100);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + apiKey);

        HttpEntity<String> entity = new HttpEntity<>(body.toJSONString(), headers);

        try
        {
            ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);
            JSONObject result = JSONObject.parseObject(response.getBody());
            JSONArray choices = result.getJSONArray("choices");
            if (choices != null && !choices.isEmpty())
            {
                String reply = choices.getJSONObject(0).getJSONObject("message").getString("content");
                log.info("quality check result: {}", reply);
                if ("OK".equals(reply.trim()))
                {
                    return null;
                }
                return reply;
            }
            return "质量检测请求失败";
        }
        catch (Exception e)
        {
            log.error("quality check error", e);
            return "质量检测异常";
        }
    }

    /**
     * 用 qwen-vl-plus 分析自拍，提取人物特征
     */
    private String analyzeSelfie(String imageUrl, String beauty, String suit)
    {
        String apiKey = photoAiProperties.getAi().getApiKey();
        String baseUrl = photoAiProperties.getAi().getBaseUrl();
        String url = baseUrl + "/chat/completions";

        String analysisPrompt = "请详细描述这张自拍照片中的人物特征，包括：性别、年龄段（儿童/青少年/青年/中年/老年）、脸型、发型、肤色、眼镜等。只返回描述文本，不要其他内容。";

        // 将本地 URL 转为 base64 data URI（DashScope 无法访问 localhost）
        String imageDataUri = resolveImageUrl(imageUrl);
        if (imageDataUri == null)
        {
            log.error("failed to resolve image: {}", imageUrl);
            return null;
        }

        JSONObject body = new JSONObject();
        body.put("model", "qwen-vl-plus");

        JSONArray messages = new JSONArray();
        JSONObject userMsg = new JSONObject();
        userMsg.put("role", "user");

        JSONArray content = new JSONArray();

        // 图片（使用 base64 data URI）
        JSONObject imgContent = new JSONObject();
        imgContent.put("type", "image_url");
        JSONObject imgUrl = new JSONObject();
        imgUrl.put("url", imageDataUri);
        imgContent.put("image_url", imgUrl);
        content.add(imgContent);

        // 文本
        JSONObject textContent = new JSONObject();
        textContent.put("type", "text");
        textContent.put("text", analysisPrompt);
        content.add(textContent);

        userMsg.put("content", content);
        messages.add(userMsg);
        body.put("messages", messages);
        body.put("max_tokens", 500);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + apiKey);

        HttpEntity<String> entity = new HttpEntity<>(body.toJSONString(), headers);

        try
        {
            ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);
            JSONObject result = JSONObject.parseObject(response.getBody());
            JSONArray choices = result.getJSONArray("choices");
            if (choices != null && !choices.isEmpty())
            {
                return choices.getJSONObject(0).getJSONObject("message").getString("content");
            }
            log.error("analysis failed: {}", response.getBody());
            return null;
        }
        catch (Exception e)
        {
            log.error("analyze selfie error", e);
            return null;
        }
    }

    /**
     * 构建生成提示词
     */
    private String buildGeneratePrompt(String personDesc, String background, String beauty, String suit)
    {
        StringBuilder sb = new StringBuilder();

        // 证件照格式要求
        sb.append("Professional ID photo, studio quality, ");

        // 背景色
        switch (background)
        {
            case "蓝底":
                sb.append("solid blue background (#438EDB), ");
                break;
            case "红底":
                sb.append("solid red background (#CF2533), ");
                break;
            case "白底":
            default:
                sb.append("solid white background, ");
                break;
        }

        // 人物描述
        if (personDesc != null && !personDesc.isEmpty())
        {
            sb.append(personDesc).append(", ");
        }

        // 服装
        if ("男士正装".equals(suit))
        {
            sb.append("wearing dark business suit with white shirt and tie, ");
        }
        else if ("女士正装".equals(suit))
        {
            sb.append("wearing dark business suit with white blouse, ");
        }

        // 美颜
        if ("精致".equals(beauty))
        {
            sb.append("smooth skin, natural beauty enhancement, ");
        }
        else if ("自然".equals(beauty))
        {
            sb.append("natural look, subtle skin enhancement, ");
        }

        // 证件照技术要求
        sb.append("front-facing portrait, centered composition, ");
        sb.append("head occupies 70% of frame, even studio lighting, ");
        sb.append("sharp focus, high resolution, photorealistic");

        return sb.toString();
    }

    /**
     * 调用 DashScope 图生图 API（保持面部特征）
     * 使用兼容模式 API：POST /compatible-mode/v1/images/generations
     * 模型：wanx2.1-imageedit（支持图生图）
     */
    private String generateImage(String prompt, String originalImageUrl)
    {
        String apiKey = photoAiProperties.getAi().getApiKey();

        // 获取原图（本地转 base64，公网直接用 URL）
        String imageInput = resolveImageForGeneration(originalImageUrl);
        if (imageInput == null)
        {
            log.error("failed to resolve image for generation: {}", originalImageUrl);
            return null;
        }

        // 尝试多种方案
        String result = tryCompatibleModeImageEdit(prompt, imageInput, apiKey);
        if (result != null)
        {
            return result;
        }

        // 备用方案：原生 API
        return tryNativeImageEdit(prompt, imageInput, apiKey);
    }

    /**
     * 方案1：兼容模式图生图
     */
    private String tryCompatibleModeImageEdit(String prompt, String imageInput, String apiKey)
    {
        String url = "https://dashscope.aliyuncs.com/compatible-mode/v1/images/generations";
        log.info("trying compatible-mode image edit: {}", url);

        // wanx2.1-imageedit 在兼容模式下的请求格式
        JSONObject body = new JSONObject();
        body.put("model", "wanx2.1-imageedit");
        body.put("prompt", prompt);
        body.put("n", 1);
        body.put("size", "1024*1024");

        // 添加图片输入
        JSONArray inputImages = new JSONArray();
        JSONObject imgObj = new JSONObject();
        imgObj.put("url", imageInput);
        inputImages.add(imgObj);
        body.put("image", inputImages);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + apiKey);

        HttpEntity<String> entity = new HttpEntity<>(body.toJSONString(), headers);

        log.info("compatible-mode request: {}", body.toJSONString());

        try
        {
            ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);
            log.info("compatible-mode response: status={}, body={}", response.getStatusCode(), response.getBody());

            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null)
            {
                JSONObject result = JSONObject.parseObject(response.getBody());
                JSONArray data = result.getJSONArray("data");
                if (data != null && !data.isEmpty())
                {
                    String imgUrl = data.getJSONObject(0).getString("url");
                    if (imgUrl != null)
                    {
                        log.info("compatible-mode image edit succeeded, url: {}", imgUrl);
                        return imgUrl;
                    }
                }
            }
            log.warn("compatible-mode image edit failed, trying native API");
        }
        catch (Exception e)
        {
            log.error("compatible-mode image edit exception", e);
        }
        return null;
    }

    /**
     * 方案2：原生 API（多尝试几种格式）
     */
    private String tryNativeImageEdit(String prompt, String imageInput, String apiKey)
    {
        // 尝试 2A: image-synthesis 端点，function 放在 input 内
        String result = tryNativeWithFunctionInInput(prompt, imageInput, apiKey);
        if (result != null) return result;

        // 尝试 2B: image-synthesis 端点，function 作为对象
        result = tryNativeWithFunctionObject(prompt, imageInput, apiKey);
        if (result != null) return result;

        // 尝试 2C: function 同时在 body 顶层和 input 内
        result = tryNativeWithFunctionBoth(prompt, imageInput, apiKey);
        if (result != null) return result;

        return null;
    }

    /**
     * 方案2A：function 放在 input 内
     */
    private String tryNativeWithFunctionInInput(String prompt, String imageInput, String apiKey)
    {
        String url = "https://dashscope.aliyuncs.com/api/v1/services/aigc/image2image/image-synthesis";
        log.info("trying native API (function in input): {}", url);

        JSONObject body = new JSONObject();
        body.put("model", "wanx2.1-imageedit");

        JSONObject input = new JSONObject();
        input.put("prompt", prompt);
        input.put("base_image_url", imageInput);
        input.put("function", "portrait_editing");
        body.put("input", input);

        JSONObject parameters = new JSONObject();
        parameters.put("n", 1);
        parameters.put("size", "1024*1024");
        body.put("parameters", parameters);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + apiKey);
        headers.set("X-DashScope-Async", "enable");

        HttpEntity<String> entity = new HttpEntity<>(body.toJSONString(), headers);
        log.info("native (function in input) request: {}", body.toJSONString());

        return executeNativeRequest(url, entity, apiKey);
    }

    /**
     * 方案2B：function 作为对象
     */
    private String tryNativeWithFunctionObject(String prompt, String imageInput, String apiKey)
    {
        String url = "https://dashscope.aliyuncs.com/api/v1/services/aigc/image2image/image-synthesis";
        log.info("trying native API (function as object): {}", url);

        JSONObject body = new JSONObject();
        body.put("model", "wanx2.1-imageedit");

        JSONObject input = new JSONObject();
        input.put("prompt", prompt);
        input.put("base_image_url", imageInput);
        body.put("input", input);

        JSONObject parameters = new JSONObject();
        parameters.put("n", 1);
        parameters.put("size", "1024*1024");
        body.put("parameters", parameters);

        // function 作为对象
        JSONObject functionObj = new JSONObject();
        functionObj.put("name", "portrait_editing");
        body.put("function", functionObj);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + apiKey);
        headers.set("X-DashScope-Async", "enable");

        HttpEntity<String> entity = new HttpEntity<>(body.toJSONString(), headers);
        log.info("native (function as object) request: {}", body.toJSONString());

        return executeNativeRequest(url, entity, apiKey);
    }

    /**
     * 方案2C：function 同时在 body 顶层和 input 内
     */
    private String tryNativeWithFunctionBoth(String prompt, String imageInput, String apiKey)
    {
        String url = "https://dashscope.aliyuncs.com/api/v1/services/aigc/image2image/image-synthesis";
        log.info("trying native API (function in both places): {}", url);

        JSONObject body = new JSONObject();
        body.put("model", "wanx2.1-imageedit");

        JSONObject input = new JSONObject();
        input.put("prompt", prompt);
        input.put("base_image_url", imageInput);
        input.put("function", "portrait_editing");
        body.put("input", input);

        // function 同时在 body 顶层
        body.put("function", "portrait_editing");

        JSONObject parameters = new JSONObject();
        parameters.put("n", 1);
        parameters.put("size", "1024*1024");
        body.put("parameters", parameters);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + apiKey);
        headers.set("X-DashScope-Async", "enable");

        HttpEntity<String> entity = new HttpEntity<>(body.toJSONString(), headers);
        log.info("native (function both) request: {}", body.toJSONString());

        return executeNativeRequest(url, entity, apiKey);
    }

    /**
     * 执行原生 API 请求并轮询结果
     */
    private String executeNativeRequest(String url, HttpEntity<String> entity, String apiKey)
    {
        try
        {
            ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);
            log.info("native API response: status={}, body={}", response.getStatusCode(), response.getBody());

            if (!response.getStatusCode().is2xxSuccessful() || response.getBody() == null)
            {
                log.warn("native API non-success response");
                return null;
            }

            JSONObject result = JSONObject.parseObject(response.getBody());

            // 检查错误
            String code = result.getString("code");
            if (code != null)
            {
                log.error("native API error: code={}, message={}", code, result.getString("message"));
                return null;
            }

            // 获取 task_id
            JSONObject output = result.getJSONObject("output");
            if (output != null)
            {
                String taskId = output.getString("task_id");
                if (taskId != null)
                {
                    log.info("native task created: {}, status: {}", taskId, output.getString("task_status"));
                    return pollTaskResult(taskId);
                }
            }

            log.warn("native API no task_id in response");
        }
        catch (Exception e)
        {
            log.error("native API call exception", e);
        }
        return null;
    }

    /**
     * 轮询任务结果（DashScope 原生 API）
     */
    private String pollTaskResult(String taskId)
    {
        String url = "https://dashscope.aliyuncs.com/api/v1/tasks/" + taskId;
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + photoAiProperties.getAi().getApiKey());
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        for (int i = 0; i < 60; i++) // 最多等待 2 分钟
        {
            try
            {
                Thread.sleep(2000);
                log.debug("polling task [{}] attempt {}/60", taskId, i + 1);
                ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
                JSONObject result = JSONObject.parseObject(response.getBody());

                // 检查顶层错误
                String code = result.getString("code");
                if (code != null)
                {
                    log.error("poll error: code={}, message={}", code, result.getString("message"));
                    return null;
                }

                JSONObject output = result.getJSONObject("output");
                if (output == null) continue;

                String status = output.getString("task_status");
                log.debug("task [{}] status: {}", taskId, status);

                if ("SUCCEEDED".equals(status))
                {
                    JSONArray results = output.getJSONArray("results");
                    if (results != null && !results.isEmpty())
                    {
                        String imgUrl = results.getJSONObject(0).getString("url");
                        log.info("task [{}] succeeded, image url: {}", taskId, imgUrl);
                        return imgUrl;
                    }
                    String resultUrl = output.getString("result_url");
                    if (resultUrl != null)
                    {
                        log.info("task [{}] succeeded, result_url: {}", taskId, resultUrl);
                    }
                    return resultUrl;
                }
                else if ("FAILED".equals(status))
                {
                    log.error("generation task failed: code={}, message={}",
                        output.getString("code"), output.getString("message"));
                    return null;
                }
                // PENDING/RUNNING: continue polling
            }
            catch (Exception e)
            {
                log.error("poll task error", e);
            }
        }
        log.error("generation task timeout after 60 polls");
        return null;
    }

    /**
     * 下载图片并保存到本地
     */
    private String downloadAndSaveImage(String imageUrl)
    {
        FileOutputStream fos = null;
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
                byte[] bytes = buffer.toByteArray();

                String fileName = "idphoto_" + System.currentTimeMillis() + ".png";
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
        }
        catch (Exception e)
        {
            log.error("download and save image error: {}", imageUrl, e);
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

    /**
     * 将图片 URL 转为 base64 data URI
     * 如果是本地 URL（localhost/127.0.0.1），则读取本地文件并转 base64
     * 否则直接返回原 URL
     */
    private String resolveImageUrl(String imageUrl)
    {
        if (imageUrl == null) return null;

        // 检测是否为本地 URL
        boolean isLocal = imageUrl.contains("localhost") || imageUrl.contains("127.0.0.1");

        if (!isLocal)
        {
            // 公网 URL 直接返回
            return imageUrl;
        }

        // 本地 URL：提取文件路径，读取文件转 base64
        try
        {
            // URL: http://127.0.0.1:9999/profile/upload/2026/06/17/xxx.jpg
            // 实际路径: D:/ruoyi/uploadPath/upload/2026/06/17/xxx.jpg
            // /profile 是 URL 前缀，/upload 是实际子目录
            String relativePath = imageUrl;

            // 提取 /profile/upload/ 之后的路径
            if (relativePath.contains("/profile/upload/"))
            {
                relativePath = relativePath.substring(relativePath.indexOf("/profile/upload/") + "/profile/upload/".length());
            }
            else if (relativePath.contains("/upload/"))
            {
                relativePath = relativePath.substring(relativePath.indexOf("/upload/") + "/upload/".length());
            }

            // 构建完整路径: getUploadPath() 已经包含 /upload，所以直接拼接日期/文件名
            // getUploadPath() = D:/ruoyi/uploadPath/upload
            String fullPath = RuoYiConfig.getUploadPath() + "/" + relativePath;
            File file = new File(fullPath);

            if (!file.exists())
            {
                // 尝试其他可能的路径
                String[] fallbackBasePaths = {
                    "D:/ruoyi/uploadPath/upload",
                    System.getProperty("user.dir") + "/uploadPath/upload",
                };

                for (String basePath : fallbackBasePaths)
                {
                    if (basePath == null) continue;
                    String candidatePath = basePath + "/" + relativePath;
                    File candidate = new File(candidatePath);
                    if (candidate.exists())
                    {
                        file = candidate;
                        log.info("found image at fallback path: {}", candidatePath);
                        break;
                    }
                }
            }

            if (!file.exists())
            {
                log.error("local image file not found, tried: {}, relativePath={}", fullPath, relativePath);
                return null;
            }

            log.info("loading image from: {}", file.getAbsolutePath());

            byte[] fileContent = java.nio.file.Files.readAllBytes(file.toPath());
            String base64 = Base64.getEncoder().encodeToString(fileContent);

            // 检测图片类型
            String mimeType = "image/jpeg";
            String lowerPath = file.getName().toLowerCase();
            if (lowerPath.endsWith(".png")) mimeType = "image/png";
            else if (lowerPath.endsWith(".gif")) mimeType = "image/gif";
            else if (lowerPath.endsWith(".webp")) mimeType = "image/webp";

            return "data:" + mimeType + ";base64," + base64;
        }
        catch (Exception e)
        {
            log.error("resolve image url error: {}", imageUrl, e);
            return null;
        }
    }

    /**
     * 为图生图准备图片输入
     * 本地 URL → base64 data URI
     * 公网 URL → 直接返回
     */
    private String resolveImageForGeneration(String imageUrl)
    {
        if (imageUrl == null) return null;

        boolean isLocal = imageUrl.contains("localhost") || imageUrl.contains("127.0.0.1");

        if (!isLocal)
        {
            return imageUrl;
        }

        // 本地 URL：转 base64 data URI
        String imageDataUri = resolveImageUrl(imageUrl);
        if (imageDataUri == null)
        {
            log.error("failed to resolve local image for generation: {}", imageUrl);
            return null;
        }
        return imageDataUri;
    }
}

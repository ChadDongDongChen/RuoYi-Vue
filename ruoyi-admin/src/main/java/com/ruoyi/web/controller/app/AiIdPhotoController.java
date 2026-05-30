package com.ruoyi.web.controller.app;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.ruoyi.common.annotation.Anonymous;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.config.RuoYiConfig;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.file.FileUploadUtils;
import com.ruoyi.common.utils.file.MimeTypeUtils;
import com.ruoyi.framework.config.ServerConfig;
import com.ruoyi.system.domain.AiIdphotoOrder;
import com.ruoyi.system.domain.AiIdphotoSpec;
import com.ruoyi.system.domain.vo.IdPhotoGenerateRequest;
import com.ruoyi.system.domain.vo.IdPhotoGenerateResponse;
import com.ruoyi.system.domain.vo.IdPhotoPayRequest;
import com.ruoyi.system.domain.vo.WxLoginRequest;
import com.ruoyi.system.service.IAiIdphotoOrderService;
import com.ruoyi.system.service.IAiIdphotoSpecService;
import com.ruoyi.framework.web.service.WxMiniAppLoginService;

/**
 * AI证件照 Controller (无需登录)
 *
 * @author ruoyi
 */
@RestController
@RequestMapping("/app/ai/idphoto")
public class AiIdPhotoController extends BaseController
{
    @Autowired
    private IAiIdphotoSpecService specService;

    @Autowired
    private IAiIdphotoOrderService orderService;

    @Autowired
    private ServerConfig serverConfig;

    @Autowired
    private WxMiniAppLoginService wxMiniAppLoginService;

    /**
     * 微信小程序登录（code 换 token）
     */
    @Anonymous
    @PostMapping("/wx-login")
    public AjaxResult wxLogin(@RequestBody WxLoginRequest request)
    {
        String token = wxMiniAppLoginService.loginByCode(request.getCode());
        return success(token);
    }

    /**
     * 查询可用规格列表
     */
    @Anonymous
    @GetMapping("/specs")
    public AjaxResult listSpecs()
    {
        List<AiIdphotoSpec> list = specService.selectEnabledSpecList();
        return success(list);
    }

    /**
     * 上传自拍照片
     */
    @Anonymous
    @Log(title = "AI证件照-上传", businessType = BusinessType.OTHER)
    @PostMapping("/upload")
    public AjaxResult uploadPhoto(@RequestParam("file") MultipartFile file)
    {
        try
        {
            String filePath = RuoYiConfig.getUploadPath() + "/idphoto";
            String fileName = FileUploadUtils.upload(filePath, file, MimeTypeUtils.IMAGE_EXTENSION);
            String url = serverConfig.getUrl() + fileName;

            AjaxResult ajax = AjaxResult.success();
            ajax.put("url", url);
            ajax.put("fileName", fileName);
            ajax.put("originalFilename", file.getOriginalFilename());
            return ajax;
        }
        catch (Exception e)
        {
            return AjaxResult.error("上传失败: " + e.getMessage());
        }
    }

    /**
     * 生成证件照 (创建订单 + AI处理)
     */
    @Anonymous
    @Log(title = "AI证件照-生成", businessType = BusinessType.OTHER)
    @PostMapping("/generate")
    public AjaxResult generate(@RequestBody IdPhotoGenerateRequest request)
    {
        IdPhotoGenerateResponse response = orderService.generate(request);
        return success(response);
    }

    /**
     * 支付订单
     */
    @Anonymous
    @Log(title = "AI证件照-支付", businessType = BusinessType.OTHER)
    @PostMapping("/pay")
    public AjaxResult pay(@RequestBody IdPhotoPayRequest request)
    {
        orderService.payOrder(request.getOrderNo(), request.getPayType());
        return success();
    }

    /**
     * 查询订单详情
     */
    @Anonymous
    @GetMapping("/order/{orderNo}")
    public AjaxResult getOrder(@PathVariable String orderNo)
    {
        AiIdphotoOrder order = orderService.selectAiIdphotoOrderByOrderNo(orderNo);
        return success(order);
    }

    /**
     * 查询订单列表
     */
    @Anonymous
    @GetMapping("/orders")
    public TableDataInfo listOrders(AiIdphotoOrder order)
    {
        startPage();
        List<AiIdphotoOrder> list = orderService.selectAiIdphotoOrderList(order);
        return getDataTable(list);
    }
}

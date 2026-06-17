package com.ruoyi.web.photo.controller;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.ruoyi.common.annotation.Anonymous;
import com.ruoyi.common.config.RuoYiConfig;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.file.FileUploadUtils;
import com.ruoyi.framework.config.ServerConfig;
import com.ruoyi.framework.web.service.TokenService;
import com.ruoyi.web.photo.config.PhotoAiProperties;
import com.ruoyi.web.photo.domain.PhotoOrder;
import com.ruoyi.web.photo.domain.PhotoSpec;
import com.ruoyi.web.photo.domain.PhotoUser;
import com.ruoyi.web.photo.service.DashScopeImageService;
import com.ruoyi.web.photo.service.IPhotoOrderService;
import com.ruoyi.web.photo.service.IPhotoSpecService;
import com.ruoyi.web.photo.service.IPhotoUserService;
import com.ruoyi.web.photo.util.WxMiniProgramUtil;

@RestController
@RequestMapping("/app/ai/idphoto")
public class AppPhotoAiController extends BaseController
{
    private static final Logger log = LoggerFactory.getLogger(AppPhotoAiController.class);

    @Autowired
    private IPhotoSpecService photoSpecService;

    @Autowired
    private IPhotoOrderService photoOrderService;

    @Autowired
    private IPhotoUserService photoUserService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private ServerConfig serverConfig;

    @Autowired
    private PhotoAiProperties photoAiProperties;

    @Autowired
    private DashScopeImageService dashScopeImageService;

    @Anonymous
    @GetMapping("/specs")
    public AjaxResult specs()
    {
        List<PhotoSpec> specs = photoSpecService.selectEnabledSpecs();
        return success(specs);
    }

    @Anonymous
    @PostMapping("/upload")
    public AjaxResult upload(@RequestParam("file") MultipartFile file) throws Exception
    {
        if (file.isEmpty())
        {
            return error("上传文件不能为空");
        }
        String filePath = RuoYiConfig.getUploadPath();
        String fileName = FileUploadUtils.upload(filePath, file);
        String url = serverConfig.getUrl() + fileName;
        AjaxResult ajax = success();
        ajax.put("url", url);
        return ajax;
    }

    @Anonymous
    @PostMapping("/generate")
    public AjaxResult generate(@RequestBody GenerateRequest req)
    {
        Long userId = getCurrentUserId();
        if (userId == null)
        {
            return error("请先登录");
        }

        PhotoSpec spec = photoSpecService.selectSpecById(req.getSpecId());
        if (spec == null)
        {
            return error("规格不存在");
        }

        String orderNo = generateOrderNo();

        PhotoOrder order = new PhotoOrder();
        order.setOrderNo(orderNo);
        order.setUserId(userId);
        order.setOriginalImageUrl(req.getOriginalUrl());
        order.setSpecId(spec.getId());
        order.setSpecName(spec.getSpecName());
        order.setBackground(req.getBackground());
        order.setBeauty(req.getBeauty());
        order.setSuit(req.getSuit());
        order.setPayAmount(spec.getPrice());
        order.setStatus("pending");

        String resultUrl = callAiGenerate(order, spec);
        order.setResultImageUrl(resultUrl);
        order.setStatus(resultUrl != null ? "pending" : "failed");

        photoOrderService.createOrder(order);

        if (resultUrl == null)
        {
            return error("AI 生成失败，请重试");
        }

        AjaxResult data = success();
        data.put("orderNo", orderNo);
        data.put("resultUrl", resultUrl);
        data.put("specName", spec.getSpecName());
        data.put("price", spec.getPrice().toString());
        return data;
    }

    @Anonymous
    @PostMapping("/pay")
    public AjaxResult pay(@RequestBody PayRequest req)
    {
        Long userId = getCurrentUserId();
        if (userId == null)
        {
            return error("请先登录");
        }

        PhotoOrder order = photoOrderService.selectOrderByOrderNo(req.getOrderNo());
        if (order == null)
        {
            return error("订单不存在");
        }
        if (!order.getUserId().equals(userId))
        {
            return error("无权操作此订单");
        }

        photoOrderService.payOrder(order.getOrderNo());

        return success("支付成功");
    }

    @Anonymous
    @GetMapping("/order/{orderNo}")
    public AjaxResult getOrder(@PathVariable String orderNo)
    {
        Long userId = getCurrentUserId();
        if (userId == null)
        {
            return error("请先登录");
        }

        PhotoOrder order = photoOrderService.selectOrderByOrderNo(orderNo);
        if (order == null || !order.getUserId().equals(userId))
        {
            return error("订单不存在");
        }
        return success(order);
    }

    @Anonymous
    @GetMapping("/orders")
    public AjaxResult orders()
    {
        Long userId = getCurrentUserId();
        if (userId == null)
        {
            return error("请先登录");
        }

        List<PhotoOrder> orders = photoOrderService.selectOrdersByUserId(userId);
        return success(orders);
    }

    @Anonymous
    @PostMapping("/wx-login")
    public AjaxResult wxLogin(@RequestBody WxLoginRequest req)
    {
        if (!photoAiProperties.isWxConfigured())
        {
            return error("微信小程序未配置");
        }

        Map<String, String> session = WxMiniProgramUtil.code2Session(
            photoAiProperties.getWx().getAppId(),
            photoAiProperties.getWx().getAppSecret(),
            req.getCode()
        );

        if (session == null)
        {
            return error("微信登录失败");
        }

        String openid = session.get("openid");
        String unionId = session.get("unionid");

        PhotoUser user = photoUserService.selectByOpenid(openid);
        if (user == null)
        {
            user = new PhotoUser();
            user.setOpenid(openid);
            user.setUnionId(unionId);
            user.setNickname("微信用户");
            user.setStatus(1);
            photoUserService.createUser(user);
        }

        photoUserService.updateLastLoginTime(user.getId());

        SysUser sysUser = new SysUser();
        sysUser.setUserId(user.getId());
        sysUser.setUserName("wx_" + openid);
        sysUser.setNickName(user.getNickname());
        LoginUser loginUser = new LoginUser(user.getId(), null, sysUser, new java.util.HashSet<>());
        String token = tokenService.createToken(loginUser);

        return AjaxResult.success("操作成功", token);
    }

    private Long getCurrentUserId()
    {
        try
        {
            LoginUser loginUser = SecurityUtils.getLoginUser();
            return loginUser != null ? loginUser.getUserId() : null;
        }
        catch (Exception e)
        {
            return null;
        }
    }

    private String generateOrderNo()
    {
        return "PHOTO" + System.currentTimeMillis() + UUID.randomUUID().toString().substring(0, 6).toUpperCase();
    }

    private String callAiGenerate(PhotoOrder order, PhotoSpec spec)
    {
        if (!photoAiProperties.isAiConfigured())
        {
            log.warn("AI not configured, using placeholder");
            return "/profile/upload/ai-placeholder-" + order.getOrderNo() + ".jpg";
        }

        int widthPx = spec.getWidthPx() != null ? spec.getWidthPx() : 295;
        int heightPx = spec.getHeightPx() != null ? spec.getHeightPx() : 413;

        log.info("calling DashScope for order: {}, spec: {} ({}x{}), bg: {}, beauty: {}, suit: {}",
            order.getOrderNo(), order.getSpecName(), widthPx, heightPx,
            order.getBackground(), order.getBeauty(), order.getSuit());

        return dashScopeImageService.generateIdPhoto(
            order.getOriginalImageUrl(),
            order.getBackground(),
            widthPx,
            heightPx
        );
    }

    public static class GenerateRequest
    {
        private String originalUrl;
        private Long specId;
        private String background;
        private String beauty;
        private String suit;

        public String getOriginalUrl() { return originalUrl; }
        public void setOriginalUrl(String originalUrl) { this.originalUrl = originalUrl; }

        public Long getSpecId() { return specId; }
        public void setSpecId(Long specId) { this.specId = specId; }

        public String getBackground() { return background; }
        public void setBackground(String background) { this.background = background; }

        public String getBeauty() { return beauty; }
        public void setBeauty(String beauty) { this.beauty = beauty; }

        public String getSuit() { return suit; }
        public void setSuit(String suit) { this.suit = suit; }
    }

    public static class PayRequest
    {
        private String orderNo;
        private String payType;

        public String getOrderNo() { return orderNo; }
        public void setOrderNo(String orderNo) { this.orderNo = orderNo; }

        public String getPayType() { return payType; }
        public void setPayType(String payType) { this.payType = payType; }
    }

    public static class WxLoginRequest
    {
        private String code;

        public String getCode() { return code; }
        public void setCode(String code) { this.code = code; }
    }
}

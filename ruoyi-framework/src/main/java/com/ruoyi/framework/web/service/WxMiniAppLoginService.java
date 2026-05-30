package com.ruoyi.framework.web.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.alibaba.fastjson2.JSONObject;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.framework.web.service.SysPermissionService;
import com.ruoyi.system.service.ISysUserService;

/**
 * 微信小程序登录处理
 *
 * @author ruoyi
 */
@Service
public class WxMiniAppLoginService
{
    private static final Logger log = LoggerFactory.getLogger(WxMiniAppLoginService.class);

    private static final String CODE2SESSION_URL = "https://api.weixin.qq.com/sns/jscode2session?appid={appid}&secret={secret}&js_code={code}&grant_type=authorization_code";

    @Value("${wechat.miniapp.appid:}")
    private String appid;

    @Value("${wechat.miniapp.secret:}")
    private String secret;

    @Autowired
    private ISysUserService userService;

    @Autowired
    private SysPermissionService permissionService;

    @Autowired
    private TokenService tokenService;

    /**
     * 通过微信 code 登录
     * @param code wx.login 返回的 code
     * @return JWT token
     */
    public String loginByCode(String code)
    {
        if (StringUtils.isEmpty(appid) || StringUtils.isEmpty(secret))
        {
            throw new ServiceException("微信小程序未配置，请在 application.yml 中配置 wechat.miniapp.appid 和 secret");
        }

        // 1. 调用微信 code2Session 获取 openid
        String openid = code2Session(code);
        if (StringUtils.isEmpty(openid))
        {
            throw new ServiceException("微信登录失败，无法获取用户标识");
        }

        String username = "wx_" + openid;

        // 2. 查找或创建用户
        SysUser user = userService.selectUserByUserName(username);
        if (user == null)
        {
            user = new SysUser();
            user.setUserName(username);
            user.setNickName("微信用户");
            user.setPassword(SecurityUtils.encryptPassword("wx_default"));
            user.setStatus("0");
            userService.insertUser(user);
            // 重新查询获取完整信息
            user = userService.selectUserByUserName(username);
            log.info("微信小程序自动注册用户: {}", username);
        }

        // 3. 生成 JWT token
        LoginUser loginUser = new LoginUser(user, permissionService.getMenuPermission(user));
        return tokenService.createToken(loginUser);
    }

    private String code2Session(String code)
    {
        try
        {
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> response = restTemplate.getForEntity(CODE2SESSION_URL, String.class,
                    appid, secret, code);
            String body = response.getBody();
            log.debug("微信 code2Session 响应: {}", body);

            JSONObject json = JSONObject.parseObject(body);
            String errcode = json.getString("errcode");
            if (errcode != null && !"0".equals(errcode))
            {
                log.error("微信 code2Session 错误: code={}, response={}", code, body);
                throw new ServiceException("微信登录失败: " + json.getString("errmsg"));
            }
            return json.getString("openid");
        }
        catch (ServiceException e)
        {
            throw e;
        }
        catch (Exception e)
        {
            log.error("调用微信 code2Session 异常", e);
            throw new ServiceException("微信登录服务异常");
        }
    }
}

package com.ruoyi.web.kf.security;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import com.alibaba.fastjson2.JSON;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.web.kf.domain.KfOpenApiKey;
import com.ruoyi.web.kf.mapper.KfOpenApiKeyMapper;

/**
 * 开放 API：校验 Bearer 或 X-Api-Key（值为库中 api_secret）
 */
@Component
public class OpenApiKeyAuthFilter extends OncePerRequestFilter
{
    @Autowired
    private KfOpenApiKeyMapper kfOpenApiKeyMapper;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request)
    {
        String uri = request.getRequestURI();
        return uri == null || !uri.startsWith("/open/api/v1/");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException
    {
        String token = resolveToken(request);
        if (StringUtils.isBlank(token))
        {
            writeUnauthorized(response, "缺少 API 凭证");
            return;
        }
        KfOpenApiKey row = kfOpenApiKeyMapper.selectByApiSecret(token.trim());
        if (row == null)
        {
            writeUnauthorized(response, "无效的 API 凭证");
            return;
        }
        filterChain.doFilter(request, response);
    }

    private static String resolveToken(HttpServletRequest request)
    {
        String auth = request.getHeader("Authorization");
        if (auth != null && auth.regionMatches(true, 0, "Bearer ", 0, 7))
        {
            return auth.substring(7).trim();
        }
        return request.getHeader("X-Api-Key");
    }

    private static void writeUnauthorized(HttpServletResponse response, String msg) throws IOException
    {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setCharacterEncoding("UTF-8");
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(JSON.toJSONString(R.fail(401, msg)));
    }
}

package com.ruoyi.web.kf.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 微信回调 XML 取 CDATA 简单解析（避免额外依赖）
 */
public final class WecomKfXml
{
    private static String extract(String xml, String tag)
    {
        Pattern p = Pattern.compile("<" + tag + "><!\\[CDATA\\[(.*?)\\]\\]></" + tag + ">", Pattern.DOTALL);
        Matcher m = p.matcher(xml);
        if (m.find())
        {
            return m.group(1).trim();
        }
        p = Pattern.compile("<" + tag + ">([^<]*)</" + tag + ">", Pattern.DOTALL);
        m = p.matcher(xml);
        return m.find() ? m.group(1).trim() : "";
    }

    public static String getEvent(String decryptedXml)
    {
        return extract(decryptedXml, "Event");
    }

    public static String getToken(String decryptedXml)
    {
        return extract(decryptedXml, "Token");
    }

    public static String getOpenKfId(String decryptedXml)
    {
        return extract(decryptedXml, "OpenKfId");
    }
}

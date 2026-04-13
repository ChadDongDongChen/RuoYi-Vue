package com.ruoyi.web.kf.controller;

import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Anonymous;
import com.ruoyi.common.wecom.aes.AesException;
import com.ruoyi.common.wecom.aes.WXBizMsgCrypt;
import com.ruoyi.web.kf.config.WecomKfProperties;
import com.ruoyi.web.kf.service.KfWecomInboundOrchestrator;
import com.ruoyi.web.kf.util.WecomKfXml;

/**
 * 微信客服回调（企业微信服务器推送）
 */
@Anonymous
@RestController
@RequestMapping("/open/wecom/kf")
public class KfWecomCallbackController
{
    private static final Logger log = LoggerFactory.getLogger(KfWecomCallbackController.class);

    @Autowired
    private WecomKfProperties wecomKfProperties;

    @Autowired
    private KfWecomInboundOrchestrator kfWecomInboundOrchestrator;

    @GetMapping(value = "/callback", produces = MediaType.TEXT_PLAIN_VALUE)
    public String verifyUrl(@RequestParam("msg_signature") String msgSignature,
            @RequestParam("timestamp") String timestamp,
            @RequestParam("nonce") String nonce,
            @RequestParam("echostr") String echoStr)
    {
        if (!wecomKfProperties.isEnabled() || !wecomKfProperties.isConfigured())
        {
            log.warn("wecom kf callback verify skipped: not configured");
            return "";
        }
        try
        {
            WXBizMsgCrypt pc = new WXBizMsgCrypt(wecomKfProperties.getToken(), wecomKfProperties.getEncodingAesKey(),
                    wecomKfProperties.getCorpId());
            return pc.verifyURL(msgSignature, timestamp, nonce, echoStr);
        }
        catch (AesException e)
        {
            log.error("wecom kf url verify failed", e);
            return "";
        }
    }

    @PostMapping(value = "/callback", produces = MediaType.APPLICATION_XML_VALUE)
    public String receive(@RequestParam("msg_signature") String msgSignature,
            @RequestParam("timestamp") String timestamp,
            @RequestParam("nonce") String nonce,
            @RequestBody String body)
    {
        if (!wecomKfProperties.isEnabled() || !wecomKfProperties.isConfigured())
        {
            return "success";
        }
        try
        {
            WXBizMsgCrypt pc = new WXBizMsgCrypt(wecomKfProperties.getToken(), wecomKfProperties.getEncodingAesKey(),
                    wecomKfProperties.getCorpId());
            String xml = pc.decryptMsg(msgSignature, timestamp, nonce, body);
            String event = WecomKfXml.getEvent(xml);
            if ("kf_msg_or_event".equals(event))
            {
                String token = WecomKfXml.getToken(xml);
                String openKfid = WecomKfXml.getOpenKfId(xml);
                kfWecomInboundOrchestrator.handleKfMsgOrEvent(token, openKfid);
            }
            String ts = String.valueOf(System.currentTimeMillis() / 1000);
            String n = UUID.randomUUID().toString().replace("-", "").substring(0, 16);
            return pc.encryptMsg("success", ts, n);
        }
        catch (AesException e)
        {
            log.error("wecom kf decrypt failed", e);
            return "success";
        }
        catch (Exception e)
        {
            log.error("wecom kf callback error", e);
            return "success";
        }
    }
}

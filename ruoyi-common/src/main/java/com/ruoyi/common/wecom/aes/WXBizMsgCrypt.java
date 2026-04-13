package com.ruoyi.common.wecom.aes;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * 企业微信消息加解密（与公众平台算法一致）。参考腾讯官方示例改写。
 */
public class WXBizMsgCrypt
{
    private final String token;
    private final String encodingAesKey;
    private final String receiveId;

    public WXBizMsgCrypt(String token, String encodingAesKey, String receiveId) throws AesException
    {
        if (encodingAesKey == null || encodingAesKey.length() != 43)
        {
            throw new AesException(AesException.IllegalAesKey);
        }
        this.token = token;
        this.encodingAesKey = encodingAesKey;
        this.receiveId = receiveId;
    }

    public String verifyURL(String msgSignature, String timeStamp, String nonce, String echoStr) throws AesException
    {
        String signature = SHA1.getSHA1(token, timeStamp, nonce, echoStr);
        if (!signature.equals(msgSignature))
        {
            throw new AesException(AesException.ValidateSignatureError);
        }
        return decrypt(echoStr);
    }

    public String decryptMsg(String msgSignature, String timeStamp, String nonce, String postData) throws AesException
    {
        Object[] extract = XMLParse.extract(postData);
        String encrypt = (String) extract[1];
        String signature = SHA1.getSHA1(token, timeStamp, nonce, encrypt);
        if (!signature.equals(msgSignature))
        {
            throw new AesException(AesException.ValidateSignatureError);
        }
        return decrypt(encrypt);
    }

    private String decrypt(String text) throws AesException
    {
        byte[] aesKey = Base64.getDecoder().decode(encodingAesKey + "=");
        byte[] original;
        try
        {
            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
            SecretKeySpec keySpec = new SecretKeySpec(aesKey, "AES");
            IvParameterSpec iv = new IvParameterSpec(ArraysCopy(aesKey, 16));
            cipher.init(Cipher.DECRYPT_MODE, keySpec, iv);
            byte[] encrypted = Base64.getDecoder().decode(text);
            original = cipher.doFinal(encrypted);
        }
        catch (Exception e)
        {
            throw new AesException(AesException.DecryptAESError);
        }
        try
        {
            byte[] bytes = PKCS7Encoder.decode(original);
            ByteBuffer buffer = ByteBuffer.wrap(bytes);
            byte[] random = new byte[16];
            buffer.get(random);
            byte[] networkOrder = new byte[4];
            buffer.get(networkOrder);
            int xmlLength = networkOrder[3] & 0xFF | (networkOrder[2] & 0xFF) << 8
                    | (networkOrder[1] & 0xFF) << 16 | (networkOrder[0] & 0xFF) << 24;
            if (xmlLength < 0 || xmlLength > buffer.remaining())
            {
                throw new AesException(AesException.IllegalBuffer);
            }
            byte[] xmlContent = new byte[xmlLength];
            buffer.get(xmlContent);
            byte[] corpidBytes = new byte[buffer.remaining()];
            buffer.get(corpidBytes);
            String fromCorpid = new String(corpidBytes, StandardCharsets.UTF_8);
            if (!fromCorpid.equals(receiveId))
            {
                throw new AesException(AesException.ValidateCorpidError);
            }
            return new String(xmlContent, StandardCharsets.UTF_8);
        }
        catch (AesException e)
        {
            throw e;
        }
        catch (Exception e)
        {
            throw new AesException(AesException.IllegalBuffer);
        }
    }

    private static byte[] ArraysCopy(byte[] src, int len)
    {
        return java.util.Arrays.copyOfRange(src, 0, len);
    }

    public String encryptMsg(String replyMsg, String timeStamp, String nonce) throws AesException
    {
        String encrypt = encrypt(replyMsg);
        String signature = SHA1.getSHA1(token, timeStamp, nonce, encrypt);
        return XMLParse.generate(encrypt, signature, timeStamp, nonce);
    }

    private String encrypt(String text) throws AesException
    {
        byte[] random = new byte[16];
        new SecureRandom().nextBytes(random);
        byte[] textBytes = text.getBytes(StandardCharsets.UTF_8);
        byte[] corpidBytes = receiveId.getBytes(StandardCharsets.UTF_8);
        ByteBuffer buffer = ByteBuffer.allocate(16 + 4 + textBytes.length + corpidBytes.length);
        buffer.put(random);
        buffer.putInt(textBytes.length);
        buffer.put(textBytes);
        buffer.put(corpidBytes);
        byte[] pad = PKCS7Encoder.encode(buffer.array());
        byte[] aesKey = Base64.getDecoder().decode(encodingAesKey + "=");
        try
        {
            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
            SecretKeySpec keySpec = new SecretKeySpec(aesKey, "AES");
            IvParameterSpec iv = new IvParameterSpec(ArraysCopy(aesKey, 16));
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, iv);
            return Base64.getEncoder().encodeToString(cipher.doFinal(pad));
        }
        catch (Exception e)
        {
            throw new AesException(AesException.EncryptAESError);
        }
    }

}

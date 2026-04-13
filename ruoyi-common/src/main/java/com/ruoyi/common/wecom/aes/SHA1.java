package com.ruoyi.common.wecom.aes;

import java.security.MessageDigest;
import java.util.Arrays;

class SHA1
{
    static String getSHA1(String token, String timestamp, String nonce, String encrypt)
    {
        try
        {
            String[] array = new String[] { token, timestamp, nonce, encrypt };
            Arrays.sort(array);
            StringBuilder sb = new StringBuilder();
            for (String s : array)
            {
                sb.append(s);
            }
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            md.update(sb.toString().getBytes());
            byte[] digest = md.digest();
            StringBuilder hex = new StringBuilder();
            for (byte b : digest)
            {
                String shaHex = Integer.toHexString(b & 0xFF);
                if (shaHex.length() < 2)
                {
                    hex.append(0);
                }
                hex.append(shaHex);
            }
            return hex.toString();
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }
}

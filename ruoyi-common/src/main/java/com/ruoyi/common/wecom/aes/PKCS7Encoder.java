package com.ruoyi.common.wecom.aes;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

class PKCS7Encoder
{
    private static final int BLOCK_SIZE = 32;

    static byte[] encode(byte[] src)
    {
        int count = src.length;
        int amountToPad = BLOCK_SIZE - (count % BLOCK_SIZE);
        if (amountToPad == 0)
        {
            amountToPad = BLOCK_SIZE;
        }
        byte pad = (byte) (amountToPad & 0xFF);
        byte[] pads = new byte[amountToPad];
        Arrays.fill(pads, pad);
        ByteGroup bg = new ByteGroup();
        bg.addBytes(src);
        bg.addBytes(pads);
        return bg.toBytes();
    }

    static byte[] decode(byte[] decrypted)
    {
        int pad = decrypted[decrypted.length - 1] & 0xFF;
        if (pad < 1 || pad > BLOCK_SIZE)
        {
            pad = 0;
        }
        return Arrays.copyOfRange(decrypted, 0, decrypted.length - pad);
    }

    static int encodeLength(int count)
    {
        int amountToPad = BLOCK_SIZE - (count % BLOCK_SIZE);
        return amountToPad == 0 ? BLOCK_SIZE : amountToPad;
    }

    static byte[] decodeUtf8(String text)
    {
        return text.getBytes(StandardCharsets.UTF_8);
    }
}

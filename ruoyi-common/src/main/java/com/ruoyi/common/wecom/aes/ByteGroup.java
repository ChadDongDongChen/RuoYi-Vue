package com.ruoyi.common.wecom.aes;

import java.util.ArrayList;

class ByteGroup
{
    private final ArrayList<Byte> bytes = new ArrayList<>();

    byte[] toBytes()
    {
        byte[] out = new byte[bytes.size()];
        for (int i = 0; i < bytes.size(); i++)
        {
            out[i] = bytes.get(i);
        }
        return out;
    }

    void addBytes(byte[] bs)
    {
        for (byte b : bs)
        {
            bytes.add(b);
        }
    }

    int size()
    {
        return bytes.size();
    }
}

package com.ruoyi.web.kf.mapper;

import java.util.List;
import com.ruoyi.web.kf.domain.KfMessage;

public interface KfMessageMapper
{
    KfMessage selectKfMessageByMsgid(String msgid);

    List<KfMessage> selectKfMessageList(KfMessage kfMessage);

    int insertKfMessage(KfMessage kfMessage);
}

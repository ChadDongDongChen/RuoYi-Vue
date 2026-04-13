package com.ruoyi.web.kf.mapper;

import org.apache.ibatis.annotations.Param;
import com.ruoyi.web.kf.domain.KfSyncCursor;

public interface KfSyncCursorMapper
{
    KfSyncCursor selectByOpenKfid(@Param("openKfid") String openKfid);

    int upsertCursor(@Param("openKfid") String openKfid, @Param("nextCursor") String nextCursor);
}

package com.ruoyi.web.kf.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.ruoyi.web.kf.domain.KfCustomer;

public interface KfCustomerMapper
{
    KfCustomer selectKfCustomerByCustomerId(Long customerId);

    KfCustomer selectByExternalAndKf(@Param("externalUserid") String externalUserid, @Param("openKfid") String openKfid);

    List<KfCustomer> selectKfCustomerList(KfCustomer kfCustomer);

    int insertKfCustomer(KfCustomer kfCustomer);

    int updateKfCustomer(KfCustomer kfCustomer);

    int upsertActive(@Param("externalUserid") String externalUserid, @Param("openKfid") String openKfid,
            @Param("nickname") String nickname);
}

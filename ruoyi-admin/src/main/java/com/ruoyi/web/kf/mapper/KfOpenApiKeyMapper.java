package com.ruoyi.web.kf.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.ruoyi.web.kf.domain.KfOpenApiKey;

public interface KfOpenApiKeyMapper
{
    KfOpenApiKey selectKfOpenApiKeyByKeyId(Long keyId);

    KfOpenApiKey selectByApiKey(@Param("apiKey") String apiKey);

    KfOpenApiKey selectByApiSecret(@Param("apiSecret") String apiSecret);

    List<KfOpenApiKey> selectKfOpenApiKeyList(KfOpenApiKey query);

    int insertKfOpenApiKey(KfOpenApiKey row);

    int updateKfOpenApiKey(KfOpenApiKey row);

    int deleteKfOpenApiKeyByKeyId(Long keyId);
}

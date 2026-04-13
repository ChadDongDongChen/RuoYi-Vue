package com.ruoyi.web.kf.mapper;

import java.util.List;
import com.ruoyi.web.kf.domain.KfProduct;

public interface KfProductMapper
{
    KfProduct selectKfProductByProductId(Long productId);

    List<KfProduct> selectKfProductList(KfProduct kfProduct);

    List<KfProduct> selectKfProductMatchKeywords(String keyword);

    int insertKfProduct(KfProduct kfProduct);

    int updateKfProduct(KfProduct kfProduct);

    int deleteKfProductByProductId(Long productId);

    int deleteKfProductByProductIds(Long[] productIds);
}

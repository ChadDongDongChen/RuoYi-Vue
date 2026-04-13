package com.ruoyi.web.kf.service;

import java.util.List;
import com.ruoyi.web.kf.domain.KfProduct;

public interface IKfProductService
{
    KfProduct selectKfProductByProductId(Long productId);

    List<KfProduct> selectKfProductList(KfProduct kfProduct);

    int insertKfProduct(KfProduct kfProduct);

    int updateKfProduct(KfProduct kfProduct);

    int deleteKfProductByProductIds(Long[] productIds);

    int deleteKfProductByProductId(Long productId);
}

package com.ruoyi.web.kf.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.web.kf.domain.KfProduct;
import com.ruoyi.web.kf.mapper.KfProductMapper;
import com.ruoyi.web.kf.service.IKfProductService;

@Service
public class KfProductServiceImpl implements IKfProductService
{
    @Autowired
    private KfProductMapper kfProductMapper;

    @Override
    public KfProduct selectKfProductByProductId(Long productId)
    {
        return kfProductMapper.selectKfProductByProductId(productId);
    }

    @Override
    public List<KfProduct> selectKfProductList(KfProduct kfProduct)
    {
        return kfProductMapper.selectKfProductList(kfProduct);
    }

    @Override
    public int insertKfProduct(KfProduct kfProduct)
    {
        return kfProductMapper.insertKfProduct(kfProduct);
    }

    @Override
    public int updateKfProduct(KfProduct kfProduct)
    {
        return kfProductMapper.updateKfProduct(kfProduct);
    }

    @Override
    public int deleteKfProductByProductIds(Long[] productIds)
    {
        return kfProductMapper.deleteKfProductByProductIds(productIds);
    }

    @Override
    public int deleteKfProductByProductId(Long productId)
    {
        return kfProductMapper.deleteKfProductByProductId(productId);
    }
}

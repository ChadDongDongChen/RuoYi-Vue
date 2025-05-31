package com.ruoyi.web.creb.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.web.creb.mapper.CrabPoolMapper;
import com.ruoyi.web.creb.domain.CrabPool;
import com.ruoyi.web.creb.service.ICrabPoolService;

/**
 * 螃蟹养殖池信息Service业务层处理
 * 
 * @author chendong
 * @date 2025-05-31
 */
@Service
public class CrabPoolServiceImpl implements ICrabPoolService 
{
    @Autowired
    private CrabPoolMapper crabPoolMapper;

    /**
     * 查询螃蟹养殖池信息
     * 
     * @param poolId 螃蟹养殖池信息主键
     * @return 螃蟹养殖池信息
     */
    @Override
    public CrabPool selectCrabPoolByPoolId(Long poolId)
    {
        return crabPoolMapper.selectCrabPoolByPoolId(poolId);
    }

    /**
     * 查询螃蟹养殖池信息列表
     * 
     * @param crabPool 螃蟹养殖池信息
     * @return 螃蟹养殖池信息
     */
    @Override
    public List<CrabPool> selectCrabPoolList(CrabPool crabPool)
    {
        return crabPoolMapper.selectCrabPoolList(crabPool);
    }

    /**
     * 新增螃蟹养殖池信息
     * 
     * @param crabPool 螃蟹养殖池信息
     * @return 结果
     */
    @Override
    public int insertCrabPool(CrabPool crabPool)
    {
        crabPool.setCreateTime(DateUtils.getNowDate());
        return crabPoolMapper.insertCrabPool(crabPool);
    }

    /**
     * 修改螃蟹养殖池信息
     * 
     * @param crabPool 螃蟹养殖池信息
     * @return 结果
     */
    @Override
    public int updateCrabPool(CrabPool crabPool)
    {
        crabPool.setUpdateTime(DateUtils.getNowDate());
        return crabPoolMapper.updateCrabPool(crabPool);
    }

    /**
     * 批量删除螃蟹养殖池信息
     * 
     * @param poolIds 需要删除的螃蟹养殖池信息主键
     * @return 结果
     */
    @Override
    public int deleteCrabPoolByPoolIds(Long[] poolIds)
    {
        return crabPoolMapper.deleteCrabPoolByPoolIds(poolIds);
    }

    /**
     * 删除螃蟹养殖池信息信息
     * 
     * @param poolId 螃蟹养殖池信息主键
     * @return 结果
     */
    @Override
    public int deleteCrabPoolByPoolId(Long poolId)
    {
        return crabPoolMapper.deleteCrabPoolByPoolId(poolId);
    }
}

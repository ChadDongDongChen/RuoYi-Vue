package com.ruoyi.web.creb.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.web.creb.mapper.CrabBatchMapper;
import com.ruoyi.web.creb.domain.CrabBatch;
import com.ruoyi.web.creb.service.ICrabBatchService;

/**
 * 螃蟹批次信息Service业务层处理
 * 
 * @author chendong
 * @date 2025-05-31
 */
@Service
public class CrabBatchServiceImpl implements ICrabBatchService 
{
    @Autowired
    private CrabBatchMapper crabBatchMapper;

    /**
     * 查询螃蟹批次信息
     * 
     * @param batchId 螃蟹批次信息主键
     * @return 螃蟹批次信息
     */
    @Override
    public CrabBatch selectCrabBatchByBatchId(Long batchId)
    {
        return crabBatchMapper.selectCrabBatchByBatchId(batchId);
    }

    /**
     * 查询螃蟹批次信息列表
     * 
     * @param crabBatch 螃蟹批次信息
     * @return 螃蟹批次信息
     */
    @Override
    public List<CrabBatch> selectCrabBatchList(CrabBatch crabBatch)
    {
        return crabBatchMapper.selectCrabBatchList(crabBatch);
    }

    /**
     * 新增螃蟹批次信息
     * 
     * @param crabBatch 螃蟹批次信息
     * @return 结果
     */
    @Override
    public int insertCrabBatch(CrabBatch crabBatch)
    {
        crabBatch.setCreateTime(DateUtils.getNowDate());
        return crabBatchMapper.insertCrabBatch(crabBatch);
    }

    /**
     * 修改螃蟹批次信息
     * 
     * @param crabBatch 螃蟹批次信息
     * @return 结果
     */
    @Override
    public int updateCrabBatch(CrabBatch crabBatch)
    {
        crabBatch.setUpdateTime(DateUtils.getNowDate());
        return crabBatchMapper.updateCrabBatch(crabBatch);
    }

    /**
     * 批量删除螃蟹批次信息
     * 
     * @param batchIds 需要删除的螃蟹批次信息主键
     * @return 结果
     */
    @Override
    public int deleteCrabBatchByBatchIds(Long[] batchIds)
    {
        return crabBatchMapper.deleteCrabBatchByBatchIds(batchIds);
    }

    /**
     * 删除螃蟹批次信息信息
     * 
     * @param batchId 螃蟹批次信息主键
     * @return 结果
     */
    @Override
    public int deleteCrabBatchByBatchId(Long batchId)
    {
        return crabBatchMapper.deleteCrabBatchByBatchId(batchId);
    }
}

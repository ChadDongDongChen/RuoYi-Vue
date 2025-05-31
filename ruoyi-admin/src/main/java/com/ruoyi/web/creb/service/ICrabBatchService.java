package com.ruoyi.web.creb.service;

import java.util.List;
import com.ruoyi.web.creb.domain.CrabBatch;

/**
 * 螃蟹批次信息Service接口
 * 
 * @author chendong
 * @date 2025-05-31
 */
public interface ICrabBatchService 
{
    /**
     * 查询螃蟹批次信息
     * 
     * @param batchId 螃蟹批次信息主键
     * @return 螃蟹批次信息
     */
    public CrabBatch selectCrabBatchByBatchId(Long batchId);

    /**
     * 查询螃蟹批次信息列表
     * 
     * @param crabBatch 螃蟹批次信息
     * @return 螃蟹批次信息集合
     */
    public List<CrabBatch> selectCrabBatchList(CrabBatch crabBatch);

    /**
     * 新增螃蟹批次信息
     * 
     * @param crabBatch 螃蟹批次信息
     * @return 结果
     */
    public int insertCrabBatch(CrabBatch crabBatch);

    /**
     * 修改螃蟹批次信息
     * 
     * @param crabBatch 螃蟹批次信息
     * @return 结果
     */
    public int updateCrabBatch(CrabBatch crabBatch);

    /**
     * 批量删除螃蟹批次信息
     * 
     * @param batchIds 需要删除的螃蟹批次信息主键集合
     * @return 结果
     */
    public int deleteCrabBatchByBatchIds(Long[] batchIds);

    /**
     * 删除螃蟹批次信息信息
     * 
     * @param batchId 螃蟹批次信息主键
     * @return 结果
     */
    public int deleteCrabBatchByBatchId(Long batchId);
}

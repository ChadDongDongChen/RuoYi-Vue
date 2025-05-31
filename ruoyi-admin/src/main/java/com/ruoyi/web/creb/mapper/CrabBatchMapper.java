package com.ruoyi.web.creb.mapper;

import java.util.List;
import com.ruoyi.web.creb.domain.CrabBatch;

/**
 * 螃蟹批次信息Mapper接口
 * 
 * @author chendong
 * @date 2025-05-31
 */
public interface CrabBatchMapper 
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
     * 删除螃蟹批次信息
     * 
     * @param batchId 螃蟹批次信息主键
     * @return 结果
     */
    public int deleteCrabBatchByBatchId(Long batchId);

    /**
     * 批量删除螃蟹批次信息
     * 
     * @param batchIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteCrabBatchByBatchIds(Long[] batchIds);
}

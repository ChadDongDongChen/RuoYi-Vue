package com.ruoyi.web.creb.mapper;

import java.util.List;
import com.ruoyi.web.creb.domain.CrabHarvest;

/**
 * 收获记录Mapper接口
 * 
 * @author chendong
 * @date 2025-05-31
 */
public interface CrabHarvestMapper 
{
    /**
     * 查询收获记录
     * 
     * @param recordId 收获记录主键
     * @return 收获记录
     */
    public CrabHarvest selectCrabHarvestByRecordId(Long recordId);

    /**
     * 查询收获记录列表
     * 
     * @param crabHarvest 收获记录
     * @return 收获记录集合
     */
    public List<CrabHarvest> selectCrabHarvestList(CrabHarvest crabHarvest);

    /**
     * 新增收获记录
     * 
     * @param crabHarvest 收获记录
     * @return 结果
     */
    public int insertCrabHarvest(CrabHarvest crabHarvest);

    /**
     * 修改收获记录
     * 
     * @param crabHarvest 收获记录
     * @return 结果
     */
    public int updateCrabHarvest(CrabHarvest crabHarvest);

    /**
     * 删除收获记录
     * 
     * @param recordId 收获记录主键
     * @return 结果
     */
    public int deleteCrabHarvestByRecordId(Long recordId);

    /**
     * 批量删除收获记录
     * 
     * @param recordIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteCrabHarvestByRecordIds(Long[] recordIds);
}

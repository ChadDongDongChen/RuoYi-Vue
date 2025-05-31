package com.ruoyi.web.creb.service;

import java.util.List;
import com.ruoyi.web.creb.domain.CrabGrowth;

/**
 * 螃蟹生长记录Service接口
 * 
 * @author chendong
 * @date 2025-05-31
 */
public interface ICrabGrowthService 
{
    /**
     * 查询螃蟹生长记录
     * 
     * @param recordId 螃蟹生长记录主键
     * @return 螃蟹生长记录
     */
    public CrabGrowth selectCrabGrowthByRecordId(Long recordId);

    /**
     * 查询螃蟹生长记录列表
     * 
     * @param crabGrowth 螃蟹生长记录
     * @return 螃蟹生长记录集合
     */
    public List<CrabGrowth> selectCrabGrowthList(CrabGrowth crabGrowth);

    /**
     * 新增螃蟹生长记录
     * 
     * @param crabGrowth 螃蟹生长记录
     * @return 结果
     */
    public int insertCrabGrowth(CrabGrowth crabGrowth);

    /**
     * 修改螃蟹生长记录
     * 
     * @param crabGrowth 螃蟹生长记录
     * @return 结果
     */
    public int updateCrabGrowth(CrabGrowth crabGrowth);

    /**
     * 批量删除螃蟹生长记录
     * 
     * @param recordIds 需要删除的螃蟹生长记录主键集合
     * @return 结果
     */
    public int deleteCrabGrowthByRecordIds(Long[] recordIds);

    /**
     * 删除螃蟹生长记录信息
     * 
     * @param recordId 螃蟹生长记录主键
     * @return 结果
     */
    public int deleteCrabGrowthByRecordId(Long recordId);
}

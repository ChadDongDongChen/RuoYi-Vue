package com.ruoyi.web.creb.mapper;

import java.util.List;
import com.ruoyi.web.creb.domain.CrabCost;

/**
 * 养殖成本记录Mapper接口
 * 
 * @author chendong
 * @date 2025-05-31
 */
public interface CrabCostMapper 
{
    /**
     * 查询养殖成本记录
     * 
     * @param recordId 养殖成本记录主键
     * @return 养殖成本记录
     */
    public CrabCost selectCrabCostByRecordId(Long recordId);

    /**
     * 查询养殖成本记录列表
     * 
     * @param crabCost 养殖成本记录
     * @return 养殖成本记录集合
     */
    public List<CrabCost> selectCrabCostList(CrabCost crabCost);

    /**
     * 新增养殖成本记录
     * 
     * @param crabCost 养殖成本记录
     * @return 结果
     */
    public int insertCrabCost(CrabCost crabCost);

    /**
     * 修改养殖成本记录
     * 
     * @param crabCost 养殖成本记录
     * @return 结果
     */
    public int updateCrabCost(CrabCost crabCost);

    /**
     * 删除养殖成本记录
     * 
     * @param recordId 养殖成本记录主键
     * @return 结果
     */
    public int deleteCrabCostByRecordId(Long recordId);

    /**
     * 批量删除养殖成本记录
     * 
     * @param recordIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteCrabCostByRecordIds(Long[] recordIds);
}

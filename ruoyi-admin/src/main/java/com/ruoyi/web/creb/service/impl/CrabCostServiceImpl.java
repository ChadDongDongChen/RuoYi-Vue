package com.ruoyi.web.creb.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.web.creb.mapper.CrabCostMapper;
import com.ruoyi.web.creb.domain.CrabCost;
import com.ruoyi.web.creb.service.ICrabCostService;

/**
 * 养殖成本记录Service业务层处理
 * 
 * @author chendong
 * @date 2025-05-31
 */
@Service
public class CrabCostServiceImpl implements ICrabCostService 
{
    @Autowired
    private CrabCostMapper crabCostMapper;

    /**
     * 查询养殖成本记录
     * 
     * @param recordId 养殖成本记录主键
     * @return 养殖成本记录
     */
    @Override
    public CrabCost selectCrabCostByRecordId(Long recordId)
    {
        return crabCostMapper.selectCrabCostByRecordId(recordId);
    }

    /**
     * 查询养殖成本记录列表
     * 
     * @param crabCost 养殖成本记录
     * @return 养殖成本记录
     */
    @Override
    public List<CrabCost> selectCrabCostList(CrabCost crabCost)
    {
        return crabCostMapper.selectCrabCostList(crabCost);
    }

    /**
     * 新增养殖成本记录
     * 
     * @param crabCost 养殖成本记录
     * @return 结果
     */
    @Override
    public int insertCrabCost(CrabCost crabCost)
    {
        crabCost.setCreateTime(DateUtils.getNowDate());
        return crabCostMapper.insertCrabCost(crabCost);
    }

    /**
     * 修改养殖成本记录
     * 
     * @param crabCost 养殖成本记录
     * @return 结果
     */
    @Override
    public int updateCrabCost(CrabCost crabCost)
    {
        return crabCostMapper.updateCrabCost(crabCost);
    }

    /**
     * 批量删除养殖成本记录
     * 
     * @param recordIds 需要删除的养殖成本记录主键
     * @return 结果
     */
    @Override
    public int deleteCrabCostByRecordIds(Long[] recordIds)
    {
        return crabCostMapper.deleteCrabCostByRecordIds(recordIds);
    }

    /**
     * 删除养殖成本记录信息
     * 
     * @param recordId 养殖成本记录主键
     * @return 结果
     */
    @Override
    public int deleteCrabCostByRecordId(Long recordId)
    {
        return crabCostMapper.deleteCrabCostByRecordId(recordId);
    }
}

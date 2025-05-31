package com.ruoyi.web.creb.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.web.creb.mapper.CrabGrowthMapper;
import com.ruoyi.web.creb.domain.CrabGrowth;
import com.ruoyi.web.creb.service.ICrabGrowthService;

/**
 * 螃蟹生长记录Service业务层处理
 * 
 * @author chendong
 * @date 2025-05-31
 */
@Service
public class CrabGrowthServiceImpl implements ICrabGrowthService 
{
    @Autowired
    private CrabGrowthMapper crabGrowthMapper;

    /**
     * 查询螃蟹生长记录
     * 
     * @param recordId 螃蟹生长记录主键
     * @return 螃蟹生长记录
     */
    @Override
    public CrabGrowth selectCrabGrowthByRecordId(Long recordId)
    {
        return crabGrowthMapper.selectCrabGrowthByRecordId(recordId);
    }

    /**
     * 查询螃蟹生长记录列表
     * 
     * @param crabGrowth 螃蟹生长记录
     * @return 螃蟹生长记录
     */
    @Override
    public List<CrabGrowth> selectCrabGrowthList(CrabGrowth crabGrowth)
    {
        return crabGrowthMapper.selectCrabGrowthList(crabGrowth);
    }

    /**
     * 新增螃蟹生长记录
     * 
     * @param crabGrowth 螃蟹生长记录
     * @return 结果
     */
    @Override
    public int insertCrabGrowth(CrabGrowth crabGrowth)
    {
        crabGrowth.setCreateTime(DateUtils.getNowDate());
        return crabGrowthMapper.insertCrabGrowth(crabGrowth);
    }

    /**
     * 修改螃蟹生长记录
     * 
     * @param crabGrowth 螃蟹生长记录
     * @return 结果
     */
    @Override
    public int updateCrabGrowth(CrabGrowth crabGrowth)
    {
        return crabGrowthMapper.updateCrabGrowth(crabGrowth);
    }

    /**
     * 批量删除螃蟹生长记录
     * 
     * @param recordIds 需要删除的螃蟹生长记录主键
     * @return 结果
     */
    @Override
    public int deleteCrabGrowthByRecordIds(Long[] recordIds)
    {
        return crabGrowthMapper.deleteCrabGrowthByRecordIds(recordIds);
    }

    /**
     * 删除螃蟹生长记录信息
     * 
     * @param recordId 螃蟹生长记录主键
     * @return 结果
     */
    @Override
    public int deleteCrabGrowthByRecordId(Long recordId)
    {
        return crabGrowthMapper.deleteCrabGrowthByRecordId(recordId);
    }
}

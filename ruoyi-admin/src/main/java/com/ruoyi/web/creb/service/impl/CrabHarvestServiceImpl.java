package com.ruoyi.web.creb.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.web.creb.mapper.CrabHarvestMapper;
import com.ruoyi.web.creb.domain.CrabHarvest;
import com.ruoyi.web.creb.service.ICrabHarvestService;

/**
 * 收获记录Service业务层处理
 * 
 * @author chendong
 * @date 2025-05-31
 */
@Service
public class CrabHarvestServiceImpl implements ICrabHarvestService 
{
    @Autowired
    private CrabHarvestMapper crabHarvestMapper;

    /**
     * 查询收获记录
     * 
     * @param recordId 收获记录主键
     * @return 收获记录
     */
    @Override
    public CrabHarvest selectCrabHarvestByRecordId(Long recordId)
    {
        return crabHarvestMapper.selectCrabHarvestByRecordId(recordId);
    }

    /**
     * 查询收获记录列表
     * 
     * @param crabHarvest 收获记录
     * @return 收获记录
     */
    @Override
    public List<CrabHarvest> selectCrabHarvestList(CrabHarvest crabHarvest)
    {
        return crabHarvestMapper.selectCrabHarvestList(crabHarvest);
    }

    /**
     * 新增收获记录
     * 
     * @param crabHarvest 收获记录
     * @return 结果
     */
    @Override
    public int insertCrabHarvest(CrabHarvest crabHarvest)
    {
        crabHarvest.setCreateTime(DateUtils.getNowDate());
        return crabHarvestMapper.insertCrabHarvest(crabHarvest);
    }

    /**
     * 修改收获记录
     * 
     * @param crabHarvest 收获记录
     * @return 结果
     */
    @Override
    public int updateCrabHarvest(CrabHarvest crabHarvest)
    {
        return crabHarvestMapper.updateCrabHarvest(crabHarvest);
    }

    /**
     * 批量删除收获记录
     * 
     * @param recordIds 需要删除的收获记录主键
     * @return 结果
     */
    @Override
    public int deleteCrabHarvestByRecordIds(Long[] recordIds)
    {
        return crabHarvestMapper.deleteCrabHarvestByRecordIds(recordIds);
    }

    /**
     * 删除收获记录信息
     * 
     * @param recordId 收获记录主键
     * @return 结果
     */
    @Override
    public int deleteCrabHarvestByRecordId(Long recordId)
    {
        return crabHarvestMapper.deleteCrabHarvestByRecordId(recordId);
    }
}

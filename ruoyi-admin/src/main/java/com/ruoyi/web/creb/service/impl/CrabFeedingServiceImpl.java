package com.ruoyi.web.creb.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.web.creb.mapper.CrabFeedingMapper;
import com.ruoyi.web.creb.domain.CrabFeeding;
import com.ruoyi.web.creb.service.ICrabFeedingService;

/**
 * 投喂记录Service业务层处理
 * 
 * @author chendong
 * @date 2025-05-31
 */
@Service
public class CrabFeedingServiceImpl implements ICrabFeedingService 
{
    @Autowired
    private CrabFeedingMapper crabFeedingMapper;

    /**
     * 查询投喂记录
     * 
     * @param recordId 投喂记录主键
     * @return 投喂记录
     */
    @Override
    public CrabFeeding selectCrabFeedingByRecordId(Long recordId)
    {
        return crabFeedingMapper.selectCrabFeedingByRecordId(recordId);
    }

    /**
     * 查询投喂记录列表
     * 
     * @param crabFeeding 投喂记录
     * @return 投喂记录
     */
    @Override
    public List<CrabFeeding> selectCrabFeedingList(CrabFeeding crabFeeding)
    {
        return crabFeedingMapper.selectCrabFeedingList(crabFeeding);
    }

    /**
     * 新增投喂记录
     * 
     * @param crabFeeding 投喂记录
     * @return 结果
     */
    @Override
    public int insertCrabFeeding(CrabFeeding crabFeeding)
    {
        crabFeeding.setCreateTime(DateUtils.getNowDate());
        return crabFeedingMapper.insertCrabFeeding(crabFeeding);
    }

    /**
     * 修改投喂记录
     * 
     * @param crabFeeding 投喂记录
     * @return 结果
     */
    @Override
    public int updateCrabFeeding(CrabFeeding crabFeeding)
    {
        return crabFeedingMapper.updateCrabFeeding(crabFeeding);
    }

    /**
     * 批量删除投喂记录
     * 
     * @param recordIds 需要删除的投喂记录主键
     * @return 结果
     */
    @Override
    public int deleteCrabFeedingByRecordIds(Long[] recordIds)
    {
        return crabFeedingMapper.deleteCrabFeedingByRecordIds(recordIds);
    }

    /**
     * 删除投喂记录信息
     * 
     * @param recordId 投喂记录主键
     * @return 结果
     */
    @Override
    public int deleteCrabFeedingByRecordId(Long recordId)
    {
        return crabFeedingMapper.deleteCrabFeedingByRecordId(recordId);
    }
}

package com.ruoyi.web.creb.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.web.creb.mapper.CrabMaintenanceMapper;
import com.ruoyi.web.creb.domain.CrabMaintenance;
import com.ruoyi.web.creb.service.ICrabMaintenanceService;

/**
 * 养殖池维护记录Service业务层处理
 * 
 * @author chendong
 * @date 2025-05-31
 */
@Service
public class CrabMaintenanceServiceImpl implements ICrabMaintenanceService 
{
    @Autowired
    private CrabMaintenanceMapper crabMaintenanceMapper;

    /**
     * 查询养殖池维护记录
     * 
     * @param recordId 养殖池维护记录主键
     * @return 养殖池维护记录
     */
    @Override
    public CrabMaintenance selectCrabMaintenanceByRecordId(Long recordId)
    {
        return crabMaintenanceMapper.selectCrabMaintenanceByRecordId(recordId);
    }

    /**
     * 查询养殖池维护记录列表
     * 
     * @param crabMaintenance 养殖池维护记录
     * @return 养殖池维护记录
     */
    @Override
    public List<CrabMaintenance> selectCrabMaintenanceList(CrabMaintenance crabMaintenance)
    {
        return crabMaintenanceMapper.selectCrabMaintenanceList(crabMaintenance);
    }

    /**
     * 新增养殖池维护记录
     * 
     * @param crabMaintenance 养殖池维护记录
     * @return 结果
     */
    @Override
    public int insertCrabMaintenance(CrabMaintenance crabMaintenance)
    {
        crabMaintenance.setCreateTime(DateUtils.getNowDate());
        return crabMaintenanceMapper.insertCrabMaintenance(crabMaintenance);
    }

    /**
     * 修改养殖池维护记录
     * 
     * @param crabMaintenance 养殖池维护记录
     * @return 结果
     */
    @Override
    public int updateCrabMaintenance(CrabMaintenance crabMaintenance)
    {
        return crabMaintenanceMapper.updateCrabMaintenance(crabMaintenance);
    }

    /**
     * 批量删除养殖池维护记录
     * 
     * @param recordIds 需要删除的养殖池维护记录主键
     * @return 结果
     */
    @Override
    public int deleteCrabMaintenanceByRecordIds(Long[] recordIds)
    {
        return crabMaintenanceMapper.deleteCrabMaintenanceByRecordIds(recordIds);
    }

    /**
     * 删除养殖池维护记录信息
     * 
     * @param recordId 养殖池维护记录主键
     * @return 结果
     */
    @Override
    public int deleteCrabMaintenanceByRecordId(Long recordId)
    {
        return crabMaintenanceMapper.deleteCrabMaintenanceByRecordId(recordId);
    }
}

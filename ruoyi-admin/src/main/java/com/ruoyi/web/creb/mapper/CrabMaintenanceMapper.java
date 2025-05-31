package com.ruoyi.web.creb.mapper;

import java.util.List;
import com.ruoyi.web.creb.domain.CrabMaintenance;

/**
 * 养殖池维护记录Mapper接口
 * 
 * @author chendong
 * @date 2025-05-31
 */
public interface CrabMaintenanceMapper 
{
    /**
     * 查询养殖池维护记录
     * 
     * @param recordId 养殖池维护记录主键
     * @return 养殖池维护记录
     */
    public CrabMaintenance selectCrabMaintenanceByRecordId(Long recordId);

    /**
     * 查询养殖池维护记录列表
     * 
     * @param crabMaintenance 养殖池维护记录
     * @return 养殖池维护记录集合
     */
    public List<CrabMaintenance> selectCrabMaintenanceList(CrabMaintenance crabMaintenance);

    /**
     * 新增养殖池维护记录
     * 
     * @param crabMaintenance 养殖池维护记录
     * @return 结果
     */
    public int insertCrabMaintenance(CrabMaintenance crabMaintenance);

    /**
     * 修改养殖池维护记录
     * 
     * @param crabMaintenance 养殖池维护记录
     * @return 结果
     */
    public int updateCrabMaintenance(CrabMaintenance crabMaintenance);

    /**
     * 删除养殖池维护记录
     * 
     * @param recordId 养殖池维护记录主键
     * @return 结果
     */
    public int deleteCrabMaintenanceByRecordId(Long recordId);

    /**
     * 批量删除养殖池维护记录
     * 
     * @param recordIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteCrabMaintenanceByRecordIds(Long[] recordIds);
}

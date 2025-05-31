package com.ruoyi.web.creb.service;

import java.util.List;
import com.ruoyi.web.creb.domain.CrabDevice;

/**
 * 环境监测设备Service接口
 * 
 * @author chendong
 * @date 2025-05-31
 */
public interface ICrabDeviceService 
{
    /**
     * 查询环境监测设备
     * 
     * @param deviceId 环境监测设备主键
     * @return 环境监测设备
     */
    public CrabDevice selectCrabDeviceByDeviceId(Long deviceId);

    /**
     * 查询环境监测设备列表
     * 
     * @param crabDevice 环境监测设备
     * @return 环境监测设备集合
     */
    public List<CrabDevice> selectCrabDeviceList(CrabDevice crabDevice);

    /**
     * 新增环境监测设备
     * 
     * @param crabDevice 环境监测设备
     * @return 结果
     */
    public int insertCrabDevice(CrabDevice crabDevice);

    /**
     * 修改环境监测设备
     * 
     * @param crabDevice 环境监测设备
     * @return 结果
     */
    public int updateCrabDevice(CrabDevice crabDevice);

    /**
     * 批量删除环境监测设备
     * 
     * @param deviceIds 需要删除的环境监测设备主键集合
     * @return 结果
     */
    public int deleteCrabDeviceByDeviceIds(Long[] deviceIds);

    /**
     * 删除环境监测设备信息
     * 
     * @param deviceId 环境监测设备主键
     * @return 结果
     */
    public int deleteCrabDeviceByDeviceId(Long deviceId);
}

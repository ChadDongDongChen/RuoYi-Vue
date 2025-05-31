package com.ruoyi.web.creb.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.web.creb.mapper.CrabDeviceMapper;
import com.ruoyi.web.creb.domain.CrabDevice;
import com.ruoyi.web.creb.service.ICrabDeviceService;

/**
 * 环境监测设备Service业务层处理
 * 
 * @author chendong
 * @date 2025-05-31
 */
@Service
public class CrabDeviceServiceImpl implements ICrabDeviceService 
{
    @Autowired
    private CrabDeviceMapper crabDeviceMapper;

    /**
     * 查询环境监测设备
     * 
     * @param deviceId 环境监测设备主键
     * @return 环境监测设备
     */
    @Override
    public CrabDevice selectCrabDeviceByDeviceId(Long deviceId)
    {
        return crabDeviceMapper.selectCrabDeviceByDeviceId(deviceId);
    }

    /**
     * 查询环境监测设备列表
     * 
     * @param crabDevice 环境监测设备
     * @return 环境监测设备
     */
    @Override
    public List<CrabDevice> selectCrabDeviceList(CrabDevice crabDevice)
    {
        return crabDeviceMapper.selectCrabDeviceList(crabDevice);
    }

    /**
     * 新增环境监测设备
     * 
     * @param crabDevice 环境监测设备
     * @return 结果
     */
    @Override
    public int insertCrabDevice(CrabDevice crabDevice)
    {
        crabDevice.setCreateTime(DateUtils.getNowDate());
        return crabDeviceMapper.insertCrabDevice(crabDevice);
    }

    /**
     * 修改环境监测设备
     * 
     * @param crabDevice 环境监测设备
     * @return 结果
     */
    @Override
    public int updateCrabDevice(CrabDevice crabDevice)
    {
        crabDevice.setUpdateTime(DateUtils.getNowDate());
        return crabDeviceMapper.updateCrabDevice(crabDevice);
    }

    /**
     * 批量删除环境监测设备
     * 
     * @param deviceIds 需要删除的环境监测设备主键
     * @return 结果
     */
    @Override
    public int deleteCrabDeviceByDeviceIds(Long[] deviceIds)
    {
        return crabDeviceMapper.deleteCrabDeviceByDeviceIds(deviceIds);
    }

    /**
     * 删除环境监测设备信息
     * 
     * @param deviceId 环境监测设备主键
     * @return 结果
     */
    @Override
    public int deleteCrabDeviceByDeviceId(Long deviceId)
    {
        return crabDeviceMapper.deleteCrabDeviceByDeviceId(deviceId);
    }
}

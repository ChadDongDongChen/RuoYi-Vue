package com.ruoyi.web.creb.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.web.creb.mapper.CrabAlertMapper;
import com.ruoyi.web.creb.domain.CrabAlert;
import com.ruoyi.web.creb.service.ICrabAlertService;

/**
 * 异常预警记录Service业务层处理
 * 
 * @author chendong
 * @date 2025-05-31
 */
@Service
public class CrabAlertServiceImpl implements ICrabAlertService 
{
    @Autowired
    private CrabAlertMapper crabAlertMapper;

    /**
     * 查询异常预警记录
     * 
     * @param alertId 异常预警记录主键
     * @return 异常预警记录
     */
    @Override
    public CrabAlert selectCrabAlertByAlertId(Long alertId)
    {
        return crabAlertMapper.selectCrabAlertByAlertId(alertId);
    }

    /**
     * 查询异常预警记录列表
     * 
     * @param crabAlert 异常预警记录
     * @return 异常预警记录
     */
    @Override
    public List<CrabAlert> selectCrabAlertList(CrabAlert crabAlert)
    {
        return crabAlertMapper.selectCrabAlertList(crabAlert);
    }

    /**
     * 新增异常预警记录
     * 
     * @param crabAlert 异常预警记录
     * @return 结果
     */
    @Override
    public int insertCrabAlert(CrabAlert crabAlert)
    {
        crabAlert.setCreateTime(DateUtils.getNowDate());
        return crabAlertMapper.insertCrabAlert(crabAlert);
    }

    /**
     * 修改异常预警记录
     * 
     * @param crabAlert 异常预警记录
     * @return 结果
     */
    @Override
    public int updateCrabAlert(CrabAlert crabAlert)
    {
        return crabAlertMapper.updateCrabAlert(crabAlert);
    }

    /**
     * 批量删除异常预警记录
     * 
     * @param alertIds 需要删除的异常预警记录主键
     * @return 结果
     */
    @Override
    public int deleteCrabAlertByAlertIds(Long[] alertIds)
    {
        return crabAlertMapper.deleteCrabAlertByAlertIds(alertIds);
    }

    /**
     * 删除异常预警记录信息
     * 
     * @param alertId 异常预警记录主键
     * @return 结果
     */
    @Override
    public int deleteCrabAlertByAlertId(Long alertId)
    {
        return crabAlertMapper.deleteCrabAlertByAlertId(alertId);
    }
}

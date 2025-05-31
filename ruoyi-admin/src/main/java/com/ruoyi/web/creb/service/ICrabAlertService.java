package com.ruoyi.web.creb.service;

import java.util.List;
import com.ruoyi.web.creb.domain.CrabAlert;

/**
 * 异常预警记录Service接口
 * 
 * @author chendong
 * @date 2025-05-31
 */
public interface ICrabAlertService 
{
    /**
     * 查询异常预警记录
     * 
     * @param alertId 异常预警记录主键
     * @return 异常预警记录
     */
    public CrabAlert selectCrabAlertByAlertId(Long alertId);

    /**
     * 查询异常预警记录列表
     * 
     * @param crabAlert 异常预警记录
     * @return 异常预警记录集合
     */
    public List<CrabAlert> selectCrabAlertList(CrabAlert crabAlert);

    /**
     * 新增异常预警记录
     * 
     * @param crabAlert 异常预警记录
     * @return 结果
     */
    public int insertCrabAlert(CrabAlert crabAlert);

    /**
     * 修改异常预警记录
     * 
     * @param crabAlert 异常预警记录
     * @return 结果
     */
    public int updateCrabAlert(CrabAlert crabAlert);

    /**
     * 批量删除异常预警记录
     * 
     * @param alertIds 需要删除的异常预警记录主键集合
     * @return 结果
     */
    public int deleteCrabAlertByAlertIds(Long[] alertIds);

    /**
     * 删除异常预警记录信息
     * 
     * @param alertId 异常预警记录主键
     * @return 结果
     */
    public int deleteCrabAlertByAlertId(Long alertId);
}

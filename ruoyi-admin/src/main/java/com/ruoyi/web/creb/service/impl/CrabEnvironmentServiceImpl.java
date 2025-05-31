package com.ruoyi.web.creb.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.web.creb.mapper.CrabEnvironmentMapper;
import com.ruoyi.web.creb.domain.CrabEnvironment;
import com.ruoyi.web.creb.service.ICrabEnvironmentService;

/**
 * 环境数据记录Service业务层处理
 * 
 * @author chendong
 * @date 2025-05-31
 */
@Service
public class CrabEnvironmentServiceImpl implements ICrabEnvironmentService 
{
    @Autowired
    private CrabEnvironmentMapper crabEnvironmentMapper;

    /**
     * 查询环境数据记录
     * 
     * @param dataId 环境数据记录主键
     * @return 环境数据记录
     */
    @Override
    public CrabEnvironment selectCrabEnvironmentByDataId(Long dataId)
    {
        return crabEnvironmentMapper.selectCrabEnvironmentByDataId(dataId);
    }

    /**
     * 查询环境数据记录列表
     * 
     * @param crabEnvironment 环境数据记录
     * @return 环境数据记录
     */
    @Override
    public List<CrabEnvironment> selectCrabEnvironmentList(CrabEnvironment crabEnvironment)
    {
        return crabEnvironmentMapper.selectCrabEnvironmentList(crabEnvironment);
    }

    /**
     * 新增环境数据记录
     * 
     * @param crabEnvironment 环境数据记录
     * @return 结果
     */
    @Override
    public int insertCrabEnvironment(CrabEnvironment crabEnvironment)
    {
        crabEnvironment.setCreateTime(DateUtils.getNowDate());
        return crabEnvironmentMapper.insertCrabEnvironment(crabEnvironment);
    }

    /**
     * 修改环境数据记录
     * 
     * @param crabEnvironment 环境数据记录
     * @return 结果
     */
    @Override
    public int updateCrabEnvironment(CrabEnvironment crabEnvironment)
    {
        return crabEnvironmentMapper.updateCrabEnvironment(crabEnvironment);
    }

    /**
     * 批量删除环境数据记录
     * 
     * @param dataIds 需要删除的环境数据记录主键
     * @return 结果
     */
    @Override
    public int deleteCrabEnvironmentByDataIds(Long[] dataIds)
    {
        return crabEnvironmentMapper.deleteCrabEnvironmentByDataIds(dataIds);
    }

    /**
     * 删除环境数据记录信息
     * 
     * @param dataId 环境数据记录主键
     * @return 结果
     */
    @Override
    public int deleteCrabEnvironmentByDataId(Long dataId)
    {
        return crabEnvironmentMapper.deleteCrabEnvironmentByDataId(dataId);
    }
}

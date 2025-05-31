package com.ruoyi.web.creb.mapper;

import java.util.List;
import com.ruoyi.web.creb.domain.CrabEnvironment;

/**
 * 环境数据记录Mapper接口
 * 
 * @author chendong
 * @date 2025-05-31
 */
public interface CrabEnvironmentMapper 
{
    /**
     * 查询环境数据记录
     * 
     * @param dataId 环境数据记录主键
     * @return 环境数据记录
     */
    public CrabEnvironment selectCrabEnvironmentByDataId(Long dataId);

    /**
     * 查询环境数据记录列表
     * 
     * @param crabEnvironment 环境数据记录
     * @return 环境数据记录集合
     */
    public List<CrabEnvironment> selectCrabEnvironmentList(CrabEnvironment crabEnvironment);

    /**
     * 新增环境数据记录
     * 
     * @param crabEnvironment 环境数据记录
     * @return 结果
     */
    public int insertCrabEnvironment(CrabEnvironment crabEnvironment);

    /**
     * 修改环境数据记录
     * 
     * @param crabEnvironment 环境数据记录
     * @return 结果
     */
    public int updateCrabEnvironment(CrabEnvironment crabEnvironment);

    /**
     * 删除环境数据记录
     * 
     * @param dataId 环境数据记录主键
     * @return 结果
     */
    public int deleteCrabEnvironmentByDataId(Long dataId);

    /**
     * 批量删除环境数据记录
     * 
     * @param dataIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteCrabEnvironmentByDataIds(Long[] dataIds);
}

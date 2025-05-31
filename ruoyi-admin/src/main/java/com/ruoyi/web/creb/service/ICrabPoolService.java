package com.ruoyi.web.creb.service;

import java.util.List;
import com.ruoyi.web.creb.domain.CrabPool;

/**
 * 螃蟹养殖池信息Service接口
 * 
 * @author chendong
 * @date 2025-05-31
 */
public interface ICrabPoolService 
{
    /**
     * 查询螃蟹养殖池信息
     * 
     * @param poolId 螃蟹养殖池信息主键
     * @return 螃蟹养殖池信息
     */
    public CrabPool selectCrabPoolByPoolId(Long poolId);

    /**
     * 查询螃蟹养殖池信息列表
     * 
     * @param crabPool 螃蟹养殖池信息
     * @return 螃蟹养殖池信息集合
     */
    public List<CrabPool> selectCrabPoolList(CrabPool crabPool);

    /**
     * 新增螃蟹养殖池信息
     * 
     * @param crabPool 螃蟹养殖池信息
     * @return 结果
     */
    public int insertCrabPool(CrabPool crabPool);

    /**
     * 修改螃蟹养殖池信息
     * 
     * @param crabPool 螃蟹养殖池信息
     * @return 结果
     */
    public int updateCrabPool(CrabPool crabPool);

    /**
     * 批量删除螃蟹养殖池信息
     * 
     * @param poolIds 需要删除的螃蟹养殖池信息主键集合
     * @return 结果
     */
    public int deleteCrabPoolByPoolIds(Long[] poolIds);

    /**
     * 删除螃蟹养殖池信息信息
     * 
     * @param poolId 螃蟹养殖池信息主键
     * @return 结果
     */
    public int deleteCrabPoolByPoolId(Long poolId);
}

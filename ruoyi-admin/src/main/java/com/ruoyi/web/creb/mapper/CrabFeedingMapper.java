package com.ruoyi.web.creb.mapper;

import java.util.List;
import com.ruoyi.web.creb.domain.CrabFeeding;

/**
 * 投喂记录Mapper接口
 * 
 * @author chendong
 * @date 2025-05-31
 */
public interface CrabFeedingMapper 
{
    /**
     * 查询投喂记录
     * 
     * @param recordId 投喂记录主键
     * @return 投喂记录
     */
    public CrabFeeding selectCrabFeedingByRecordId(Long recordId);

    /**
     * 查询投喂记录列表
     * 
     * @param crabFeeding 投喂记录
     * @return 投喂记录集合
     */
    public List<CrabFeeding> selectCrabFeedingList(CrabFeeding crabFeeding);

    /**
     * 新增投喂记录
     * 
     * @param crabFeeding 投喂记录
     * @return 结果
     */
    public int insertCrabFeeding(CrabFeeding crabFeeding);

    /**
     * 修改投喂记录
     * 
     * @param crabFeeding 投喂记录
     * @return 结果
     */
    public int updateCrabFeeding(CrabFeeding crabFeeding);

    /**
     * 删除投喂记录
     * 
     * @param recordId 投喂记录主键
     * @return 结果
     */
    public int deleteCrabFeedingByRecordId(Long recordId);

    /**
     * 批量删除投喂记录
     * 
     * @param recordIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteCrabFeedingByRecordIds(Long[] recordIds);
}

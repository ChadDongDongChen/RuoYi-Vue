package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.AiIdphotoSpec;

/**
 * AI证件照规格 Mapper 接口
 *
 * @author ruoyi
 */
public interface AiIdphotoSpecMapper
{
    /**
     * 查询规格列表
     *
     * @param spec 规格信息
     * @return 规格集合
     */
    List<AiIdphotoSpec> selectAiIdphotoSpecList(AiIdphotoSpec spec);

    /**
     * 查询启用的规格列表
     *
     * @return 规格集合
     */
    List<AiIdphotoSpec> selectEnabledSpecList();

    /**
     * 根据规格ID查询规格
     *
     * @param specId 规格ID
     * @return 规格信息
     */
    AiIdphotoSpec selectAiIdphotoSpecById(Long specId);

    /**
     * 新增规格
     *
     * @param spec 规格信息
     * @return 结果
     */
    int insertAiIdphotoSpec(AiIdphotoSpec spec);

    /**
     * 修改规格
     *
     * @param spec 规格信息
     * @return 结果
     */
    int updateAiIdphotoSpec(AiIdphotoSpec spec);

    /**
     * 删除规格
     *
     * @param specId 规格ID
     * @return 结果
     */
    int deleteAiIdphotoSpecById(Long specId);

    /**
     * 批量删除规格
     *
     * @param specIds 需要删除的规格ID
     * @return 结果
     */
    int deleteAiIdphotoSpecByIds(Long[] specIds);
}

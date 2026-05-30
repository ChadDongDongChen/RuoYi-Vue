package com.ruoyi.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.domain.AiIdphotoSpec;
import com.ruoyi.system.mapper.AiIdphotoSpecMapper;
import com.ruoyi.system.service.IAiIdphotoSpecService;

/**
 * AI证件照规格 Service 实现
 *
 * @author ruoyi
 */
@Service
public class AiIdphotoSpecServiceImpl implements IAiIdphotoSpecService
{
    @Autowired
    private AiIdphotoSpecMapper specMapper;

    @Override
    public List<AiIdphotoSpec> selectAiIdphotoSpecList(AiIdphotoSpec spec)
    {
        return specMapper.selectAiIdphotoSpecList(spec);
    }

    @Override
    public List<AiIdphotoSpec> selectEnabledSpecList()
    {
        return specMapper.selectEnabledSpecList();
    }

    @Override
    public AiIdphotoSpec selectAiIdphotoSpecById(Long specId)
    {
        return specMapper.selectAiIdphotoSpecById(specId);
    }

    @Override
    public int insertAiIdphotoSpec(AiIdphotoSpec spec)
    {
        return specMapper.insertAiIdphotoSpec(spec);
    }

    @Override
    public int updateAiIdphotoSpec(AiIdphotoSpec spec)
    {
        return specMapper.updateAiIdphotoSpec(spec);
    }

    @Override
    public int deleteAiIdphotoSpecByIds(Long[] specIds)
    {
        return specMapper.deleteAiIdphotoSpecByIds(specIds);
    }
}

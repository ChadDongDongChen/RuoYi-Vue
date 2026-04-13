package com.ruoyi.web.kf.service;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.web.kf.domain.KfProduct;
import com.ruoyi.web.kf.mapper.KfProductMapper;

@Service
public class KfProductRecommendService
{
    @Autowired
    private KfProductMapper kfProductMapper;

    public List<KfProduct> recommendForQuestion(String userText)
    {
        Set<Long> seen = new LinkedHashSet<>();
        List<KfProduct> out = new ArrayList<>();
        if (StringUtils.isBlank(userText))
        {
            addAll(kfProductMapper.selectKfProductMatchKeywords(""), out, seen);
            return out;
        }
        String[] parts = userText.split("[\\s,，。！？!?]+");
        for (String p : parts)
        {
            if (p.length() < 2)
            {
                continue;
            }
            addAll(kfProductMapper.selectKfProductMatchKeywords(p), out, seen);
        }
        if (out.isEmpty())
        {
            addAll(kfProductMapper.selectKfProductMatchKeywords(userText.trim()), out, seen);
        }
        if (out.isEmpty())
        {
            addAll(kfProductMapper.selectKfProductMatchKeywords(""), out, seen);
        }
        return out;
    }

    private static void addAll(List<KfProduct> batch, List<KfProduct> out, Set<Long> seen)
    {
        if (batch == null)
        {
            return;
        }
        for (KfProduct p : batch)
        {
            if (p.getProductId() != null && seen.add(p.getProductId()))
            {
                out.add(p);
                if (out.size() >= 8)
                {
                    return;
                }
            }
        }
    }

    public String formatCatalogForPrompt(List<KfProduct> products)
    {
        if (products == null || products.isEmpty())
        {
            return "（当前商品库暂无条目，请礼貌说明需要人工确认价格。）";
        }
        StringBuilder sb = new StringBuilder();
        for (KfProduct p : products)
        {
            sb.append("- ").append(p.getProductName());
            if (p.getPrice() != null)
            {
                sb.append("，参考价：").append(p.getPrice());
            }
            if (StringUtils.isNotBlank(p.getIntro()))
            {
                sb.append("，").append(p.getIntro());
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}

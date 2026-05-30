package com.ruoyi.system.service;

import com.ruoyi.system.domain.AiIdphotoSpec;

/**
 * AI证件照处理 Service 接口
 *
 * 可替换实现: Mock(当前) -> 真实AI服务(后续)
 *
 * @author ruoyi
 */
public interface IIdPhotoAiService
{
    /**
     * 处理证件照
     *
     * @param originalUrl 原图URL
     * @param spec        规格
     * @param background  背景色
     * @param beauty      美颜级别
     * @param suit        正装类型
     * @return 结果图URL
     */
    String process(String originalUrl, AiIdphotoSpec spec, String background, String beauty, String suit);
}

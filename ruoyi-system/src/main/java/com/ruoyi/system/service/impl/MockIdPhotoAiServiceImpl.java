package com.ruoyi.system.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.ruoyi.system.domain.AiIdphotoSpec;
import com.ruoyi.system.service.IIdPhotoAiService;

/**
 * AI证件照处理 Mock 实现
 *
 * 模拟AI处理流程: 延迟3秒后返回预设样例图URL
 * 后续替换为真实AI服务调用
 *
 * @author ruoyi
 */
@Service("idPhotoAiService")
public class MockIdPhotoAiServiceImpl implements IIdPhotoAiService
{
    private static final Logger log = LoggerFactory.getLogger(MockIdPhotoAiServiceImpl.class);

    /** 样例结果图路径 */
    private static final String SAMPLE_RESULT = "/profile/upload/idphoto/sample.jpg";

    @Override
    public String process(String originalUrl, AiIdphotoSpec spec, String background, String beauty, String suit)
    {
        log.info("开始AI证件照处理: 原图={}, 规格={}, 背景={}, 美颜={}, 正装={}",
                originalUrl, spec.getSpecName(), background, beauty, suit);

        try
        {
            // 模拟AI处理耗时
            Thread.sleep(3000);
        }
        catch (InterruptedException e)
        {
            Thread.currentThread().interrupt();
            log.error("AI处理被中断", e);
        }

        log.info("AI证件照处理完成: 返回样例结果图 {}", SAMPLE_RESULT);
        return SAMPLE_RESULT;
    }
}

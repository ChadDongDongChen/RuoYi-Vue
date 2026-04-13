package com.ruoyi.web.kf.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.client.RestTemplate;

/**
 * 微信客服异步与 HTTP 客户端
 */
@Configuration
@EnableAsync
public class KfAsyncConfig
{
    @Bean
    public RestTemplate kfRestTemplate()
    {
        return new RestTemplate();
    }

    @Bean(name = "kfTaskExecutor")
    public ThreadPoolTaskExecutor kfTaskExecutor()
    {
        ThreadPoolTaskExecutor ex = new ThreadPoolTaskExecutor();
        ex.setCorePoolSize(2);
        ex.setMaxPoolSize(8);
        ex.setQueueCapacity(200);
        ex.setThreadNamePrefix("kf-ai-");
        ex.initialize();
        return ex;
    }
}

package com.ruoyi.web.core.config;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * EMQX MQTT 配置
 * @author ruoyi
 */
@Configuration
public class EmqxMqttConfig {

    @Value("${emqx.broker}")
    private String broker;

    @Value("${emqx.username}")
    private String username;

    @Value("${emqx.password}")
    private String password;

    @Value("${emqx.client-id}")
    private String clientId;

    @Value("${emqx.clean-session:true}")
    private boolean cleanSession;

    @Value("${emqx.connection-timeout:10}")
    private int connectionTimeout;

    @Value("${emqx.keep-alive-interval:20}")
    private int keepAliveInterval;

    @Bean
    public MqttClient mqttClient() throws MqttException {
        MqttConnectOptions options = new MqttConnectOptions();
        options.setUserName(username);
        options.setPassword(password.toCharArray());
        options.setCleanSession(cleanSession);
        options.setConnectionTimeout(connectionTimeout);
        options.setKeepAliveInterval(keepAliveInterval);
        MqttClient client = new MqttClient(broker, clientId, new MemoryPersistence());
        client.connect(options);
        return client;
    }
} 
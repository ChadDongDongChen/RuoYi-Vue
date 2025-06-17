package com.ruoyi.web.creb;

import com.alibaba.fastjson2.JSONObject;
import com.ruoyi.web.creb.domain.CrabDevice;
import com.ruoyi.web.creb.domain.CrabEnvironment;
import com.ruoyi.web.creb.mapper.CrabDeviceMapper;
import com.ruoyi.web.creb.mapper.CrabEnvironmentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;

@Component
public class MqttIotReceiver implements MessageHandler {

    @Autowired
    private CrabDeviceMapper deviceMapper;
    @Autowired
    private CrabEnvironmentMapper envMapper;

    @Override
    @ServiceActivator(inputChannel = "mqttInputChannel")
    public void handleMessage(Message<?> message) throws MessagingException {
        try {
            String payload = message.getPayload().toString();
            JSONObject obj = JSONObject.parseObject(payload);
            Long deviceId = obj.getLong("deviceId");
            BigDecimal value = obj.getBigDecimal("value");
            String timestamp = obj.getString("timestamp");

            // 1. 入库环境数据
            CrabDevice device = deviceMapper.selectCrabDeviceByDeviceId(deviceId);
            if (device != null) {
                CrabEnvironment env = new CrabEnvironment();
                env.setDeviceId(deviceId);
                env.setPoolId(device.getPoolId());
                env.setDataType("1"); // 温度
                env.setDataValue(value);
                env.setCollectTime(java.sql.Timestamp.valueOf(timestamp));
                env.setCreateTime(new Date());
                envMapper.insertCrabEnvironment(env);

                // 2. 更新设备在线状态
                device.setLastOnlineTime(new Date());
                device.setDeviceStatus("0"); // 正常/在线
                deviceMapper.updateCrabDevice(device);
            }
        } catch (Exception e) {
            throw new MessagingException(message, "处理MQTT消息失败", e);
        }
    }
} 
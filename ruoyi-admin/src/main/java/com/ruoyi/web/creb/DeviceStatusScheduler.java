package com.ruoyi.web.creb;

import com.ruoyi.web.creb.domain.CrabDevice;
import com.ruoyi.web.creb.mapper.CrabDeviceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class DeviceStatusScheduler {

    @Autowired
    private CrabDeviceMapper deviceMapper;

    @Scheduled(fixedRate = 300000) // 每5分钟执行一次
    public void checkDeviceStatus() {
        List<CrabDevice> devices = deviceMapper.selectCrabDeviceList(new CrabDevice());
        Date now = new Date();
        for (CrabDevice device : devices) {
            if (device.getLastOnlineTime() == null ||
                now.getTime() - device.getLastOnlineTime().getTime() > 5 * 60 * 1000) {
                device.setDeviceStatus("2"); // 离线
                deviceMapper.updateCrabDevice(device);
            }
        }
    }
} 
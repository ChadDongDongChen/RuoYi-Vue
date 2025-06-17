'''
实验名称：I2C总线(OLED显示屏)
版本：v1.0
日期：2019.7
作者：01Studio
'''

import time
from machine import I2C, Pin
from ssd1306 import SSD1306_I2C
from simple import MQTTClient
import network
import math
from machine import ADC
import ujson

# LED指示灯初始化（使用GPIO2，这是ESP32板载的蓝色LED）
led = Pin(2, Pin.OUT)
led.value(0)  # 默认关闭

# 自定义画圆函数
def draw_circle(oled, x0, y0, r, color=1):
    for angle in range(0, 360, 5):
        x = int(x0 + r * math.cos(math.radians(angle)))
        y = int(y0 + r * math.sin(math.radians(angle)))
        oled.pixel(x, y, color)

# 画笑脸（微笑）
def draw_smile(oled):
    draw_circle(oled, 110, 20, 15, 1)
    oled.fill_rect(104, 15, 3, 3, 1)  # 左眼
    oled.fill_rect(115, 15, 3, 3, 1)  # 右眼
    for x in range(105, 116):         # 微笑嘴
        y = int(25 + 0.2 * (x-110)**2 / 2)
        oled.pixel(x, y, 1)

# 画哭脸
def draw_sad(oled):
    draw_circle(oled, 110, 20, 15, 1)
    oled.fill_rect(104, 15, 3, 3, 1)  # 左眼
    oled.fill_rect(115, 15, 3, 3, 1)  # 右眼
    for x in range(105, 116):         # 哭嘴
        y = int(32 - 0.2 * (x-110)**2 / 2)
        oled.pixel(x, y, 1)

# OLED 初始化
i2c = I2C(sda=Pin(13), scl=Pin(14))
oled = SSD1306_I2C(128, 64, i2c, addr=0x3c)

# WiFi 连接
SSID = 'FUSHAN'  # 修改为你的WiFi名
PASSWORD = '123456789'  # 修改为你的WiFi密码

def connect_wifi():
    wlan = network.WLAN(network.STA_IF)
    wlan.active(True)
    if not wlan.isconnected():
        oled.fill(0)
        oled.text('Connecting WiFi...', 0, 0)
        oled.show()
        wlan.connect(SSID, PASSWORD)
        start = time.time()
        while not wlan.isconnected():
            if time.time() - start > 15:
                oled.fill(0)
                oled.text('WiFi Timeout!', 0, 0)
                oled.show()
                led.value(0)  # WiFi连接失败，LED关闭
                return False
            time.sleep(0.5)
    ip = wlan.ifconfig()[0]
    oled.fill(0)
    oled.text('WiFi Connected!', 0, 0)
    oled.text('IP:', 0, 20)
    oled.text(ip, 0, 40)
    oled.show()
    led.value(1)  # WiFi连接成功，LED点亮
    print('network information:', wlan.ifconfig())
    return True

# 温度读取（NTC热敏电阻）
adc = ADC(Pin(36))
adc.atten(ADC.ATTN_11DB)
adc.width(ADC.WIDTH_12BIT)
R_known = 10000
B = 3435
T0 = 298.15

def get_temperature():
    adc_value = adc.read()
    if adc_value == 0:
        return None
    voltage = adc_value * (3.3 / 4095)
    if voltage < 0.1 or voltage > 3.0:
        return None
    R_ntc = R_known * (3.3 - voltage) / voltage
    if R_ntc <= 0 or R_ntc > 1000000:
        return None
    try:
        temperature = (1 / ((1 / T0) + (1 / B) * math.log(R_ntc / 10000))) - 273.15
    except:
        return None
    return temperature

# MQTT 配置
SERVER = 'op.cdd9527.cn'
PORT = 5007
CLIENT_ID = 'esp32_01'  # 可自定义
TOPIC = 'temperature'   # 可自定义

def main():
    if not connect_wifi():
        return
    client = MQTTClient(CLIENT_ID, SERVER, PORT)
    client.connect()
    last_send_ok = True
    msg = ''
    # 获取IP地址
    wlan = network.WLAN(network.STA_IF)
    ip_addr = wlan.ifconfig()[0]
    
    while True:
        temp = get_temperature()
        if temp is not None:
            now = time.localtime()
            timestamp = '{:04d}-{:02d}-{:02d} {:02d}:{:02d}:{:02d}'.format(
                now[0], now[1], now[2], now[3], now[4], now[5]
            )
            data = {
                "deviceId": 100,
                "type": "temperature",
                "value": temp,
                "timestamp": timestamp
            }
            msg = ujson.dumps(data)
        else:
            msg = ''
        # 10秒内每秒刷新时间和表情
        for i in range(10):
            now = time.localtime()
            date_str = '{:04d}-{:02d}-{:02d}'.format(now[0], now[1], now[2])
            time_str = '{:02d}:{:02d}:{:02d}'.format(now[3], now[4], now[5])
            oled.fill(0)
            if temp is not None:
                oled.text('Temp: {:.2f}C'.format(temp), 0, 0)
                if last_send_ok:
                    oled.text('MQTT Sent!', 0, 10)
                else:
                    oled.text('MQTT Fail!', 0, 10)
            else:
                oled.text('Temp Error!', 0, 0)
            # 显示IP地址
            oled.text('IP:', 0, 20)
            oled.text(ip_addr, 0, 30)
            # 显示日期和时间
            oled.text(date_str, 0, 45)
            oled.text(time_str, 0, 55)
            if last_send_ok:
                draw_smile(oled)
            else:
                draw_sad(oled)
            oled.show()
            time.sleep(1)
        # 10秒发一次MQTT
        if temp is not None and msg:
            try:
                client.publish(TOPIC, msg)
                print('已发送温度:', msg)
                last_send_ok = True
            except Exception as e:
                print('温度发送失败:', e)
                # 关键：遇到ENOTCONN时重连
                try:
                    client.connect()
                    print('MQTT重连成功')
                except Exception as e2:
                    print('MQTT重连失败:', e2)
                last_send_ok = False
        else:
            print('温度读取错误')
            last_send_ok = False

if __name__ == '__main__':
    main()




from machine import Pin, ADC
import time
import math

adc = ADC(Pin(36))  # GPIO36 -> ADC1 通道 0
adc.atten(ADC.ATTN_11DB)  # 0-1.0V（可根据接线选择其他，例如 3.3V 用 ATTN_11DB）
adc.width(ADC.WIDTH_12BIT)  # 12 位精度

R_known = 10000  # 10kΩ
B = 3435         # B 参数，与你的传感器规格一致
T0 = 298.15      # 25°C 转为开尔文

def calculate_temperature(adc_value):
    if adc_value == 0:
        return -999

    voltage = adc_value * (3.3 / 4095)
    if voltage < 0.1 or voltage > 3.0:
        return -999

    R_ntc = R_known * (3.3 - voltage) / voltage
    if R_ntc <= 0 or R_ntc > 1000000:
        return -999

    try:
        temperature = (1 / ((1 / T0) + (1 / B) * math.log(R_ntc / 10000))) - 273.15
    except:
        return -999

    return temperature

while True:
    adc_value = adc.read()
    temp = calculate_temperature(adc_value)
    if temp == -999:
        print("Error: Invalid temperature reading")
    else:
        print("Temperature: {:.2f} °C".format(temp))
    time.sleep(1)

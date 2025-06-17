import time
import machine
import main

# 等待系统稳定（建议在通电后等待几秒）
time.sleep(2)

# 设置看门狗定时器，防止程序卡死
wdt = machine.WDT(timeout=30000)  # 30秒超时

def watchdog_feed():
    global wdt
    while True:
        wdt.feed()
        time.sleep(10)  # 每10秒喂一次狗

# 创建喂狗线程
import _thread
_thread.start_new_thread(watchdog_feed, ())

# 启动主程序
retry_count = 0
max_retries = 3

while retry_count < max_retries:
    try:
        main.main()
        break  # 如果程序正常运行，跳出循环
    except Exception as e:
        retry_count += 1
        print(f'Main program error (attempt {retry_count}/{max_retries}):', e)
        if retry_count < max_retries:
            # 如果还有重试机会，等待10秒后重试
            time.sleep(10)
            continue
        else:
            print('Maximum retry attempts reached. System halted.')
            # 可以选择在这里停止喂狗，让系统自动重启
            while True:
                time.sleep(1) 
#!/bin/bash

# 设置变量
SERVICE_JAR="/home/cdd/cdd.jar"
BACKUP_JAR="${SERVICE_JAR}.bak$(date +%Y%m%d_%H%M%S)"

# 备份jar
if [ -f "$SERVICE_JAR" ]; then
    cp "$SERVICE_JAR" "$BACKUP_JAR"
    echo "备份完成: $BACKUP_JAR"
else
    echo "未找到 $SERVICE_JAR"
    exit 1
fi

# 找到并杀死 all-service 进程
PID=$(pgrep -f "cdd")
if [ -n "$PID" ]; then
    kill -9 "$PID"
    echo "已杀死进程: $PID"
else
    echo "未找到运行中的进程"
fi

# 以 nohup 启动服务
nohup java -jar "$SERVICE_JAR" --spring.profiles.active=release > nohup.log 2>&1 &
echo "服务已重启"
#!/bin/sh
# 脚本用途：备份旧jar包、停止服务并重启新服务

# 配置参数
SERVICE_JAR="/home/cdd/ruoyi-admin.jar"
BACKUP_DIR="/home/cdd/backup"
LOG_FILE="/home/cdd/logs/ruoyi.log"
APP_NAME="ruoyi-admin.jar"

# 创建备份目录
mkdir -p "$BACKUP_DIR"
BACKUP_JAR="${BACKUP_DIR}/${APP_NAME}_$(date +%Y%m%d_%H%M%S).jar"

# 开始备份 jar 文件（可启用）
echo "开始备份 jar 文件..."
cp "$SERVICE_JAR" "$BACKUP_JAR"
if [ $? -ne 0 ]; then
    echo "备份失败，退出脚本"
    exit 1
fi
echo "备份完成: $BACKUP_JAR"

# 停止正在运行的服务
echo "尝试停止已运行的服务..."

# 使用兼容方式获取 PID 列表
PIDS=$(pgrep -f "$APP_NAME")
if [ -z "$PIDS" ]; then
    echo "未找到运行中的进程"
else
    for PID in $PIDS; do
        echo "尝试优雅关闭进程: $PID"
        kill "$PID" >/dev/null 2>&1
        sleep 5

        if ps -p "$PID" >/dev/null 2>&1; then
            echo "强制关闭进程: $PID"
            kill -9 "$PID" >/dev/null 2>&1
        fi
    done
fi

# 启动服务
echo "启动服务..."
nohup java -jar "$SERVICE_JAR" >> "$LOG_FILE" 2>&1 &
if [ $? -ne 0 ]; then
    echo "服务启动失败，请检查日志: $LOG_FILE"
    exit 1
fi

echo "服务已成功重启，日志路径: $LOG_FILE"

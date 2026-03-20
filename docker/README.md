# RuoYi-Vue Docker 部署手册

## 适用场景
仅启动“前后端容器”，后端默认继续使用你项目里的 `application.yml` / `application-druid.yml` 配置的 MySQL、Redis、EMQX（即数据库不放入 docker-compose）。

## 前置条件
1. 安装并启动 Docker（含 Docker Compose）。
2. 你的主机网络可访问项目里配置的：
   - MySQL（`ruoyi-admin/src/main/resources/application-druid.yml` 里的 `master.url`）
   - Redis（`application.yml` 里的 `spring.redis.host/port`）
   - EMQX（`application.yml` 里的 `emqx.broker`）

## 启动
在 `RuoYi-Vue/docker` 目录执行：

```powershell
cd E:\workspace\cdd-workspace\RuoYi-Vue\docker
docker compose up -d --build
```

## 访问
- 前端：`http://localhost:8080`
- 后端：`http://localhost:9999`

## 常见问题排查
1. 若前端报“后端接口连接异常 / Network Error”：检查 `ruoyi-frontend` 容器是否能访问 `backend:9999`，以及后端是否已正常启动。
2. 若后端启动失败：查看 `docker logs -f ruoYi-backend`，通常是数据库/Redis/EMQX 连接配置不通导致。


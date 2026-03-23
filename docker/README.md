# RuoYi-Vue Docker 部署手册（合并版）

## 镜像说明
- 前后端打在一个镜像里，镜像名：**`myfarm:latest`**（由 `docker-compose.yml` 中 `image: myfarm:latest` + `Dockerfile.combined` 构建）。
- 容器内：**Nginx:80** 提供前端并反向代理 **`/dev-api` → Java（9999）**；Supervisor 同时拉起 Java 与 Nginx。

## 适用场景
启动合并的“前后端容器”，后端默认继续使用你项目里的 `application.yml` / `application-druid.yml` 配置的 MySQL、Redis、EMQX（即数据库不放入 docker-compose）。

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
- 前端：`http://localhost:8080`（通过Nginx提供前端页面和API代理）
- 后端直连：`http://localhost:9999`（可选，仅用于调试）

## 常见问题排查
1. 若前端报“后端接口连接异常 / Network Error”：查看容器日志 `docker logs -f myfarm`，检查Java后端是否正常启动。
2. 若后端启动失败：查看容器日志 `docker logs -f myfarm`，通常是数据库/Redis/EMQX 连接配置不通导致。
3. 若容器启动后无法访问：检查supervisor进程状态 `docker exec myfarm supervisorctl status`。


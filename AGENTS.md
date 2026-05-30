# AGENTS.md

This file provides guidance to Codex (Codex.ai/code) when working with code in this repository.

## Project Overview

RuoYi v3.9.0 -- a Spring Boot 2.5.15 + Vue 2.6 admin management system. Java 8, Maven multi-module.

## Directory Structure

```
RuoYi-Vue/
├── ruoyi-admin/          # Web entry point, all controllers, packs executable JAR
├── ruoyi-framework/      # Spring Security, JWT, Redis, MyBatis config, AOP aspects
├── ruoyi-system/         # Domain entities, MyBatis mappers, service layer
├── ruoyi-common/         # Utilities: BaseController, AjaxResult, annotations (@Log, @DataScope, @RateLimiter)
├── ruoyi-quartz/         # Quartz scheduled task management
├── ruoyi-generator/      # Code generator (Velocity templates -> CRUD code)
├── ruoyi-ui/             # Vue 2 + Element UI web frontend
├── ruoyi-app/            # uni-app mobile frontend (H5/WeChat Mini Program/Android/iOS)
└── sql/                  # Database schema: ry_20250522.sql, quartz.sql
```

## Build & Run Commands

### Backend (Maven)
```bash
# Build all modules
mvn clean package -Dmaven.test.skip=true

# Run locally
java -jar ruoyi-admin/target/ruoyi-admin.jar

# Windows quick start
bin/package.bat      # build
bin/run.bat          # run
ry.bat               # interactive menu (start/stop/restart/status)

# Linux
./ry.sh start|stop|restart|status
```

### Web Frontend (ruoyi-ui)
```bash
cd ruoyi-ui
npm install
npm run dev          # dev server on port 80, proxies API to localhost:8080
npm run build:prod   # production build
npm run build:stage  # staging build
```

### Mobile App (ruoyi-app)
Built via HBuilderX (uni-app). Targets H5, WeChat Mini Program, Android, iOS.
API base: `https://api.cdd9527.cn` (prod) / `http://localhost:8080` (dev).

## Key Configuration

### Backend (`ruoyi-admin/src/main/resources/application.yml`)
- Server port: **8080**
- Redis: `op.cdd9527.cn:5004`
- File upload path: `D:/ruoyi/uploadPath`
- Token: JWT with `Authorization` header, 30min expiry
- Captcha type: `math` (numeric calculation)
- XSS filter: enabled for `/system/*,/monitor/*,/tool/*`

### Database (`application-druid.yml`)
- MySQL: `op.cdd9527.cn:5002/ry-vue`
- Druid pool: initial 5, min 10, max 20
- Slow SQL threshold: 1000ms
- Druid console: `/druid/*` (ruoyi/123456)

### Web Frontend (`ruoyi-ui/vue.config.js`)
- Dev server port: **80**
- API proxy: `VUE_APP_BASE_API` -> `http://localhost:8080`
- Code splitting: chunk-libs, chunk-elementUI, chunk-commons
- Gzip compression via CompressionPlugin

## Architecture Patterns

### Module Dependencies
```
ruoyi-admin -> ruoyi-framework -> ruoyi-system -> ruoyi-common
ruoyi-admin -> ruoyi-quartz
ruoyi-admin -> ruoyi-generator
```

### Layer Convention
- **Controller** (`ruoyi-admin/web/controller/`): extends `BaseController`, returns `AjaxResult` or `TableDataInfo`
- **Service** (`ruoyi-system/service/`): `I*Service` interface + `*ServiceImpl` implementation
- **Mapper** (`ruoyi-system/mapper/`): Java interface + XML at `src/main/resources/mapper/{module}/*Mapper.xml`
- **Domain** (`ruoyi-system/domain/`): entity classes, annotated for MyBatis and Swagger

### Security (`ruoyi-framework`)
- `SecurityConfig`: stateless JWT auth, CORS enabled, CSRF disabled
- `JwtAuthenticationTokenFilter`: extracts JWT from `Authorization` header
- `TokenService`: token creation/refresh using `jjwt`
- Password hashing: `BCryptPasswordEncoder`
- `@Anonymous`: skips auth for specific endpoints
- `@DataScope`: data-level permission filtering via AOP (department-based row filtering)
- `@RateLimiter`: rate limiting via Redis-backed sliding window
- `@Log`: auto-logs operation details (method, params, result, duration)

### Frontend Auth Flow
1. Login -> backend returns JWT token -> stored in Vuex + localStorage
2. `permission.js` router guard: checks token, calls `/getInfo` + `/getRouters`
3. Dynamic routes built from backend menu data (`store/modules/permission.js`)
4. `axios` interceptor adds `Authorization: Bearer <token>` to every request

### Common Utilities (`ruoyi-common`)
- `AjaxResult`: standard API response wrapper `{code, msg, data}`
- `TableDataInfo`: paginated response `{total, rows, code, msg}`
- `BaseController`: `getDataTable()`, `toAjax()`, `startPage()` helpers
- `RedisCache`: Redis operations wrapper
- `@Excel`: annotation for Excel import/export
- `@Sensitive`: data desensitization (phone, email, etc.)

## Database

Main schema: `sql/ry_20250522.sql` -- RBAC tables (`sys_user`, `sys_role`, `sys_menu`, `sys_dept`, etc.), dict, config, notice, logs.
Quartz schema: `sql/quartz.sql` -- `QRTZ_*` tables for scheduled tasks.

Default users: `admin/admin123`, `ry/admin123`.

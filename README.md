## CarDIY 三端系统本地运行说明

### 1. 目录结构

- `frontend-h5`：H5 网站，面向车主选配改装
- `frontend-admin`：管理控制台，维护车型与改装配置
- `backend`：Java 微服务（Gateway、Auth、Car、Render、Order、Admin、Common）

### 2. 前端环境安装（Windows）

1. 安装 Node.js（建议 18 LTS 以上），从官网下载安装包并完成安装。
2. PowerShell 执行策略允许 npm 脚本（以管理员身份打开 PowerShell）：
   ```powershell
   Set-ExecutionPolicy -Scope CurrentUser RemoteSigned
   ```
   之后重新打开终端。

### 3. 启动 H5 前端（5173）

```bash
cd frontend-h5
npm install
npm run dev
```

浏览器访问：`http://localhost:5173`，通过网关转发到后端。

### 4. 启动管理端前端（5174）

```bash
cd frontend-admin
npm install
npm run dev
```

浏览器访问：`http://localhost:5174`。

### 5. 后端运行环境

- JDK：Java 17
- Maven：3.8+（已配置到 `PATH`）
- MongoDB：本地 `mongodb://localhost:27017`，库名 `cardiy`
- MySQL（可选，仅订单服务使用）：本地 `cardiy` 数据库，用户名/密码默认 `root/root`
- Nacos（可选）：`localhost:8848`
- Seata Server（可选）：`127.0.0.1:8091`
- SkyWalking（可选）：APM 采集与 UI

### 6. 构建与启动后端

1. 在 `backend` 目录下构建：
   ```bash
   cd backend
   mvn clean install
   ```
2. 启动网关与核心服务（每个服务开一个终端，或在 IDE 中分别运行）：
   ```bash
   # 网关 8080
   cd backend/cardiy-gateway
   mvn spring-boot:run

   # 车型与改装配置服务 8081
   cd backend/cardiy-car
   mvn spring-boot:run

   # 管理服务 8085
   cd backend/cardiy-admin
   mvn spring-boot:run
   ```

如需演示完整架构，可再启动：

```bash
# 认证服务 8082
cd backend/cardiy-auth
mvn spring-boot:run

# 渲染服务 8084
cd backend/cardiy-render
mvn spring-boot:run

# 订单服务 8086（需要本地 MySQL + Seata）
cd backend/cardiy-order
mvn spring-boot:run
```

### 7. Nacos / Seata / SkyWalking 快速接入指引（可选）

- **Nacos**
  - 下载 Nacos Server（standalone 版），解压后执行 `startup.cmd -m standalone`。
  - 在各服务的 `application.yml` 中取消注释 `spring.cloud.nacos.discovery` 配置，并在网关中把静态 URL 换为 `lb://服务名`。
- **Seata**
  - 下载 Seata Server，修改 `application.yml` 中 `seata.service.grouplist` 等配置与订单服务保持一致，启动 `seata-server.bat`。
  - 在需要分布式事务的业务方法上增加 `@GlobalTransactional`。
- **SkyWalking**
  - 启动 SkyWalking OAP 与 UI。
  - 对每个后端服务 JVM 增加 agent 参数，例如：
    ```bash
    -javaagent:/path/to/skywalking-agent.jar
    -Dskywalking.agent.service_name=cardiy-gateway
    -Dskywalking.collector.backend_service=127.0.0.1:11800
    ```

### 8. 核心接口对照

- H5 前端：
  - `GET /CardiyCar/api/models` → `cardiy-car` 车型列表
  - `POST /CardiyCar/api/plans` → `cardiy-car` 保存改装方案
- 管理端：
  - `GET /api/admin/stats` → `cardiy-admin` 概览统计
  - `GET /api/admin/models` / `POST /api/admin/models` → 车型管理



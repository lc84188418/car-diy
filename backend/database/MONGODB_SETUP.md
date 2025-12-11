# MongoDB 数据库初始化指南

## 快速开始

### 1. 确保MongoDB已安装并运行

```bash
# Windows
# 启动MongoDB服务（如果已安装为服务）
net start MongoDB

# 或者直接运行mongod
mongod --dbpath "C:\data\db"
```

### 2. 执行初始化脚本

#### 方法1：使用 mongosh（推荐，MongoDB 5.x+）
```bash
mongosh cardiy --file backend/database/init-mongodb.js
```

#### 方法2：使用 mongo shell（MongoDB 4.x）
```bash
mongo cardiy backend/database/init-mongodb.js
```

#### 方法3：在MongoDB Compass中执行
1. 打开 MongoDB Compass
2. 连接到 `mongodb://localhost:27017`
3. 点击 "MONGOSH" 标签
4. 复制 `init-mongodb.js` 内容并执行

### 3. 验证初始化

在 mongosh 中执行：
```javascript
use cardiy;

// 检查用户数量
db.sys_user.countDocuments();

// 检查车型数量
db.car_model.countDocuments();

// 查看用户列表
db.sys_user.find().pretty();
```

## 数据库连接配置

### 开发环境（无认证）
```yaml
spring:
  data:
    mongodb:
      uri: mongodb://localhost:27017/cardiy
```

### 生产环境（有认证）
```yaml
spring:
  data:
    mongodb:
      uri: mongodb://username:password@localhost:27017/cardiy?authSource=admin
```

## 集合说明

### 系统管理集合
- `sys_dept` - 部门表
- `sys_user` - 用户信息表
- `sys_role` - 角色信息表
- `sys_menu` - 菜单权限表
- `sys_user_role` - 用户和角色关联表
- `sys_role_menu` - 角色和菜单关联表
- `sys_post` - 岗位表
- `sys_user_post` - 用户与岗位关联表
- `sys_dict_type` - 字典类型表
- `sys_dict_data` - 字典数据表
- `sys_config` - 参数配置表
- `sys_oper_log` - 操作日志记录
- `sys_logininfor` - 登录日志

### 业务集合
- `car_model` - 车型表
- `car_model_option` - 车型配置选项表

## 默认账号

- 管理员账号：`admin`
- 密码：`123456`（已加密存储）
- 测试账号：`test`
- 密码：`123456`（已加密存储）

## 注意事项

1. **数据备份**：执行脚本前建议备份现有数据
2. **索引**：所有集合都已创建必要的索引
3. **ID类型**：MongoDB使用ObjectId或自定义ID，本系统使用Long类型ID
4. **时区**：所有时间字段使用LocalDateTime，注意时区设置

## 常见问题

### 1. 连接失败
- 检查MongoDB服务是否启动
- 检查端口27017是否被占用
- 检查防火墙设置

### 2. 认证失败
- 检查用户名密码是否正确
- 检查authSource参数是否正确

### 3. 数据未初始化
- 检查脚本是否执行成功
- 检查数据库名称是否正确
- 查看MongoDB日志


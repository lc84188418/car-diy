# MongoDB 数据库初始化说明

## 使用方法

### 方法1：使用 mongo shell（MongoDB 4.x及以下）
```bash
mongo cardiy init-mongodb.md
```

### 方法2：使用 mongosh（MongoDB 5.x及以上）
```bash
mongosh cardiy init-mongodb.md
```

### 方法3：在 MongoDB Compass 中执行
1. 打开 MongoDB Compass
2. 连接到 MongoDB 服务器
3. 选择 `cardiy` 数据库（如果不存在会自动创建）
4. 打开 MongoDB Shell
5. 复制 `init-mongodb.md` 文件内容并执行

## 数据库结构

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

## 注意事项

1. 执行脚本前确保 MongoDB 服务已启动
2. 脚本会先删除已存在的集合，然后重新创建
3. 所有集合都已创建必要的索引
4. 初始化数据包含管理员账号：admin/123456（密码已加密）

## 字段更新脚本

### 将 phonenumber 字段重命名为 tel

如果数据库中的 `sys_user` 集合使用的是 `phonenumber` 字段，需要将其重命名为 `tel`：

**使用方法：**

```bash
# 使用 mongosh（MongoDB 5.x及以上）
mongosh cardiy update-sys-user-phonenumber-to-tel.md

# 或使用 mongo shell（MongoDB 4.x及以下）
mongo cardiy update-sys-user-phonenumber-to-tel.md
```

**脚本功能：**
- 将 `sys_user` 集合中所有文档的 `phonenumber` 字段重命名为 `tel`
- 只更新包含 `phonenumber` 字段的文档
- 显示更新结果和示例文档

## 验证初始化

执行以下命令验证数据是否初始化成功：

```javascript
use cardiy;

// 检查用户数量
db.sys_user.countDocuments();

// 检查车型数量
db.car_model.countDocuments();

// 检查配置选项数量
db.car_model_option.countDocuments();
```


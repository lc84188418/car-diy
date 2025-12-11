# MongoDB 数据库迁移说明

## ✅ 已完成的修改

### 1. 数据库脚本
- ✅ 创建了 MongoDB 初始化脚本：`backend/database/init-mongodb.md`
- ✅ 包含所有15个集合的初始化数据和索引
- ✅ 删除了旧的MySQL SQL脚本

### 2. 后端实体类更新
- ✅ `BaseEntity` - 使用Spring Data MongoDB注解（@CreatedDate, @LastModifiedDate等）
- ✅ `SysUser` - 使用@Document和@Id注解
- ✅ `SysRole` - 使用@Document和@Id注解
- ✅ `SysMenu` - 使用@Document和@Id注解，@Transient标记子菜单
- ✅ `CarModel` - 使用@Document和@Id注解
- ✅ `CarModelOption` - 使用@Document和@Id注解

### 3. Repository层更新
- ✅ `SysUserMapper` - 改为继承 `MongoRepository<SysUser, Long>`
- ✅ `CarModelMapper` - 改为继承 `MongoRepository<CarModel, String>`
- ✅ `CarModelOptionMapper` - 改为继承 `MongoRepository<CarModelOption, Long>`
- ✅ 添加了自定义查询方法（findByUserName, findByStatus等）

### 4. Service层更新
- ✅ `ISysUserService` - 改为使用Spring Data MongoDB的方法
- ✅ `SysUserServiceImpl` - 实现MongoDB相关方法
- ✅ `ICarModelService` - 改为使用MongoDB方法
- ✅ `CarModelServiceImpl` - 实现MongoDB相关方法

### 5. Controller层更新
- ✅ `SysUserController` - 使用Spring Data的Page和Pageable
- ✅ `CarController` - 使用MongoDB查询方法

### 6. 配置文件更新
- ✅ `cardiy-admin/application.yml` - 配置MongoDB连接
- ✅ `cardiy-car/application.yml` - 配置MongoDB连接
- ✅ 移除了MyBatis-Plus和MySQL相关配置

### 7. 依赖更新
- ✅ `cardiy-admin/pom.xml` - 移除了MyBatis-Plus和MySQL依赖
- ✅ `cardiy-car/pom.xml` - 移除了MyBatis-Plus和MySQL依赖
- ✅ 保留了Spring Data MongoDB依赖（已在父pom中）

## 📋 使用步骤

### 1. 启动MongoDB
```bash
# Windows
net start MongoDB
# 或
mongod --dbpath "C:\data\db"
```

### 2. 执行初始化脚本
```bash
# MongoDB 5.x+
mongosh cardiy --file backend/database/init-mongodb.md

# MongoDB 4.x
mongo cardiy backend/database/init-mongodb.md
```

### 3. 验证数据
```javascript
use cardiy;
db.sys_user.countDocuments();  // 应该返回2
db.car_model.countDocuments(); // 应该返回2
```

### 4. 启动后端服务
```bash
# 启动Gateway
cd backend/cardiy-gateway
mvn spring-boot:run

# 启动Car服务
cd backend/cardiy-car
mvn spring-boot:run

# 启动Admin服务
cd backend/cardiy-admin
mvn spring-boot:run
```

## 🔄 主要变化

### 实体类变化
- **之前**：使用MyBatis-Plus注解（@TableName, @TableId等）
- **现在**：使用Spring Data MongoDB注解（@Document, @Id等）

### Repository变化
- **之前**：继承 `BaseMapper<T>`
- **现在**：继承 `MongoRepository<T, ID>`

### Service变化
- **之前**：使用MyBatis-Plus的IService接口
- **现在**：自定义接口，使用MongoRepository的方法

### 查询方式变化
- **之前**：使用LambdaQueryWrapper构建查询
- **现在**：使用方法命名查询或MongoTemplate

## ⚠️ 注意事项

1. **ID类型**：MongoDB中ID可以是ObjectId或自定义类型，本系统使用Long/String
2. **时间字段**：使用LocalDateTime，MongoDB会自动转换为Date
3. **索引**：已在初始化脚本中创建，如需修改请手动执行
4. **关联查询**：MongoDB不支持JOIN，需要手动查询关联数据

## 🐛 常见问题

### 1. 连接失败
- 检查MongoDB服务是否启动
- 检查application.yml中的连接字符串
- 检查端口27017是否被占用

### 2. 数据查询为空
- 确认已执行初始化脚本
- 检查数据库名称是否为cardiy
- 查看MongoDB日志

### 3. 编译错误
- 确保已移除MyBatis-Plus相关导入
- 确保已添加Spring Data MongoDB依赖
- 清理并重新编译：`mvn clean install`

## 📚 参考文档

- [Spring Data MongoDB文档](https://docs.spring.io/spring-data/mongodb/docs/current/reference/html/)
- [MongoDB Java驱动文档](https://mongodb.github.io/mongo-java-driver/)
- 初始化脚本说明：`backend/database/README.md`


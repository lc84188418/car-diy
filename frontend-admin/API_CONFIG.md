# 前端 API 配置说明

## 代理配置

### Vite 代理配置 (vite.config.mts)

所有后端服务的代理配置：

```typescript
proxy: {
  "/CardiyCar": {
    target: "http://localhost:8080",
    changeOrigin: true
  },
  "/CardiyRender": {
    target: "http://localhost:8080",
    changeOrigin: true
  },
  "/CardiyAdmin": {
    target: "http://localhost:8080",
    changeOrigin: true
  },
  "/CardiyOrder": {
    target: "http://localhost:8080",
    changeOrigin: true
  }
}
```

### Axios 配置 (utils/request.js)

管理端 API 的 baseURL：

```javascript
baseURL: "/CardiyAdmin"  // 对应后端的 servlet-path
```

## API 路径规则

### 管理端 API (CardiyAdmin)

所有管理端 API 的路径格式：
- **baseURL**: `/CardiyAdmin`
- **API 路径**: `/api/admin/system/...`
- **完整路径**: `/CardiyAdmin/api/admin/system/...`
- **实际请求**: `http://localhost:8080/CardiyAdmin/api/admin/system/...`

### 示例

#### 用户管理 API
```javascript
// API 文件: src/api/system/user.js
listUser(query) {
  return request({
    url: "/api/admin/system/user/list",  // 注意：不包含 /CardiyAdmin
    method: "get",
    params: query
  });
}
```

实际请求路径：
- 前端发起：`/CardiyAdmin/api/admin/system/user/list`
- Vite 代理转发：`http://localhost:8080/CardiyAdmin/api/admin/system/user/list`

## 其他服务 API

### 车型服务 (CardiyCar)
```javascript
// 使用独立的 axios 实例
const carHttp = axios.create({
  baseURL: "/CardiyCar",
  timeout: 5000
});
```

### 渲染服务 (CardiyRender)
```javascript
const renderHttp = axios.create({
  baseURL: "/CardiyRender",
  timeout: 8000
});
```

## 注意事项

1. **不要重复 servlet-path**：API 路径中不要包含 `/CardiyAdmin`，因为 baseURL 已经包含了
2. **代理路径匹配**：Vite 代理会根据请求路径的前缀进行匹配
3. **路径拼接**：`baseURL + url` = 完整请求路径

## 调试技巧

如果接口无法访问，检查：

1. **浏览器 Network 面板**：
   - 查看实际请求的 URL
   - 检查请求是否被正确代理

2. **后端日志**：
   - 确认请求是否到达后端
   - 检查 servlet-path 是否正确

3. **代理配置**：
   - 确认 vite.config.mts 中的代理配置
   - 确认 target 地址是否正确


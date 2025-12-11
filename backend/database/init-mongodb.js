// CarDIY 系统 MongoDB 初始化脚本
// 使用方法：mongo cardiy init-mongodb.js

// 切换到 cardiy 数据库（如果不存在会自动创建）
use cardiy;

// ==================== 1. 部门表 (sys_dept) ====================
db.sys_dept.drop();
db.sys_dept.insertMany([
  {
    deptId: 100,
    parentId: 0,
    ancestors: "0",
    deptName: "CarDIY科技",
    orderNum: 0,
    leader: "admin",
    phone: "15888888888",
    email: "cardiy@qq.com",
    status: "0",
    delFlag: "0",
    createBy: "admin",
    createTime: new Date(),
    updateBy: "",
    updateTime: null
  },
  {
    deptId: 101,
    parentId: 100,
    ancestors: "0,100",
    deptName: "深圳总公司",
    orderNum: 1,
    leader: "admin",
    phone: "15888888888",
    email: "cardiy@qq.com",
    status: "0",
    delFlag: "0",
    createBy: "admin",
    createTime: new Date(),
    updateBy: "",
    updateTime: null
  },
  {
    deptId: 102,
    parentId: 100,
    ancestors: "0,100",
    deptName: "长沙分公司",
    orderNum: 2,
    leader: "admin",
    phone: "15888888888",
    email: "cardiy@qq.com",
    status: "0",
    delFlag: "0",
    createBy: "admin",
    createTime: new Date(),
    updateBy: "",
    updateTime: null
  }
]);

// 创建索引
db.sys_dept.createIndex({ deptId: 1 }, { unique: true });
db.sys_dept.createIndex({ parentId: 1 });

// ==================== 2. 用户信息表 (sys_user) ====================
db.sys_user.drop();
db.sys_user.insertMany([
  {
    userId: 1,
    deptId: 103,
    userName: "admin",
    nickName: "管理员",
    userType: "00",
    email: "admin@163.com",
    tel: "15888888888",
    sex: "1",
    avatar: "",
    password: "$2a$10$7JB720yubVSOfvVaMWxKvOqQK8qJqJqJqJqJqJqJqJqJqJqJqJqJqJq",
    status: "0",
    delFlag: "0",
    loginIp: "127.0.0.1",
    loginDate: new Date(),
    createBy: "admin",
    createTime: new Date(),
    updateBy: "",
    updateTime: null,
    remark: "管理员"
  },
  {
    userId: 2,
    deptId: 105,
    userName: "test",
    nickName: "测试",
    userType: "00",
    email: "test@qq.com",
    tel: "15666666666",
    sex: "1",
    avatar: "",
    password: "$2a$10$7JB720yubVSOfvVaMWxKvOqQK8qJqJqJqJqJqJqJqJqJqJqJqJqJqJq",
    status: "0",
    delFlag: "0",
    loginIp: "127.0.0.1",
    loginDate: new Date(),
    createBy: "admin",
    createTime: new Date(),
    updateBy: "",
    updateTime: null,
    remark: "测试员"
  }
]);

// 创建索引
db.sys_user.createIndex({ userId: 1 }, { unique: true });
db.sys_user.createIndex({ userName: 1 }, { unique: true });
db.sys_user.createIndex({ deptId: 1 });

// ==================== 3. 角色信息表 (sys_role) ====================
db.sys_role.drop();
db.sys_role.insertMany([
  {
    roleId: 1,
    roleName: "超级管理员",
    roleKey: "admin",
    roleSort: 1,
    dataScope: "1",
    menuCheckStrictly: true,
    deptCheckStrictly: true,
    status: "0",
    delFlag: "0",
    createBy: "admin",
    createTime: new Date(),
    updateBy: "",
    updateTime: null,
    remark: "超级管理员"
  },
  {
    roleId: 2,
    roleName: "普通角色",
    roleKey: "common",
    roleSort: 2,
    dataScope: "2",
    menuCheckStrictly: true,
    deptCheckStrictly: true,
    status: "0",
    delFlag: "0",
    createBy: "admin",
    createTime: new Date(),
    updateBy: "",
    updateTime: null,
    remark: "普通角色"
  }
]);

// 创建索引
db.sys_role.createIndex({ roleId: 1 }, { unique: true });
db.sys_role.createIndex({ roleKey: 1 }, { unique: true });

// ==================== 4. 菜单权限表 (sys_menu) ====================
db.sys_menu.drop();
db.sys_menu.insertMany([
  {
    menuId: 1,
    menuName: "系统管理",
    parentId: 0,
    orderNum: 1,
    path: "system",
    component: null,
    query: null,
    isFrame: 1,
    isCache: 0,
    menuType: "M",
    visible: "0",
    status: "0",
    perms: "",
    icon: "system",
    createBy: "admin",
    createTime: new Date(),
    updateBy: "",
    updateTime: null,
    remark: "系统管理目录"
  },
  {
    menuId: 100,
    menuName: "用户管理",
    parentId: 1,
    orderNum: 1,
    path: "user",
    component: "system/user/index",
    query: null,
    isFrame: 1,
    isCache: 0,
    menuType: "C",
    visible: "0",
    status: "0",
    perms: "system:user:list",
    icon: "user",
    createBy: "admin",
    createTime: new Date(),
    updateBy: "",
    updateTime: null,
    remark: "用户管理菜单"
  },
  {
    menuId: 101,
    menuName: "角色管理",
    parentId: 1,
    orderNum: 2,
    path: "role",
    component: "system/role/index",
    query: null,
    isFrame: 1,
    isCache: 0,
    menuType: "C",
    visible: "0",
    status: "0",
    perms: "system:role:list",
    icon: "peoples",
    createBy: "admin",
    createTime: new Date(),
    updateBy: "",
    updateTime: null,
    remark: "角色管理菜单"
  },
  {
    menuId: 102,
    menuName: "菜单管理",
    parentId: 1,
    orderNum: 3,
    path: "menu",
    component: "system/menu/index",
    query: null,
    isFrame: 1,
    isCache: 0,
    menuType: "C",
    visible: "0",
    status: "0",
    perms: "system:menu:list",
    icon: "tree-table",
    createBy: "admin",
    createTime: new Date(),
    updateBy: "",
    updateTime: null,
    remark: "菜单管理菜单"
  },
  {
    menuId: 103,
    menuName: "部门管理",
    parentId: 1,
    orderNum: 4,
    path: "dept",
    component: "system/dept/index",
    query: null,
    isFrame: 1,
    isCache: 0,
    menuType: "C",
    visible: "0",
    status: "0",
    perms: "system:dept:list",
    icon: "tree",
    createBy: "admin",
    createTime: new Date(),
    updateBy: "",
    updateTime: null,
    remark: "部门管理菜单"
  },
  {
    menuId: 104,
    menuName: "岗位管理",
    parentId: 1,
    orderNum: 5,
    path: "post",
    component: "system/post/index",
    query: null,
    isFrame: 1,
    isCache: 0,
    menuType: "C",
    visible: "0",
    status: "0",
    perms: "system:post:list",
    icon: "post",
    createBy: "admin",
    createTime: new Date(),
    updateBy: "",
    updateTime: null,
    remark: "岗位管理菜单"
  },
  {
    menuId: 105,
    menuName: "字典管理",
    parentId: 1,
    orderNum: 6,
    path: "dict",
    component: "system/dict/index",
    query: null,
    isFrame: 1,
    isCache: 0,
    menuType: "C",
    visible: "0",
    status: "0",
    perms: "system:dict:list",
    icon: "dict",
    createBy: "admin",
    createTime: new Date(),
    updateBy: "",
    updateTime: null,
    remark: "字典管理菜单"
  },
  {
    menuId: 106,
    menuName: "参数设置",
    parentId: 1,
    orderNum: 7,
    path: "config",
    component: "system/config/index",
    query: null,
    isFrame: 1,
    isCache: 0,
    menuType: "C",
    visible: "0",
    status: "0",
    perms: "system:config:list",
    icon: "edit",
    createBy: "admin",
    createTime: new Date(),
    updateBy: "",
    updateTime: null,
    remark: "参数设置菜单"
  },
  {
    menuId: 107,
    menuName: "操作日志",
    parentId: 1,
    orderNum: 8,
    path: "operlog",
    component: "system/operlog/index",
    query: null,
    isFrame: 1,
    isCache: 0,
    menuType: "C",
    visible: "0",
    status: "0",
    perms: "system:operlog:list",
    icon: "form",
    createBy: "admin",
    createTime: new Date(),
    updateBy: "",
    updateTime: null,
    remark: "操作日志菜单"
  },
  {
    menuId: 108,
    menuName: "登录日志",
    parentId: 1,
    orderNum: 9,
    path: "logininfor",
    component: "system/logininfor/index",
    query: null,
    isFrame: 1,
    isCache: 0,
    menuType: "C",
    visible: "0",
    status: "0",
    perms: "system:logininfor:list",
    icon: "logininfor",
    createBy: "admin",
    createTime: new Date(),
    updateBy: "",
    updateTime: null,
    remark: "登录日志菜单"
  }
]);

// 创建索引
db.sys_menu.createIndex({ menuId: 1 }, { unique: true });
db.sys_menu.createIndex({ parentId: 1 });

// ==================== 5. 用户和角色关联表 (sys_user_role) ====================
db.sys_user_role.drop();
db.sys_user_role.insertMany([
  { userId: 1, roleId: 1 },
  { userId: 2, roleId: 2 }
]);

// 创建索引
db.sys_user_role.createIndex({ userId: 1, roleId: 1 }, { unique: true });

// ==================== 6. 角色和菜单关联表 (sys_role_menu) ====================
db.sys_role_menu.drop();
db.sys_role_menu.insertMany([
  { roleId: 1, menuId: 1 },
  { roleId: 1, menuId: 100 },
  { roleId: 1, menuId: 101 },
  { roleId: 1, menuId: 102 },
  { roleId: 1, menuId: 103 },
  { roleId: 1, menuId: 104 },
  { roleId: 1, menuId: 105 },
  { roleId: 1, menuId: 106 },
  { roleId: 1, menuId: 107 },
  { roleId: 1, menuId: 108 }
]);

// 创建索引
db.sys_role_menu.createIndex({ roleId: 1, menuId: 1 }, { unique: true });

// ==================== 7. 岗位表 (sys_post) ====================
db.sys_post.drop();
db.sys_post.insertMany([
  {
    postId: 1,
    postCode: "ceo",
    postName: "董事长",
    postSort: 1,
    status: "0",
    createBy: "admin",
    createTime: new Date(),
    updateBy: "",
    updateTime: null,
    remark: ""
  },
  {
    postId: 2,
    postCode: "se",
    postName: "项目经理",
    postSort: 2,
    status: "0",
    createBy: "admin",
    createTime: new Date(),
    updateBy: "",
    updateTime: null,
    remark: ""
  },
  {
    postId: 3,
    postCode: "hr",
    postName: "人力资源",
    postSort: 3,
    status: "0",
    createBy: "admin",
    createTime: new Date(),
    updateBy: "",
    updateTime: null,
    remark: ""
  },
  {
    postId: 4,
    postCode: "user",
    postName: "普通员工",
    postSort: 4,
    status: "0",
    createBy: "admin",
    createTime: new Date(),
    updateBy: "",
    updateTime: null,
    remark: ""
  }
]);

// 创建索引
db.sys_post.createIndex({ postId: 1 }, { unique: true });
db.sys_post.createIndex({ postCode: 1 }, { unique: true });

// ==================== 8. 用户与岗位关联表 (sys_user_post) ====================
db.sys_user_post.drop();
// 初始化数据为空，根据实际需要添加

// 创建索引
db.sys_user_post.createIndex({ userId: 1, postId: 1 }, { unique: true });

// ==================== 9. 字典类型表 (sys_dict_type) ====================
db.sys_dict_type.drop();
db.sys_dict_type.insertMany([
  {
    dictId: 1,
    dictName: "用户性别",
    dictType: "sys_user_sex",
    status: "0",
    createBy: "admin",
    createTime: new Date(),
    updateBy: "",
    updateTime: null,
    remark: "用户性别列表"
  },
  {
    dictId: 2,
    dictName: "菜单状态",
    dictType: "sys_show_hide",
    status: "0",
    createBy: "admin",
    createTime: new Date(),
    updateBy: "",
    updateTime: null,
    remark: "菜单状态列表"
  },
  {
    dictId: 3,
    dictName: "系统开关",
    dictType: "sys_normal_disable",
    status: "0",
    createBy: "admin",
    createTime: new Date(),
    updateBy: "",
    updateTime: null,
    remark: "系统开关列表"
  }
]);

// 创建索引
db.sys_dict_type.createIndex({ dictId: 1 }, { unique: true });
db.sys_dict_type.createIndex({ dictType: 1 }, { unique: true });

// ==================== 10. 字典数据表 (sys_dict_data) ====================
db.sys_dict_data.drop();
db.sys_dict_data.insertMany([
  {
    dictCode: 1,
    dictSort: 1,
    dictLabel: "男",
    dictValue: "0",
    dictType: "sys_user_sex",
    cssClass: "",
    listClass: "primary",
    isDefault: "Y",
    status: "0",
    createBy: "admin",
    createTime: new Date(),
    updateBy: "",
    updateTime: null,
    remark: "性别男"
  },
  {
    dictCode: 2,
    dictSort: 2,
    dictLabel: "女",
    dictValue: "1",
    dictType: "sys_user_sex",
    cssClass: "",
    listClass: "danger",
    isDefault: "N",
    status: "0",
    createBy: "admin",
    createTime: new Date(),
    updateBy: "",
    updateTime: null,
    remark: "性别女"
  },
  {
    dictCode: 3,
    dictSort: 3,
    dictLabel: "未知",
    dictValue: "2",
    dictType: "sys_user_sex",
    cssClass: "",
    listClass: "info",
    isDefault: "N",
    status: "0",
    createBy: "admin",
    createTime: new Date(),
    updateBy: "",
    updateTime: null,
    remark: "性别未知"
  },
  {
    dictCode: 4,
    dictSort: 1,
    dictLabel: "显示",
    dictValue: "0",
    dictType: "sys_show_hide",
    cssClass: "",
    listClass: "primary",
    isDefault: "Y",
    status: "0",
    createBy: "admin",
    createTime: new Date(),
    updateBy: "",
    updateTime: null,
    remark: "显示菜单"
  },
  {
    dictCode: 5,
    dictSort: 2,
    dictLabel: "隐藏",
    dictValue: "1",
    dictType: "sys_show_hide",
    cssClass: "",
    listClass: "danger",
    isDefault: "N",
    status: "0",
    createBy: "admin",
    createTime: new Date(),
    updateBy: "",
    updateTime: null,
    remark: "隐藏菜单"
  },
  {
    dictCode: 6,
    dictSort: 1,
    dictLabel: "正常",
    dictValue: "0",
    dictType: "sys_normal_disable",
    cssClass: "",
    listClass: "primary",
    isDefault: "Y",
    status: "0",
    createBy: "admin",
    createTime: new Date(),
    updateBy: "",
    updateTime: null,
    remark: "正常状态"
  },
  {
    dictCode: 7,
    dictSort: 2,
    dictLabel: "停用",
    dictValue: "1",
    dictType: "sys_normal_disable",
    cssClass: "",
    listClass: "danger",
    isDefault: "N",
    status: "0",
    createBy: "admin",
    createTime: new Date(),
    updateBy: "",
    updateTime: null,
    remark: "停用状态"
  }
]);

// 创建索引
db.sys_dict_data.createIndex({ dictCode: 1 }, { unique: true });
db.sys_dict_data.createIndex({ dictType: 1 });

// ==================== 11. 参数配置表 (sys_config) ====================
db.sys_config.drop();
db.sys_config.insertMany([
  {
    configId: 1,
    configName: "主框架页-默认皮肤样式名称",
    configKey: "sys.index.skinName",
    configValue: "skin-blue",
    configType: "Y",
    createBy: "admin",
    createTime: new Date(),
    updateBy: "",
    updateTime: null,
    remark: "蓝色 skin-blue、绿色 skin-green、紫色 skin-purple、红色 skin-red、黄色 skin-yellow"
  },
  {
    configId: 2,
    configName: "用户管理-账号初始密码",
    configKey: "sys.user.initPassword",
    configValue: "123456",
    configType: "Y",
    createBy: "admin",
    createTime: new Date(),
    updateBy: "",
    updateTime: null,
    remark: "初始化密码 123456"
  }
]);

// 创建索引
db.sys_config.createIndex({ configId: 1 }, { unique: true });
db.sys_config.createIndex({ configKey: 1 }, { unique: true });

// ==================== 12. 操作日志记录 (sys_oper_log) ====================
db.sys_oper_log.drop();
// 初始化数据为空，运行时自动添加

// 创建索引
db.sys_oper_log.createIndex({ operId: 1 }, { unique: true });
db.sys_oper_log.createIndex({ operTime: -1 });

// ==================== 13. 登录日志 (sys_logininfor) ====================
db.sys_logininfor.drop();
// 初始化数据为空，运行时自动添加

// 创建索引
db.sys_logininfor.createIndex({ infoId: 1 }, { unique: true });
db.sys_logininfor.createIndex({ loginTime: -1 });

// ==================== 14. 车型表 (car_model) ====================
db.car_model.drop();
db.car_model.insertMany([
  {
    id: "model-1",
    brand: "宝马",
    series: "3 系",
    year: "2023",
    mainImageUrl: "https://images.unsplash.com/photo-1555215695-3004980ad54e?w=800&h=600&fit=crop",
    description: "运动豪华轿车，完美平衡性能与舒适",
    status: "0",
    createBy: "admin",
    createTime: new Date(),
    updateBy: "",
    updateTime: null
  },
  {
    id: "model-2",
    brand: "奔驰",
    series: "C 级",
    year: "2024",
    mainImageUrl: "https://images.unsplash.com/photo-1618843479313-40f8afb4b4d8?w=800&h=600&fit=crop",
    description: "豪华与科技的完美融合",
    status: "0",
    createBy: "admin",
    createTime: new Date(),
    updateBy: "",
    updateTime: null
  }
]);

// 创建索引
db.car_model.createIndex({ id: 1 }, { unique: true });
db.car_model.createIndex({ brand: 1, series: 1 });

// ==================== 15. 车型配置选项表 (car_model_option) ====================
db.car_model_option.drop();
db.car_model_option.insertMany([
  // 宝马3系 - 车漆颜色
  {
    id: 1,
    modelId: "model-1",
    optionType: "paintColor",
    code: "white",
    name: "珍珠白",
    previewColor: "#f9fafb",
    imageUrl: "https://images.unsplash.com/photo-1552519507-da3b142c6e3d?w=400&h=300&fit=crop",
    description: "经典白色，优雅永不过时",
    sortOrder: 1,
    status: "0",
    createBy: "admin",
    createTime: new Date(),
    updateBy: "",
    updateTime: null
  },
  {
    id: 2,
    modelId: "model-1",
    optionType: "paintColor",
    code: "black",
    name: "曜夜黑",
    previewColor: "#020617",
    imageUrl: "https://images.unsplash.com/photo-1549317661-bd32c8ce0db2?w=400&h=300&fit=crop",
    description: "深邃黑色，彰显运动气质",
    sortOrder: 2,
    status: "0",
    createBy: "admin",
    createTime: new Date(),
    updateBy: "",
    updateTime: null
  },
  {
    id: 3,
    modelId: "model-1",
    optionType: "paintColor",
    code: "blue",
    name: "波尔蒂芒蓝",
    previewColor: "#1e40af",
    imageUrl: "https://images.unsplash.com/photo-1552519507-88aa2dfa9fdb?w=400&h=300&fit=crop",
    description: "活力蓝色，年轻时尚",
    sortOrder: 3,
    status: "0",
    createBy: "admin",
    createTime: new Date(),
    updateBy: "",
    updateTime: null
  },
  // 宝马3系 - 轮毂
  {
    id: 4,
    modelId: "model-1",
    optionType: "rim",
    code: "rim-18",
    name: "18 寸运动轮毂",
    previewColor: null,
    imageUrl: "https://images.unsplash.com/photo-1558618666-fcd25c85cd64?w=400&h=300&fit=crop",
    description: "五辐式设计，运动感十足",
    sortOrder: 1,
    status: "0",
    createBy: "admin",
    createTime: new Date(),
    updateBy: "",
    updateTime: null
  },
  {
    id: 5,
    modelId: "model-1",
    optionType: "rim",
    code: "rim-19",
    name: "19 寸双色轮毂",
    previewColor: null,
    imageUrl: "https://images.unsplash.com/photo-1558618666-fcd25c85cd64?w=400&h=300&fit=crop",
    description: "双色设计，更显豪华",
    sortOrder: 2,
    status: "0",
    createBy: "admin",
    createTime: new Date(),
    updateBy: "",
    updateTime: null
  },
  // 宝马3系 - 轮胎
  {
    id: 6,
    modelId: "model-1",
    optionType: "tire",
    code: "sport-tire",
    name: "运动胎",
    previewColor: null,
    imageUrl: "https://images.unsplash.com/photo-1558618666-fcd25c85cd64?w=400&h=300&fit=crop",
    description: "高性能运动轮胎，抓地力强",
    sortOrder: 1,
    status: "0",
    createBy: "admin",
    createTime: new Date(),
    updateBy: "",
    updateTime: null
  },
  {
    id: 7,
    modelId: "model-1",
    optionType: "tire",
    code: "comfort-tire",
    name: "舒适胎",
    previewColor: null,
    imageUrl: "https://images.unsplash.com/photo-1558618666-fcd25c85cd64?w=400&h=300&fit=crop",
    description: "静音舒适，适合日常驾驶",
    sortOrder: 2,
    status: "0",
    createBy: "admin",
    createTime: new Date(),
    updateBy: "",
    updateTime: null
  },
  // 宝马3系 - 卡钳
  {
    id: 8,
    modelId: "model-1",
    optionType: "caliper",
    code: "red-caliper",
    name: "红色高性能卡钳",
    previewColor: null,
    imageUrl: "https://images.unsplash.com/photo-1558618666-fcd25c85cd64?w=400&h=300&fit=crop",
    description: "红色卡钳，性能与美观并存",
    sortOrder: 1,
    status: "0",
    createBy: "admin",
    createTime: new Date(),
    updateBy: "",
    updateTime: null
  },
  {
    id: 9,
    modelId: "model-1",
    optionType: "caliper",
    code: "blue-caliper",
    name: "蓝色高性能卡钳",
    previewColor: null,
    imageUrl: "https://images.unsplash.com/photo-1558618666-fcd25c85cd64?w=400&h=300&fit=crop",
    description: "蓝色卡钳，低调奢华",
    sortOrder: 2,
    status: "0",
    createBy: "admin",
    createTime: new Date(),
    updateBy: "",
    updateTime: null
  },
  // 奔驰C级 - 车漆颜色
  {
    id: 10,
    modelId: "model-2",
    optionType: "paintColor",
    code: "silver",
    name: "皓沙银",
    previewColor: "#e5e7eb",
    imageUrl: "https://images.unsplash.com/photo-1552519507-da3b142c6e3d?w=400&h=300&fit=crop",
    description: "经典银色，商务首选",
    sortOrder: 1,
    status: "0",
    createBy: "admin",
    createTime: new Date(),
    updateBy: "",
    updateTime: null
  },
  {
    id: 11,
    modelId: "model-2",
    optionType: "paintColor",
    code: "black",
    name: "曜石黑",
    previewColor: "#020617",
    imageUrl: "https://images.unsplash.com/photo-1549317661-bd32c8ce0db2?w=400&h=300&fit=crop",
    description: "深邃黑色，尽显尊贵",
    sortOrder: 2,
    status: "0",
    createBy: "admin",
    createTime: new Date(),
    updateBy: "",
    updateTime: null
  },
  // 奔驰C级 - 轮毂
  {
    id: 12,
    modelId: "model-2",
    optionType: "rim",
    code: "rim-18-luxury",
    name: "18 寸豪华轮毂",
    previewColor: null,
    imageUrl: "https://images.unsplash.com/photo-1558618666-fcd25c85cd64?w=400&h=300&fit=crop",
    description: "多辐式设计，豪华感十足",
    sortOrder: 1,
    status: "0",
    createBy: "admin",
    createTime: new Date(),
    updateBy: "",
    updateTime: null
  },
  // 奔驰C级 - 轮胎
  {
    id: 13,
    modelId: "model-2",
    optionType: "tire",
    code: "luxury-tire",
    name: "豪华静音胎",
    previewColor: null,
    imageUrl: "https://images.unsplash.com/photo-1558618666-fcd25c85cd64?w=400&h=300&fit=crop",
    description: "静音舒适，豪华体验",
    sortOrder: 1,
    status: "0",
    createBy: "admin",
    createTime: new Date(),
    updateBy: "",
    updateTime: null
  },
  // 奔驰C级 - 卡钳
  {
    id: 14,
    modelId: "model-2",
    optionType: "caliper",
    code: "silver-caliper",
    name: "银色卡钳",
    previewColor: null,
    imageUrl: "https://images.unsplash.com/photo-1558618666-fcd25c85cd64?w=400&h=300&fit=crop",
    description: "银色卡钳，低调优雅",
    sortOrder: 1,
    status: "0",
    createBy: "admin",
    createTime: new Date(),
    updateBy: "",
    updateTime: null
  }
]);

// 创建索引
db.car_model_option.createIndex({ id: 1 }, { unique: true });
db.car_model_option.createIndex({ modelId: 1, optionType: 1 });

print("MongoDB 初始化完成！");
print("数据库: cardiy");
print("已创建所有集合和索引");


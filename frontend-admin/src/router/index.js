import { createRouter, createWebHistory } from "vue-router";
import Dashboard from "../views/Dashboard.vue";
import User from "../views/system/User.vue";
import Role from "../views/system/Role.vue";
import Menu from "../views/system/Menu.vue";
import Dept from "../views/system/Dept.vue";
import Post from "../views/system/Post.vue";
import Dict from "../views/system/Dict.vue";
import Config from "../views/system/Config.vue";
import OperLog from "../views/system/OperLog.vue";
import LoginLog from "../views/system/LoginLog.vue";
import CarModels from "../views/car/Models.vue";
import CarOptions from "../views/car/Options.vue";

const routes = [
  { path: "/", redirect: "/dashboard" },
  {
    path: "/dashboard",
    name: "Dashboard",
    component: Dashboard,
    meta: { title: "首页" }
  },
  {
    path: "/system/user",
    name: "User",
    component: User,
    meta: { title: "用户管理" }
  },
  {
    path: "/system/role",
    name: "Role",
    component: Role,
    meta: { title: "角色管理" }
  },
  {
    path: "/system/menu",
    name: "Menu",
    component: Menu,
    meta: { title: "菜单管理" }
  },
  {
    path: "/system/dept",
    name: "Dept",
    component: Dept,
    meta: { title: "部门管理" }
  },
  {
    path: "/system/post",
    name: "Post",
    component: Post,
    meta: { title: "岗位管理" }
  },
  {
    path: "/system/dict",
    name: "Dict",
    component: Dict,
    meta: { title: "字典管理" }
  },
  {
    path: "/system/config",
    name: "Config",
    component: Config,
    meta: { title: "参数设置" }
  },
  {
    path: "/system/operlog",
    name: "OperLog",
    component: OperLog,
    meta: { title: "操作日志" }
  },
  {
    path: "/system/logininfor",
    name: "LoginLog",
    component: LoginLog,
    meta: { title: "登录日志" }
  },
  {
    path: "/car/models",
    name: "CarModels",
    component: CarModels,
    meta: { title: "车型列表" }
  },
  {
    path: "/car/options",
    name: "CarOptions",
    component: CarOptions,
    meta: { title: "配置管理" }
  }
];

const router = createRouter({
  history: createWebHistory(),
  routes
});

export default router;

<template>
  <el-container class="layout-container">
    <!-- 侧边栏 -->
    <el-aside :width="isCollapse ? '64px' : '210px'" class="sidebar-container">
      <div class="logo-container">
        <div v-if="!isCollapse" class="logo-icon">🚗</div>
        <span v-if="!isCollapse" class="logo-text">CarDIY</span>
        <span v-else class="logo-text-mini">CD</span>
      </div>
      <el-menu
        :default-active="activeMenu"
        :collapse="isCollapse"
        :unique-opened="true"
        class="sidebar-menu"
        router
        background-color="#304156"
        text-color="#bfcbd9"
        active-text-color="#409EFF"
        :collapse-transition="false"
      >
        <el-menu-item index="/dashboard">
          <el-icon><Odometer /></el-icon>
          <template #title>首页</template>
        </el-menu-item>
        <el-sub-menu index="system">
          <template #title>
            <el-icon><Setting /></el-icon>
            <span>系统管理</span>
          </template>
          <el-menu-item index="/system/user">用户管理</el-menu-item>
          <el-menu-item index="/system/role">角色管理</el-menu-item>
          <el-menu-item index="/system/menu">菜单管理</el-menu-item>
          <el-menu-item index="/system/dept">部门管理</el-menu-item>
          <el-menu-item index="/system/post">岗位管理</el-menu-item>
          <el-menu-item index="/system/dict">字典管理</el-menu-item>
          <el-menu-item index="/system/config">参数设置</el-menu-item>
          <el-menu-item index="/system/operlog">操作日志</el-menu-item>
          <el-menu-item index="/system/logininfor">登录日志</el-menu-item>
        </el-sub-menu>
        <el-sub-menu index="car">
          <template #title>
            <el-icon><Van /></el-icon>
            <span>车型管理</span>
          </template>
          <el-menu-item index="/car/models">车型列表</el-menu-item>
          <el-menu-item index="/car/options">配置管理</el-menu-item>
        </el-sub-menu>
      </el-menu>
    </el-aside>

    <!-- 主容器 -->
    <el-container>
      <!-- 顶部导航栏 -->
      <el-header class="navbar">
        <div class="navbar-left">
          <el-icon class="collapse-icon" @click="toggleSideBar">
            <Expand v-if="isCollapse" />
            <Fold v-else />
          </el-icon>
          <breadcrumb class="breadcrumb-container" />
        </div>
        <div class="navbar-right">
          <el-dropdown>
            <span class="user-info">
              <el-avatar :size="32" :src="userAvatar" />
              <span class="user-name">管理员</span>
              <el-icon><ArrowDown /></el-icon>
            </span>
            <template #dropdown>
              <el-dropdown-menu>
                <el-dropdown-item>个人中心</el-dropdown-item>
                <el-dropdown-item divided>退出登录</el-dropdown-item>
              </el-dropdown-menu>
            </template>
          </el-dropdown>
        </div>
      </el-header>

      <!-- 主内容区 -->
      <el-main class="app-main">
        <router-view />
      </el-main>
    </el-container>
  </el-container>
</template>

<script setup>
import { ref, computed } from "vue";
import { useRoute } from "vue-router";
import {
  Odometer,
  Setting,
  Van,
  Expand,
  Fold,
  ArrowDown
} from "@element-plus/icons-vue";

const route = useRoute();
const isCollapse = ref(false);
const userAvatar = ref("");

const activeMenu = computed(() => route.path);

const toggleSideBar = () => {
  isCollapse.value = !isCollapse.value;
};
</script>

<style scoped>
.layout-container {
  height: 100vh;
}

/* 侧边栏 */
.sidebar-container {
  position: fixed;
  top: 0;
  bottom: 0;
  left: 0;
  z-index: 1001;
  overflow: hidden;
  background-color: #304156;
  transition: width 0.28s;
}

.logo-container {
  height: 50px;
  line-height: 50px;
  background: #2b2f3a;
  text-align: center;
  overflow: hidden;
}

.logo-icon {
  display: inline-block;
  width: 32px;
  height: 32px;
  line-height: 32px;
  text-align: center;
  font-size: 24px;
  vertical-align: middle;
  margin-right: 8px;
}

.logo-text {
  font-size: 16px;
  font-weight: 600;
  color: #fff;
  vertical-align: middle;
}

.logo-text-mini {
  font-size: 14px;
  font-weight: 600;
  color: #fff;
}

.sidebar-menu {
  border: none;
  height: calc(100vh - 50px);
  overflow-y: auto;
  overflow-x: hidden;
}

.sidebar-menu:not(.el-menu--collapse) {
  width: 210px;
}

.sidebar-menu.el-menu--collapse {
  width: 64px;
}

/* 顶部导航栏 */
.navbar {
  height: 50px;
  overflow: hidden;
  position: relative;
  background: #fff;
  box-shadow: 0 1px 4px rgba(0, 21, 41, 0.08);
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 0 16px;
  margin-left: 210px;
  transition: margin-left 0.28s;
}

.navbar-left {
  display: flex;
  align-items: center;
}

.collapse-icon {
  font-size: 18px;
  cursor: pointer;
  margin-right: 16px;
  color: #5a5e66;
}

.collapse-icon:hover {
  color: #409eff;
}

.breadcrumb-container {
  font-size: 14px;
}

.navbar-right {
  display: flex;
  align-items: center;
}

.user-info {
  display: flex;
  align-items: center;
  cursor: pointer;
  padding: 0 12px;
}

.user-name {
  margin: 0 8px;
  font-size: 14px;
  color: #606266;
}

/* 主内容区 */
.app-main {
  min-height: calc(100vh - 50px);
  width: 100%;
  position: relative;
  overflow-x: hidden;
  overflow-y: auto;
  background-color: #f0f2f5;
  margin-left: 210px;
  transition: margin-left 0.28s;
  padding: 16px;
}

/* 侧边栏折叠时的样式调整 */
.layout-container:has(.sidebar-container[style*="width: 64px"]) .navbar {
  margin-left: 64px;
}

.layout-container:has(.sidebar-container[style*="width: 64px"]) .app-main {
  margin-left: 64px;
}

/* 滚动条样式 */
.sidebar-menu::-webkit-scrollbar {
  width: 6px;
}

.sidebar-menu::-webkit-scrollbar-thumb {
  background: rgba(144, 147, 153, 0.3);
  border-radius: 3px;
}

.sidebar-menu::-webkit-scrollbar-thumb:hover {
  background: rgba(144, 147, 153, 0.5);
}
</style>

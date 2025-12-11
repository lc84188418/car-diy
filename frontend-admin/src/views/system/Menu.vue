<template>
  <div class="app-container">
    <div class="table-wrapper">
      <el-table
        v-loading="loading"
        :data="menuList"
        row-key="menuId"
        :tree-props="{ children: 'children', hasChildren: 'hasChildren' }"
        class="data-table"
        border
        stripe
        v-table-resize
      >
      <el-table-column prop="menuName" label="菜单名称" min-width="200" show-overflow-tooltip />
      <el-table-column prop="icon" label="图标" align="center" min-width="100">
        <template #default="scope">
          <el-icon v-if="scope.row.icon">
            <component :is="scope.row.icon" />
          </el-icon>
        </template>
      </el-table-column>
      <el-table-column prop="orderNum" label="排序" min-width="80" />
      <el-table-column prop="path" label="路由地址" min-width="150" show-overflow-tooltip />
      <el-table-column prop="component" label="组件路径" min-width="200" show-overflow-tooltip />
      <el-table-column prop="status" label="状态" min-width="100">
        <template #default="scope">
          <el-tag v-if="scope.row.status === '0'" type="success">正常</el-tag>
          <el-tag v-else type="danger">停用</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="创建时间" align="center" prop="createTime" min-width="180">
        <template #default="scope">
          <span>{{ parseTime(scope.row.createTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" width="200" fixed="right">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)">修改</el-button>
          <el-button link type="primary" icon="Plus" @click="handleAdd(scope.row)">新增</el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from "vue";
import { ElMessage, ElMessageBox } from "element-plus";
import { listMenu, getMenu, delMenu, addMenu, updateMenu } from "@/api/system/menu";

const loading = ref(true);
const menuList = ref([]);

const getList = () => {
  loading.value = true;
  listMenu({}).then((response) => {
    // 后端返回格式：{ code: 200, message: "OK", data: [...] }
    // response.data 就是菜单列表（树形结构）
    menuList.value = response.data || [];
    loading.value = false;
  }).catch(() => {
    loading.value = false;
  });
};

const handleAdd = (row) => {
  // TODO: 实现新增菜单对话框
  ElMessage.info("功能开发中");
};

const handleUpdate = (row) => {
  // TODO: 实现修改菜单对话框
  ElMessage.info("功能开发中");
};

const handleDelete = (row) => {
  const menuId = row.menuId;
  ElMessageBox.confirm('是否确认删除菜单编号为"' + menuId + '"的数据项？')
    .then(() => {
      return delMenu(menuId);
    })
    .then(() => {
      getList();
      ElMessage.success("删除成功");
    })
    .catch(() => {});
};

const parseTime = (time) => {
  if (!time) return "";
  return new Date(time).toLocaleString("zh-CN");
};

onMounted(() => {
  getList();
});
</script>

<style scoped>
.app-container {
  padding: 20px;
  background: #fff;
  border-radius: 4px;
}

.data-table {
  width: 100%;
}

/* 表格列宽调整样式 */
.data-table .el-table__header-wrapper th {
  user-select: none;
}

.data-table .el-table__body-wrapper {
  overflow-x: auto;
}
</style>


<template>
  <div class="app-container">
    <div class="table-wrapper">
      <el-table
        v-loading="loading"
        :data="deptList"
        row-key="deptId"
        :tree-props="{ children: 'children', hasChildren: 'hasChildren' }"
        class="data-table"
        border
        stripe
        v-table-resize
      >
      <el-table-column prop="deptName" label="部门名称" min-width="200" show-overflow-tooltip />
      <el-table-column prop="orderNum" label="排序" min-width="80" />
      <el-table-column prop="leader" label="负责人" min-width="120" show-overflow-tooltip />
      <el-table-column prop="phone" label="联系电话" min-width="120" show-overflow-tooltip />
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
import { listDept, getDept, delDept, addDept, updateDept } from "@/api/system/dept";

const loading = ref(true);
const deptList = ref([]);

const getList = () => {
  loading.value = true;
  listDept({}).then((response) => {
    // 后端返回格式：{ code: 200, message: "OK", data: [...] }
    // response.data 就是部门列表（树形结构）
    deptList.value = response.data || [];
    loading.value = false;
  }).catch(() => {
    loading.value = false;
  });
};

const handleAdd = (row) => {
  // TODO: 实现新增部门对话框
  ElMessage.info("功能开发中");
};

const handleUpdate = (row) => {
  // TODO: 实现修改部门对话框
  ElMessage.info("功能开发中");
};

const handleDelete = (row) => {
  const deptId = row.deptId;
  ElMessageBox.confirm('是否确认删除部门编号为"' + deptId + '"的数据项？')
    .then(() => {
      return delDept(deptId);
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


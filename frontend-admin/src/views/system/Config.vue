<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryFormRef" :inline="true" label-width="68px" class="search-form">
      <el-form-item label="参数名称" prop="configName">
        <el-input
          v-model="queryParams.configName"
          placeholder="请输入参数名称"
          clearable
          style="width: 200px"
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="参数键名" prop="configKey">
        <el-input
          v-model="queryParams.configKey"
          placeholder="请输入参数键名"
          clearable
          style="width: 200px"
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="Search" @click="handleQuery">搜索</el-button>
        <el-button icon="Refresh" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain icon="Plus" @click="handleAdd">新增</el-button>
      </el-col>
    </el-row>

    <div class="table-wrapper">
      <el-table v-loading="loading" :data="configList" class="data-table" border stripe v-table-resize>
      <el-table-column label="参数主键" prop="configId" min-width="100" show-overflow-tooltip />
      <el-table-column label="参数名称" prop="configName" min-width="150" show-overflow-tooltip />
      <el-table-column label="参数键名" prop="configKey" min-width="150" show-overflow-tooltip />
      <el-table-column label="参数键值" prop="configValue" min-width="200" show-overflow-tooltip />
      <el-table-column label="系统内置" prop="configType" min-width="100">
        <template #default="scope">
          <el-tag v-if="scope.row.configType === 'Y'" type="warning">是</el-tag>
          <el-tag v-else>否</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="创建时间" align="center" prop="createTime" min-width="180">
        <template #default="scope">
          <span>{{ parseTime(scope.row.createTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" width="180" fixed="right">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)">修改</el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>

    <el-pagination
      v-show="total > 0"
      :total="total"
      v-model:current-page="queryParams.current"
      v-model:page-size="queryParams.size"
      :page-sizes="[10, 20, 50, 100]"
      layout="total, sizes, prev, pager, next, jumper"
      @size-change="getList"
      @current-change="getList"
      class="pagination"
    />
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from "vue";
import { ElMessage, ElMessageBox } from "element-plus";
import { listConfig, getConfig, delConfig, addConfig, updateConfig } from "@/api/system/config";

const loading = ref(true);
const configList = ref([]);
const total = ref(0);

const queryParams = reactive({
  current: 1,
  size: 10,
  configName: null,
  configKey: null
});

const getList = () => {
  loading.value = true;
  listConfig(queryParams).then((response) => {
    configList.value = response.data?.content || response.data?.records || [];
    total.value = response.data?.totalElements || response.data?.total || 0;
    loading.value = false;
  }).catch(() => {
    loading.value = false;
  });
};

const handleQuery = () => {
  queryParams.current = 1;
  getList();
};

const resetQuery = () => {
  queryParams.configName = null;
  queryParams.configKey = null;
  handleQuery();
};

const handleAdd = () => {
  // TODO: 实现新增参数对话框
  ElMessage.info("功能开发中");
};

const handleUpdate = (row) => {
  // TODO: 实现修改参数对话框
  ElMessage.info("功能开发中");
};

const handleDelete = (row) => {
  const configId = row.configId;
  ElMessageBox.confirm('是否确认删除参数编号为"' + configId + '"的数据项？')
    .then(() => {
      return delConfig(configId);
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

.search-form {
  margin-bottom: 16px;
}

.mb8 {
  margin-bottom: 8px;
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

.pagination {
  margin-top: 16px;
  justify-content: flex-end;
}
</style>


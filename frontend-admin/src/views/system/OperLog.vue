<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryFormRef" :inline="true" label-width="68px" class="search-form">
      <el-form-item label="系统模块" prop="title">
        <el-input
          v-model="queryParams.title"
          placeholder="请输入系统模块"
          clearable
          style="width: 200px"
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="操作人员" prop="operName">
        <el-input
          v-model="queryParams.operName"
          placeholder="请输入操作人员"
          clearable
          style="width: 200px"
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="操作状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="操作状态" clearable style="width: 200px">
          <el-option label="成功" value="0" />
          <el-option label="失败" value="1" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="Search" @click="handleQuery">搜索</el-button>
        <el-button icon="Refresh" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <div class="table-wrapper">
      <el-table v-loading="loading" :data="logList" class="data-table" border stripe v-table-resize>
      <el-table-column label="日志编号" prop="operId" min-width="100" show-overflow-tooltip />
      <el-table-column label="系统模块" prop="title" min-width="150" show-overflow-tooltip />
      <el-table-column label="操作类型" prop="businessType" min-width="100" />
      <el-table-column label="请求方式" prop="requestMethod" min-width="100" />
      <el-table-column label="操作人员" prop="operName" min-width="120" show-overflow-tooltip />
      <el-table-column label="操作地址" prop="operIp" min-width="120" show-overflow-tooltip />
      <el-table-column label="操作状态" prop="status" min-width="100">
        <template #default="scope">
          <el-tag v-if="scope.row.status === 0" type="success">成功</el-tag>
          <el-tag v-else type="danger">失败</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作时间" align="center" prop="operTime" min-width="180">
        <template #default="scope">
          <span>{{ parseTime(scope.row.operTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" width="100" fixed="right">
        <template #default="scope">
          <el-button link type="primary" @click="handleDetail(scope.row)">详细</el-button>
        </template>
      </el-table-column>
    </el-table>

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
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from "vue";
import { ElMessage } from "element-plus";
import { listOperlog, getOperlog } from "@/api/system/operlog";

const loading = ref(true);
const logList = ref([]);
const total = ref(0);

const queryParams = reactive({
  current: 1,
  size: 10,
  title: null,
  operName: null,
  status: null
});

const getList = () => {
  loading.value = true;
  listOperlog(queryParams).then((response) => {
    logList.value = response.data?.content || response.data?.records || [];
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
  queryParams.title = null;
  queryParams.operName = null;
  queryParams.status = null;
  handleQuery();
};

const handleDetail = (row) => {
  // TODO: 实现详情对话框
  ElMessage.info("功能开发中");
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


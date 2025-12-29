<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryFormRef" :inline="true" label-width="68px" class="search-form">
      <el-form-item label="用户名" prop="userName">
        <el-input
          v-model="queryParams.userName"
          placeholder="请输入用户名"
          clearable
          style="width: 200px"
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="登录地址" prop="ipaddr">
        <el-input
          v-model="queryParams.ipaddr"
          placeholder="请输入登录地址"
          clearable
          style="width: 200px"
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="登录状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="登录状态" clearable style="width: 200px">
          <el-option label="成功" value="0" />
          <el-option label="失败" value="1" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="Search" @click="handleQuery">搜索</el-button>
        <el-button icon="Refresh" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>
  </div>
    <div class="table-wrapper">
      <el-table v-loading="loading" :data="logList" class="data-table" border stripe v-table-resize>
      <el-table-column label="访问编号" prop="infoId" min-width="100" show-overflow-tooltip />
      <el-table-column label="用户名" prop="userName" min-width="120" show-overflow-tooltip />
      <el-table-column label="登录地址" prop="ipaddr" min-width="150" show-overflow-tooltip />
      <el-table-column label="登录地点" prop="loginLocation" min-width="150" show-overflow-tooltip />
      <el-table-column label="浏览器" prop="browser" min-width="120" show-overflow-tooltip />
      <el-table-column label="操作系统" prop="os" min-width="120" show-overflow-tooltip />
      <el-table-column label="登录状态" prop="status" min-width="100">
        <template #default="scope">
          <el-tag v-if="scope.row.status === '0'" type="success">成功</el-tag>
          <el-tag v-else type="danger">失败</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="提示消息" prop="msg" min-width="150" show-overflow-tooltip />
      <el-table-column label="访问时间" align="center" prop="loginTime" min-width="180">
        <template #default="scope">
          <span>{{ parseTime(scope.row.loginTime) }}</span>
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
</template>

<script setup>
import { ref, reactive, onMounted } from "vue";
import { listLogininfor } from "@/api/system/logininfor";

const loading = ref(true);
const logList = ref([]);
const total = ref(0);

const queryParams = reactive({
  current: 1,
  size: 10,
  userName: null,
  ipaddr: null,
  status: null
});

const getList = () => {
  loading.value = true;
  listLogininfor(queryParams).then((response) => {
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
  queryParams.userName = null;
  queryParams.ipaddr = null;
  queryParams.status = null;
  handleQuery();
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


<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryFormRef" :inline="true" label-width="68px" class="search-form">
      <el-form-item label="岗位名称" prop="postName">
        <el-input
          v-model="queryParams.postName"
          placeholder="请输入岗位名称"
          clearable
          style="width: 200px"
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="岗位编码" prop="postCode">
        <el-input
          v-model="queryParams.postCode"
          placeholder="请输入岗位编码"
          clearable
          style="width: 200px"
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="岗位状态" clearable style="width: 200px">
          <el-option label="正常" value="0" />
          <el-option label="停用" value="1" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="Search" @click="handleQuery">搜索</el-button>
        <el-button icon="Refresh" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>
  </div>
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain icon="Plus" @click="handleAdd">新增</el-button>
      </el-col>
    </el-row>

    <div class="table-wrapper">
      <el-table v-loading="loading" :data="postList" class="data-table" border stripe v-table-resize>
      <el-table-column label="岗位编号" prop="postId" min-width="100" show-overflow-tooltip />
      <el-table-column label="岗位编码" prop="postCode" min-width="120" show-overflow-tooltip />
      <el-table-column label="岗位名称" prop="postName" min-width="150" show-overflow-tooltip />
      <el-table-column label="岗位排序" prop="postSort" min-width="100" />
      <el-table-column label="状态" min-width="100">
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
      <el-table-column label="操作" align="center" width="180" fixed="right">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)">修改</el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)">删除</el-button>
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
import { ElMessage, ElMessageBox } from "element-plus";
import { listPost, getPost, delPost, addPost, updatePost } from "@/api/system/post";

const loading = ref(true);
const postList = ref([]);
const total = ref(0);

const queryParams = reactive({
  current: 1,
  size: 10,
  postName: null,
  postCode: null,
  status: null
});

const getList = () => {
  loading.value = true;
  listPost(queryParams).then((response) => {
    postList.value = response.data?.content || response.data?.records || [];
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
  queryParams.postName = null;
  queryParams.postCode = null;
  queryParams.status = null;
  handleQuery();
};

const handleAdd = () => {
  // TODO: 实现新增岗位对话框
  ElMessage.info("功能开发中");
};

const handleUpdate = (row) => {
  // TODO: 实现修改岗位对话框
  ElMessage.info("功能开发中");
};

const handleDelete = (row) => {
  const postId = row.postId;
  ElMessageBox.confirm('是否确认删除岗位编号为"' + postId + '"的数据项？')
    .then(() => {
      return delPost(postId);
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


<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryFormRef" :inline="true" label-width="68px" class="search-form">
      <el-form-item label="品牌" prop="brand">
        <el-input
          v-model="queryParams.brand"
          placeholder="请输入品牌"
          clearable
          style="width: 200px"
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="车系" prop="series">
        <el-input
          v-model="queryParams.series"
          placeholder="请输入车系"
          clearable
          style="width: 200px"
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="状态" clearable style="width: 200px">
          <el-option label="正常" value="0" />
          <el-option label="停用" value="1" />
        </el-select>
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
      <el-table v-loading="loading" :data="modelList" class="data-table" border stripe v-table-resize>
      <el-table-column label="车型ID" prop="id" min-width="150" show-overflow-tooltip />
      <el-table-column label="品牌" prop="brand" min-width="120" show-overflow-tooltip />
      <el-table-column label="车系" prop="series" min-width="120" show-overflow-tooltip />
      <el-table-column label="年份" prop="year" min-width="100" />
      <el-table-column label="主图" prop="mainImageUrl" min-width="150">
        <template #default="scope">
          <el-image
            v-if="scope.row.mainImageUrl"
            :src="scope.row.mainImageUrl"
            style="width: 100px; height: 60px"
            fit="cover"
          />
        </template>
      </el-table-column>
      <el-table-column label="描述" prop="description" min-width="200" show-overflow-tooltip />
      <el-table-column label="状态" prop="status" min-width="100">
        <template #default="scope">
          <el-tag v-if="scope.row.status === '0'" type="success">正常</el-tag>
          <el-tag v-else type="danger">停用</el-tag>
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

    <!-- 添加或修改车型对话框 -->
    <el-dialog :title="title" v-model="open" width="600px" append-to-body>
      <el-form ref="formRef" :model="form" :rules="rules" label-width="100px">
        <el-form-item label="品牌" prop="brand">
          <el-input v-model="form.brand" placeholder="请输入品牌" />
        </el-form-item>
        <el-form-item label="车系" prop="series">
          <el-input v-model="form.series" placeholder="请输入车系" />
        </el-form-item>
        <el-form-item label="年份" prop="year">
          <el-input v-model="form.year" placeholder="请输入年份" />
        </el-form-item>
        <el-form-item label="主图URL" prop="mainImageUrl">
          <el-input v-model="form.mainImageUrl" placeholder="请输入主图URL" />
        </el-form-item>
        <el-form-item label="描述" prop="description">
          <el-input v-model="form.description" type="textarea" :rows="3" placeholder="请输入描述" />
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-radio-group v-model="form.status">
            <el-radio label="0">正常</el-radio>
            <el-radio label="1">停用</el-radio>
          </el-radio-group>
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button type="primary" @click="submitForm">确 定</el-button>
          <el-button @click="cancel">取 消</el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from "vue";
import { ElMessage, ElMessageBox } from "element-plus";
import { listModel, getModel, delModel, addModel, updateModel } from "@/api/car/model";

const loading = ref(true);
const modelList = ref([]);
const total = ref(0);
const open = ref(false);
const title = ref("");

const queryParams = reactive({
  current: 1,
  size: 10,
  brand: null,
  series: null,
  status: null
});

const form = reactive({
  id: "",
  brand: "",
  series: "",
  year: "",
  mainImageUrl: "",
  description: "",
  status: "0"
});

const rules = {
  brand: [{ required: true, message: "品牌不能为空", trigger: "blur" }],
  series: [{ required: true, message: "车系不能为空", trigger: "blur" }],
  year: [{ required: true, message: "年份不能为空", trigger: "blur" }]
};

const formRef = ref(null);

const getList = () => {
  loading.value = true;
  listModel(queryParams).then((response) => {
    modelList.value = response.data?.content || response.data?.records || [];
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
  queryParams.brand = null;
  queryParams.series = null;
  queryParams.status = null;
  handleQuery();
};

const reset = () => {
  form.id = "";
  form.brand = "";
  form.series = "";
  form.year = "";
  form.mainImageUrl = "";
  form.description = "";
  form.status = "0";
  if (formRef.value) {
    formRef.value.resetFields();
  }
};

const handleAdd = () => {
  reset();
  open.value = true;
  title.value = "添加车型";
};

const handleUpdate = (row) => {
  reset();
  const id = row.id;
  getModel(id).then((response) => {
    Object.assign(form, response.data);
    open.value = true;
    title.value = "修改车型";
  });
};

const submitForm = () => {
  formRef.value.validate((valid) => {
    if (valid) {
      if (form.id) {
        updateModel(form).then(() => {
          ElMessage.success("修改成功");
          open.value = false;
          getList();
        });
      } else {
        addModel(form).then(() => {
          ElMessage.success("新增成功");
          open.value = false;
          getList();
        });
      }
    }
  });
};

const cancel = () => {
  open.value = false;
  reset();
};

const handleDelete = (row) => {
  const id = row.id;
  ElMessageBox.confirm('是否确认删除车型编号为"' + id + '"的数据项？')
    .then(() => {
      return delModel(id);
    })
    .then(() => {
      getList();
      ElMessage.success("删除成功");
    })
    .catch(() => {});
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


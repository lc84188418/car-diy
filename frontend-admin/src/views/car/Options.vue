<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryFormRef" :inline="true" label-width="68px" class="search-form">
      <el-form-item label="车型" prop="modelId">
        <el-select v-model="queryParams.modelId" placeholder="请选择车型" clearable style="width: 200px">
          <el-option
            v-for="item in modelOptions"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="配置类型" prop="optionType">
        <el-select v-model="queryParams.optionType" placeholder="配置类型" clearable style="width: 200px">
          <el-option label="车漆颜色" value="paintColor" />
          <el-option label="轮毂" value="rim" />
          <el-option label="轮胎" value="tire" />
          <el-option label="卡钳" value="caliper" />
        </el-select>
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
      <el-table v-loading="loading" :data="optionList" class="data-table" border stripe v-table-resize>
      <el-table-column label="配置ID" prop="id" min-width="100" show-overflow-tooltip />
      <el-table-column label="车型ID" prop="modelId" min-width="150" show-overflow-tooltip />
      <el-table-column label="配置类型" prop="optionType" min-width="120" show-overflow-tooltip />
      <el-table-column label="配置代码" prop="code" min-width="150" show-overflow-tooltip />
      <el-table-column label="配置名称" prop="name" min-width="150" show-overflow-tooltip />
      <el-table-column label="图片" prop="imageUrl" min-width="150">
        <template #default="scope">
          <el-image
            v-if="scope.row.imageUrl"
            :src="scope.row.imageUrl"
            style="width: 100px; height: 60px"
            fit="cover"
          />
        </template>
      </el-table-column>
      <el-table-column label="排序" prop="sortOrder" min-width="80" />
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

    <!-- 添加或修改配置选项对话框 -->
    <el-dialog :title="title" v-model="open" width="600px" append-to-body>
      <el-form ref="formRef" :model="form" :rules="rules" label-width="100px">
        <el-form-item label="车型" prop="modelId">
          <el-select v-model="form.modelId" placeholder="请选择车型" style="width: 100%">
            <el-option
              v-for="item in modelOptions"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="配置类型" prop="optionType">
          <el-select v-model="form.optionType" placeholder="请选择配置类型" style="width: 100%">
            <el-option label="车漆颜色" value="paintColor" />
            <el-option label="轮毂" value="rim" />
            <el-option label="轮胎" value="tire" />
            <el-option label="卡钳" value="caliper" />
          </el-select>
        </el-form-item>
        <el-form-item label="配置代码" prop="code">
          <el-input v-model="form.code" placeholder="请输入配置代码" />
        </el-form-item>
        <el-form-item label="配置名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入配置名称" />
        </el-form-item>
        <el-form-item label="预览颜色" prop="previewColor">
          <el-input v-model="form.previewColor" placeholder="请输入颜色值（如：#ffffff）" />
        </el-form-item>
        <el-form-item label="图片URL" prop="imageUrl">
          <el-input v-model="form.imageUrl" placeholder="请输入图片URL" />
        </el-form-item>
        <el-form-item label="描述" prop="description">
          <el-input v-model="form.description" type="textarea" :rows="3" placeholder="请输入描述" />
        </el-form-item>
        <el-form-item label="排序" prop="sortOrder">
          <el-input-number v-model="form.sortOrder" :min="0" />
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
import { listOption, getOption, delOption, addOption, updateOption } from "@/api/car/option";
import { listModel } from "@/api/car/model";

const loading = ref(true);
const optionList = ref([]);
const total = ref(0);
const open = ref(false);
const title = ref("");
const modelOptions = ref([]);

const queryParams = reactive({
  current: 1,
  size: 10,
  modelId: null,
  optionType: null,
  status: null
});

const form = reactive({
  id: "",
  modelId: "",
  optionType: "",
  code: "",
  name: "",
  previewColor: "",
  imageUrl: "",
  description: "",
  sortOrder: 0,
  status: "0"
});

const rules = {
  modelId: [{ required: true, message: "车型不能为空", trigger: "change" }],
  optionType: [{ required: true, message: "配置类型不能为空", trigger: "change" }],
  code: [{ required: true, message: "配置代码不能为空", trigger: "blur" }],
  name: [{ required: true, message: "配置名称不能为空", trigger: "blur" }]
};

const formRef = ref(null);

const getList = () => {
  loading.value = true;
  listOption(queryParams).then((response) => {
    optionList.value = response.data?.content || response.data?.records || [];
    total.value = response.data?.totalElements || response.data?.total || 0;
    loading.value = false;
  }).catch(() => {
    loading.value = false;
  });
};

const loadModels = () => {
  listModel({ current: 1, size: 1000 }).then((response) => {
    const models = response.data?.content || response.data?.records || [];
    modelOptions.value = models.map(m => ({
      label: `${m.brand} ${m.series} ${m.year}`,
      value: m.id
    }));
  });
};

const handleQuery = () => {
  queryParams.current = 1;
  getList();
};

const resetQuery = () => {
  queryParams.modelId = null;
  queryParams.optionType = null;
  queryParams.status = null;
  handleQuery();
};

const reset = () => {
  form.id = "";
  form.modelId = "";
  form.optionType = "";
  form.code = "";
  form.name = "";
  form.previewColor = "";
  form.imageUrl = "";
  form.description = "";
  form.sortOrder = 0;
  form.status = "0";
  if (formRef.value) {
    formRef.value.resetFields();
  }
};

const handleAdd = () => {
  reset();
  open.value = true;
  title.value = "添加配置选项";
};

const handleUpdate = (row) => {
  reset();
  const id = row.id;
  getOption(id).then((response) => {
    Object.assign(form, response.data);
    open.value = true;
    title.value = "修改配置选项";
  });
};

const submitForm = () => {
  formRef.value.validate((valid) => {
    if (valid) {
      if (form.id) {
        updateOption(form).then(() => {
          ElMessage.success("修改成功");
          open.value = false;
          getList();
        });
      } else {
        addOption(form).then(() => {
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
  ElMessageBox.confirm('是否确认删除配置选项编号为"' + id + '"的数据项？')
    .then(() => {
      return delOption(id);
    })
    .then(() => {
      getList();
      ElMessage.success("删除成功");
    })
    .catch(() => {});
};

onMounted(() => {
  getList();
  loadModels();
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


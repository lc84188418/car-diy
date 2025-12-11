<template>
  <div class="app-container">
    <el-form
      :model="queryParams"
      ref="queryFormRef"
      :inline="true"
      label-width="68px"
      class="search-form"
    >
      <el-form-item label="角色名称" prop="roleName">
        <el-input
          v-model="queryParams.roleName"
          placeholder="请输入角色名称"
          clearable
          style="width: 200px"
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="权限字符" prop="roleKey">
        <el-input
          v-model="queryParams.roleKey"
          placeholder="请输入权限字符"
          clearable
          style="width: 200px"
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-select
          v-model="queryParams.status"
          placeholder="角色状态"
          clearable
          style="width: 200px"
        >
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
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="Edit"
          :disabled="single"
          @click="handleUpdate"
        >
          修改
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="Delete"
          :disabled="multiple"
          @click="handleDelete"
        >
          删除
        </el-button>
      </el-col>
    </el-row>

    <div class="table-wrapper">
      <el-table
        v-loading="loading"
        :data="roleList"
        @selection-change="handleSelectionChange"
        class="data-table"
        border
        stripe
        v-table-resize
      >
      <el-table-column type="selection" width="55" align="center" fixed="left" />
      <el-table-column label="角色编号" prop="roleId" min-width="100" show-overflow-tooltip />
      <el-table-column label="角色名称" prop="roleName" min-width="150" show-overflow-tooltip />
      <el-table-column label="权限字符" prop="roleKey" min-width="150" show-overflow-tooltip />
      <el-table-column label="显示顺序" prop="roleSort" min-width="100" />
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

    <!-- 添加或修改角色配置对话框 -->
    <el-dialog :title="title" v-model="open" width="600px" append-to-body>
      <el-form ref="roleFormRef" :model="form" :rules="rules" label-width="100px">
        <el-form-item label="角色名称" prop="roleName">
          <el-input v-model="form.roleName" placeholder="请输入角色名称" />
        </el-form-item>
        <el-form-item label="权限字符" prop="roleKey">
          <el-input v-model="form.roleKey" placeholder="请输入权限字符" />
        </el-form-item>
        <el-form-item label="角色顺序" prop="roleSort">
          <el-input-number v-model="form.roleSort" controls-position="right" :min="0" />
        </el-form-item>
        <el-form-item label="状态">
          <el-radio-group v-model="form.status">
            <el-radio label="0">正常</el-radio>
            <el-radio label="1">停用</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="备注">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入内容" />
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
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from "vue";
import { ElMessage, ElMessageBox } from "element-plus";
import { listRole, getRole, delRole, addRole, updateRole } from "@/api/system/role";

const loading = ref(true);
const roleList = ref([]);
const total = ref(0);
const title = ref("");
const open = ref(false);
const single = ref(true);
const multiple = ref(true);
const ids = ref([]);

const queryParams = reactive({
  current: 1,
  size: 10,
  roleName: null,
  roleKey: null,
  status: null
});

const form = ref({});
const roleFormRef = ref(null);
const rules = {
  roleName: [{ required: true, message: "角色名称不能为空", trigger: "blur" }],
  roleKey: [{ required: true, message: "权限字符不能为空", trigger: "blur" }]
};

const getList = () => {
  loading.value = true;
  listRole(queryParams).then((response) => {
    // Spring Data Page 对象结构：content 是数据列表，totalElements 是总记录数
    roleList.value = response.data?.content || response.data?.records || [];
    total.value = response.data?.totalElements || response.data?.total || 0;
    loading.value = false;
  }).catch(() => {
    loading.value = false;
  });
};

const cancel = () => {
  open.value = false;
  reset();
};

const reset = () => {
  form.value = {
    roleId: undefined,
    roleName: undefined,
    roleKey: undefined,
    roleSort: 0,
    status: "0",
    remark: undefined
  };
};

const handleQuery = () => {
  queryParams.current = 1;
  getList();
};

const resetQuery = () => {
  reset();
  handleQuery();
};

const handleSelectionChange = (selection) => {
  ids.value = selection.map((item) => item.roleId);
  single.value = selection.length !== 1;
  multiple.value = !selection.length;
};

const handleAdd = () => {
  reset();
  open.value = true;
  title.value = "添加角色";
};

const handleUpdate = (row) => {
  reset();
  const roleId = row.roleId || ids.value[0];
  getRole(roleId).then((response) => {
    // 后端返回格式：{ code: 200, message: "OK", data: { ...role对象... } }
    // response.data 就是角色对象
    form.value = response.data || {};
    open.value = true;
    title.value = "修改角色";
  }).catch(() => {
    // 错误处理
  });
};

const submitForm = () => {
  roleFormRef.value.validate((valid) => {
    if (valid) {
      if (form.value.roleId != undefined) {
        updateRole(form.value).then((response) => {
          ElMessage.success("修改成功");
          open.value = false;
          getList();
        });
      } else {
        addRole(form.value).then((response) => {
          ElMessage.success("新增成功");
          open.value = false;
          getList();
        });
      }
    }
  });
};

const handleDelete = (row) => {
  const roleIds = row.roleId || ids.value;
  ElMessageBox.confirm('是否确认删除角色编号为"' + roleIds + '"的数据项？')
    .then(() => {
      return delRole(roleIds);
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

.dialog-footer {
  text-align: right;
}

.pagination {
  margin-top: 16px;
  justify-content: flex-end;
}
</style>


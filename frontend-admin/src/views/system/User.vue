<template>
  <div class="app-container">
    <el-form
      :model="queryParams"
      ref="queryFormRef"
      :inline="true"
      label-width="68px"
      class="search-form"
    >
      <el-form-item label="用户名" prop="userName">
        <el-input
          v-model="queryParams.userName"
          placeholder="请输入用户名"
          clearable
          style="width: 200px"
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="用户昵称" prop="nickName">
        <el-input
          v-model="queryParams.nickName"
          placeholder="请输入用户昵称"
          clearable
          style="width: 200px"
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="手机号码" prop="tel">
        <el-input
          v-model="queryParams.tel"
          placeholder="请输入手机号码"
          clearable
          style="width: 200px"
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-select
          v-model="queryParams.status"
          placeholder="用户状态"
          clearable
          style="width: 200px"
        >
          <el-option
            v-for="item in statusOptions"
            :key="item.dictValue"
            :label="item.dictLabel"
            :value="item.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="Search" @click="handleQuery">搜索</el-button>
        <el-button icon="Refresh" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="Plus"
          @click="handleAdd"
          v-hasPermi="['system:user:add']"
        >
          新增
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="Edit"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['system:user:edit']"
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
          v-hasPermi="['system:user:remove']"
        >
          删除
        </el-button>
      </el-col>
    </el-row>

    <div class="table-wrapper">
      <el-table
        v-loading="loading"
        :data="userList"
        @selection-change="handleSelectionChange"
        class="data-table"
        border
        stripe
        v-table-resize
      >
      <el-table-column type="selection" width="55" align="center" fixed="left" />
      <el-table-column label="用户编号" prop="userId" min-width="100" show-overflow-tooltip />
      <el-table-column
        label="用户名"
        prop="userName"
        min-width="120"
        show-overflow-tooltip
      />
      <el-table-column
        label="用户昵称"
        prop="nickName"
        min-width="120"
        show-overflow-tooltip
      />
      <el-table-column label="部门" prop="deptName" min-width="120" show-overflow-tooltip>
        <template #default="scope">
          <span>{{ scope.row.deptName || scope.row.dept?.deptName || '-' }}</span>
        </template>
      </el-table-column>
      <el-table-column label="手机号码" prop="tel" min-width="120" show-overflow-tooltip />
      <el-table-column label="状态" min-width="100">
        <template #default="scope">
          <el-tag v-if="scope.row.status === '0'" type="success">正常</el-tag>
          <el-tag v-else-if="scope.row.status === '1'" type="danger">停用</el-tag>
          <span v-else>-</span>
        </template>
      </el-table-column>
      <el-table-column label="创建时间" align="center" prop="createTime" min-width="180">
        <template #default="scope">
          <span>{{ parseTime(scope.row.createTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" width="180" fixed="right" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button
            link
            type="primary"
            icon="Edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:user:edit']"
          >
            修改
          </el-button>
          <el-button
            link
            type="primary"
            icon="Delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:user:remove']"
          >
            删除
          </el-button>
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

    <!-- 添加或修改用户配置对话框 -->
    <el-dialog :title="title" v-model="open" width="600px" append-to-body>
      <el-form ref="userFormRef" :model="form" :rules="rules" label-width="80px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="用户昵称" prop="nickName">
              <el-input v-model="form.nickName" placeholder="请输入用户昵称" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="归属部门" prop="deptId">
              <el-tree-select
                v-model="form.deptId"
                :data="deptOptions"
                :props="{ value: 'id', label: 'label', children: 'children' }"
                value-key="id"
                placeholder="请选择归属部门"
                check-strictly
              />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="手机号码" prop="tel">
              <el-input v-model="form.tel" placeholder="请输入手机号码" maxlength="11" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="邮箱" prop="email">
              <el-input v-model="form.email" placeholder="请输入邮箱" maxlength="50" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item v-if="form.userId == undefined" label="用户名" prop="userName">
              <el-input v-model="form.userName" placeholder="请输入用户名" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item v-if="form.userId == undefined" label="用户密码" prop="password">
              <el-input
                v-model="form.password"
                placeholder="请输入用户密码"
                type="password"
                show-password
              />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="用户性别">
              <el-select v-model="form.sex" placeholder="请选择性别">
                <el-option label="男" value="0" />
                <el-option label="女" value="1" />
                <el-option label="未知" value="2" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="状态">
              <el-radio-group v-model="form.status">
                <el-radio label="0">正常</el-radio>
                <el-radio label="1">停用</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="岗位">
              <el-select v-model="form.postIds" multiple placeholder="请选择岗位">
                <el-option
                  v-for="item in postOptions"
                  :key="item.postId"
                  :label="item.postName"
                  :value="item.postId"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="角色">
              <el-select v-model="form.roleIds" multiple placeholder="请选择角色">
                <el-option
                  v-for="item in roleOptions"
                  :key="item.roleId"
                  :label="item.roleName"
                  :value="item.roleId"
                />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="备注">
              <el-input
                v-model="form.remark"
                type="textarea"
                placeholder="请输入内容"
              />
            </el-form-item>
          </el-col>
        </el-row>
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
import { listUser, getUser, delUser, addUser, updateUser } from "@/api/system/user";
import { listDept } from "@/api/system/dept";
import { roleSelector } from "@/api/system/role";
import { postSelector } from "@/api/system/post";
import { listData } from "@/api/system/dict";

const loading = ref(true);
const userList = ref([]);
const total = ref(0);
const title = ref("");
const open = ref(false);
const single = ref(true);
const multiple = ref(true);
const showSearch = ref(true);
const ids = ref([]);
const deptOptions = ref([]);
const postOptions = ref([]);
const roleOptions = ref([]);
const statusOptions = ref([]);
const userFormRef = ref(null);

const queryParams = reactive({
  current: 1,
  size: 10,
  userName: undefined,
  nickName: undefined,
  tel: undefined,
  status: undefined,
  deptId: undefined
});

const form = ref({
  userId: undefined,
  deptId: undefined,
  userName: undefined,
  nickName: undefined,
  password: undefined,
  email: undefined,
  tel: undefined,
  sex: undefined,
  status: "0",
  remark: undefined,
  postIds: [],
  roleIds: []
});

const rules = {
  userName: [{ required: true, message: "用户名不能为空", trigger: "blur" }],
  nickName: [{ required: true, message: "用户昵称不能为空", trigger: "blur" }],
  password: [
    { required: true, message: "用户密码不能为空", trigger: "blur" },
    { min: 5, max: 20, message: "用户密码长度必须介于 5 和 20 之间", trigger: "blur" }
  ],
  email: [
    { type: "email", message: "请输入正确的邮箱地址", trigger: ["blur", "change"] }
  ],
  tel: [
    { pattern: /^1[3|4|5|6|7|8|9][0-9]\d{8}$/, message: "请输入正确的手机号码", trigger: "blur" }
  ]
};

/** 查询用户列表 */
const getList = () => {
  loading.value = true;
  listUser(queryParams).then((response) => {
    // Spring Data Page 对象结构：content 是数据列表，totalElements 是总记录数
    const users = response.data?.content || response.data?.records || [];
    // 处理部门名称显示
    userList.value = users.map(user => {
      // 如果后端返回了 dept 对象，提取 deptName
      if (user.dept && user.dept.deptName) {
        user.deptName = user.dept.deptName;
      }
      return user;
    });
    total.value = response.data?.totalElements || response.data?.total || 0;
    loading.value = false;
  }).catch(() => {
    loading.value = false;
  });
};

/** 加载部门选项（树形结构） */
const loadDeptOptions = () => {
  listDept({}).then((response) => {
    // 后端可能返回树形结构或平铺结构
    let depts = [];
    if (Array.isArray(response.data)) {
      depts = response.data;
    } else if (response.data?.content) {
      depts = response.data.content;
    } else if (response.data?.data) {
      depts = response.data.data;
    }
    
    // 如果已经是树形结构，直接使用
    if (depts.length > 0 && depts[0].children !== undefined) {
      // 转换为树形选择器需要的格式
      const convertTree = (items) => {
        return items.map(item => ({
          id: item.deptId,
          label: item.deptName,
          children: item.children && item.children.length > 0 ? convertTree(item.children) : undefined
        }));
      };
      deptOptions.value = convertTree(depts);
    } else {
      // 如果是平铺结构，构建树形结构
      const buildTree = (items, parentId = null) => {
        return items
          .filter(item => {
            if (parentId === null) {
              return !item.parentId || item.parentId === 0 || item.parentId === null;
            }
            return item.parentId === parentId;
          })
          .map(item => ({
            id: item.deptId,
            label: item.deptName,
            children: buildTree(items, item.deptId)
          }));
      };
      deptOptions.value = buildTree(depts);
    }
  }).catch(() => {
    // 错误处理
  });
};

/** 加载角色选项 */
const loadRoleOptions = () => {
  roleSelector({}).then((response) => {
    const roles = response.data || [];
    roleOptions.value = roles.map(role => ({
      roleId: role.id,
      roleName: role.name
    }));
  });
};

/** 加载岗位选项 */
const loadPostOptions = () => {
  postSelector({}).then((response) => {
    const posts = response.data || [];
    postOptions.value = posts.map(post => ({
      postId: post.id,
      postName: post.name
    }));
  });
};

/** 加载状态选项（从字典数据获取） */
const loadStatusOptions = () => {
  // 根据字典类型获取用户状态选项，字典类型是 sys_normal_disable
  listData({ dictType: "sys_normal_disable", status: "0" }).then((response) => {
    const data = response.data || [];
    statusOptions.value = data.map(item => ({
      dictLabel: item.dictLabel,
      dictValue: item.dictValue
    }));
  }).catch(() => {
    // 如果接口失败，使用默认值
    statusOptions.value = [
      { dictLabel: "正常", dictValue: "0" },
      { dictLabel: "停用", dictValue: "1" }
    ];
  });
};

// 取消按钮
const cancel = () => {
  open.value = false;
  reset();
};

// 表单重置
const reset = () => {
  form.value = {
    userId: undefined,
    deptId: undefined,
    userName: undefined,
    nickName: undefined,
    password: undefined,
    email: undefined,
    tel: undefined,
    sex: undefined,
    status: "0",
    remark: undefined,
    postIds: [],
    roleIds: []
  };
};

/** 搜索按钮操作 */
const handleQuery = () => {
  queryParams.current = 1;
  getList();
};

/** 重置按钮操作 */
const resetQuery = () => {
  reset();
  handleQuery();
};

// 多选框选中数据
const handleSelectionChange = (selection) => {
  ids.value = selection.map((item) => item.userId);
  single.value = selection.length !== 1;
  multiple.value = !selection.length;
};

/** 新增按钮操作 */
const handleAdd = () => {
  reset();
  // 确保选项数据已加载
  if (deptOptions.value.length === 0) {
    loadDeptOptions();
  }
  if (roleOptions.value.length === 0) {
    loadRoleOptions();
  }
  if (postOptions.value.length === 0) {
    loadPostOptions();
  }
  open.value = true;
  title.value = "添加用户";
  form.value.password = "";
};

/** 修改按钮操作 */
const handleUpdate = (row) => {
  reset();
  const userId = row.userId || ids.value[0];
  // 确保选项数据已加载
  if (deptOptions.value.length === 0) {
    loadDeptOptions();
  }
  if (roleOptions.value.length === 0) {
    loadRoleOptions();
  }
  if (postOptions.value.length === 0) {
    loadPostOptions();
  }
  getUser(userId).then((response) => {
    // 后端返回格式：{ code: 200, message: "OK", data: { ...user对象... } }
    const userData = response.data || {};
    // 处理表单数据
    form.value = {
      id: userData.id,
      userId: userData.userId,
      deptId: userData.deptId,
      userName: userData.userName,
      nickName: userData.nickName,
      email: userData.email,
      tel: userData.tel,
      sex: userData.sex,
      status: userData.status || "0",
      remark: userData.remark,
      postIds: userData.postIds || [],
      roleIds: userData.roleIds || [],
      password: "" // 编辑时不显示密码
    };
    open.value = true;
    title.value = "修改用户";
  }).catch(() => {
    // 错误处理
  });
};

/** 提交按钮 */
const submitForm = () => {
  userFormRef.value.validate((valid) => {
    if (valid) {
      if (form.value.userId != undefined) {
        updateUser(form.value).then((response) => {
          ElMessage.success("修改成功");
          open.value = false;
          getList();
        });
      } else {
        addUser(form.value).then((response) => {
          ElMessage.success("新增成功");
          open.value = false;
          getList();
        });
      }
    }
  });
};

/** 删除按钮操作 */
const handleDelete = (row) => {
  const userIds = row.userId || ids.value;
  ElMessageBox.confirm('是否确认删除用户编号为"' + userIds + '"的数据项？')
    .then(() => {
      return delUser(userIds);
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
  // 加载下拉框选项数据
  loadDeptOptions();
  loadStatusOptions();
  // loadRoleOptions();
  // loadPostOptions();
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


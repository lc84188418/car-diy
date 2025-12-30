<template>
  <div class="app-container">
    <el-tabs v-model="activeName">
      <el-tab-pane label="字典类型" name="type">
        <div class="table-wrapper">
          <el-table v-loading="loading" :data="typeList" class="data-table" border stripe v-table-resize>
          <el-table-column label="字典编号" prop="dictId" min-width="100" show-overflow-tooltip />
          <el-table-column label="字典名称" prop="dictName" min-width="150" show-overflow-tooltip />
          <el-table-column label="字典类型" prop="dictType" min-width="150" show-overflow-tooltip />
          <el-table-column label="状态" min-width="100">
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
      </el-tab-pane>
      <el-tab-pane label="字典数据" name="data">
        <el-table v-loading="loading" :data="dataList" class="data-table" border stripe v-table-resize>
          <el-table-column label="字典编码" prop="dictCode" min-width="100" show-overflow-tooltip />
          <el-table-column label="字典标签" prop="dictLabel" min-width="150" show-overflow-tooltip />
          <el-table-column label="字典键值" prop="dictValue" min-width="150" show-overflow-tooltip />
          <el-table-column label="字典类型" prop="dictType" min-width="150" show-overflow-tooltip />
          <el-table-column label="状态" min-width="100">
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
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script setup>
import { ref, onMounted, watch } from "vue";
import { ElMessage, ElMessageBox } from "element-plus";
import { listType, getType, delType, addType, updateType } from "@/api/system/dict";
import { listData, getData, delData, addData, updateData } from "@/api/system/dict";

const loading = ref(true);
//默认查询字典类型数据
const activeName = ref("type");
const typeList = ref([]);
const dataList = ref([]);

const getTypeList = () => {
  loading.value = true;
  listType({}).then((response) => {
    // 后端返回格式：{ code: 200, message: "OK", data: [...] }
    typeList.value = response.data || [];
    loading.value = false;
  }).catch(() => {
    loading.value = false;
  });
};

const getDataList = () => {
  loading.value = true;
  listData({}).then((response) => {
    // 后端返回格式：{ code: 200, message: "OK", data: [...] }
    dataList.value = response.data || [];
    loading.value = false;
  }).catch(() => {
    loading.value = false;
  });
};

const getList = () => {
  if (activeName.value === "type") {
    getTypeList();
  } else {
    getDataList();
  }
};

// 使用 watch 监听 activeName 的变化，当 tab 切换时自动加载对应的数据
watch(activeName, (newVal) => {
  if (newVal === "type") {
    getTypeList();
  } else if (newVal === "data") {
    getDataList();
  }
});

const handleUpdate = (row) => {
  // TODO: 实现修改对话框
  ElMessage.info("功能开发中");
};

const handleDelete = (row) => {
  const id = activeName.value === "type" ? row.dictId : row.dictCode;
  const name = activeName.value === "type" ? "字典类型" : "字典数据";
  ElMessageBox.confirm('是否确认删除' + name + '编号为"' + id + '"的数据项？')
    .then(() => {
      if (activeName.value === "type") {
        return delType(id);
      } else {
        return delData(id);
      }
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

.data-table {
  width: 100%;
}
</style>


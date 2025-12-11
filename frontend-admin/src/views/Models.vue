<template>
  <div>
    <el-page-header content="车型管理" />
    <el-button type="primary" style="margin: 12px 0" @click="openDialog()">
      新增车型
    </el-button>
    <el-table :data="models" style="width: 100%">
      <el-table-column prop="brand" label="品牌" />
      <el-table-column prop="series" label="车系" />
      <el-table-column prop="year" label="年份" />
      <el-table-column label="操作" width="150">
        <template #default="{ row }">
          <el-button size="small" @click="openDialog(row)">编辑</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-dialog v-model="dialogVisible" title="车型">
      <el-form :model="form" label-width="80px">
        <el-form-item label="品牌">
          <el-input v-model="form.brand" />
        </el-form-item>
        <el-form-item label="车系">
          <el-input v-model="form.series" />
        </el-form-item>
        <el-form-item label="年份">
          <el-input v-model="form.year" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="saveModel">保存</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { onMounted, reactive, ref } from "vue";
import { fetchModels, saveModel as saveModelApi } from "../services/api";

const models = ref([]);
const dialogVisible = ref(false);
const form = reactive({
  id: "",
  brand: "",
  series: "",
  year: ""
});

const loadData = async () => {
  const data = await fetchModels();
  models.value = data;
};

const openDialog = (row) => {
  if (row) {
    Object.assign(form, row);
  } else {
    Object.assign(form, {
      id: "",
      brand: "",
      series: "",
      year: ""
    });
  }
  dialogVisible.value = true;
};

const saveModel = async () => {
  await saveModelApi({ ...form });
  dialogVisible.value = false;
  await loadData();
};

onMounted(loadData);
</script>



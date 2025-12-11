<template>
  <div>
    <el-page-header content="概览" />
    <el-row :gutter="16" style="margin-top: 16px">
      <el-col :span="8">
        <el-card>
          <div>上线车型数</div>
          <h2>{{ stats.modelCount }}</h2>
        </el-card>
      </el-col>
      <el-col :span="8">
        <el-card>
          <div>可选改装项</div>
          <h2>{{ stats.optionCount }}</h2>
        </el-card>
      </el-col>
      <el-col :span="8">
        <el-card>
          <div>用户 DIY 方案</div>
          <h2>{{ stats.planCount }}</h2>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script setup>
import { onMounted, reactive } from "vue";
import { getAdminStats } from "../services/api";

const stats = reactive({
  modelCount: 0,
  optionCount: 0,
  planCount: 0
});

onMounted(async () => {
  try {
    const data = await getAdminStats();
    Object.assign(stats, data);
  } catch (e) {
    console.error("加载统计失败", e);
  }
});
</script>



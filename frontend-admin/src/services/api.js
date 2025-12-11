import axios from "axios";

const http = axios.create({
  baseURL: "/CardiyAdmin",
  timeout: 5000
});

export async function getAdminStats() {
  const res = await http.get("/api/admin/stats");
  // 后端返回格式: { code: 0, message: "OK", data: {...} }
  return res.data?.data || { modelCount: 0, optionCount: 0, planCount: 0 };
}

export async function fetchModels() {
  const res = await http.get("/api/admin/models");
  // 后端返回格式: { code: 0, message: "OK", data: [...] }
  return res.data?.data || [];
}

export async function saveModel(payload) {
  const res = await http.post("/api/admin/models", payload);
  // 后端返回格式: { code: 0, message: "OK", data: {...} }
  return res.data?.data || res.data;
}



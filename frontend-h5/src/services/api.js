import axios from "axios";

const carHttp = axios.create({
  baseURL: "/CardiyCar",
  timeout: 5000
});

const renderHttp = axios.create({
  baseURL: "/CardiyRender",
  timeout: 8000
});

const authHttp = axios.create({
  baseURL: "/CardiyAuth",
  timeout: 8000
});

export async function fetchModels() {
  const res = await carHttp.get("/api/models");
  // 后端返回格式: { code: 0, message: "OK", data: [...] }
  return res.data?.data || [];
}

export async function saveConfigPlan(payload) {
  const res = await carHttp.post("/api/plans", payload);
  // 后端返回格式: { code: 0, message: "OK", data: {...} }
  return res.data?.data || res.data;
}

export async function previewRender(payload) {
  const res = await renderHttp.post("/api/preview", payload);
  // 后端返回格式: { code: 200, message: "OK", data: { planId, modelUrl } }
  // 兼容 code 为 200 或 0 的情况
  if (res.data && (res.data.code === 200 || res.data.code === 0) && res.data.data) {
    return res.data.data;
  }
  return res.data?.data || res.data;
}


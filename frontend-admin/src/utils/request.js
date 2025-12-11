import axios from "axios";
import { ElMessage, ElMessageBox } from "element-plus";
import { getToken } from "@/utils/auth";

// 创建axios实例
const service = axios.create({
  baseURL: "/CardiyAdmin", // api的base_url，对应后端的servlet-path
  timeout: 10000 // 请求超时时间
});

// request拦截器
service.interceptors.request.use(
  (config) => {
    // 如果需要token，可以在这里添加
    // if (getToken()) {
    //   config.headers["Authorization"] = "Bearer " + getToken();
    // }
    return config;
  },
  (error) => {
    console.log(error);
    Promise.reject(error);
  }
);

// response拦截器
service.interceptors.response.use(
  (response) => {
    const res = response.data;
    // 如果返回的状态码不是200，则视为错误
    if (res.code !== 200 && res.code !== 0) {
      ElMessage({
        message: res.message || "Error",
        type: "error",
        duration: 5 * 1000
      });
      return Promise.reject(new Error(res.message || "Error"));
    } else {
      return res;
    }
  },
  (error) => {
    console.log("err" + error);
    ElMessage({
      message: error.message,
      type: "error",
      duration: 5 * 1000
    });
    return Promise.reject(error);
  }
);

export default service;


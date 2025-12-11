import request from "@/utils/request";

// 查询操作日志列表
export function listOperlog(query) {
  return request({
    url: "/api/admin/system/operlog/list",
    method: "get",
    params: query
  });
}

// 查询操作日志详细
export function getOperlog(operId) {
  return request({
    url: "/api/admin/system/operlog/" + operId,
    method: "get"
  });
}

// 删除操作日志
export function delOperlog(operId) {
  return request({
    url: "/api/admin/system/operlog/" + operId,
    method: "delete"
  });
}


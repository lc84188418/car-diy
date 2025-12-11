import request from "@/utils/request";

// 查询登录日志列表
export function listLogininfor(query) {
  return request({
    url: "/api/admin/system/logininfor/list",
    method: "get",
    params: query
  });
}

// 查询登录日志详细
export function getLogininfor(infoId) {
  return request({
    url: "/api/admin/system/logininfor/" + infoId,
    method: "get"
  });
}

// 删除登录日志
export function delLogininfor(infoId) {
  return request({
    url: "/api/admin/system/logininfor/" + infoId,
    method: "delete"
  });
}


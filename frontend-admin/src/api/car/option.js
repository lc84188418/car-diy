import request from "@/utils/request-car";

// 查询配置选项列表
export function listOption(query) {
  return request({
    url: "/api/admin/car/option/list",
    method: "get",
    params: query
  });
}

// 查询配置选项详细
export function getOption(id) {
  return request({
    url: "/api/admin/car/option/" + id,
    method: "get"
  });
}

// 新增配置选项
export function addOption(data) {
  return request({
    url: "/api/admin/car/option",
    method: "post",
    data: data
  });
}

// 修改配置选项
export function updateOption(data) {
  return request({
    url: "/api/admin/car/option",
    method: "put",
    data: data
  });
}

// 删除配置选项
export function delOption(id) {
  return request({
    url: "/api/admin/car/option/" + id,
    method: "delete"
  });
}


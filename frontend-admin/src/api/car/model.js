import request from "@/utils/request-car";

// 查询车型列表
export function listModel(query) {
  return request({
    url: "/api/admin/car/model/list",
    method: "get",
    params: query
  });
}

// 查询车型详细
export function getModel(id) {
  return request({
    url: "/api/admin/car/model/" + id,
    method: "get"
  });
}

// 新增车型
export function addModel(data) {
  return request({
    url: "/api/admin/car/model",
    method: "post",
    data: data
  });
}

// 修改车型
export function updateModel(data) {
  return request({
    url: "/api/admin/car/model",
    method: "put",
    data: data
  });
}

// 删除车型
export function delModel(id) {
  return request({
    url: "/api/admin/car/model/" + id,
    method: "delete"
  });
}


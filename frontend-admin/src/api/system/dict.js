import request from "@/utils/request";

// 查询字典类型列表
export function listType(query) {
  return request({
    url: "/api/admin/system/dict/type/list",
    method: "get",
    params: query
  });
}

// 查询字典类型详细
export function getType(dictId) {
  return request({
    url: "/api/admin/system/dict/type/" + dictId,
    method: "get"
  });
}

// 新增字典类型
export function addType(data) {
  return request({
    url: "/api/admin/system/dict/type",
    method: "post",
    data: data
  });
}

// 修改字典类型
export function updateType(data) {
  return request({
    url: "/api/admin/system/dict/type",
    method: "put",
    data: data
  });
}

// 删除字典类型
export function delType(dictId) {
  return request({
    url: "/api/admin/system/dict/type/" + dictId,
    method: "delete"
  });
}

// 查询字典数据列表
export function listData(query) {
  return request({
    url: "/api/admin/system/dict/data/list",
    method: "get",
    params: query
  });
}

// 查询字典数据详细
export function getData(dictCode) {
  return request({
    url: "/api/admin/system/dict/data/" + dictCode,
    method: "get"
  });
}

// 新增字典数据
export function addData(data) {
  return request({
    url: "/api/admin/system/dict/data",
    method: "post",
    data: data
  });
}

// 修改字典数据
export function updateData(data) {
  return request({
    url: "/api/admin/system/dict/data",
    method: "put",
    data: data
  });
}

// 删除字典数据
export function delData(dictCode) {
  return request({
    url: "/api/admin/system/dict/data/" + dictCode,
    method: "delete"
  });
}


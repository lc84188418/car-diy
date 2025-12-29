import request from "@/utils/request";

// 查询角色列表
export function listRole(query) {
  return request({
    url: "/api/admin/system/role/list",
    method: "get",
    params: query
  });
}
// 查询角色列表-下来选择器用
export function roleSelector(query) {
  return request({
    url: "/api/admin/system/role/selector",
    method: "get",
    params: query
  });
}

// 查询角色详细
export function getRole(roleId) {
  return request({
    url: "/api/admin/system/role/" + roleId,
    method: "get"
  });
}

// 新增角色
export function addRole(data) {
  return request({
    url: "/api/admin/system/role",
    method: "post",
    data: data
  });
}

// 修改角色
export function updateRole(data) {
  return request({
    url: "/api/admin/system/role",
    method: "put",
    data: data
  });
}

// 删除角色
export function delRole(roleId) {
  return request({
    url: "/api/admin/system/role/" + roleId,
    method: "delete"
  });
}


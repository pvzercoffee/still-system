import request from '@/utils/request'

// 查询电子围栏区域列表
export function listFence(query) {
  return request({
    url: '/system/fence/list',
    method: 'get',
    params: query
  })
}

// 查询电子围栏区域详细
export function getFence(fenceId) {
  return request({
    url: '/system/fence/' + fenceId,
    method: 'get'
  })
}

// 新增电子围栏区域
export function addFence(data) {
  return request({
    url: '/system/fence',
    method: 'post',
    data: data
  })
}

// 修改电子围栏区域
export function updateFence(data) {
  return request({
    url: '/system/fence',
    method: 'put',
    data: data
  })
}

// 删除电子围栏区域
export function delFence(fenceId) {
  return request({
    url: '/system/fence/' + fenceId,
    method: 'delete'
  })
}

import request from '@/utils/request'

// 查询巡查与违规记录列表
export function listViolation(query) {
  return request({
    url: '/system/violation/list',
    method: 'get',
    params: query
  })
}

// 查询巡查与违规记录详细
export function getViolation(violationId) {
  return request({
    url: '/system/violation/' + violationId,
    method: 'get'
  })
}

// 新增巡查与违规记录
export function addViolation(data) {
  return request({
    url: '/system/violation',
    method: 'post',
    data: data
  })
}

// 修改巡查与违规记录
export function updateViolation(data) {
  return request({
    url: '/system/violation',
    method: 'put',
    data: data
  })
}

// 删除巡查与违规记录
export function delViolation(violationId) {
  return request({
    url: '/system/violation/' + violationId,
    method: 'delete'
  })
}

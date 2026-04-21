import request from '@/utils/request'

// 查询志愿者申请与档案列表
export function listVolunteer(query) {
  return request({
    url: '/system/volunteer/list',
    method: 'get',
    params: query
  })
}

// 查询志愿者申请与档案详细
export function getVolunteer(volunteerId) {
  return request({
    url: '/system/volunteer/' + volunteerId,
    method: 'get'
  })
}

// 新增志愿者申请与档案
export function addVolunteer(data) {
  return request({
    url: '/system/volunteer',
    method: 'post',
    data: data
  })
}

// 修改志愿者申请与档案
export function updateVolunteer(data) {
  return request({
    url: '/system/volunteer',
    method: 'put',
    data: data
  })
}

// 删除志愿者申请与档案
export function delVolunteer(volunteerId) {
  return request({
    url: '/system/volunteer/' + volunteerId,
    method: 'delete'
  })
}

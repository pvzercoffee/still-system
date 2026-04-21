import request from '@/utils/request'

// 查询每日出摊打卡列表
export function listCheckin(query) {
  return request({
    url: '/system/checkin/list',
    method: 'get',
    params: query
  })
}

// 查询每日出摊打卡详细
export function getCheckin(checkinId) {
  return request({
    url: '/system/checkin/' + checkinId,
    method: 'get'
  })
}

// 新增每日出摊打卡
export function addCheckin(data) {
  return request({
    url: '/system/checkin',
    method: 'post',
    data: data
  })
}

// 修改每日出摊打卡
export function updateCheckin(data) {
  return request({
    url: '/system/checkin',
    method: 'put',
    data: data
  })
}

// 删除每日出摊打卡
export function delCheckin(checkinId) {
  return request({
    url: '/system/checkin/' + checkinId,
    method: 'delete'
  })
}

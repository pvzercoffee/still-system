import request from '@/utils/request'

// 查询摊贩档案列表
export function listVendor(query) {
  return request({
    url: '/system/vendor/list',
    method: 'get',
    params: query
  })
}

// 查询摊贩档案详细
export function getVendor(vendorId) {
  return request({
    url: '/system/vendor/' + vendorId,
    method: 'get'
  })
}

// 新增摊贩档案
export function addVendor(data) {
  return request({
    url: '/system/vendor',
    method: 'post',
    data: data
  })
}

// 修改摊贩档案
export function updateVendor(data) {
  return request({
    url: '/system/vendor',
    method: 'put',
    data: data
  })
}

// 删除摊贩档案
export function delVendor(vendorId) {
  return request({
    url: '/system/vendor/' + vendorId,
    method: 'delete'
  })
}

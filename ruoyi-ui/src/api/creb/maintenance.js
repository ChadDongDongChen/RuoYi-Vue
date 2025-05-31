import request from '@/utils/request'

// 查询养殖池维护记录列表
export function listMaintenance(query) {
  return request({
    url: '/creb/maintenance/list',
    method: 'get',
    params: query
  })
}

// 查询养殖池维护记录详细
export function getMaintenance(recordId) {
  return request({
    url: '/creb/maintenance/' + recordId,
    method: 'get'
  })
}

// 新增养殖池维护记录
export function addMaintenance(data) {
  return request({
    url: '/creb/maintenance',
    method: 'post',
    data: data
  })
}

// 修改养殖池维护记录
export function updateMaintenance(data) {
  return request({
    url: '/creb/maintenance',
    method: 'put',
    data: data
  })
}

// 删除养殖池维护记录
export function delMaintenance(recordId) {
  return request({
    url: '/creb/maintenance/' + recordId,
    method: 'delete'
  })
}

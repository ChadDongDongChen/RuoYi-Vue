import request from '@/utils/request'

// 查询螃蟹生长记录列表
export function listGrowth(query) {
  return request({
    url: '/creb/growth/list',
    method: 'get',
    params: query
  })
}

// 查询螃蟹生长记录详细
export function getGrowth(recordId) {
  return request({
    url: '/creb/growth/' + recordId,
    method: 'get'
  })
}

// 新增螃蟹生长记录
export function addGrowth(data) {
  return request({
    url: '/creb/growth',
    method: 'post',
    data: data
  })
}

// 修改螃蟹生长记录
export function updateGrowth(data) {
  return request({
    url: '/creb/growth',
    method: 'put',
    data: data
  })
}

// 删除螃蟹生长记录
export function delGrowth(recordId) {
  return request({
    url: '/creb/growth/' + recordId,
    method: 'delete'
  })
}

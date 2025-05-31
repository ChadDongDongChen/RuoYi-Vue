import request from '@/utils/request'

// 查询养殖成本记录列表
export function listCost(query) {
  return request({
    url: '/creb/cost/list',
    method: 'get',
    params: query
  })
}

// 查询养殖成本记录详细
export function getCost(recordId) {
  return request({
    url: '/creb/cost/' + recordId,
    method: 'get'
  })
}

// 新增养殖成本记录
export function addCost(data) {
  return request({
    url: '/creb/cost',
    method: 'post',
    data: data
  })
}

// 修改养殖成本记录
export function updateCost(data) {
  return request({
    url: '/creb/cost',
    method: 'put',
    data: data
  })
}

// 删除养殖成本记录
export function delCost(recordId) {
  return request({
    url: '/creb/cost/' + recordId,
    method: 'delete'
  })
}

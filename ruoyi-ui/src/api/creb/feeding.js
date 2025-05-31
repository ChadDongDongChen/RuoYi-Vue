import request from '@/utils/request'

// 查询投喂记录列表
export function listFeeding(query) {
  return request({
    url: '/creb/feeding/list',
    method: 'get',
    params: query
  })
}

// 查询投喂记录详细
export function getFeeding(recordId) {
  return request({
    url: '/creb/feeding/' + recordId,
    method: 'get'
  })
}

// 新增投喂记录
export function addFeeding(data) {
  return request({
    url: '/creb/feeding',
    method: 'post',
    data: data
  })
}

// 修改投喂记录
export function updateFeeding(data) {
  return request({
    url: '/creb/feeding',
    method: 'put',
    data: data
  })
}

// 删除投喂记录
export function delFeeding(recordId) {
  return request({
    url: '/creb/feeding/' + recordId,
    method: 'delete'
  })
}

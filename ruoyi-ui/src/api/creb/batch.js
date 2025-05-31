import request from '@/utils/request'

// 查询螃蟹批次信息列表
export function listBatch(query) {
  return request({
    url: '/creb/batch/list',
    method: 'get',
    params: query
  })
}

// 查询螃蟹批次信息详细
export function getBatch(batchId) {
  return request({
    url: '/creb/batch/' + batchId,
    method: 'get'
  })
}

// 新增螃蟹批次信息
export function addBatch(data) {
  return request({
    url: '/creb/batch',
    method: 'post',
    data: data
  })
}

// 修改螃蟹批次信息
export function updateBatch(data) {
  return request({
    url: '/creb/batch',
    method: 'put',
    data: data
  })
}

// 删除螃蟹批次信息
export function delBatch(batchId) {
  return request({
    url: '/creb/batch/' + batchId,
    method: 'delete'
  })
}

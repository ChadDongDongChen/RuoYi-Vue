import request from '@/utils/request'

// 查询收获记录列表
export function listHarvest(query) {
  return request({
    url: '/creb/harvest/list',
    method: 'get',
    params: query
  })
}

// 查询收获记录详细
export function getHarvest(recordId) {
  return request({
    url: '/creb/harvest/' + recordId,
    method: 'get'
  })
}

// 新增收获记录
export function addHarvest(data) {
  return request({
    url: '/creb/harvest',
    method: 'post',
    data: data
  })
}

// 修改收获记录
export function updateHarvest(data) {
  return request({
    url: '/creb/harvest',
    method: 'put',
    data: data
  })
}

// 删除收获记录
export function delHarvest(recordId) {
  return request({
    url: '/creb/harvest/' + recordId,
    method: 'delete'
  })
}

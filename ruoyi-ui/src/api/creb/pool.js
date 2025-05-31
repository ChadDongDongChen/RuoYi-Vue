import request from '@/utils/request'

// 查询螃蟹养殖池信息列表
export function listPool(query) {
  return request({
    url: '/creb/pool/list',
    method: 'get',
    params: query
  })
}

// 查询螃蟹养殖池信息详细
export function getPool(poolId) {
  return request({
    url: '/creb/pool/' + poolId,
    method: 'get'
  })
}

// 新增螃蟹养殖池信息
export function addPool(data) {
  return request({
    url: '/creb/pool',
    method: 'post',
    data: data
  })
}

// 修改螃蟹养殖池信息
export function updatePool(data) {
  return request({
    url: '/creb/pool',
    method: 'put',
    data: data
  })
}

// 删除螃蟹养殖池信息
export function delPool(poolId) {
  return request({
    url: '/creb/pool/' + poolId,
    method: 'delete'
  })
}

import request from '@/utils/request'

// 查询环境数据记录列表
export function listEnvironment(query) {
  return request({
    url: '/creb/environment/list',
    method: 'get',
    params: query
  })
}

// 查询环境数据记录详细
export function getEnvironment(dataId) {
  return request({
    url: '/creb/environment/' + dataId,
    method: 'get'
  })
}

// 新增环境数据记录
export function addEnvironment(data) {
  return request({
    url: '/creb/environment',
    method: 'post',
    data: data
  })
}

// 修改环境数据记录
export function updateEnvironment(data) {
  return request({
    url: '/creb/environment',
    method: 'put',
    data: data
  })
}

// 删除环境数据记录
export function delEnvironment(dataId) {
  return request({
    url: '/creb/environment/' + dataId,
    method: 'delete'
  })
}

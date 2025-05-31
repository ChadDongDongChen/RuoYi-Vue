import request from '@/utils/request'

// 查询异常预警记录列表
export function listAlert(query) {
  return request({
    url: '/creb/alert/list',
    method: 'get',
    params: query
  })
}

// 查询异常预警记录详细
export function getAlert(alertId) {
  return request({
    url: '/creb/alert/' + alertId,
    method: 'get'
  })
}

// 新增异常预警记录
export function addAlert(data) {
  return request({
    url: '/creb/alert',
    method: 'post',
    data: data
  })
}

// 修改异常预警记录
export function updateAlert(data) {
  return request({
    url: '/creb/alert',
    method: 'put',
    data: data
  })
}

// 删除异常预警记录
export function delAlert(alertId) {
  return request({
    url: '/creb/alert/' + alertId,
    method: 'delete'
  })
}

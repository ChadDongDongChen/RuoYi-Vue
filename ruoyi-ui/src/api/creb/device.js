import request from '@/utils/request'

// 查询环境监测设备列表
export function listDevice(query) {
  return request({
    url: '/creb/device/list',
    method: 'get',
    params: query
  })
}

// 查询环境监测设备详细
export function getDevice(deviceId) {
  return request({
    url: '/creb/device/' + deviceId,
    method: 'get'
  })
}

// 新增环境监测设备
export function addDevice(data) {
  return request({
    url: '/creb/device',
    method: 'post',
    data: data
  })
}

// 修改环境监测设备
export function updateDevice(data) {
  return request({
    url: '/creb/device',
    method: 'put',
    data: data
  })
}

// 删除环境监测设备
export function delDevice(deviceId) {
  return request({
    url: '/creb/device/' + deviceId,
    method: 'delete'
  })
}

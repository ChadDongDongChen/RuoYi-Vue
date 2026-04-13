import request from '@/utils/request'

export function listOpenkey(query) {
  return request({ url: '/kf/openkey/list', method: 'get', params: query })
}

export function getOpenkey(keyId) {
  return request({ url: '/kf/openkey/' + keyId, method: 'get' })
}

export function addOpenkey(data) {
  return request({ url: '/kf/openkey', method: 'post', data })
}

export function updateOpenkey(data) {
  return request({ url: '/kf/openkey', method: 'put', data })
}

export function delOpenkey(keyId) {
  return request({ url: '/kf/openkey/' + keyId, method: 'delete' })
}

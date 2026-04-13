import request from '@/utils/request'

export function listMessage(query) {
  return request({ url: '/kf/message/list', method: 'get', params: query })
}

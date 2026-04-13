import request from '@/utils/request'

export function listCustomer(query) {
  return request({ url: '/kf/customer/list', method: 'get', params: query })
}

export function getCustomer(customerId) {
  return request({ url: '/kf/customer/' + customerId, method: 'get' })
}

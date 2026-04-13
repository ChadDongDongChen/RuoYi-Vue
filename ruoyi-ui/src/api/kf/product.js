import request from '@/utils/request'

export function listProduct(query) {
  return request({ url: '/kf/product/list', method: 'get', params: query })
}

export function getProduct(productId) {
  return request({ url: '/kf/product/' + productId, method: 'get' })
}

export function addProduct(data) {
  return request({ url: '/kf/product', method: 'post', data })
}

export function updateProduct(data) {
  return request({ url: '/kf/product', method: 'put', data })
}

export function delProduct(productId) {
  return request({ url: '/kf/product/' + productId, method: 'delete' })
}

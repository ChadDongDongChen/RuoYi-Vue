import request from '@/utils/request'
import { upload } from '@/utils/upload'

// 查询可用规格列表
export function getSpecList() {
  return request({ url: '/app/ai/idphoto/specs', method: 'get' })
}

// 上传自拍照片
export function uploadPhoto(data) {
  return upload({ url: '/app/ai/idphoto/upload', name: 'file', filePath: data.filePath })
}

// 生成证件照
export function generateIdPhoto(data, options) {
  return request({ url: '/app/ai/idphoto/generate', method: 'post', data, ...options })
}

// 支付订单
export function payOrder(data) {
  return request({ url: '/app/ai/idphoto/pay', method: 'post', data })
}

// 查询订单详情
export function getOrderDetail(orderNo) {
  return request({ url: `/app/ai/idphoto/order/${orderNo}`, method: 'get' })
}

// 查询订单列表
export function getOrderList(params) {
  return request({ url: '/app/ai/idphoto/orders', method: 'get', params })
}

// 微信小程序登录（code 换 token）
export function wxLogin(code) {
  return request({ url: '/app/ai/idphoto/wx-login', method: 'post', data: { code } })
}

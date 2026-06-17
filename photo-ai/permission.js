import { getToken } from '@/utils/auth'

// 无需登录的页面白名单
const whiteList = ['/pages/ai-idphoto/index', '/pages/login/index']

// 全局路由守卫
uni.addInterceptor('navigateTo', {
  invoke(e) {
    return checkAuth(e.url)
  }
})

uni.addInterceptor('redirectTo', {
  invoke(e) {
    return checkAuth(e.url)
  }
})

uni.addInterceptor('reLaunch', {
  invoke(e) {
    return checkAuth(e.url)
  }
})

function checkAuth(url) {
  // 解析路径
  const path = url.split('?')[0].replace(/^\//, '')
  const fullPath = '/' + path

  // 在白名单中的页面无需登录
  if (whiteList.includes(fullPath)) {
    return true
  }

  // 检查 token
  if (getToken()) {
    return true
  }

  // 未登录跳转登录页
  uni.reLaunch({ url: '/pages/login' })
  return false
}

<template>
  <view class="page">
    <view class="status-bar"></view>
    <view class="logo-wrap">
      <view class="logo"></view>
      <text class="app-name">AI证件照</text>
      <text class="app-desc">自拍一键变标准证件照</text>
    </view>
    <view class="login-wrap">
      <!-- #ifdef MP-WEIXIN -->
      <button class="login-btn" @tap="wxLogin">
        <view class="wechat-icon"></view>
        <text>微信一键登录</text>
      </button>
      <!-- #endif -->
      <!-- #ifndef MP-WEIXIN -->
      <button class="login-btn disabled">
        <text>请在微信小程序中使用</text>
      </button>
      <!-- #endif -->
    </view>
  </view>
</template>

<script>
import { wxLogin as apiWxLogin } from '@/api/idphoto'
import { getToken, setToken } from '@/utils/auth'

export default {
  data() {
    return { loading: false }
  },
  onLoad() {
    if (getToken()) {
      uni.navigateBack()
    }
  },
  methods: {
    wxLogin() {
      if (this.loading) return
      this.loading = true
      uni.showLoading({ title: '登录中...' })
      uni.login({
        provider: 'weixin',
        success: async (res) => {
          try {
            const loginRes = await apiWxLogin(res.code)
            // token 在 data 或 msg 字段
            const token = loginRes.data || loginRes.msg
            setToken(token)
            uni.hideLoading()
            uni.showToast({ title: '登录成功', icon: 'success' })
            setTimeout(() => {
              uni.navigateBack()
            }, 500)
          } catch (e) {
            uni.hideLoading()
            uni.showToast({ title: '登录失败，请重试', icon: 'none' })
          } finally {
            this.loading = false
          }
        },
        fail: () => {
          uni.hideLoading()
          this.loading = false
          uni.showToast({ title: '取消登录', icon: 'none' })
        }
      })
    }
  }
}
</script>

<style lang="scss" scoped>
page { background: linear-gradient(180deg, #f0f6ff 0%, #ffffff 40%); }
.status-bar { height: 36px; }
.logo-wrap {
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 80px 20px 40px;
}
.logo {
  width: 80px;
  height: 80px;
  border-radius: 20px;
  background: linear-gradient(135deg, #1977ff, #26d5ff);
  margin-bottom: 20px;
  box-shadow: 0 12px 28px rgba(25, 119, 255, 0.28);
}
.logo::before {
  content: '';
  position: absolute;
  width: 32px;
  height: 4px;
  background: rgba(255, 255, 255, 0.9);
  border-radius: 4px;
  left: 50%;
  top: 44px;
  transform: translate(-50%, -50%);
}
.logo::after {
  content: '';
  position: absolute;
  width: 4px;
  height: 32px;
  background: rgba(255, 255, 255, 0.9);
  border-radius: 4px;
  left: 50%;
  top: 40px;
  transform: translate(-50%, -50%);
}
.app-name { font-size: 24px; font-weight: 800; color: #111827; margin-bottom: 6px; }
.app-desc { font-size: 14px; color: #6b7280; }
.login-wrap { padding: 40px 30px; }
.login-btn {
  width: 100%;
  height: 52px;
  border-radius: 16px;
  background: linear-gradient(135deg, #1977ff, #26d5ff);
  color: #fff;
  font-size: 16px;
  font-weight: 700;
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 10px;
  border: none;
  box-shadow: 0 12px 24px rgba(25, 119, 255, 0.24);
}
.login-btn:active { transform: scale(0.98); }
.login-btn.disabled {
  background: #e5e7eb;
  color: #9ca3af;
  box-shadow: none;
}
.wechat-icon {
  width: 22px;
  height: 22px;
  border-radius: 50%;
  background: rgba(255, 255, 255, 0.3);
  position: relative;
}
.wechat-icon::before {
  content: '';
  position: absolute;
  width: 5px;
  height: 5px;
  border-radius: 50%;
  background: #fff;
  top: 7px;
  left: 5px;
  box-shadow: 7px 0 #fff;
}
</style>

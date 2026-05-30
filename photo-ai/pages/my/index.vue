<template>
  <view class="page">
    <view class="status-bar"></view>
    <view class="profile">
      <view class="avatar"></view>
      <text class="nick">{{ nickname }}</text>
    </view>
    <view class="menu">
      <view class="menu-item" @tap="viewOrders">
        <text>我的订单</text>
        <text class="arrow">></text>
      </view>
      <view class="menu-item" @tap="viewHistory">
        <text>制作记录</text>
        <text class="arrow">></text>
      </view>
      <view class="menu-item" @tap="about">
        <text>关于</text>
        <text class="arrow">></text>
      </view>
    </view>
  </view>
</template>

<script>
import { getToken } from '@/utils/auth'

export default {
  data() {
    return {}
  },
  computed: {
    nickname() {
      return getToken() ? '微信用户' : '未登录'
    }
  },
  methods: {
    viewOrders() { uni.showToast({ title: '敬请期待', icon: 'none' }) },
    viewHistory() { uni.showToast({ title: '敬请期待', icon: 'none' }) },
    about() { uni.showToast({ title: 'AI证件照 v1.0.0', icon: 'none' }) }
  }
}
</script>

<style lang="scss" scoped>
page { background: #f7fbff; }
.status-bar { height: 36px; }
.profile {
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 40px 20px 30px;
}
.avatar {
  width: 72px;
  height: 72px;
  border-radius: 50%;
  background: linear-gradient(135deg, #eef6ff, #d4e8ff);
  margin-bottom: 14px;
}
.nick { font-size: 18px; font-weight: 700; color: #111827; }
.menu {
  margin: 0 18px;
  background: #fff;
  border-radius: 16px;
  box-shadow: 0 4px 16px rgba(0,0,0,0.06);
}
.menu-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 16px 18px;
  font-size: 15px;
  color: #26364d;
  border-bottom: 1px solid #f0f4f8;
}
.menu-item:last-child { border-bottom: none; }
.arrow { color: #c9d6ea; }
</style>

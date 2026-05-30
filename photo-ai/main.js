import App from './App'
import Vue from 'vue'
Vue.config.productionTip = false

// 挂载全局配置
import config from '@/config'
Vue.prototype.$config = config

App.mpType = 'app'

const app = new Vue({
  ...App
})
app.$mount()

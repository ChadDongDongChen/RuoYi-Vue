<script>
import config from '@/config'

export default {
  globalData: {
    config: config
  },
  onLaunch: function() {
    console.log('App Launch')
  },
  onShow: function() {
    console.log('App Show')
  },
  onHide: function() {
    console.log('App Hide')
  }
}
</script>

<style>
page {
  background-color: #f7fbff;
}
</style>

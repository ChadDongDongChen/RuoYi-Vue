<template>
  <view class="page">
    <view class="status-bar"></view>

    <nav class="nav">
      <text class="nav-title">AI证件照</text>
<!--      <view class="capsule">-->
<!--        <view class="dot-menu"></view>-->
<!--        <view class="mini-circle"></view>-->
<!--      </view>-->
    </nav>

    <view class="banner">
      <view class="badge"><view class="badge-dot"></view> 微信小程序快速出片</view>
      <text class="banner-h1">AI智能生成专业证件照</text>
      <text class="banner-p">自拍一键变标准证件照，背景、尺寸、美颜、正装一次搞定。</text>
    </view>

    <view class="upload-card">
      <button class="upload-btn" @tap="openUpload">
        <view class="upload-icon"></view>
        <view class="upload-text-wrap">
          <text class="upload-title">上传自拍开始制作</text>
          <text class="upload-subtitle">支持相册 / 拍照，约 10 秒生成</text>
        </view>
      </button>
      <view class="price-strip">
        <text class="price">¥9.9<small>/次</small></text>
        <view class="benefits">
          <text class="benefit-tag">高清无水印下载</text>
          <text class="benefit-tag">多背景可选</text>
          <text class="benefit-tag">电子版+冲印排版</text>
        </view>
      </view>
    </view>

    <view class="section">
      <view class="section-head">
        <text class="section-title">热门证件照</text>
        <text class="section-more">更多规格</text>
      </view>
      <view class="category-grid">
        <view
          v-for="spec in specList"
          :key="spec.specId"
          class="category"
          :class="{ 'is-active': selectedSpec.specId === spec.specId }"
          @tap="selectSpec(spec)"
        >
          <view class="category-icon"></view>
          <text class="category-name">{{ spec.specName }}</text>
          <text class="category-price">¥{{ spec.price }}</text>
        </view>
      </view>
    </view>

    <view class="section">
      <view class="section-head">
        <text class="section-title">效果设置</text>
        <text class="section-more">生成前可调整</text>
      </view>
      <view class="setting-card">
        <view class="setting-row">
          <text class="setting-label">背景色</text>
          <view class="option-group">
            <view
              v-for="opt in bgOptions"
              :key="opt"
              class="option"
              :class="{ 'is-active': selectedBackground === opt }"
              @tap="selectedBackground = opt"
            >{{ opt }}</view>
          </view>
        </view>
        <view class="setting-row">
          <text class="setting-label">AI美颜</text>
          <view class="option-group">
            <view
              v-for="opt in beautyOptions"
              :key="opt"
              class="option"
              :class="{ 'is-active': selectedBeauty === opt }"
              @tap="selectedBeauty = opt"
            >{{ opt }}</view>
          </view>
        </view>
        <view class="setting-row">
          <text class="setting-label">AI正装</text>
          <view class="option-group">
            <view
              v-for="opt in suitOptions"
              :key="opt"
              class="option"
              :class="{ 'is-active': selectedSuit === opt }"
              @tap="selectedSuit = opt"
            >{{ opt }}</view>
          </view>
        </view>
      </view>
    </view>

    <view class="section">
      <view class="section-head">
        <text class="section-title">AI生成流程</text>
        <text class="section-more">简单 3 步</text>
      </view>
      <view class="flow-card">
        <view class="flow-step">
          <view class="flow-icon photo"></view>
          <text class="flow-title">上传</text>
          <text class="flow-desc">自拍原图</text>
        </view>
        <view class="arrow"></view>
        <view class="flow-step">
          <view class="flow-icon ai"></view>
          <text class="flow-title">AI生成</text>
          <text class="flow-desc">美颜正装</text>
        </view>
        <view class="arrow"></view>
        <view class="flow-step">
          <view class="flow-icon download"></view>
          <text class="flow-title">下载</text>
          <text class="flow-desc">高清排版</text>
        </view>
      </view>
    </view>

    <view class="section">
      <view class="section-head">
        <text class="section-title">生成案例</text>
        <text class="section-more">背景可切换</text>
      </view>
      <view class="case-card">
        <view class="compare">
          <view class="portrait before" data-label="自拍原图"></view>
          <view
            class="portrait after"
            :class="casePreviewClasses"
            data-label="AI成片"
          ></view>
        </view>
        <view class="case-tags">
          <text class="case-tag">{{ caseBgText }}</text>
          <text class="case-tag">{{ caseBeautyText }}</text>
          <text class="case-tag">{{ caseSuitText }}</text>
        </view>
      </view>
    </view>

    <view class="bottom-bar">
      <button class="primary-action" @tap="openUpload">{{ bottomActionText }}</button>
    </view>

    <!-- 上传弹窗 -->
    <view class="mask" v-if="uploadSheetVisible" @tap="closeUploadSheet">
      <view class="panel" @tap.stop>
        <view class="panel-head">
          <text class="panel-title">上传自拍</text>
          <button class="close-btn" @tap="closeUploadSheet">×</button>
        </view>
        <text class="panel-subtitle">请选择清晰正脸照片，AI会自动裁剪为标准证件照。</text>
        <view class="sheet-body">
          <button class="sheet-action primary" @tap="handleCameraUpload">拍照上传</button>
          <button class="sheet-action" @tap="handleAlbumSelect">从相册选择</button>
          <button class="sheet-action cancel" @tap="closeUploadSheet">取消</button>
        </view>
      </view>
    </view>

    <!-- 生成中弹窗 -->
    <view class="mask" v-if="generatingVisible" @tap.stop>
      <view class="panel center">
        <view class="panel-head">
          <text class="panel-title">AI正在生成</text>
          <button class="close-btn" @tap="closeGenerating">×</button>
        </view>
        <text class="panel-subtitle">正在为你处理 <text class="bold-text">{{ selectedSpec.specName || '一寸照' }}</text>，请稍等片刻。</text>
        <view class="generate-body">
          <view class="progress-track">
            <view class="progress-bar" :style="{ width: progressPercent + '%' }"></view>
          </view>
          <view class="progress-meta">
            <text>{{ progressText }}</text>
            <text>{{ progressPercent }}%</text>
          </view>
          <view class="step-list">
            <view
              v-for="(step, index) in genSteps"
              :key="index"
              class="gen-step"
              :class="stepClasses[index]"
            >
              <view class="step-dot"></view>
              <text>{{ step.label }}</text>
            </view>
          </view>
        </view>
      </view>
    </view>

    <!-- 确认成片与订单弹窗 -->
    <view class="mask" v-if="resultVisible" @tap="closeResult">
      <view class="panel" @tap.stop>
        <view class="panel-head">
          <text class="panel-title">确认成片与订单</text>
          <button class="close-btn" @tap="closeResult">×</button>
        </view>
        <text class="panel-subtitle">已生成高清预览，支付后可下载无水印电子版和冲印排版。</text>
        <view class="result-body">
          <view class="result-layout">
            <view
              class="result-preview"
              :class="resultPreviewClasses"
            ></view>
            <view class="order-card">
              <view class="order-row"><text>证件照规格</text><text class="order-value">{{ selectedSpec.specName }}</text></view>
              <view class="order-row"><text>背景色</text><text class="order-value">{{ selectedBackground }}</text></view>
              <view class="order-row"><text>AI美颜</text><text class="order-value">{{ selectedBeauty }}</text></view>
              <view class="order-row"><text>AI正装</text><text class="order-value">{{ selectedSuit }}</text></view>
              <view class="order-row total-row"><text>应付金额</text><text class="total-price">¥{{ priceDisplay }}</text></view>
            </view>
          </view>
          <button class="pay-confirm" @tap="handlePay">确认支付 ¥{{ priceDisplay }}</button>
        </view>
      </view>
    </view>

    <!-- 支付弹窗 -->
    <view class="mask" v-if="payVisible" @tap.stop>
      <view class="panel center">
        <view class="panel-head">
          <text class="panel-title">微信支付</text>
          <button class="close-btn" @tap="closePay">×</button>
        </view>
        <view class="pay-body">
          <text class="pay-amount">¥{{ priceDisplay }}</text>
          <text class="pay-note">AI证件照单次制作服务</text>
          <view class="pay-method">
            <view class="wechat-mark">
              <view class="wechat-icon"></view>
              <text class="pay-method-text">微信支付</text>
            </view>
            <view class="pay-check"></view>
          </view>
          <button class="pay-confirm" :disabled="paying" @tap="confirmPay">{{ paying ? '支付处理中...' : '确认支付' }}</button>
        </view>
      </view>
    </view>

    <!-- 成功弹窗 -->
    <view class="mask" v-if="successVisible" @tap="closeSuccess">
      <view class="panel center" @tap.stop>
        <view class="success-body">
          <view class="success-icon"></view>
          <text class="success-title">制作完成</text>
          <text class="success-desc">高清无水印证件照已生成，可保存电子版或下载冲印排版。</text>
          <view class="success-actions">
            <button class="success-primary" @tap="downloadHDPhoto">下载高清照</button>
            <button class="secondary-action" @tap="saveElectronic">保存电子版</button>
            <button class="secondary-action" @tap="makeAgain">继续制作</button>
          </view>
        </view>
      </view>
    </view>
  </view>
</template>

<script>
import { getSpecList, uploadPhoto, generateIdPhoto, payOrder, wxLogin } from '@/api/idphoto'
import { getToken, setToken } from '@/utils/auth'

const STEPS = [
  { label: '人像检测', text: '人像检测中' },
  { label: '背景替换', text: '正在替换证件照背景' },
  { label: '智能美颜', text: '正在进行自然美颜' },
  { label: '正装生成', text: '正在生成AI正装' },
  { label: '高清排版', text: '正在输出高清排版' }
]

export default {
  data() {
    return {
      specList: [],
      selectedSpec: {},
      selectedBackground: '蓝底',
      selectedBeauty: '自然',
      selectedSuit: '男士正装',
      bgOptions: ['蓝底', '白底', '红底'],
      beautyOptions: ['自然', '精致', '关闭'],
      suitOptions: ['不开启', '男士正装', '女士正装'],
      genSteps: STEPS,
      uploadSheetVisible: false,
      generatingVisible: false,
      resultVisible: false,
      payVisible: false,
      successVisible: false,
      generating: false,
      progressPercent: 0,
      currentStepIndex: 0,
      uploadedImageUrl: '',
      orderInfo: null,
      progressTimer: null,
      paying: false,
      priceText: '¥9.9'
    }
  },
  computed: {
    priceDisplay() {
      if (this.selectedSpec.price) {
        return this.selectedSpec.price
      }
      return '9.9'
    },
    bottomActionText() {
      return `立即制作 ¥${this.priceDisplay}`
    },
    progressText() {
      const idx = Math.min(STEPS.length - 1, Math.floor(this.progressPercent / 22))
      return STEPS[idx].text
    },
    casePreviewClasses() {
      const cls = {}
      if (this.selectedBackground === '白底') cls['bg-white'] = true
      if (this.selectedBackground === '红底') cls['bg-red'] = true
      if (this.selectedSuit === '不开启') cls['no-suit'] = true
      return cls
    },
    resultPreviewClasses() {
      return this.casePreviewClasses
    },
    caseBgText() {
      return `${this.selectedBackground}证件照`
    },
    caseBeautyText() {
      return this.selectedBeauty === '关闭' ? '原生肤色' : `${this.selectedBeauty}美颜`
    },
    caseSuitText() {
      return this.selectedSuit === '不开启' ? '不换正装' : 'AI正装'
    },
    stepClasses() {
      const currentIdx = Math.min(STEPS.length - 1, Math.floor(this.progressPercent / 22))
      return STEPS.map((_, i) => {
        if (this.progressPercent >= 100) return 'is-done'
        if (i < currentIdx) return 'is-done'
        if (i === currentIdx) return 'is-active'
        return ''
      })
    }
  },
  onLoad() {
    this.loadSpecs()
  },
  methods: {
    loadSpecs() {
      getSpecList().then(res => {
        this.specList = res.data || []
        if (this.specList.length > 0 && !this.selectedSpec.specId) {
          this.selectedSpec = this.specList[0]
        }
      }).catch(() => {
        // 使用默认规格
        this.specList = [
          { specId: 1, specName: '一寸照', price: 9.90 },
          { specId: 2, specName: '二寸照', price: 9.90 },
          { specId: 3, specName: '公务员考试', price: 9.90 },
          { specId: 4, specName: '教资报名', price: 9.90 },
          { specId: 5, specName: '求职简历照', price: 9.90 }
        ]
        this.selectedSpec = this.specList[0]
      })
    },
    selectSpec(spec) {
      this.selectedSpec = spec
      uni.showToast({ title: `已选择${spec.specName}`, icon: 'none' })
    },
    openUpload() {
      this.uploadSheetVisible = true
    },
    closeUploadSheet() {
      this.uploadSheetVisible = false
    },
    handleCameraUpload() {
      this.closeUploadSheet()
      uni.chooseImage({
        count: 1,
        sourceType: ['camera'],
        success: (res) => this.doUpload(res.tempFilePaths[0]),
        fail: () => {
          uni.showToast({ title: '取消拍照', icon: 'none' })
        }
      })
    },
    handleAlbumSelect() {
      this.closeUploadSheet()
      uni.chooseImage({
        count: 1,
        sourceType: ['album'],
        success: (res) => this.doUpload(res.tempFilePaths[0]),
        fail: () => {
          uni.showToast({ title: '取消选择', icon: 'none' })
        }
      })
    },
    async doUpload(filePath) {
      try {
        uni.showLoading({ title: '上传中...' })
        const res = await uploadPhoto({ filePath })
        this.uploadedImageUrl = res.url
        uni.hideLoading()
        this.startGeneration()
      } catch (e) {
        uni.hideLoading()
        uni.showToast({ title: '上传失败', icon: 'none' })
      }
    },
    startGeneration() {
      this.generatingVisible = true
      this.progressPercent = 0
      this.currentStepIndex = 0
      this.generating = true

      this.progressTimer = setInterval(() => {
        this.progressPercent = Math.min(100, this.progressPercent + Math.ceil(Math.random() * 9) + 4)
        if (this.progressPercent >= 100) {
          this.stopGenerate()
          setTimeout(() => {
            this.callGenerateAPI()
          }, 420)
        }
      }, 230)
    },
    stopGenerate() {
      if (this.progressTimer) {
        clearInterval(this.progressTimer)
        this.progressTimer = null
      }
      this.generating = false
    },
    closeGenerating() {
      this.stopGenerate()
      this.generatingVisible = false
    },
    async callGenerateAPI() {
      try {
        const res = await generateIdPhoto({
          originalUrl: this.uploadedImageUrl,
          specId: this.selectedSpec.specId,
          background: this.selectedBackground,
          beauty: this.selectedBeauty,
          suit: this.selectedSuit
        })
        this.generatingVisible = false
        this.orderInfo = res.data
        this.resultVisible = true
      } catch (e) {
        this.generatingVisible = false
        uni.showToast({ title: '生成失败，请重试', icon: 'none' })
      }
    },
    handlePay() {
      this.resultVisible = false
      this.payVisible = true
    },
    closeResult() {
      this.resultVisible = false
    },
    closePay() {
      this.payVisible = false
    },
    async confirmPay() {
      if (this.paying) return
      this.paying = true
      try {
        const token = getToken()
        if (!token) {
          const loginOk = await this.wxLoginByCode()
          if (!loginOk) return
        }
        await payOrder({
          orderNo: this.orderInfo.orderNo,
          payType: 'wechat'
        })
        this.paying = false
        this.payVisible = false
        this.successVisible = true
      } catch (e) {
        this.paying = false
        uni.showToast({ title: '支付失败', icon: 'none' })
      }
    },
    wxLoginByCode() {
      return new Promise((resolve) => {
        // #ifdef MP-WEIXIN
        uni.login({
          provider: 'weixin',
          success: async (res) => {
            try {
              const loginRes = await wxLogin(res.code)
              setToken(loginRes.data)
              resolve(true)
            } catch (e) {
              uni.showToast({ title: '微信登录失败', icon: 'none' })
              resolve(false)
            }
          },
          fail: () => {
            uni.showToast({ title: '微信登录已取消', icon: 'none' })
            resolve(false)
          }
        })
        // #endif
        // #ifndef MP-WEIXIN
        uni.showToast({ title: '请在微信小程序中使用', icon: 'none' })
        resolve(false)
        // #endif
      })
    },
    closeSuccess() {
      this.successVisible = false
    },
    downloadHDPhoto() {
      const url = this.orderInfo ? this.orderInfo.resultUrl : ''
      if (!url) {
        uni.showToast({ title: '暂无可下载图片', icon: 'none' })
        return
      }
      const baseUrl = getApp().globalData.config.baseUrl
      const downloadUrl = baseUrl + url
      uni.downloadFile({
        url: downloadUrl,
        success: (res) => {
          if (res.statusCode === 200) {
            uni.saveImageToPhotosAlbum({
              filePath: res.tempFilePath,
              success: () => {
                uni.showToast({ title: '高清照已保存到相册', icon: 'success' })
              },
              fail: () => {
                uni.showToast({ title: '保存失败，请检查权限', icon: 'none' })
              }
            })
          }
        },
        fail: () => {
          uni.showToast({ title: '下载失败', icon: 'none' })
        }
      })
      this.successVisible = false
    },
    saveElectronic() {
      uni.showToast({ title: '电子版已保存', icon: 'success' })
      this.successVisible = false
    },
    makeAgain() {
      this.successVisible = false
      this.orderInfo = null
      this.uploadedImageUrl = ''
      this.uploadSheetVisible = true
    }
  }
}
</script>

<style lang="scss" scoped>
page {
  background:
    radial-gradient(circle at 14% 3%, rgba(38, 213, 255, 0.2), transparent 28%),
    linear-gradient(180deg, #f7fbff 0%, #ffffff 36%, #f6f9fe 100%);
}

::v-deep button::after {
  border: none;
}

.page {
  position: relative;
  width: 100%;
  max-width: 430px;
  min-height: 100vh;
  margin: 0 auto;
  padding: 0 18px 112px;
  box-sizing: border-box;
  overflow: hidden;
}

.status-bar {
  height: 36px;
}

.nav {
  display: flex;
  align-items: center;
  justify-content: space-between;
  height: 44px;
}

.nav-title {
  font-size: 17px;
  font-weight: 700;
  color: #111827;
}

.capsule {
  display: flex;
  align-items: center;
  gap: 8px;
  padding: 6px 10px;
  border: 1px solid rgba(18, 83, 166, 0.12);
  border-radius: 999px;
  background: rgba(255, 255, 255, 0.72);
  backdrop-filter: blur(14px);
}

.dot-menu {
  width: 22px;
  height: 22px;
  border-radius: 50%;
  background: #f0f5ff;
  position: relative;
}

.dot-menu::before {
  content: '';
  position: absolute;
  top: 10px;
  left: 5px;
  width: 3px;
  height: 3px;
  border-radius: 50%;
  background: #335174;
  box-shadow: 5px 0 #335174, 10px 0 #335174;
}

.mini-circle {
  width: 22px;
  height: 22px;
  border-radius: 50%;
  background: #f0f5ff;
  position: relative;
}

.mini-circle::before {
  content: '';
  position: absolute;
  inset: 6px;
  border: 2px solid #335174;
  border-radius: 50%;
}

.banner {
  position: relative;
  margin-top: 14px;
  padding: 24px 20px 22px;
  min-height: 178px;
  border-radius: 28px;
  overflow: hidden;
  color: #fff;
  background:
    radial-gradient(circle at 88% 16%, rgba(255, 255, 255, 0.42), transparent 18%),
    linear-gradient(135deg, #1677ff 0%, #0f5cff 48%, #23d3ff 100%);
  box-shadow: 0 22px 56px rgba(22, 119, 255, 0.25);
}

.banner::before {
  content: '';
  position: absolute;
  right: -28px;
  bottom: -34px;
  width: 174px;
  height: 174px;
  border-radius: 46px;
  background: rgba(255, 255, 255, 0.16);
  transform: rotate(18deg);
}

.banner::after {
  content: '';
  position: absolute;
  right: 18px;
  bottom: 18px;
  width: 94px;
  height: 118px;
  border-radius: 44px 44px 28px 28px;
  background:
    radial-gradient(circle at 50% 28%, #ffe2cf 0 22px, transparent 23px),
    linear-gradient(180deg, transparent 0 45px, rgba(255, 255, 255, 0.92) 46px 100%);
  box-shadow: inset 0 -26px 0 rgba(22, 119, 255, 0.14), 0 16px 34px rgba(0, 46, 126, 0.18);
}

.badge {
  display: inline-flex;
  align-items: center;
  gap: 6px;
  padding: 6px 10px;
  border-radius: 999px;
  font-size: 12px;
  font-weight: 600;
  background: rgba(255, 255, 255, 0.18);
  border: 1px solid rgba(255, 255, 255, 0.26);
}

.badge-dot {
  width: 7px;
  height: 7px;
  border-radius: 50%;
  background: #9ff7ff;
  box-shadow: 0 0 12px rgba(159, 247, 255, 0.92);
}

.banner-h1 {
  display: block;
  position: relative;
  z-index: 1;
  width: 220px;
  margin-top: 16px;
  margin-bottom: 8px;
  font-size: 28px;
  line-height: 1.18;
  font-weight: 800;
  color: #fff;
}

.banner-p {
  display: block;
  position: relative;
  z-index: 1;
  width: 220px;
  margin: 0;
  font-size: 13px;
  line-height: 1.7;
  color: rgba(255, 255, 255, 0.84);
}

.upload-card {
  margin-top: -18px;
  position: relative;
  z-index: 2;
  padding: 16px;
  border-radius: 24px;
  background: rgba(255, 255, 255, 0.92);
  box-shadow: 0 18px 50px rgba(18, 83, 166, 0.12);
  backdrop-filter: blur(18px);
}

.upload-btn {
  width: 100%;
  min-height: 96px;
  border-radius: 20px;
  color: #fff;
  background: linear-gradient(135deg, #1977ff, #26d5ff);
  box-shadow: 0 16px 34px rgba(25, 119, 255, 0.28);
  display: flex;
  align-items: center;
  gap: 12px;
  padding: 18px;
  text-align: left;
  font-size: inherit;
  line-height: inherit;
}

.upload-btn:active {
  transform: scale(0.98);
}

.upload-icon {
  width: 54px;
  height: 54px;
  min-width: 54px;
  border-radius: 18px;
  background: rgba(255, 255, 255, 0.2);
  position: relative;
}

.upload-icon::before {
  content: '';
  position: absolute;
  width: 25px;
  height: 4px;
  background: #fff;
  border-radius: 4px;
  left: 50%;
  top: 50%;
  transform: translate(-50%, -50%);
}

.upload-icon::after {
  content: '';
  position: absolute;
  width: 4px;
  height: 25px;
  background: #fff;
  border-radius: 4px;
  left: 50%;
  top: 50%;
  transform: translate(-50%, -50%);
}

.upload-text-wrap {
  display: flex;
  flex-direction: column;
}

.upload-title {
  display: block;
  font-size: 20px;
  font-weight: 800;
  margin-bottom: 6px;
  color: #fff;
}

.upload-subtitle {
  display: block;
  font-size: 13px;
  color: rgba(255, 255, 255, 0.84);
}

.price-strip {
  margin-top: 12px;
  display: flex;
  align-items: center;
  gap: 12px;
  padding: 12px;
  border-radius: 18px;
  background: #f5f9ff;
  border: 1px solid #e6f0ff;
}

.price {
  color: #ff4d4f;
  font-size: 24px;
  font-weight: 900;
  line-height: 1;
}

.price small {
  font-size: 12px;
  font-weight: 700;
}

.benefits {
  display: flex;
  flex-wrap: wrap;
  gap: 6px;
  flex: 1;
}

.benefit-tag {
  padding: 5px 8px;
  border-radius: 999px;
  color: #1764df;
  background: #ffffff;
  font-size: 11px;
  font-weight: 700;
  box-shadow: inset 0 0 0 1px #e0ecff;
}

.section {
  margin-top: 24px;
}

.section-head {
  display: flex;
  align-items: center;
  justify-content: space-between;
  margin-bottom: 12px;
}

.section-title {
  font-size: 18px;
  font-weight: 800;
  color: #111827;
}

.section-more {
  font-size: 12px;
  color: #697386;
}

.category-grid {
  display: flex;
  gap: 8px;
  overflow-x: auto;
}

.category {
  min-width: 0;
  flex: 1;
  min-height: 92px;
  padding: 10px 5px;
  border-radius: 18px;
  background: #ffffff;
  border: 1px solid rgba(232, 238, 248, 0.8);
  box-shadow: 0 10px 26px rgba(19, 63, 116, 0.06);
  text-align: center;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
}

.category:active {
  transform: scale(0.98);
}

.category.is-active {
  border-color: rgba(25, 119, 255, 0.75);
  box-shadow: 0 14px 28px rgba(25, 119, 255, 0.15);
  background: linear-gradient(180deg, #ffffff, #f3f8ff);
}

.category-icon {
  width: 38px;
  height: 38px;
  margin: 0 auto 8px;
  border-radius: 14px;
  display: flex;
  align-items: center;
  justify-content: center;
  color: #1977ff;
  background: linear-gradient(180deg, #edf6ff, #ffffff);
  box-shadow: inset 0 0 0 1px #e7f1ff;
}

.category-icon::before {
  content: '';
  width: 17px;
  height: 21px;
  border-radius: 5px;
  border: 2px solid currentColor;
  background: linear-gradient(180deg, transparent 46%, rgba(25, 119, 255, 0.18) 47%);
}

.category-name {
  display: block;
  font-size: 11px;
  line-height: 1.25;
  font-weight: 700;
  color: #26364d;
  word-break: keep-all;
}

.category-price {
  margin-top: 5px;
  color: #ff4d4f !important;
  font-size: 10px !important;
  font-weight: 800 !important;
}

.flow-card {
  border-radius: 24px;
  background: #ffffff;
  border: 1px solid rgba(232, 238, 248, 0.86);
  box-shadow: 0 14px 38px rgba(19, 63, 116, 0.07);
  padding: 18px 12px;
  display: flex;
  align-items: center;
}

.flow-step {
  flex: 1;
  text-align: center;
}

.flow-icon {
  width: 54px;
  height: 54px;
  margin: 0 auto 9px;
  border-radius: 19px;
  background: #f0f7ff;
  display: flex;
  align-items: center;
  justify-content: center;
  position: relative;
}

.flow-icon.photo::before {
  content: '';
  width: 27px;
  height: 22px;
  border-radius: 7px;
  border: 2px solid #1977ff;
  background: radial-gradient(circle at 50% 45%, #1977ff 0 4px, transparent 5px);
}

.flow-icon.ai::before {
  content: '';
  width: 26px;
  height: 26px;
  border-radius: 9px;
  background: linear-gradient(135deg, #1977ff, #26d5ff);
  box-shadow: 0 0 0 5px rgba(25, 119, 255, 0.12);
}

.flow-icon.download::before {
  content: '';
  width: 24px;
  height: 24px;
  border-bottom: 3px solid #1977ff;
  border-radius: 0 0 7px 7px;
  background: linear-gradient(90deg, transparent 10px, #1977ff 10px 14px, transparent 14px);
}

.flow-icon.download::after {
  content: '';
  position: absolute;
  top: 19px;
  width: 12px;
  height: 12px;
  border-right: 3px solid #1977ff;
  border-bottom: 3px solid #1977ff;
  transform: rotate(45deg);
}

.flow-title {
  display: block;
  font-size: 13px;
  font-weight: 700;
  margin-bottom: 4px;
  color: #111827;
}

.flow-desc {
  display: block;
  font-size: 11px;
  color: #697386;
}

.arrow {
  width: 22px;
  height: 1px;
  background: linear-gradient(90deg, #d4e4fb, #9cc7ff);
  position: relative;
  flex-shrink: 0;
}

.arrow::after {
  content: '';
  position: absolute;
  right: 0;
  top: -4px;
  width: 8px;
  height: 8px;
  border-top: 1px solid #9cc7ff;
  border-right: 1px solid #9cc7ff;
  transform: rotate(45deg);
}

.setting-card {
  border-radius: 24px;
  background: #ffffff;
  border: 1px solid rgba(232, 238, 248, 0.86);
  box-shadow: 0 14px 38px rgba(19, 63, 116, 0.07);
  padding: 16px;
}

.setting-row + .setting-row {
  margin-top: 16px;
}

.setting-label {
  display: block;
  margin-bottom: 9px;
  color: #26364d;
  font-size: 13px;
  font-weight: 800;
}

.option-group {
  display: flex;
  gap: 8px;
}

.option {
  flex: 1;
  min-height: 38px;
  border-radius: 13px;
  color: #40516b;
  background: #f5f8fd;
  border: 1px solid #e7edf7;
  font-size: 12px;
  font-weight: 800;
  text-align: center;
  line-height: 38px;
}

.option:active {
  transform: scale(0.98);
}

.option.is-active {
  color: #fff;
  border-color: transparent;
  background: linear-gradient(135deg, #1977ff, #26d5ff);
  box-shadow: 0 10px 24px rgba(25, 119, 255, 0.22);
}

.case-card {
  border-radius: 24px;
  background: #ffffff;
  border: 1px solid rgba(232, 238, 248, 0.86);
  box-shadow: 0 14px 38px rgba(19, 63, 116, 0.07);
  padding: 14px;
}

.compare {
  display: flex;
  gap: 10px;
  margin-bottom: 14px;
}

.portrait {
  flex: 1;
  min-height: 176px;
  border-radius: 20px;
  overflow: hidden;
  position: relative;
  background: linear-gradient(180deg, #eef3f9, #ffffff);
}

.portrait.before {
  background:
    radial-gradient(circle at 50% 42%, #f4ceb7 0 24px, transparent 25px),
    linear-gradient(180deg, #dfe9f6 0 40%, #eff5fb 40% 100%);
}

.portrait.after,
.result-preview {
  background:
    radial-gradient(circle at 50% 38%, #f5d0ba 0 24px, transparent 25px),
    linear-gradient(180deg, #2f8cff 0 53%, #ffffff 53% 100%);
}

.portrait.after.bg-white,
.result-preview.bg-white {
  background:
    radial-gradient(circle at 50% 38%, #f5d0ba 0 24px, transparent 25px),
    linear-gradient(180deg, #f7fbff 0 53%, #ffffff 53% 100%);
}

.portrait.after.bg-red,
.result-preview.bg-red {
  background:
    radial-gradient(circle at 50% 38%, #f5d0ba 0 24px, transparent 25px),
    linear-gradient(180deg, #e85252 0 53%, #ffffff 53% 100%);
}

.portrait::before,
.result-preview::before {
  content: '';
  position: absolute;
  left: 50%;
  bottom: 27px;
  width: 78px;
  height: 70px;
  border-radius: 28px 28px 16px 16px;
  transform: translateX(-50%);
  background: linear-gradient(180deg, #26364d, #111827);
}

.portrait.before::before {
  background: linear-gradient(180deg, #b5c0ce, #8d99a8);
}

.portrait.no-suit::before,
.result-preview.no-suit::before {
  background: linear-gradient(180deg, #6ba8ff, #2f80ed);
}

.portrait::after {
  content: attr(data-label);
  position: absolute;
  left: 10px;
  top: 10px;
  padding: 5px 8px;
  border-radius: 999px;
  font-size: 11px;
  font-weight: 700;
  color: #fff;
  background: rgba(17, 24, 39, 0.36);
  backdrop-filter: blur(8px);
}

.case-tags {
  display: flex;
  gap: 8px;
  flex-wrap: wrap;
}

.case-tag {
  padding: 7px 10px;
  border-radius: 999px;
  font-size: 12px;
  font-weight: 700;
  color: #1764df;
  background: #eef6ff;
}

.bottom-bar {
  position: fixed;
  left: 50%;
  bottom: 0;
  z-index: 10;
  width: 100%;
  max-width: 430px;
  padding: 12px 18px;
  background: rgba(255, 255, 255, 0.88);
  border-top: 1px solid rgba(232, 238, 248, 0.9);
  box-shadow: 0 -16px 36px rgba(19, 63, 116, 0.08);
  backdrop-filter: blur(18px);
  transform: translateX(-50%);
  box-sizing: border-box;
}

.primary-action {
  width: 100%;
  height: 56px;
  border-radius: 18px;
  color: #fff;
  font-size: 18px;
  font-weight: 800;
  background: linear-gradient(135deg, #0958ff, #26d5ff);
  box-shadow: 0 14px 30px rgba(25, 119, 255, 0.3);
  border: none;
  line-height: 56px;
  text-align: center;
}

.primary-action:active {
  transform: scale(0.98);
}

.mask {
  position: fixed;
  inset: 0;
  z-index: 20;
  display: flex;
  align-items: flex-end;
  justify-content: center;
  padding: 18px;
  background: rgba(11, 24, 43, 0.42);
  backdrop-filter: blur(8px);
}

.panel {
  width: 100%;
  max-width: 430px;
  border-radius: 28px 28px 24px 24px;
  background: #fff;
  box-shadow: 0 26px 70px rgba(11, 24, 43, 0.24);
  animation: rise 0.24s ease both;
  overflow: hidden;
}

.panel.center {
  align-self: center;
  border-radius: 28px;
  animation: fadeIn 0.24s ease both;
}

@keyframes rise {
  from {
    opacity: 0;
    transform: translateY(18px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}

@keyframes fadeIn {
  from { opacity: 0; transform: scale(0.96); }
  to { opacity: 1; transform: scale(1); }
}

.panel-head {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 18px 18px 10px;
}

.panel-title {
  font-size: 18px;
  font-weight: 900;
  color: #111827;
}

.panel-subtitle {
  margin: 4px 18px 0;
  color: #697386;
  font-size: 13px;
  line-height: 1.6;
  display: block;
}

.bold-text {
  font-weight: 900;
}

.close-btn {
  width: 32px;
  height: 32px;
  border-radius: 50%;
  color: #6b778d;
  background: #f3f6fb;
  font-size: 22px;
  line-height: 1;
  border: none;
  padding: 0;
  display: flex;
  align-items: center;
  justify-content: center;
}

.sheet-body {
  padding: 8px 14px 14px;
}

.sheet-action {
  width: 100%;
  min-height: 58px;
  margin-top: 8px;
  border-radius: 18px;
  background: #f6f9fe;
  color: #172033;
  font-size: 16px;
  font-weight: 800;
  text-align: center;
  border: none;
  line-height: 58px;
}

.sheet-action.primary {
  color: #fff;
  background: linear-gradient(135deg, #1977ff, #26d5ff);
}

.sheet-action.cancel {
  color: #697386;
  background: #fff;
  border: 1px solid #edf2fa;
}

.sheet-action:active {
  transform: scale(0.98);
}

.generate-body,
.result-body,
.pay-body,
.success-body {
  padding: 10px 18px 20px;
}

.progress-track {
  height: 10px;
  margin: 18px 0 12px;
  border-radius: 999px;
  overflow: hidden;
  background: #edf4ff;
}

.progress-bar {
  width: 0%;
  height: 100%;
  border-radius: inherit;
  background: linear-gradient(90deg, #1977ff, #26d5ff);
  transition: width 0.18s ease;
}

.progress-meta {
  display: flex;
  align-items: center;
  justify-content: space-between;
  color: #26364d;
  font-size: 13px;
  font-weight: 800;
}

.step-list {
  display: flex;
  flex-direction: column;
  gap: 9px;
  margin-top: 18px;
}

.gen-step {
  display: flex;
  align-items: center;
  gap: 10px;
  padding: 11px 12px;
  border-radius: 16px;
  color: #697386;
  background: #f7f9fd;
  font-size: 13px;
  font-weight: 700;
}

.step-dot {
  width: 18px;
  height: 18px;
  min-width: 18px;
  border-radius: 50%;
  border: 2px solid #c9d6ea;
}

.gen-step.is-active {
  color: #1764df;
  background: #eff7ff;
}

.gen-step.is-active .step-dot {
  border-color: #1977ff;
  border-top-color: transparent;
  animation: spin 0.8s linear infinite;
}

.gen-step.is-done {
  color: #1b8a62;
  background: #effbf6;
}

.gen-step.is-done .step-dot {
  border-color: #16c784;
  background: #16c784;
  position: relative;
}

.gen-step.is-done .step-dot::after {
  content: '';
  position: absolute;
  left: 4px;
  top: 2px;
  width: 6px;
  height: 10px;
  border-right: 2px solid #fff;
  border-bottom: 2px solid #fff;
  transform: rotate(45deg);
}

@keyframes spin {
  to { transform: rotate(360deg); }
}

.result-layout {
  display: flex;
  gap: 14px;
  margin-bottom: 14px;
}

.result-preview {
  width: 124px;
  min-height: 168px;
  border-radius: 22px;
  position: relative;
  overflow: hidden;
  flex-shrink: 0;
}

.order-card {
  flex: 1;
  padding: 12px;
  border-radius: 20px;
  background: #f6f9fe;
  border: 1px solid #e8eef8;
}

.order-row {
  display: flex;
  justify-content: space-between;
  gap: 10px;
  padding: 6px 0;
  color: #697386;
  font-size: 12px;
  line-height: 1.35;
}

.order-value {
  color: #26364d;
  font-weight: 800;
  text-align: right;
}

.total-row {
  margin-top: 6px;
  padding-top: 10px;
  border-top: 1px solid #e1eaf7;
  color: #26364d;
  font-size: 14px;
  font-weight: 900;
}

.total-price {
  color: #ff4d4f;
  font-size: 20px;
  font-weight: 900;
}

.pay-confirm {
  width: 100%;
  height: 56px;
  border-radius: 18px;
  color: #fff;
  font-size: 18px;
  font-weight: 800;
  background: linear-gradient(135deg, #0958ff, #26d5ff);
  box-shadow: 0 14px 30px rgba(25, 119, 255, 0.3);
  border: none;
  line-height: 56px;
  text-align: center;
}

.pay-confirm:active {
  transform: scale(0.98);
}

.pay-confirm[disabled] {
  opacity: 0.72;
}

.pay-amount {
  margin: 8px 0 4px;
  text-align: center;
  color: #ff4d4f;
  font-size: 34px;
  font-weight: 900;
  line-height: 1.1;
  display: block;
}

.pay-note {
  margin: 0;
  text-align: center;
  color: #697386;
  font-size: 12px;
  display: block;
}

.pay-method {
  display: flex;
  align-items: center;
  justify-content: space-between;
  gap: 14px;
  padding: 15px;
  margin: 12px 0 18px;
  border-radius: 18px;
  background: #f7fbf8;
  border: 1px solid #dff3e8;
}

.wechat-mark {
  display: flex;
  align-items: center;
  gap: 10px;
}

.pay-method-text {
  font-weight: 900;
  color: #111827;
}

.wechat-icon {
  width: 34px;
  height: 34px;
  border-radius: 50%;
  background: #19c15f;
  position: relative;
}

.wechat-icon::before {
  content: '';
  position: absolute;
  width: 7px;
  height: 7px;
  border-radius: 50%;
  background: #fff;
  top: 12px;
  left: 9px;
  box-shadow: 9px 0 #fff;
}

.wechat-icon::after {
  content: '';
  position: absolute;
  width: 17px;
  height: 9px;
  left: 8px;
  top: 20px;
  border-radius: 0 0 12px 12px;
  background: rgba(255, 255, 255, 0.9);
}

.pay-check {
  width: 22px;
  height: 22px;
  border-radius: 50%;
  background: #16c784;
  position: relative;
}

.pay-check::after {
  content: '';
  position: absolute;
  left: 7px;
  top: 4px;
  width: 6px;
  height: 11px;
  border-right: 2px solid #fff;
  border-bottom: 2px solid #fff;
  transform: rotate(45deg);
}

.success-icon {
  width: 70px;
  height: 70px;
  margin: 8px auto 14px;
  border-radius: 50%;
  background: linear-gradient(135deg, #17c977, #42e6ad);
  position: relative;
  box-shadow: 0 16px 30px rgba(22, 199, 132, 0.22);
}

.success-icon::after {
  content: '';
  position: absolute;
  left: 25px;
  top: 16px;
  width: 18px;
  height: 31px;
  border-right: 5px solid #fff;
  border-bottom: 5px solid #fff;
  transform: rotate(45deg);
}

.success-title {
  margin: 0;
  text-align: center;
  font-size: 22px;
  font-weight: 900;
  color: #111827;
  display: block;
}

.success-desc {
  margin: 8px 0 18px;
  text-align: center;
  color: #697386;
  font-size: 13px;
  line-height: 1.6;
  display: block;
}

.success-actions {
  display: flex;
  flex-direction: column;
  gap: 10px;
}

.success-primary {
  width: 100%;
  height: 56px;
  border-radius: 18px;
  color: #fff;
  font-size: 18px;
  font-weight: 800;
  background: linear-gradient(135deg, #0958ff, #26d5ff);
  box-shadow: 0 14px 30px rgba(25, 119, 255, 0.3);
  border: none;
  line-height: 56px;
  text-align: center;
}

.success-primary:active {
  transform: scale(0.98);
}

.secondary-action {
  width: 100%;
  height: 48px;
  border-radius: 16px;
  color: #1764df;
  background: #eef6ff;
  font-size: 15px;
  font-weight: 900;
  border: none;
  line-height: 48px;
  text-align: center;
}

.secondary-action:active {
  transform: scale(0.98);
}
</style>

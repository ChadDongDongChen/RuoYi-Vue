import config from '@/config'
import { getToken } from '@/utils/auth'

const baseUrl = config.baseUrl

/**
 * 上传文件
 * @param options.url 上传接口地址
 * @param options.filePath 文件路径
 * @param options.name 文件字段名
 * @param options.formData 附加表单数据
 */
export function upload(options) {
  return new Promise((resolve, reject) => {
    const isToken = (options.headers || {}).isToken === false
    const header = options.headers || {}
    if (getToken() && !isToken) {
      header['Authorization'] = 'Bearer ' + getToken()
    }

    const finalUrl = options.baseUrl || baseUrl + options.url
    console.log('uploadFile url:', finalUrl)
    console.log('filePath:', options.filePath)
    uni.uploadFile({
      url: finalUrl,
      filePath: options.filePath,
      name: options.name || 'file',
      header: header,
      formData: options.formData || {},
      success: (res) => {
        const data = JSON.parse(res.data)
        if (data.code === 200) {
          resolve(data)
        } else {
          reject(data.msg || '上传失败')
        }
      },
      fail: (error) => {
        console.error('uploadFile fail:', error)
        reject(error)
      }
    })
  })
}

export default { upload }

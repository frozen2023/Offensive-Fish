// 约定一个通用的键名
const INFO_KEY = 'user_info'

// 获取个人信息
export const getInfo = () => {
  const defaultObj = { token: '', loginId: '', userName: '' }
  const result = localStorage.getItem(INFO_KEY)
  return result ? JSON.parse(result) : defaultObj
}

// 设置个人信息
export const setInfo = (obj) => {
  localStorage.setItem(INFO_KEY, JSON.stringify(obj))
}

// 更新token
export const setToken = (newToken) => {
  const result = localStorage.getItem(INFO_KEY)
  const info = JSON.parse(result)
  info.token = newToken
  localStorage.setItem(INFO_KEY, JSON.stringify(info))
}

// 移除个人信息
export const removeInfo = () => {
  localStorage.removeItem(INFO_KEY)
}

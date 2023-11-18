import Vue from 'vue'
export const validTel = (userName) => {
  if (!/^1[3-9]\d{9}$/.test(userName)) {
    Vue.prototype.$message.error('请输入正确的手机号')
    return false
  }
  return true
}

export const validReg = (userName, passwd, nickName, validate) => {
  if (!/^1[3-9]\d{9}$/.test(userName)) {
    Vue.prototype.$message.error('请输入正确的手机号')
    return false
  }
  if (!/^\w{6,12}$/.test(passwd)) {
    Vue.prototype.$message.error('请输入6-16位密码')
    return false
  }
  if (!/^\w{2,8}$/.test(nickName)) {
    Vue.prototype.$message.error('请输入2-8位用户名')
    return false
  }
  if (!/^\d{6}$/.test(validate)) {
    Vue.prototype.$message.error('请输入6位验证码')
    return false
  }
  return true
}

export const validLog = (userName, passwd) => {
  if (!/^1[3-9]\d{9}$/.test(userName)) {
    Vue.prototype.$message.error('请输入正确的手机号')
    return false
  }
  if (!/^\w{6,12}$/.test(passwd)) {
    Vue.prototype.$message.error('请输入6-12位密码')
    return false
  }
  return true
}

export const validChg = (userName, passwd, repasswd, validate) => {
  if (!/^1[3-9]\d{9}$/.test(userName)) {
    Vue.prototype.$message.error('请输入正确的手机号')
    return false
  }
  if (!/^\w{6,12}$/.test(passwd)) {
    Vue.prototype.$message.error('请输入6-16位密码')
    return false
  }
  if (passwd !== repasswd) {
    Vue.prototype.$message.error('两次密码不一致')
    return false
  }
  if (!/^\d{6}$/.test(validate)) {
    Vue.prototype.$message.error('请输入6位验证码')
    return false
  }
  return true
}

export const validRoom = (code) => {
  // 4位数字
  if (!/^\d{4}$/.test(code)) {
    Vue.prototype.$message({
      message: '请输入4位数字房间号',
      type: 'warning',
      duration: 1500
    })
    return false
  }
  return true
}
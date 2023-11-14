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
    this.$message.error('请输入正确的手机号')
    return false
  }
  if (!/^\w{6,12}$/.test(passwd)) {
    this.$message.error('请输入6-12位密码')
    return false
  }
  return true
}

export const validChg = (userName, passwd, repasswd, validate) => {
  if (!/^1[3-9]\d{9}$/.test(userName)) {
    this.$message.error('请输入正确的手机号')
    return false
  }
  if (!/^\w{6,12}$/.test(passwd)) {
    this.$message.error('请输入6-16位密码')
    return false
  }
  if (passwd !== repasswd) {
    this.$message.error('两次密码不一致')
    return false
  }
  if (!/^\d{6}$/.test(validate)) {
    this.$message.error('请输入6位验证码')
    return false
  }
  return true
}
// 此处用于存放所有登录相关的接口请求
import request from '@/utils/request'

// 1. 获取短信验证码
export const getMsgCode = (userName) => {
  return request.get('/User/sendMsg', {
    params: {
      userName
    }
  })
}

// 2. 验证码登录接口
export const codeLogin = (userName, validate) => {
  return request.get('/User/LoginByCode', {
    params: {
      userName,
      validate
    }
  })
}

// 3. 密码登录接口
export const passwordLogin = (userName, passwd) => {
  return request.get('/User/login', {
    params: {
      userName,
      passwd
    }
  })
}

// 4. 注册接口
export const pwdRegistered = (userName, passwd, name, validate, verifyCode) => {
  return request.get('/User/enroll', {
    headers: {
      'Content-Type': 'application/x-www-form-urlencoded',
      'Verifycode': verifyCode
    },
    params: {
      userName,
      passwd,
      name,
      validate
    }
  })
}

// 5. 忘记密码接口
export const codeForgetPassword = (userName, validate, passwd) => {
  return request.get('/User/changePasswordByTelephone', {
    params: {
      userName,
      validate,
      passwd
    }
  })
}
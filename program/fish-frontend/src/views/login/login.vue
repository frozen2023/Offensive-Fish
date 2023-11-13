<template>
  <div class="login">
    <img src="@/assets/images/login1.png" class="img1">
    <img src="@/assets/images/login.png" class="img2">
    <div class="login_wrap">
      <div class="login_wrap_head">
        <div class="login_wrap_head_title">请输入你的账号</div>
      </div>
      <div class="login_wrap_body">
        <div class="login_form">
          <div class="login_form_container">
            <label for="account" class="login_form_container_acclb">账号:</label>
            <input type="tel" id="account" class="login_form_container_acc" v-model="userName" placeholder="手机号">
          </div>
          <div class="login_form_container">
            <label for="password" class="login_form_container_pwdlb">密码:</label>
            <input type="password" id="password" class="login_form_container_pwd" v-model="passwd" placeholder="密码">
          </div>
        </div>
        <div class="login_btn">
          <button class="login_btn_login" @click="handleLogin()">登录</button>
          <button class="login_btn_reg" @click="$router.push('/register')">注册</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { passwordLogin } from '@/api/login.js'
export default {
  name: 'login',
  components: {},
  data () {
    return {
      passwd: '',
      userName: ''
    }
  },
  methods: {
    validFn() {
      if (!/^1[3-9]\d{9}$/.test(this.userName)) {
        this.$message.error('请输入正确的手机号')
        return false
      }
      if (!/^\w{6,12}$/.test(this.passwd)) {
        this.$message.error('请输入6-12位密码')
        return false
      }
      return true
    },
    async handleLogin() {
      if(!this.validFn()) return
      await passwordLogin(this.userName, this.passwd)
      .then(res => {
        const user = res.data.token.data
        const info = {
          token: user.tokenValue,
          loginId: user.loginId,
          userName: res.data.Name,
        }
        this.$store.commit('user/setUserInfo', info)
        localStorage.setItem('isLogin', true)
        this.$message.success('登录成功')
        const url = this.$route.query.backUrl || '/home'
        this.$router.replace(url)
      })
      .catch(err => {
        console.log(err)
        this.$message.error('登陆失败')
      })
    }
  },
}
</script>
<style scoped lang='less'>
.login {
  position: relative;
  width: 100vw;
  height: 60vh;
  z-index: 1;
  padding-left: 30vw;
}
.img1 {
  position: absolute;
  bottom: 0;
  right: 2vw;
  // width: 100vw;
  height: 50vh;
  z-index: -1;
}
.img2 {
  position: absolute;
  bottom: 9.4vh;
  right: 8.3vw;
  height: 50vh;
  scale: 1.05;
}
.login_wrap {
  width: 40vw;
  height: 70%;
  background-color: #12A8DF;
  border-radius: 2vw;
  z-index: 100;
  box-shadow: 1vw 2vh 0.1vw #A4ADB3;
  &_head {
    height: 20%;
    width: 100%;
    text-align: center;
    &_title {
      padding-top: 1vh;
      .pxfont(40);
      color: #101010;
      font-weight: 550;
    }
  }
  &_body {
    height: 80%;
    width: 100%;
    border-bottom-left-radius: 2vw;
    border-bottom-right-radius: 2vw;
  }
}
.login_form {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  height: 60%;
  width: 40vw;
  &_container {
    display: flex;
    flex-direction: row;
    align-items: center;
    height: 50%;
    width: 40vw;
    padding-left: 1vw;
    &_acclb, &_pwdlb {
      .pxfont(40);
      font-weight: 550;
    }
    &_acc, &_pwd {
      margin-left: 1vw;
      height: 70%;
      width: 82%;
      border-radius: 1vw;
      .pxfont(40);
      padding-left: 1.5vw;
    }
  }
}
.login_btn {
  display: flex;
  flex-direction: row;
  justify-content: center;
  align-items: center;
  height: 40%;
  width: 40vw;
  border-bottom-left-radius: 2vw;
  border-bottom-right-radius: 2vw;
  &_login, &_reg {
    height: 7vh;
    width: 8vw;
    border-radius: 2vw;
    background-color: #056DA6;
    .pxfont(40);
    .pxvw(letter-spacing, 2);
    font-weight: 600;
    box-shadow: 0 1vh 0.1vw #333333;
    &:hover {
      transition: all 0.25s linear;
      transform: scale(1.05);
    }
  }
  &_login {
    margin-right: 2vw;
  }
  &_reg {
    margin-left: 5vw;
  }
}
</style>

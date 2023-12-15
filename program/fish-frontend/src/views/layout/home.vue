<template>
  <div class="home">
    <h1 class="home_logo">
      <img class="home_logo_img" src="@/assets/images/visit.png"/>
    </h1>
    <div class="container1">
      <img src="@/assets/images/bg_fish1.png" class="fish1">
    </div>
    <div class="container2">
      <img src="@/assets/images/bg_fish2.png" class="fish2">
    </div>
    <img src="@/assets/images/bubble.png" class="bb1">
    <img src="@/assets/images/bubble2.png" class="bb2">
    <img src="@/assets/images/bubble2.png" class="bb3">
    <div class="home_body">
      <div class="home_body_middle">
        <div class="home_body_middle_box">
          <div class="home_body_middle_out white">
            <div class="home_body_middle_out_begin" @click="goPlay()">开始游戏</div>
          </div>
        </div>
        <div class="home_body_middle_box">
          <div class="home_body_middle_out gray">
            <div class="home_body_middle_out_rank" @click="$router.push('/rank')">排行榜</div>
          </div>
        </div>
      </div>
      <div class="home_body_aside">
        <div class="home_body_aside_left">
          <div class="home_body_aside_left_login">
            <button class="home_login" @click="$router.push('/auth')">登录</button>
          </div>
          <div class="home_body_aside_left_fri">
            <button class="home_fri" @click="$router.push('/friendlist')">好友</button>
          </div>
        </div>
        <div class="home_body_aside_middle">
          <div class="home_body_aside_middle_role">
            <button class="home_role" @click="$router.push('/character')">角色</button>
          </div>
        </div>
        <div class="home_body_aside_right">
          <div class="home_body_aside_right_changepwd">
            <button class="home_cgpwd" @click="$router.push('/changepwd')">修改密码</button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'home',
  data () {
    return {}
  },
  computed: {},
  methods: {
    goPlay() {
      if(!this.$store.getters.token) {
        this.$message({
          message: '请先登录',
          type: 'warning'
        })
        this.$router.push('/auth')
        return
      }
      this.$router.push({
        path: '/room',
        query: {
          type: 'create'
        }
      })
    }
  },
  created () {},
}
</script>
<style scoped lang='less'>
.home {
  position: relative;
  width: 100vw;
  height: 100vh;
  z-index: 1;
}
.home_logo {
  width: 100vw;
  height: 50vh;
  text-align: center;
  z-index: 10;
  &_img {
    height: 100%;
    scale: 1.3;
  }
}
.home_body {
  display: flex;
  flex-direction: row;
  width: 100vw;
  height: 50vh;
  padding-left: 36vw;
  &_middle {
    display: flex;
    height: 100%;
    width: 24vw;
    flex-direction: column;
    align-items: center;
    &_box {
      display: flex;
      align-items: center;
      justify-content: center;
      width: 100%;
      height: 50%;
    }
    &_out {
      display: flex;
      height: 15vh;
      width: 22vw;
      align-items: center;
      border-radius: 1.2vw;
      cursor: pointer;
      &_begin, &_rank {
        margin: 0 auto;
        height: 12.3vh;
        line-height: 12vh;
        text-align: center;
        width: 92%;
        border-radius: 1.2vw;
        background-color: #A4ADB3;
        .pxfont(55);
        .pxvw(letter-spacing, 10);
        font-weight: 600;
      }
      &_begin {
        color: #000;
      }
      &_rank {
        color: #f5f5f5;
      }
      &:hover {
        -webkit-box-shadow: 0.5vh 1vw 2.1vh 0.6vw rgba(76,201,240,1);
        -moz-box-shadow: 0.5vh 1vw 2.1vh 0.6vw rgba(76,201,240,1);
        box-shadow: 0.5vh 0.5vw 8.1vh 0.5vw rgba(76,201,240,1);
        transition: all 0.25s linear;
        transform: scale(1.05);
      }
    }
    
  }
  &_aside {
    display: flex;
    flex-direction: row;
    width: 25vw;
    height: 50%;
    &_left {
      flex: 1;
      &_login, &_fri {
        display: flex;
        width: 100%;
        height: 12.5vh;
        justify-content: center;
        align-items: center;
        text-align: center;
      }
    }
    &_middle {
      flex: 1;
      &_role {
        display: flex;
        height: 25vh;
        justify-content: center;
        align-items: center;
        text-align: center;
      }
    }
    &_right {
      flex: 1.5;
      &_changepwd {
        display: flex;
        height: 25vh;
        justify-content: center;
        align-items: center;
        text-align: center;
      }
    }
  }
}

.white {
  background-color: #f5f5f5;
}

.gray {
  background-color: #7D7979;
}

.home {
  &_login, &_fri, &_role, &_cgpwd {
    background-color: #dedede;
    opacity: 0.4;
    height: 7vh;
    .pxfont(30);
    border-radius: 1.2vw;
    font-weight: 600;
    color: #0831a3;
    &:hover {
      -webkit-box-shadow: 0.5vh 1vw 2.1vh 0.6vw rgba(76,201,240,1);
      -moz-box-shadow: 0.5vh 1vw 2.1vh 0.6vw rgba(76,201,240,1);
      box-shadow: 0.5vh 0.5vw 8.1vh 0.5vw rgba(76,201,240,1);
      transition: all 0.25s linear;
      scale: 1.05;
    }
  }
  &_login {
    width: 6.9vw;
    text-transform: uppercase;
  }
  &_fri {
    width: 6.9vw;
  }
  &_role {
    width: 6.9vw;
  }
  &_cgpwd {
    width: 8vw;
  }
}

.container1, .container2 {
  position: absolute;
  bottom: 0;
  width: 60%;
  height: 100%;
  overflow: hidden;
  z-index: -1;
}
.container1 {
  right: 0;
  width: 60%;
}
.container2 {
  left: 0;
  width: 50%;

}
.fish2 {
  position: absolute;
  height: 100%;
  scale: 1.3;
  left: 0;
  bottom: -25%;
  z-index: -1;
}
.fish1 {
  position: absolute;
  height: 100%;
  scale: 1.5;
  right: 10%;
  bottom: -10%;
  z-index: -1;
}
.bb1 {
  position: absolute;
  height: 30%;
  left: 10vw;
  bottom: 13vh;
  z-index: -1;
}
.bb2 {
  position: absolute;
  height: 30%;
  left: 25vw;
  bottom: 3vh;
  z-index: -1;
}
.bb3 {
  position: absolute;
  height: 30%;
  right: 20vw;
  bottom: 8vh;
  z-index: -1;
}
</style>

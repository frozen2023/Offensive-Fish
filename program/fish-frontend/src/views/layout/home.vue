<template>
  <div class="home">
    <h1 class="home_logo">
      <img class="home_logo_img" src="@/assets/images/visit.png"/>
    </h1>
    <div class="home_body">
      <div class="home_body_middle">
        <div class="home_body_middle_box">
          <div class="home_body_middle_out white">
            <div class="home_body_middle_out_begin">开始游戏</div>
          </div>
        </div>
        <div class="home_body_middle_box">
          <div class="home_body_middle_out gray">
            <div class="home_body_middle_out_rank">排行榜</div>
          </div>
        </div>
      </div>
      <div class="home_body_aside">
        <div class="home_body_aside_left">
          <div class="home_body_aside_left_login">
            <button class="home_login">登录</button>
          </div>
          <div class="home_body_aside_left_fri">
            <button class="home_fri">好友</button>
          </div>
        </div>
        <div class="home_body_aside_middle">
          <div class="home_body_aside_middle_role">
            <button class="home_role">角色</button>
          </div>
        </div>
        <div class="home_body_aside_right">
          <div class="home_body_aside_right_changepwd">
            <button class="home_cgpwd">修改密码</button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'home',
  components: {},
  data () {
    return {}
  },
  computed: {},
  watch: {},
  methods: {},
  created () {},
}
</script>
<style scoped lang='less'>
.home {
  width: 100vw;
  height: 100vh;
}
.home_logo {
  width: 100vw;
  height: 50vh;
  text-align: center;
  &_img {
    height: 100%;
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
        font-size: 42px;
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
</style>

<template>
  <div class="room">
    <div class="room_logo">
      <img src="@/assets/images/logo.png">
    </div>
    <div class="room_body">
      <div class="room_body_creation">
        <div class="creation_title">创建房间</div>
        <div class="creation_invition">
          <div class="creation_invition_room">
            <div class="creation_invition_room_text">房间邀请码</div>
            <div class="creation_invition_room_code">{{ code }}</div>
          </div>
          <div class="creation_invition_code">
            <div class="creation_invition_room_text">输入邀请码</div>
            <input type="text" class="creation_invition_code_ipt" placeholder="请输入邀请码">
          </div>
        </div>
        <div class="creation_player">
          <div class="creation_player_text">当前玩家</div>
          <div class="creation_player_master">
            <div class="creation_player_status" :style="master.status?ready:noready"></div>
            <div class="creation_player_name">{{ master.name }}(我)</div>
          </div>
          <div class="creation_player_other">
            <div class="creation_player_status" :style="player.status?ready:noready"></div>
            <div class="creation_player_name">{{ '等待中...'||player.name }}</div>
          </div>
        </div>
        <div class="creation_btnbox">
          <button class="begin">开始</button>
          <button class="return" @click="$router.push('/home')">返回</button>
        </div>
      </div>
      <Fri :style="customStyle"></Fri>
    </div>
  </div>
</template>

<script>
import Fri from '@/components/fri.vue'
export default {
  name: 'createRoom',
  components: {
    Fri
  },
  data () {
    return {
      customStyle: {
        height: '58vh'
      },
      ready: {
        backgroundColor: 'green'
      },
      noready: {
        backgroundColor: 'red'
      },
      code: 'vivo50',
      master: {
        name: 'xh懒觉大支',
        status: true
      },
      player: {
        name: 'xh懒觉小支',
        status: false
      }
    }
  },
  computed: {},
  watch: {},
  methods: {},
  created () {},
}
</script>
<style scoped lang='less'>
.room {
  height: 100vh;
  width: 100vw;
  &_logo {
    width: 100vw;
    height: 40vh;
    text-align: center;
    img {
      height: 100%;
      scale: 1;
    }
  }
}
.room_body {
  display: flex;
  flex-direction: row;
  justify-content: center;
  align-items: center;
  width: 100vw;
  height: 60vh;
  // padding: 0 12vw;
  &_creation {
    width: 50%;
    height: 58vh;
    background-color: rgba(255, 255, 255, 0.5);
    border-radius: 1vw;
    flex-shrink: 0;
    margin-right: 1vw;
  }
}
.creation {
  &_title {
    height: 8vh;
    line-height: 8vh;
    width: 100%;
    text-align: center;
    .pxfont(40)
  }
  &_invition {
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    height: 20vh;
    width: 100%;
    &_room, &_code {
      display: flex;
      flex-direction: row;
      justify-content: center;
      align-items: center;
      height: 50%;
      width: 100%;
      &_text {
        .pxfont(40);
        font-weight: 550;
        margin-right: 2vw;
      }
      &_code, &_ipt {
        width: 50%;
        height: 7vh;
        line-height: 6vh;
        background-color: #fff;
        text-align: center;
        border: 0.2vw solid black;
        .pxfont(36);
      }
    }
  }
  &_player {
    display: flex;
    flex-direction: row;
    justify-content: center;
    align-items: center;
    height: 20vh;
    width: 100%;
    &_text {
      .pxfont(40);
      font-weight: 550;
      margin-right: 2.7vw;
    }
    &_master, &_other {
      display: flex;
      flex-direction: column;
      align-items: center;
      height: 12vh;
      width: 12vw;
      background-color: #fff;
      border: #dfd9d9 solid 0.1vw;
    }
    &_master {
      margin-right: 2vw;
    }
    &_status {
      width: 100%;
      height: 1vh;
    }
    &_name {
      width: 100%;
      height: calc(100%-1vh);
      line-height: 11vh;
      text-align: center;
      .pxfont(23);
    }
  }
  &_btnbox {
    display: flex;
    flex-direction: row;
    justify-content: center;
    align-items: center;
    height: 10vh;
    width: 100%;
    .begin, .return {
      height: 70%;
      width: 20%;
      background-color: #629DF4;
      border-radius: 1vw;
      .pxfont(32);
      font-weight: 550;
      letter-spacing: 0.2vw;
      text-align: center;
      &:hover {
        transition: all 0.2s linear;
        transform: scale(1.05);
      }
    }
    .begin {
      margin-right: 8vw;
    }
  }
}
</style>

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
            <div class="creation_invition_room_code">{{ roomInfo.code||'暂无邀请码' }}</div>
          </div>
          <div class="creation_invition_code">
            <div class="creation_invition_room_text">输入邀请码</div>
            <input type="text" v-model="iptCode" class="creation_invition_code_ipt" placeholder="请输入邀请码">
            <button class="enter" @click="enterRoom()">进入</button>
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
            <div class="creation_player_name">{{ player.name||'等待中...' }}</div>
          </div>
        </div>
        <div class="creation_btnbox">
          <button class="begin" @click="begin()" v-if="isMaster">开始</button>
          <button class="ready" @click="readying($event)" v-else>{{ player.status?'已准备':'准备' }}</button>
          <button class="return" @click="exit()">返回</button>
        </div>
      </div>
      <Fri :style="customStyle"></Fri>
    </div>
  </div>
</template>

<script>
import Fri from '@/components/fri.vue'
import { createRoom, destroyRoom } from '@/api/room.js'
import { validRoom } from '@/utils/validate.js'
import io from 'socket.io-client'
import store from '@/store'
import merge from 'webpack-merge';

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
      iptCode: '',
      isMaster: false,
      master: {
        name: 'XH大支',
        status: true
      },
      player: {
        name: '',
        status: false
      },
      roomInfo: {
        code: "3870",
        isOpen: 1,
        numbers: 1,
        playerId: '',
        roomId: 0,
        roomOwnerId: "15860929147"
      }
    }
  },
  computed: {
    enterType() {
      return this.$route.query.type
    }
  },
  watch: {
    // 监听entertype变化
    enterType(val) {
      if (val === 'enter') {
        this.isMaster = false
      }
    }
  },
  methods: {
    begin() {
      this.$router.push('/area')
    },
    exit() {
      if(this.destroyMyRoom()) {
        this.$router.push('/home')
      }
    },
    readying(e) {
      this.player.status = !this.player.status
      if (this.player.status) {
        e.target.style.backgroundColor = '#629DF4'
      } else {
        e.target.style.backgroundColor = '#F4A962'
      }
    },
    // 销毁当前房间
    async destroyMyRoom() {
      await destroyRoom(this.roomInfo.roomId)
      .then((res) => {
        console.log(res)
        console.log('房间销毁成功')
        return true
      })
      .catch((err) => {
        console.log(err)
        return false
      })
    },
    // 加入房间
    async enterRoom () {
      if(!validRoom(this.iptCode)) {
        return
      }
      this.$router.push({
          query: merge(this.$route.query,{'type': 'enter'})
      })
      // this.destroyMyRoom() // 销毁当前创建的房间
      // this.roomInfo = {} // 清空房间信息
      // await enterRoomByCode(this.iptCode)
      // .then((res) => {
      //   console.log(res)
      // })
      // .catch((err) => {
      //   console.log(err)
      // })
    },
    // 进入房间默认创建房间 
    async createRoomByMaster() {
      // await createRoom()
      // .then((res) => {
      //   const msg = res.data.msg
      //   if(msg !== '房间创建成功') {
      //     this.$message({
      //       message: '创建房间失败',
      //       type: 'error',
      //       duration: 1000
      //     })
      //     return
      //   }
      //   this.roomInfo = res.data.Object
      //   console.log(res)
      //   socket = io('http://10.132.62.87:9999/', {
      //     query: { userId: store.getters.loginId },
      //   })
      //   socket.on('connect', () => {
      //     console.log('Socket 连接已建立')
      //   })
      //   // 监听 Socket 连接断开事件
      //   socket.on('disconnect', () => {
      //     console.log('Socket 连接已断开')
      //   })
      // })
      // .catch((err) => {
      //   console.log(err)
      //   this.$message({
      //     message: '创建房间失败',
      //     type: 'error',
      //     duration: 1000
      //   })
      // })
    }
  },
  created () {
    if (this.enterType === 'create') {
      this.isMaster = true
      this.createRoomByMaster()
    }
  },
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
        margin-right: 4%;
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
    .enter {
      height: 7vh;
      width: 10%;
      border-radius: 1vw;
      background-color: #629DF4;
      .pxfont(30);
      margin-left: 4%;
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
    .begin, .return, .ready {
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
    .begin, .ready {
      margin-right: 8vw;
    }
    .ready {
      background-color: #F4A962;
    }
  }
}
</style>

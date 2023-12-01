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
            <button class="enter" @click="enter()">进入</button>
          </div>
        </div>
        <div class="creation_player">
          <div class="creation_player_text">当前玩家</div>
          <div class="creation_player_master">
            <div class="creation_player_status" :style="master.status?ready:noready"></div>
            <div class="creation_player_name">{{ master.name }}</div>
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
import { createRoom, destroyRoom, getRoomId, getRoomInfo, enterRoomByCode } from '@/api/room.js'
import { validRoom } from '@/utils/validate.js'
import { setRoomInfoByLocal, getRoomInfoByLocal } from '@/utils/storage'
import io from 'socket.io-client'
import store from '@/store'
import merge from 'webpack-merge'

export default {
  name: 'createRoom',
  components: {
    Fri
  },
  data () {
    return {
      customStyle: {  // 好友列表自定义样式
        height: '58vh'
      },
      ready: {  // 准备状态
        backgroundColor: 'green'
      },
      noready: { // 未准备状态
        backgroundColor: 'red'
      },
      iptCode: '', // 输入的邀请码
      isMaster: false, // 是否是房主
      status: false, // 当前socket准备状态
      roomId: -1 || roomInfo.roomId, // 当前房间ID
      master: { // 房主信息
        name: '',
        status: true,
        isMaster: true
      },
      player: { // 玩家信息
        name: '',
        status: false,
        isMaster: false
      },
      roomInfo: { // 房间信息
      }
    }
  },
  computed: {
    enterType() {
      return this.$route.query.type
    },
    masterId() {
      return store.getters.loginId
    },
    socket() {
      return store.getters.socket
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
      if(this.player.name == '') {
        this.$message({
          message: '请等待玩家加入',
          type: 'info',
          duration: 2000
        })
        return
      }
      if(!this.player.status) {
        this.$message({
          message: this.player.name + '未准备',
          type: 'info',
          duration: 2000
        })
        return
      }
      this.socket.emit('playGame', this.roomInfo.roomId)
      localStorage.setItem('playerName', this.player.name)
      store.commit('socket/setRoomId', this.roomInfo.roomId)
      this.$router.push('/area')
    },
    exit() {
      if(!this.socket) {
        this.$router.push('/home')
      }
      this.socket.emit("leaveRoom", this.roomInfo.roomId)
      setRoomInfoByLocal({})
      this.destroyMyRoom()
      this.$router.push('/home')
    },
    readying(e) {
      this.player.status = !this.player.status
      if (this.player.status) {
        e.target.style.backgroundColor = '#629DF4'
      } else {
        e.target.style.backgroundColor = '#F4A962'
      }
      if(this.player.status) {
        this.socket.emit('prepare', this.roomInfo.roomId)
      } else {
        this.socket.emit('unprepared', this.roomInfo.roomId)
      }
    },
    // 销毁当前房间
    destroyMyRoom() {
      destroyRoom(this.roomId)
    },
    // 加入房间
    enter() {
      if(!validRoom(this.iptCode)) {
        return
      }
      this.socket.emit("leaveRoom", this.roomInfo.roomId)
      this.destroyMyRoom() // 销毁当前创建的房间
      this.enterRoom()
    },
    async enterRoom() {
      if(this.enterType !== 'enter') {
        this.$router.push({
          query: merge(this.$route.query,{'type': 'enter'})
        })
      }
      if(this.iptCode == this.roomInfo.code) {
        this.$message({
          message: '你已经在该房间',
          type: 'info',
          duration: 2000
        })
        return
      }
      await enterRoomByCode(this.iptCode)
      .then((res) => {
        if(res.data.msg === '成功进入房间') {
          this.roomInfo = res.data.object
          this.roomId = res.data.object.roomId
          setRoomInfoByLocal(res.data.object)
          this.getRoomInfomation() // 获取加入的房间信息
        }
        // 加入房间成功后，创建socket连接
        this.socket.emit("joinRoom", this.roomInfo.roomId) // 监听加入房间函数
        const socket = io.connect(`ws://10.132.62.87:9999?userId=${ store.getters.loginId }`,{transports:['websocket','xhr-polling','jsonp-polling']})
        store.commit('socket/setSocket', socket)
        this.socket.on('connect', () => {
          this.player.name = store.getters.userName + '(我)'  // 设置当前player的信息
          localStorage.setItem('isMaster', false)
        })
      })
      .catch((err) => {
        console.log(err)
      })
    },
    //获取房间信息
    async getRoomInfomation() {
      await getRoomInfo(this.roomInfo.roomId)
      .then((res) => {
        this.master.name = res.data.msg.roomOwnerName
        localStorage.setItem('playerName', this.master.name)
        this.roomInfo = res.data.msg
      })
      .catch((err) => {
        console.log(err)
      })
    },
    // 进入房间默认创建房间 
    async createRoomByMaster() {
      await createRoom()
      .then((res) => {
        const msg = res.data.msg
        if(msg !== '房间创建成功') {
          this.$message({
            message: '创建房间失败',
            type: 'error',
            duration: 1000
          })
          return
        }
        this.roomInfo = res.data.object
        this.getRoomID(this.roomInfo.code)
        setRoomInfoByLocal(res.data.object)
        localStorage.setItem('isMaster', true)
        const socket = io.connect(`ws://10.132.62.87:9999?userId=${ this.masterId }`,{transports:['websocket','xhr-polling','jsonp-polling']})
        store.commit('socket/setSocket', socket)
        this.socket.on('connect', () => {
          this.$message({
            message: '房间创建成功, Socket连接成功',
            type: 'success',
            duration: 2000
          })
          // console.log(this.socket.getSession());
          this.master.name = store.getters.userName + '(我)'
        })
        this.socket.emit("joinRoom", this.roomInfo.roomId)
        //监听消息
        this.socket.on('message', (data) => {
          console.log(data)
          if(data.isMaster !== 1 && data.msg == '进入房间！') {
            this.player.name = data.name
            this.$message({
              message: this.player.name + data.msg,
              type: 'info',
              duration: 2000
            })
            this.roomInfo.playerId = data.userName
            this.roomInfo.numbers = 2
            setRoomInfoByLocal(this.roomInfo)
          }
          if(data.isMaster === 1 && data.msg == '离开房间！') {
            if(localStorage.getItem('isMaster') == 'false') {
              this.$message({
                message: '房主离开房间，房间已解散',
                type: 'info',
                duration: 2000
              })
              setRoomInfoByLocal({})
              this.$router.push('/home')
              return
            }
          } 
          else if(data.isMaster !== 1 && data.msg == '离开房间！') {
            this.$message({
              message: this.player.name + data.msg,
              type: 'info',
              duration: 2000
            })
            this.roomInfo.numbers = 1
            this.roomInfo.playerId = ''
            this.player = {
              name: '',
              status: false,
              isMaster: false
            }
            setRoomInfoByLocal(this.roomInfo)
          }
          if(data.msg == '已经准备!') {
            if(localStorage.getItem('isMaster') == 'true') {
              this.$message({
                message: data.name + '已准备',
                type: 'info',
                duration: 2000
              })
            }
            this.player.status = true // 设置非房主玩家准备状态
          }
          else if(data.msg == '取消准备!') {
            if(localStorage.getItem('isMaster') == 'true') {
              this.$message({
                message: data.name + '取消准备',
                type: 'info',
                duration: 2000
              })
            }
            this.player.status = false // 设置非房主玩家准备状态
          }
          if(data.msg == '开始游戏!' && localStorage.getItem('isMaster') == 'false') {
            this.$message({
              message: '游戏开始！',
              type: 'info',
              duration: 2000
            })
            store.commit('socket/setRoomId', this.roomInfo.roomId)
            this.$router.push('/area')
          }
          if(data.msg == '选择地图成功!') {
            store.commit('socket/setMap', data.map)
          }
          if(data.msg == '开始选择英雄!' && localStorage.getItem('isMaster') == 'false') {
            this.$router.push('/role')
          }
          if(data.msg == '选择英雄成功!') {
            if(localStorage.getItem('isMaster') == 'false') {
              if(data.userId == this.masterId) {
                store.commit('socket/setPlayerRole', data.role)
              } else {
                store.commit('socket/setMyRole', data.role)
              }
            } else {
              if(data.userId == this.masterId) {
                store.commit('socket/setMyRole', data.role)
              } else {
                store.commit('socket/setPlayerRole', data.role)
              }
            }
          }
          if(data.msg == '发送消息成功!') {
            if(data.userName == this.masterId) {
              store.commit('socket/setChatMessages', { text: data.data, position: 'right' })
            } else {
              store.commit('socket/setChatMessages', { text: data.data, position: 'left' })
            }
          }
        })
        this.socket.on('error', (error) => {
          this.$message({
            message: 'Socket连接错误',
            type: 'error',
            duration: 1800
          })
          console.log('连接错误：')
          console.log(error)
        });
        // 监听 Socket 连接断开事件
        this.socket.on('disconnect', () => {
          console.log('Socket 连接已断开')
        })
      })
      .catch((err) => {
        console.log(err)
        this.$message({
          message: '创建房间失败',
          type: 'error',
          duration: 2000
        })
      })
    },
    // 获取房间ID
    async getRoomID(code) {
      await getRoomId(code)
      .then((res) => {
        this.roomId = res.data
        this.roomInfo.roomId = res.data
      })
      .catch((err) => {
        this.$message({
          message: '获取房间ID失败',
          type: 'error',
          duration: 1700
        })
      })
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

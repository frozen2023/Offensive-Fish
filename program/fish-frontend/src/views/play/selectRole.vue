<template>
  <div class="role">
    <div class="role_left">
      <div class="role_head">
        <div class="role_head_players">
          <div class="role_head_players_p1">
            <div class="role_head_players_p1_color"></div>
            P1
            <div class="role_head_players_p1_name">{{ players[0].name }}</div>
          </div>
          <div class="role_head_players_p2">
            <div class="role_head_players_p2_color"></div>
            P2
            <div class="role_head_players_p2_name">{{ players[1].name }}</div>
          </div>
        </div>
        <div class="role_head_title">选择角色</div>
      </div>
      <div class="role_body">
        <div class="role_wrap" v-for="(role,index) in roles" :key="index" @click="choose(index)">
          <i class="slected" v-show="players[0].selection == role.name?true:false" ref="slectedLable">已选择</i>
          <i class="slected_o" v-show="players[1].selection == role.name?true:false" ref="slectedLable_o">已选择</i>
          <div class="role_wrap_img"><img :src="role.img" alt=""></div>
          <div class="role_wrap_name">{{ role.name }}</div>
          <div class="role_wrap_desc">{{ role.desc }}</div>
          <div class="role_wrap_ability">{{ role.ability }}</div>
        </div>
      </div>
    </div>
    <div class="role_right">
      <div class="role_right_box">
        <div class="countdown_title">距离游戏开始还有</div>
        <div class="countdown_wrap">{{ totalSecond === second ? text : second + ' 秒' }}</div>
      </div>
      <div class="role_right_chatwrap">
        <div class="role_right_chatwrap_chat">
          <div class="chat_wrap">
            <ul class="chat_list" ref="chatList">
              <li v-for="(message, index) in chatMessages" :key="index" :class="message.position">
                <span>{{ message.text }}</span>
              </li>
            </ul>
          </div>
          <div class="chat_ipt">
            <input type="text" placeholder="说点什么吧" v-model="sendText" @keyup.enter="sendMsg()">
            <button @click="sendMsg()">发送</button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import store from '@/store'
export default {
  name: 'slectRole',
  components: {},
  data () {
    return {
      players: [
        { id: 1, name: '', master: true, pos: 'P1', selection: '' },
        { id: 2, name: '', master: false, pos: 'P2', selection: '' },
      ],
      roles: [
        { id: 1, img: require('@/assets/images/1.png'), slected: false, name: '金枪鱼', desc: '变异之处：这种金枪鱼变异出令人惊叹的超高速度。它们的身体线条更加流线型，鳍和鳍尾变得更大。', ability: '特殊能力：雷速金枪鱼具有闪电一般的加速能力，能够在水中迅速穿梭。它们可以迅速追赶其他鱼，或者逃脱捕食者。此外，它们可以在快速移动时创建出引力漩涡，吸引附近的小鱼，为自己提供更多的食物来源。' },
        { id: 2, img: require('@/assets/images/2.png'), slected: false, name: '鳐鱼', desc: '变异之处：这种鳐鱼由于核污染而发光，身体覆盖着发光的鳞片。它们的扁平体形使它们擅长在海底滑行，并且拥有特殊的治疗能力。', ability: '特殊能力：光线鳐鱼可以发出闪亮的光束，使周围的水域一片明亮。这可以用来吸引其他鱼，使猎食更容易。此外，它可以治疗他的小伙伴。' }
      ],
      totalSecond: 10, // 总秒数
      second: 10, // 当前秒数，开定时器对 second--
      timer: null, // 定时器 id
      text: '游戏即将开始',
      nowId: 1, // 当前页面玩家id
      sendText: '',
      // chatMessages: [
      //   { position: 'left', text: '小宝贝', id: 1 }
      // ],
      isMaster: false
    }
  },
  computed: {
    chatMessages() {
      return store.getters.chatMessages
    },
    nowPlayerPos() {
      return this.players.find((item) => {
        return item.id === this.nowId
      }).pos
    },
    nowPlayerSelection() {
      return this.players.find((item) => {
        return item.id === this.nowId
      }).selection
    },
    roomId() {
      return store.getters.roomId
    },
    masterRoleId() {
      return store.getters.masterRoleId
    },
    playerRoleId() {
      return store.getters.playerRoleId
    },
    socket() {
      return store.getters.socket
    }
  },
  watch: {
    masterRoleId(val) {
      if(val !== '0') {
        if(this.isMaster == 'false') {
          this.players[0].selection = this.roles[val - 1].name
        }
      }
    },
    playerRoleId(val) {
      if(val !== '0') {
        if(this.isMaster == 'true') {
          this.players[1].selection = this.roles[val - 1].name
        }
      }
    }
  },
  methods: {
    choose(index) {
      this.players.forEach((item) => {
        if (item.id == this.nowId) {
          item.selection = this.roles[index].name
        }
      })
      const roleId = this.roles[index].id
      this.socket.emit('chooseRole', parseInt(this.roomId), parseInt(roleId))
    },
    sendMsg() {
      this.socket.emit('sendRoomMessage', parseInt(this.roomId), this.sendText)
      this.sendText = '' // 清空发送文本框
    },
  },
  created () {
    this.isMaster = localStorage.getItem('isMaster')
    if(this.isMaster === 'true') {
      this.nowId = 1
      this.players[0].name = store.getters.userName
      this.players[1].name = localStorage.getItem('playerName')
    } else {
      this.nowId = 2
      this.players[0].name = localStorage.getItem('playerName')
      this.players[1].name = store.getters.userName
    }
    if (!this.timer && this.second === this.totalSecond) {
      this.timer = setInterval(() => {
        this.second--
        if (this.second <= 0) {
          clearInterval(this.timer)
          this.timer = null // 重置定时器 id
          this.second = this.totalSecond // 归位
          this.text = '游戏开始'
          this.$router.push('/game')
        }
      }, 1000)
    }
  },
}
</script>
<style scoped lang='less'>
.role {
  display: flex;
  flex-direction: row;
  height: 100vh;
  width: 100vw;
  &_left {
    flex: 2.5;
  }
  &_right {
    flex: 1;
  }
}
.role_head {
  display: flex;
  flex-direction: row;
  justify-content: center;
  align-items: center;
  height: 20vh;
  width: 100%;
  &_players {
    display: flex;
    flex-direction: column;
    justify-content: space-evenly;
    align-items: center;
    width: 35%;
    height: 90%;
    margin-right: 7%;
    &_p1, &_p2 {
      display: flex;
      border-radius: 0.8vw;
      flex-direction: row;
      align-items: center;
      width: 90%;
      height: 40%;
      background-color: #fff;
      .pxfont(30);
      &_color {
        border-bottom-left-radius: 0.8vw;
        border-top-left-radius: 0.8vw;
        height: 100%;
        width: 16%;
      }
      &_name {
        margin-left: 10%;
        .pxfont(28);
        text-align: center;
      }
    }
    &_p1 {
      &_color {
        background-color: #c73434;
      }
    }
    &_p2 {
      &_color {
        background-color: #d0d85d;
      }
    }
  }
  &_title {
    margin-left: 7%;
    font-weight: 550;
    .pxfont(70);
    letter-spacing: 0.3vw;
  }
}
.role {
  &_body {
    display: flex;
    flex-direction: row;
    justify-content: space-evenly;
    align-items: center;
    height: 80vh;
    width: 100%;
  }
  &_wrap {
    position: relative;
    height: 64vh;
    width: 30%;
    background-color: rgba(255, 255, 255, 0.4);
    border: 0.2vw solid #000;
    &_img {
      height: 50%;
      width: 100%;
      img {
        width: 100%;
      }
    }
    &_name {
      height: 6.4vh;
      line-height: 6.4vh;
      width: 100%;
      .pxfont(32);
      font-weight: 550;
      text-align: center;
    }
    &_desc, &_ability {
      padding: 0 0.5vw;
      width: 100%;
      .pxfont(18);
    }
    &_desc {
      margin-bottom: 1vh;
    }
    .slected, .slected_o {
      position: absolute;
      right: -5vh;
      top: -5vh;
      height: 10vh;
      line-height: 10vh;
      width: 10vh;
      border-radius: 50%;
      text-align: center;
      .pxfont(22);
      background-color: #c73434;
    }
    .slected_o {
      left: -5vh;
      background-color: #d0d85d;
    }
  }
}
.role_right {
  &_box {
    height: 40%;
    width: 100%;
  }
  &_chatwrap {
    display: flex;
    justify-content: center;
    align-items: center;
    height: 60%;
    width: 100%;
    &_chat {
      height: 90%;
      width: 90%;
      border-radius: 1vw;
      background-color: rgba(255, 255, 255, 0.4);
    }
  }
}
.countdown {
  &_title {
    height: 10vh;
    line-height: 10vh;
    width: 100%;
    text-align: center;
    .pxfont(40);
    font-weight: 550;
    letter-spacing: 0.2vw;
  }
  &_wrap {
    height: 15vh;
    line-height: 15vh;
    width: 100%;
    .pxfont(50);
    font-weight: 550;
    text-align: center;
    color: #0011ff;
  }
}
.chat {
  &_wrap {
    height: 88%;
    width: 100%;
    // background-color: red;
    padding: 1vw;
    overflow-y: scroll;
    &::-webkit-scrollbar {
      display: none;
    }
  }
  &_ipt {
    display: flex;
    flex-direction: row;
    justify-content: center;
    align-items: center;
    height: 12%;
    width: 100%;
    // background-color: blue;
    input {
      margin-right: 5%;
      height: 80%;
      width: 60%;
      border: none;
      outline: none;
      border-radius: 1vw;
      padding: 0 1vw;
      .pxfont(24);
    }
    button {
      height: 80%;
      width: 20%;
      border: none;
      outline: none;
      border-radius: 1vw;
      background-color: #056DA6;
      .pxfont(26);
      color: #000;
    }
  }
}
li {
  display: flex;
  align-items: top;
}
li~li {
  /* 除了第一个li, 选择所有的兄弟li标签 */
  margin-top: 1vh;
}
.right {
  display: flex;
  justify-content: flex-end;
}
.left span {
  margin-left: 1vw;
  border-radius: 0.1vw 0.5vw 0.1vw 0.5vw;
  display: inline-block;
  padding: 1.1vh 0.8vw;
  background-color: #3374d6;
  color: #FFFFFF;
  .pxfont(20);
}
.right span {
  margin-right: 1vw;
  border-radius: 0.1vw 0.5vw 0.1vw 0.5vw;
  display: inline-block;
  padding: 1.1vh 0.8vw;
  background: #FFFFFF;
  border: 0.1vw solid rgba(247, 247, 247, 1);
  color: #000000;
  .pxfont(20);
}
</style>

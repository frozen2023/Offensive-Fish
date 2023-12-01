<template>
  <div class="f" :style="customStyle">
    <div class="f_head">
      <div class="f_head_title">好友列表</div>
    </div>
    <div class="f_body">
      <div class="online">
        <div class="online_title">在线</div>
        <div class="online_list">
          <div class="online_item" v-for="(item, index) in onlineList" :key="index" :v-if="friList">
            <div class="online_item_name">{{item.name}}</div>
            <button class="online_item_invite" @click="invite()">邀请</button>
          </div>
        </div>
      </div>
      <div class="offline">
        <div class="offline_title">离线</div>
        <div class="offline_list">
          <div class="offline_item" v-for="(item, index) in offlineList" :key="index">
            <div class="offline_item_name">{{item.name}}</div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { getFriendList } from '@/api/friend.js'
export default {
  name: 'f',
  data () {
    return {
      friList: [
      ],
    }
  },
  props: {
    customStyle: {
      type: Object,
      default: () => {
        return {}
      }
    }
  },
  computed: {
    onlineList () {
      return this.friList.filter(item => item.status)
    },
    offlineList () {
      return this.friList.filter(item => !item.status)
    }
  },
  watch: {},
  methods: {
    invite() {

    },
    async getFriList() {
      await getFriendList().then((res) => {
        this.friList = res.data
      })
      .catch((err) => {
        console.log(err)
        this.$message({
          message: '获取好友列表失败',
          type: 'error',
          duration: 1000
        })
      })
    }
  },
  created () {
    this.getFriList()
  },
}
</script>
<style scoped lang='less'>
.f {
  width: 20vw;
  height: 60vh;
  flex-shrink: 0;
  background-color: rgba(255, 255, 255, 0.5);
  border: 0.2vw solid black;
  border-right-width: 0.25vw;
  border-radius: 1vw;
  &_head {
    display: flex;
    flex-direction: row;
    justify-content: center;
    align-items: center;
    width: 100%;
    height: 8vh;
    line-height: 8vh;
    text-align: center;
    &_title {
      height: 6vh;
      line-height: 6vh;
      width: 50%;
      .pxfont(36);
      background-color: rgba(255, 255, 255, 0.3);
      border-radius: 1vw;
    }
  }
  &_body {
    width: 100%;
    height: calc(100% - 8vh);
  }
}
.online, .offline {
  width: 100%;
  height: 49%;
}
.online {
  &_title {
    margin-left: 3%;
    height: 4.5vh;
    line-height: 4.5vh;
    width: 20%;
    background-color: rgb(84, 236, 97);
    .pxfont(24);
    text-align: center;
    border-radius: 1vw;
    letter-spacing: 0.1vw;
  }
  &_list {
    height: calc(100% - 4.5vh);
    width: 100%;
    padding: 0.5vw 0;
    overflow-y: scroll;
  }
  &_item {
    display: flex;
    flex-direction: row;
    align-items: center;
    margin: 0 auto;
    height: 20%;
    width: 88%;
    background-color: rgba(255, 255, 255, 0.7);
    opacity: 1 !important;
    margin-bottom: 1vh;
    border-radius: 0.7vw;
    &_name {
      flex: 1;
      margin-left: 10%;
      .pxfont(24);
      color: rgb(84, 236, 97);
    }
    &_invite {
      margin-right: 5%;
      height: 80%;
      line-height: 80%;
      width: 20%;
      background-color: #8CB7F3;
      .pxfont(20);
      border-radius: 1vw;
    }
  }
}
.offline {
  margin-top: 2%;
  &_title {
    margin-left: 3%;
    height: 4.5vh;
    line-height: 4.5vh;
    width: 20%;
    background-color: #189DEE;
    .pxfont(24);
    text-align: center;
    border-radius: 1vw;
    letter-spacing: 0.1vw;
  }
  &_list {
    height: calc(100% - 4.5vh);
    width: 100%;
    padding: 0.5vw 0;
    overflow-y: scroll;
  }
  &_item {
    display: flex;
    flex-direction: row;
    align-items: center;
    margin: 0 auto;
    height: 20%;
    width: 88%;
    background-color: rgba(255, 255, 255, 0.8);
    opacity: 1 !important;
    margin-bottom: 1vh;
    border-radius: 0.7vw;
    &_name {
      flex: 1;
      margin-left: 10%;
      .pxfont(24);
      color: rgb(146, 150, 146);
    }
  }
}
.online, .offline {
  &_list {
    &::-webkit-scrollbar {
      background-color: rgba(255, 255, 255, 0);
      width: 1vw;
    }
    &::-webkit-scrollbar-thumb{
      background: #fff;
      border-radius: 0.4vw;
      border: 0.1vw solid black;
      width: 1vw;
    }
  }
}
</style>

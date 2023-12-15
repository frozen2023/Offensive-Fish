<template>
  <div>
    <button class="back" @click="goHome()">返回主界面</button>
  </div>
</template>

<script>
import store from '@/store'
export default {
  name: 'back',
  data () {
    return {}
  },
  computed: {},
  methods: {
    goHome() {
      // 清除房间信息
      store.commit('socket/setRoomId', '')
      store.commit('socket/setMap', '0')
      store.commit('socket/setMyRole', '0')
      store.commit('socket/setPlayerRole', '0')
      store.commit('socket/setChatMessages', [])
      this.$router.push('/home')
    }
  },
  created () {

  },
}
</script>
<style scoped lang='less'>
.back {
  margin-top: 5vh;
  height: 10vh;
  width: 15vw;
  .pxfont(40);
  border-radius: 1vw;
  background-color: rgba(28, 65, 187, 0.7);
  &:hover {
    -webkit-box-shadow: 0.5vh 1vw 2.1vh 0.6vw rgba(76,201,240,1);
    -moz-box-shadow: 0.5vh 1vw 2.1vh 0.6vw rgba(76,201,240,1);
    box-shadow: 0.5vh 0.5vw 8.1vh 0.5vw rgba(76,201,240,1);
    transition: all 0.25s linear;
    transform: scale(1.05);
  }
}
</style>

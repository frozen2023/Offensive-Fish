<template>
  <div class="play">
    <router-view></router-view>
  </div>
</template>

<script>
export default {
  name: 'playIndex',
  data () {
    return {}
  },
}
</script>
<style scoped lang='less'>
.play {
  width: 100vw;
  height: 100vh;
  background: url('@/assets/images/bg.png') no-repeat;
  background-size: 100vw 100vh;
}
</style>

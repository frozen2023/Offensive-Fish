<template>
  <div class="loginindex">
    <div class="loginindex_logo">
      <img src="@/assets/images/logo.png">
    </div>
    <router-view></router-view>
  </div>
</template>

<script>
export default {
  name: 'loginIndex',
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
.loginindex {
  width: 100vw;
  height: 100vh;
  background: url('@/assets/images/bg.png') no-repeat;
  background-size: 100vw 100vh;
}
.loginindex_logo {
  width: 100vw;
  height: 40vh;
  text-align: center;
  img {
    height: 100%;
    scale: 1;
  }
}
</style>

<template>
  <div class="layout">
    <router-view></router-view>
  </div>
</template>

<script>
export default {
  name: 'layoutIndex',
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
.layout {
  width: 100vw;
  height: 100vh;
  background: url('@/assets/images/bg.png') no-repeat;
  background-size: 100vw 100vh;
}
</style>

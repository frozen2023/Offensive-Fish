<template>
  <div class="layout">
    <router-view></router-view>
    index
    <button @click="$router.push('/rank')">rank</button>
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

</style>

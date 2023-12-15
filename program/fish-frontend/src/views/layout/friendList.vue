<template>
  <div class="fri">
    <div class="fri_wrap">
      <Fri :style="customStyle"></Fri>
      <backBtn />
    </div>
  </div>
</template>

<script>
import Fri from '@/components/fri.vue'
import backBtn from '@/components/backBtn.vue'
export default {
  name: 'friendList',
  components: {
    Fri,
    backBtn
  },
  data () {
    return {
      customStyle: {
        height: '70vh',
        width: '25vw'
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
.fri {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  width: 100vw;
  height: 100vh;
  &_wrap {
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    width: 50vw;
    height: 90vh;
  }
}
</style>

<template>
  <div>
    <button class="back" @click="$router.push('/home')">返回主界面</button>
  </div>
</template>

<script>
export default {
  name: 'back',
  data () {
    return {}
  },
  computed: {},
  methods: {},
  created () {},
}
</script>
<style scoped lang='less'>
.back {
  margin-top: 5vh;
  height: 10vh;
  width: 15vw;
  .pxfont(40);
  border-radius: 1vw;
  background-color: rgb(28, 65, 187);
  &:hover {
    -webkit-box-shadow: 0.5vh 1vw 2.1vh 0.6vw rgba(76,201,240,1);
    -moz-box-shadow: 0.5vh 1vw 2.1vh 0.6vw rgba(76,201,240,1);
    box-shadow: 0.5vh 0.5vw 8.1vh 0.5vw rgba(76,201,240,1);
    transition: all 0.25s linear;
    transform: scale(1.05);
  }
}
</style>

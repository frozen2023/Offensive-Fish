<template>
  <div class="character">
    <div class="character_left">
      <div class="character_left_card" v-for="role in roles" :key="role.id">
        <div class="card_wrap">

        </div>
      </div>
    </div>
    <div class="character_right">

    </div>
  </div>
</template>

<script>
import Roles from '@/dicts/static_roles.js'
export default {
  name: 'character',
  components: {},
  data () {
    return {
      roles: Roles.roles
    }
  },
  computed: {},
  watch: {},
  methods: {},
  created () {},
}
</script>
<style scoped lang='less'>
.character {
  display: flex;
  flex-direction: row;
  padding: 0 8vw;
  width: 100vw;
  height: 100vh;
  &_left {
    display: flex;
    flex-direction: column;
    align-items: flex-end;
    flex: 1.2;
    height: 100%;
    padding: 20vh 0;
    &_card {
      display: flex;
      justify-content: flex-end;
      align-items: center;
      width: 80%;
      height: 40%;
      background-color: #fff;
      &:first-child {
        border-top-left-radius: 0.8vw;

      }
      &:last-child {
        border-bottom-left-radius: 0.8vw;
      }
    }
  }
  &_right {
    flex: 5;
    height: 100%;
    background-color: blue;
  }
}
.card {
  &_wrap {
    height: 90%;
    width: 93%;
    background-color: #78AAF2;
    border-top-left-radius: 0.8vw;
    border-bottom-left-radius: 0.8vw;
  }
}
</style>

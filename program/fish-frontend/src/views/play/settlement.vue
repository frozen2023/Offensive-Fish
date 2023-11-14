<template>
  <div class="settlement">
    <div class="settlement_wrap">
      <div class="settlement_wrap_title">结算</div>
      <div class="settlement_wrap_table">
        <table>
          <thead>
            <tr>
              <th>排名</th>
              <th>玩家ID</th>
              <th>角色</th>
              <th>分数</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="(item, index) in settleList" :key="index">
              <td>{{ item.rank }}</td>
              <td>{{ item.id }}</td>
              <td>{{ item.role }}</td>
              <td>{{ item.score }}</td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
    <backBtn />
  </div>
</template>

<script>
import backBtn from '@/components/backBtn.vue'
export default {
  name: 'settlement',
  components: {
    backBtn
  },
  data () {
    return {
      settleList: [
        { rank: '1', id: '玩家1', role: '角色1', score: '13353' },
        { rank: '2', id: '玩家2', role: '角色2', score: '6593' },
      ]
    }
  },
  computed: {},
  watch: {},
  methods: {},
  created () {},
}
</script>
<style scoped lang='less'>
.settlement {
  display: flex;
  width: 100vw;
  height: 100vh;
  padding: 0 8vw;
  align-items: center;
  flex-direction: column;
  justify-content: center;
  &_wrap {
    margin: 0 auto;
    width: 80%;
    height: 70vh;
    background-color: rgb(28, 65, 187);
    border-radius: 2vw;
    &_title {
      height: 10vh;
      line-height: 10vh;
      width: 100%;
      text-align: center;
      .pxfont(60);
    }
    &_table {
      width: 100%;
      height: 60vh;
    }
  }
  &_back {
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
}
table {
  border-collapse: collapse;
  border-spacing: 0;
  width: 90%;
  margin: 0 auto;
}
th, td {
  height: 7vh;
  border: 0.1vw solid #000;
  padding: 0.1vw;
  background-color: #699eca;
  .pxfont(36);
  text-align: center;
}
td {
  height: 25vh;
  border-bottom: none;
  &:last-child{
    border-right: none;
  }
  &:first-child {
    border-left: none;
  }
}
th {
  border-top: none;
  border-bottom: none;
  &:first-child {
    border-left: none;
    border-top-left-radius: 1vw;
  }
  &:last-child {
    border-right: none;
    border-top-right-radius: 1vw;
  }
}
tr:last-child td:first-child {
  border-bottom-left-radius: 1vw;
}
tr:last-child td:last-child {
  border-bottom-right-radius: 1vw;
}
</style>

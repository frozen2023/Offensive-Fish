<template>
  <div class="settlement">
    <div class="settlement_wrap">
      <div class="settlement_wrap_title">结算</div>
      <div class="settlement_wrap_table">
        <table>
          <thead>
            <tr>
              <th>排名</th>
              <th>玩家</th>
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
import store from '@/store'
import { insertRankList } from '@/api/rank.js'
export default {
  name: 'settlement',
  components: {
    backBtn
  },
  data () {
    return {
      settleList: [
        { rank: '', id: '', role: '', score: '' },
        { rank: '', id: '', role: '', score: '' },
      ]
    }
  },
  computed: {
    scores() {
      return store.getters.scores
    },
    masterRoleId() {
      return store.getters.masterRoleId
    },
    playerRoleId() {
      return store.getters.playerRoleId
    },
    masterName() {
      if(localStorage.getItem('isMaster') == 'false') {
        return localStorage.getItem('playerName')
      } else {
        return store.getters.userName
      }
    },
    playerName() {
      if(localStorage.getItem('isMaster') == 'false') {
        return store.getters.userName
      } else {
        return localStorage.getItem('playerName')
      }
    }
  },
  watch: {},
  methods: {
    async insertRank() {
      await insertRankList(this.masterName, this.playerName, this.scores.master + this.scores.player)
      .catch(err => {
        console.log(err)
      })
    },
  },
  created () {
    const playerInfo = {
      master: {
        id: this.masterName,
        role: this.masterRoleId == 1 ? '金枪鱼' : '鳐鱼',
        score: this.scores.master,
      },
      player: {
        id: this.playerName,
        role: this.playerRoleId == 1 ? '金枪鱼' : '鳐鱼',
        score: this.scores.player,
      },
    }
    this.settleList = Object.values(playerInfo)
    // 按分数进行排序，并且添加排名
    this.settleList.sort((a, b) => {
      return b.score - a.score
    })
    this.settleList.forEach((item, index) => {
      item.rank = index + 1
    })
    if(localStorage.getItem('isMaster') == 'false') {
      this.insertRank()
    }
  },
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
    background-color: rgba(28, 65, 187, 0.4);
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
  opacity: 0.8;
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

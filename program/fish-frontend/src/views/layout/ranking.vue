<template>
  <div class="rank">
    <div class="rank_logo">
      <img src="@/assets/images/logo.png">
    </div>
    <div class="rank_table">
      <table>
        <thead>
          <tr>
            <th>排名</th>
            <th>玩家ID</th>
            <th>使用角色</th>
            <th>分数</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="(item, index) in limitedRankList" :key="index">
            <td>{{ item.rank }}</td>
            <td>{{ item.id }}</td>
            <td>{{ item.role }}</td>
            <td>{{ item.score }}</td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>

<script>
import { getRankList } from '@/api/rank.js'
export default {
  name: 'ranking',
  components: {},
  data () {
    return {
      rankList: [
        { rank: '1', id: 'player1', role: '角色A', score: '100' },
        { rank: '2', id: 'player2', role: '角色B', score: '90' },
        { rank: '3', id: 'player3', role: '角色C', score: '80' },
        { rank: '1', id: 'player1', role: '角色A', score: '100' },
        { rank: '2', id: 'player2', role: '角色B', score: '90' },
        { rank: '3', id: 'player3', role: '角色C', score: '80' },
      ]
    }
  },
  computed: {
    limitedRankList() {
      if(this.rankList.length <= 6) {
        return this.rankList
      }
      return this.rankList.slice(0, 6) // 返回前6条记录，如果rankList的长度小于6，则返回全部记录  
    }
  },
  methods: {
    async getRank() {
      await getRankList()
      .then(res => {
        console.log(res)
        this.$message.success('获取排行榜成功')
      })
      .catch(err => {
        console.log(err)
        this.$message.error('获取排行榜失败')
      })
    }
  },
  created () {
    this.getRank()
  },
}
</script>
<style scoped lang='less'>
.rank {
  width: 100vw;
  height: 100vh;
}
.rank_logo {
  width: 100vw;
  height: 40vh;
  text-align: center;
  img {
    height: 100%;
    scale: 1;
  }
}
.rank_table {
  border-radius: 1vw;
  overflow: hidden;
  margin: 0 auto;
  width: 60%;
}

th, td {
  height: 8vh;
  border: 0.1vw solid #000;
  padding: 0.1vw;
  text-align: left;
  background-color: #f2f2f2;
  .pxfont(36);
  text-align: center;
}
th {
  border-top: none;
  border-bottom: none;
  &:first-child {
    border-left: none;
  }
  &:last-child {
    border-right: none;
  }
}
td {
  border-bottom: none;
  &:last-child{
    border-right: none;
  }
  &:first-child {
    border-left: none;
  }
}
table {
  border-collapse: collapse;
  border-spacing: 0;
  width: 100%;
}

</style>

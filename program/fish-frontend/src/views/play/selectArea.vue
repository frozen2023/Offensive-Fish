<template>
  <div class="area">
    <div class="area_head">
      <div class="area_head_title">选择水域</div>
    </div>
    <div class="area_body">
      <div class="area_body_left" @click="left()">
        <div class="area_body_left_arrow"></div>
      </div>
      <div class="area_body_map" v-for="(map) in displayedMaps" :key="map.id" @click="slected(map.id)">{{ map.name }}
        <i class="slected" v-show="map.slected">已选择</i>
      </div>
      <div class="area_body_right" @click="right()">
        <div class="area_body_right_arrow"></div>
      </div>
    </div>
    <div class="area_foot">
      <button class="area_foot_confirm" @click="confirm()" v-if="master">{{  hasSlection?'确认选择':'随机选择' }}</button>
      <button class="area_foot_return" @click="doBack()" ref="backBtn" :style="master?margin:nomargin">返回</button>
    </div>
  </div>
</template>

<script>
import io from 'socket.io-client'
import store from '@/store'
export default {
  name: 'selectArea',
  data () {
    return {
      margin: {
        marginLeft: '5vw'
      },
      nomargin: {
        marginLeft: '0'
      },
      maps: [
        { id: 1, name: '1', slected: false },
        { id: 2, name: '2', slected: false },
        { id: 3, name: '3', slected: false },
        { id: 4, name: '4', slected: false },
        { id: 5, name: '5', slected: false }
      ],
      currentIndex: 0, // 当前展示地图数组的索引
      displayCount: 3, // 每次展示的地图数量
      master: false,
      mapId: ''
    }
  },
  computed: {
    displayedMaps() {
      const endIndex = this.currentIndex + this.displayCount
      return this.maps.slice(this.currentIndex, endIndex)
    },
    hasSlection() {
      return this.maps.some((item) => {
        return item.slected
      })
    },
    slection() {
      return this.maps.filter((item) => {
        return item.slected
      })
    },
    isMaster() {
      if(localStorage.getItem('isMaster') == 'false') {
        return false
      }
      return true
    },
    socket() {
      return store.getters.socket
    },
    roomId() {
      return store.getters.roomId
    },
    map() {
      return localStorage.getItem('mapId')
    }
  },
  watch: {
    // 监听map的变化，如果map不为0，说明房主选择了地图，此时找到对应的map，将其slected置为true
    map() {
      if(this.map != '0') {
        this.maps.forEach((item) => {
          if(item.id == this.map) {
            item.slected = true
          }
        })
      }
    }
  },
  methods: {
    left() {
      if (this.currentIndex > 0) {
        this.currentIndex--
      }
    },
    right() {
      if (this.currentIndex < this.maps.length - this.displayCount) {
        this.currentIndex++
      }
    },
    slected(id) {
      if(!this.master) {
        this.message({
          message: '你不是房主噢，不能选择地图~~~',
          type: 'info',
          duration: 2000
        })
        return
      }
      this.maps.forEach((item) => {
        if (item.id === id) {
          item.slected = true
        } else {
          item.slected = false
        }
      })
      this.socket.emit('chooseMap', parseInt(this.roomId), parseInt(id))
    },
    doBack() {
      this.$message({
        message: '不能返回噢！！！游戏开始啦！！！不要抛弃你的队友！！！',
        type: 'warning',
        duration: 2200
      })
    },
    confirm() {
      let mapId = ''
      if(!this.hasSlection) {
        const index = Math.floor(Math.random() * this.maps.length)
        mapId = this.maps[index].id
      } else {
        mapId = this.slection[0].id
      }
      this.$router.push({ path: '/role', query: { mapid: mapId } })
    }
  },
  created () {
    localStorage.setItem('mapId', '0')
    if(localStorage.getItem('isMaster') == 'true') {
      this.master = true
    } else {
      this.master = false
    }
    this.socket.on('message', (data) => {
      if(data.msg == '选择地图成功!' && localStorage.getItem('isMaster') == 'true') {
        const mapId = toString(data.map)
        // 寻找对应maoId的map，将其slected置为true
        this.mapId = mapId
      }
    })
  },
}
</script>
<style scoped lang='less'>
.area {
  width: 100vw;
  height: 100vh;
  &_head {
    height: 18vh;
    line-height: 18vh;
    text-align: center;
    width: 100%;
    &_title {
      font-weight: 550;
      .pxfont(70);
      letter-spacing: 0.3vw;
    }
  }
  &_body {
    display: flex;
    flex-direction: row;
    justify-content: center;
    align-items: center;
    height: 64vh;
    width: 100%;
    padding: 0 5vw;
    &_left, &_right {
      position: relative;
      width: 3vw;
      height: 15vh;
      line-height: 15vh;
      background-color: #fff;
      cursor: pointer;
      &_arrow {
        position: absolute;
        top: 2.5vh;
        left: 0.5vw;
        border-top: 5vh solid transparent;
        border-bottom: 5vh solid transparent;
      }
      &:hover {
        transition: all 0.25s linear;
        transform: scale(1.05);
      }
    }
    &_left {
      margin-right: 4vw;
      &_arrow {
        border-left: 0vw solid transparent;
        border-right: 2vw solid black;
      }
    }
    &_right {
      margin-left: 4vw;
      &_arrow {
        border-right: 0vw solid transparent;
        border-left: 2vw solid black;
      }
    }
    &_map {
      position: relative;
      margin-right: 4vw;
      height: 45vh;
      width: 45vh;
      background-color: red;
      cursor: pointer;
      &:nth-child(4) {
        margin-right: 0;
      }
      .slected {
        position: absolute;
        right: -5vh;
        top: -5vh;
        height: 10vh;
        line-height: 10vh;
        width: 10vh;
        border-radius: 50%;
        background-color: #fff;
        text-align: center;
        .pxfont(22);
      }
    }
  }
  &_foot {
    display: flex;
    padding: 0 5vw;
    height: 18vh;
    width: 100%;
    flex-direction: row;
    justify-content: center;
    align-items: center;
    &_confirm, &_return {
      width: 10vw;
      height: 8vh;
      line-height: 8vh;
      background-color: rgba(255, 255, 255, 0.5);
      border-radius: 1vh;
      .pxfont(34);
      &:hover {
        box-shadow: 0.5vh 0.5vw 8.1vh 0.5vw rgba(76,201,240,1);
        transition: all 0.25s linear;
        transform: scale(1.05);
      }
    }
    &_confirm {
      margin-right: 5vw;
    }
    &_return {
      margin-left: 5vw;
    }
  }
}
</style>

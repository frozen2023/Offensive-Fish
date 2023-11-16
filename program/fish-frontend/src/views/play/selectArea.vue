<template>
  <div class="area">
    <div class="area_head">
      <div class="area_head_title">选择水域</div>
    </div>
    <div class="area_body">
      <div class="area_body_left" @click="left()">
        <div class="area_body_left_arrow"></div>
      </div>
      <div class="area_body_map" v-for="(map, index) in displayedMaps" :key="index">{{ map.name }}</div>
      <div class="area_body_right" @click="right()">
        <div class="area_body_right_arrow"></div>
      </div>
    </div>
    <div class="area_foot">
      <button class="area_foot_confirm">确认选择</button>
      <button class="area_foot_return">返回</button>
    </div>
  </div>
</template>

<script>
export default {
  name: 'selectArea',
  data () {
    return {
      maps: [
        { id: 1, name: '1' },
        { id: 2, name: '2' },
        { id: 3, name: '3' },
        { id: 4, name: '4' }
      ],
      currentIndex: 0, // 当前展示地图数组的索引
      displayCount: 3 // 每次展示的地图数量
    }
  },
  computed: {
    displayedMaps() {
      const endIndex = this.currentIndex + this.displayCount;
      return this.maps.slice(this.currentIndex, endIndex);
    }
  },
  watch: {},
  methods: {
    left() {
      if (this.currentIndex > 0) {
        this.currentIndex--;
      }
    },
    right() {
      if (this.currentIndex < this.maps.length - this.displayCount) {
        this.currentIndex++;
      }
    }
  },
  created () {},
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
      margin-right: 4vw;
      height: 45vh;
      width: 45vh;
      background-color: red;
      &:nth-child(4) {
        margin-right: 0;
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

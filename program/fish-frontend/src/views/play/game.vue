<template>
  <div class="game" @keydown="handleKeyDown" @keyup="handleKeyUp" tabindex="0">
    <canvas id="fishCanvas" ref="fishCanvas">
      您的浏览器不支持canvas,请更换浏览器
    </canvas>
    <canvas id="otherfishCanvas" ref="otherfishCanvas">
      您的浏览器不支持canvas,请更换浏览器
    </canvas>
  </div>
</template>

<script>
import fish1 from '@/assets/images/1.png'
import fish2 from '@/assets/images/2.png'
import fish1_turn from '@/assets/images/1_turn.png'
import fish2_turn from '@/assets/images/2_turn.png'
import store from '@/store'
export default {
  name: 'game',
  data () {
    return {
      isMaster: false,
      canvas: null,
      ctx: null,
      fishCanvas: null,
      fishCtx: null,
      player: {
        img: null,
        x: 0,
        y: 0,
        speed: 0.5,
        moving: {
          up: false,
          down: false,
          left: false,
          right: false,
        },
      },
      master: {
        img: null,
        x: 0,
        y: 0,
        speed: 0.5,
        moving: {
          up: false,
          down: false,
          left: false,
          right: false,
        },
      },
      animationId: null,
      w: 35,
      h: 20,
      fishTimer: null, // 随机生成鱼的定时器
      randomInterval: null, // 随机请求时间间隔
      fishes: [], // 保存所有人机鱼的数组
    }
  },
  computed: {
    socket() {
      return store.getters.socket
    },
    roomId() {
      return store.getters.roomId
    },
    loginId() {
      return store.getters.loginId
    },
    masterRole() {
      // return store.getters.masterRoleId
      return 1
    },
    playerRole() {
      // return store.getters.playerRoleId
      return 2
    }
  },
  watch: {},
  methods: {
    initImage(roleId, flag) {
      const img = new Image()
      img.src = roleId == 1 ? fish1 : fish2
      const role = flag ? this.master : this.player
      img.onload = () => {
        this.ctx.drawImage(img, role.x, role.y, this.w, this.h)
      }
      role.img = img
    },
    drawFish() {
      this.ctx.clearRect(0, 0, this.canvas.width, this.canvas.height)
      if(this.master.moving.left) {
        this.master.img.src = fish1_turn
      } else if(this.master.moving.right) {
        this.master.img.src = fish1
      }
      if(this.player.moving.left) {
        this.player.img.src = fish2_turn
      } else if(this.player.moving.right) {
        this.player.img.src = fish2
      }
      this.ctx.drawImage(this.master.img, this.master.x , this.master.y , this.w, this.h)
      this.ctx.drawImage(this.player.img, this.player.x , this.player.y , this.w, this.h)
      this.ctx.fill()
    },
    drawOtherFish(fishInfo) {
      const offscreenCanvas = document.createElement('canvas')
      offscreenCanvas.width = this.fishCanvas.width
      offscreenCanvas.height = this.fishCanvas.height
      const offscreenCtx = offscreenCanvas.getContext('2d')

      for (const fish of this.fishes) {
        offscreenCtx.drawImage(fish.img, fish.x, fish.y, this.w, this.h)
      }

      this.fishCtx.clearRect(0, 0, this.fishCanvas.width, this.fishCanvas.height)
      this.fishCtx.drawImage(offscreenCanvas, 0, 0)
    },
    handleKeyDown(event) {
      let role
      if (this.isMaster) {
        role = this.master
      } else {
        role = this.player
      }
      switch (event.key) {
        case 'ArrowUp':
          role.moving.up = true
          break
        case 'ArrowDown':
          role.moving.down = true
          break
        case 'ArrowLeft':
          role.moving.left = true
          break
        case 'ArrowRight':
          role.moving.right = true
          break
      }
      // 开始动画循环
      this.startAnimation()
    },
    handleKeyUp(event) {
      let role
      if (this.isMaster) {
        role = this.master
      } else {
        role = this.player
      }
      switch (event.key) {
        case 'ArrowUp':
          role.moving.up = false
          break
        case 'ArrowDown':
          role.moving.down = false
          break
        case 'ArrowLeft':
          role.moving.left = false
          break
        case 'ArrowRight':
          role.moving.right = false
          break
      }
      // 如果没有任何方向键被按下，停止动画循环
      if(!this.player.moving.up && !this.player.moving.down && !this.player.moving.left && !this.player.moving.right &&
        !this.master.moving.up && !this.master.moving.down && !this.master.moving.left && !this.master.moving.right) {
        this.stopAnimation()
      }
    },
    movePlayer() {
      let role
      if (this.isMaster) {
        role = this.master
      } else {
        role = this.player
      }
      role.x += (role.moving.right ? role.speed : 0) - (role.moving.left ? role.speed : 0)
      role.y += (role.moving.down ? role.speed : 0) - (role.moving.up ? role.speed : 0)
      if(role.x < 0) { // 限制角色移动范围，防止移出屏幕
        role.x = 0
      } else if(role.x > this.canvas.width - this.w) {
        role.x = this.canvas.width - this.w
      }
      if(role.y < 0) {
        role.y = 0
      } else if(role.y > this.canvas.height - this.h) {
        role.y = this.canvas.height - this.h
      }
      // 发送移动事件到其他玩家
      let pos = JSON.stringify({ x: role.x, y: role.y })
      this.socket.emit('move', this.roomId, pos)
      let roler = { x: role.x, y: role.y, img: role.img }
      for (let i = 0; i < this.fishes.length; i++) {
        const fish = this.fishes[i]
        if (this.checkCollision(roler, fish)) {
          this.eatFish(fish)
          this.socket.emit('eatFish', this.roomId, i)
          clearInterval(this.fishes[i].fishTimer)
          this.fishCtx.clearRect(0, 0, this.fishCanvas.width, this.fishCanvas.height)
          this.fishes.splice(i, 1)
          i-- // 更新索引，因为数组长度已经改变
        }
      }
      this.drawFish()
    },
    startAnimation() {
      // 如果动画循环已经开始，直接返回
      if (this.animationId !== null) {
        return
      }
      const animate = () => {
        this.movePlayer()
        this.animationId = requestAnimationFrame(animate)
      };

      this.animationId = requestAnimationFrame(animate)
    },
    stopAnimation() {
      cancelAnimationFrame(this.animationId)
      this.animationId = null
    },
    checkCollision(obj1, obj2) {
      return (
        obj1.x < obj2.x + 35 &&
        obj1.x + 35 > obj2.x &&
        obj1.y < obj2.y + 20 &&
        obj1.y + 20 > obj2.y
      )
    },
    eatFish(fish) {
      // 处理吃到鱼的逻辑
      console.log('吃到鱼了！', fish)
    },
  },
  mounted() {
    this.canvas = this.$refs.fishCanvas
    this.fishCanvas = this.$refs.otherfishCanvas
    this.ctx = this.canvas.getContext('2d')
    this.fishCtx = this.fishCanvas.getContext('2d')
    this.master.x = this.canvas.width / 3
    this.master.y = this.canvas.height / 4
    this.player.x = this.canvas.width / 3*2
    this.player.y = this.canvas.height / 4
    this.initImage(this.masterRole, true)
    this.initImage(this.playerRole, false)
  },
  created () {
    if(localStorage.getItem('isMaster') == 'false') {
      this.isMaster = false
    } else {
      this.isMaster = true
    }
    this.socket.on('anotherMove', (userId, data) => {
      console.log(data)
      let pos = JSON.parse(data)
      if(userId !== this.loginId) {
        const role = !this.isMaster ? this.master : this.player
        if(pos.x < role.x) {
          role.moving.left = true
          role.moving.right = false
        } else if(pos.x > role.x) {
          role.moving.right = true
          role.moving.left = false
        }
        role.x = pos.x
        role.y = pos.y
      }
      this.drawFish()
    })
    // 监听 fishSpace 事件
    this.socket.on('fish', (data) => {
      // 处理从服务器广播的鱼的生成信息
      console.log(data)
      let { lr, SerialNum, y } = data
      let x = lr === 0 ? -35 : this.canvas.width + 35 // 从左或右边界出现
      let speed = 0.3
      if(y > 0.86) {
        y = 0.86
      }
      // fish.img.src = `path/to/fish${SerialNum}.png` // 替换为你实际的鱼的图片路径
      let fish = { x, y: y*this.canvas.height, speed, SerialNum, lr, img: new Image(), fishTimer: null }
      if(lr === 0) {
        fish.img.src = fish1
      } else {
        fish.img.src = fish1_turn
      }
      // 通过定时器移动鱼
      fish.fishTimer = setInterval(() => {
        fish.x += fish.speed * (lr === 0 ? 1 : -1)
        if(fish.x < -35 || fish.x > this.canvas.width + 35) {
          clearInterval(fish.fishTimer)
          this.fishes.splice(this.fishes.indexOf(fish), 1)
        }
        console.log("鱼鱼:", fish.x)
        this.drawOtherFish(fish)
        // 如果鱼超出边界，可以在这里处理
      }, 1000 / 100) // 60帧每秒
      this.fishes.push(fish)
      this.randomInterval = Math.floor(Math.random() * (6000 - 4000 + 1) + 4000) // 随机间隔
    })
    // 监听 eatFish 事件
    this.socket.on('anotherEat', (userId, index) => {
      // 处理从服务器广播的鱼的消失信息
      if(userId !== this.loginId) {
        this.fishes.splice(index, 1)
      }
    })
    // 设置定时器，在 created 时触发，每隔 2s 到 4s 向 socket 发起 emit 事件
    this.randomInterval = Math.floor(Math.random() * (6000 - 4000 + 1) + 4000) // 随机间隔
    if(this.isMaster) {
      // this.fishTimer = setInterval(() => {
        // console.log(this.randomInterval)
        this.socket.emit('fishSpace', this.roomId)
      // }, this.randomInterval)
    }
    store.commit('socket/setSocket', this.socket)
  },
  beforeDestroy() {
    // 清除定时器
    clearInterval(this.fishTimer)
  },
}
</script>
<style scoped lang='less'>
.game {
  display: flex;
  width: 100vw;
  height: 100vh;
}
#fishCanvas {
  position: relative;
  display: flex;
  width: 100vw;
  height: 100vh;
}
#otherfishCanvas {
  position: absolute;
  top: 0;
  left: 0;
  width: 100vw;
  height: 100vh;
}
</style>

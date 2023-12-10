<template>
  <div class="game" @keydown="handleKeyDown" @keyup="handleKeyUp" tabindex="0">
    <canvas id="fishCanvas" ref="fishCanvas">
      您的浏览器不支持canvas,请更换浏览器
    </canvas>
    <canvas id="otherfishCanvas" ref="otherfishCanvas">
      您的浏览器不支持canvas,请更换浏览器
    </canvas>
    <div class="box">
      <div class="box_display">
        <div class="text">P1</div>
        <div class="wrap">
          <div class="health p1" ref="p1"></div>
        </div>
        <div class="score">{{ scoreMaster }}</div>
      </div>
      <div class="box_display">
        <div class="text">P2</div>
        <div class="wrap">
          <div class="health p2" ref="p2"></div>
        </div>
        <div class="score">{{ scorePlayer }}</div>
      </div>
    </div>
  </div>
</template>

<script>
import fish1 from '@/assets/images/1.png'
import fish2 from '@/assets/images/2.png'
import fish1_turn from '@/assets/images/1_turn.png'
import fish2_turn from '@/assets/images/2_turn.png'
import store from '@/store'
import { mapActions } from 'vuex'
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
        health: 100,
        defense: 5,
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
        health: 100,
        defense: 5,
        moving: {
          up: false,
          down: false,
          left: false,
          right: false,
        },
      },
      scoreMaster: 0,
      scorePlayer: 0,
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
  watch: {
    // 监听master和player的血量，如果血量为0，游戏结束
    'master.health': function(val) {
      if(val <= 0) {
        this.goSettle()
      }
    },
    'player.health': function(val) {
      if(val <= 0) {
        this.goSettle()
      }
    }
  },
  methods: {
    goSettle() {
      this.stopAnimation()
      store.commit('socket/setScore', { role: 'master', score: this.scoreMaster })
      store.commit('socket/setScore', { role: 'player', score: this.scorePlayer })
      this.$router.push({ path: '/settlement' })
    },
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
      if(this.isMaster) {
        if(!this.master.moving.up && !this.master.moving.down && !this.master.moving.left && !this.master.moving.right) {
          this.stopAnimation()
        }
      } else {
        if(!this.player.moving.up && !this.player.moving.down && !this.player.moving.left && !this.player.moving.right) {
          this.stopAnimation()
        }
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
      let roler = { x: role.x, y: role.y, img: role.img, defense: role.defense }
      for (let i = 0; i < this.fishes.length; i++) {
        const fish = this.fishes[i]
        if (this.checkCollision(roler, fish)) {
          console.log('玩家战斗力:', roler.defense, ' 鱼战斗力:', fish.defense)
          if(roler.defense >= fish.defense) {
            this.eatFish(fish, fish.num)
            i-- // 更新索引，数组长度已经改变
          } else {
            if(this.isMaster && !fish.isIn) {
              fish.isIn = true
              this.master.health -= 10
              this.socket.emit('health', this.roomId, this.master.health)
              this.$refs.p1.style.width = `${this.master.health}%`
            } else if(!this.isMaster && !fish.isIn){
              fish.isIn = true
              this.player.health -= 10
              this.socket.emit('health', this.roomId, this.player.health)
              this.$refs.p2.style.width = `${this.player.health}%`
            }
          }
        } else {
          fish.isIn = false
        }
      }
      this.drawFish()
    },
    startAnimation() {
      if(this.master.moving.up || this.master.moving.down || this.master.moving.left || this.master.moving.right ||
      this.player.moving.up || this.player.moving.down || this.player.moving.left || this.player.moving.right) {
        if (this.animationId !== null) {
          return
        }
        const animate = () => {
          this.movePlayer()
          this.animationId = requestAnimationFrame(animate)
        }

        this.animationId = requestAnimationFrame(animate)
      }
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
    eatFish(fish, index) {
      console.log('eat:', fish)
      this.socket.emit('eatFish', this.roomId, index)
      // 清除num==index的鱼的定时器
      clearInterval(this.fishes.find(f => f.num === index)?.fishTimer)
      // 将鱼的位置设定为画布外
      fish.x = -100
      fish.y = -100
      this.fishCtx.clearRect(0, 0, this.fishCanvas.width, this.fishCanvas.height)
      if(this.isMaster) {
        this.scoreMaster += 1
        store.commit('socket/setScore', { role: 'master', score: this.scoreMaster })
      } else {
        this.scorePlayer += 1
        store.commit('socket/setScore', { role: 'player', score: this.scorePlayer })
      }
      const fishIndex = this.fishes.findIndex(f => f.num === index)
      if (fishIndex !== -1) {
        this.fishes.splice(fishIndex, 1)
      }
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
      let { lr, SerialNum, y, num } = data
      let x = lr === 0 ? -35 : this.canvas.width + 35 // 从左或右边界出现
      let speed = 0.3
      if(y > 0.86) {
        y = 0.86
      }
      // fish.img.src = `path/to/fish${SerialNum}.png` // 替换为你实际的鱼的图片路径
      // defense 设置1-10的随机数
      let defense = Math.floor(Math.random() * 10) + 1
      let fish = { x, y: y*this.canvas.height, speed, SerialNum, lr, img: new Image(), fishTimer: null, isIn: false, defense, num }
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
        }
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
        const fishIndex = this.fishes.findIndex(f => f.num === index)
        if (fishIndex !== -1) {
          this.fishes.splice(fishIndex, 1)
        }
        if(this.isMaster) {
          this.scorePlayer += 1
          store.commit('socket/setScore', { role: 'player', score: this.scorePlayer })
        } else {
          this.scoreMaster += 1
          store.commit('socket/setScore', { role: 'master', score: this.scoreMaster })
        }
      }
    })
    // 监听 health 事件
    this.socket.on('health', (userId, health) => {
      // 处理从服务器广播的血量信息
      if(userId !== this.loginId) {
        if(this.isMaster) {
          this.player.health = health
          this.$refs.p2.style.width = `${this.player.health}%`
        } else {
          this.master.health = health
          this.$refs.p1.style.width = `${this.master.health}%`
        }
      }
    })
    // 设置定时器，在 created 时触发，每隔 2s 到 4s 向 socket 发起 emit 事件
    this.randomInterval = Math.floor(Math.random() * (6000 - 4000 + 1) + 4000) // 随机间隔
    if(this.isMaster) {
      this.fishTimer = setInterval(() => {
        console.log(this.randomInterval)
        this.socket.emit('fishSpace', this.roomId)
      }, this.randomInterval)
    }
    store.commit('socket/setSocket', this.socket)
  },
  beforeDestroy() {
    // 清除fishes里的所有定时器
    for (const fish of this.fishes) {
      clearInterval(fish.fishTimer)
    }
    clearInterval(this.fishTimer)
    // 清空画布
    this.ctx.clearRect(0, 0, this.canvas.width, this.canvas.height)
    this.fishCtx.clearRect(0, 0, this.fishCanvas.width, this.fishCanvas.height)
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
.box {
  position: absolute;
  display: flex;
  flex-direction: column;
  top: 0;
  left: 0;
  width: 20%;
  height: 15%;
  &_display {
    display: flex;
    flex-direction: row;
    justify-content: center;
    align-items: center;
    width: 100%;
    height: 50%;
    .text {
      .pxfont(30);
    }
    .wrap {
      margin-left: 5%;
      width: 50%;
      height: 20%;
      border-radius: 1vw;
      border: 0.1vw solid #000;
    }
    .health {
      width: 100%;
      height: 100%;
      border-radius: 1vw;
      transition: width 1s ease;
    }
    .score {
      margin-left: 10%;
      .pxfont(30);
    }
    .p1 {
      background-color: #c73434;
    }
    .p2 {
      background-color: #d0d85d;
    }
  }
}
</style>

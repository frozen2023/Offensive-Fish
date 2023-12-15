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
import { bus } from '@/main.js'
import fish1 from '@/assets/images/111.png'
import fish2 from '@/assets/images/22.png'
import fish1_turn from '@/assets/images/111_turn.png'
import fish2_turn from '@/assets/images/22_turn.png'
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
      acceleration: false,
      accelerationTimer: null,
      s: 5,
      t: 5,
      player: {
        img: null,
        imgS: '',
        x: 0,
        y: 0,
        speed: 0.5,
        health: 100,
        olddefense: 7,
        defense: 7,
        moving: {
          up: false,
          down: false,
          left: false,
          right: false,
        },
      },
      master: {
        img: null,
        imgS: '',
        x: 0,
        y: 0,
        speed: 0.5,
        health: 100,
        defense: 7,
        olddefense: 7,
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
      h: 35,
      fishTimer: null, // 随机生成鱼的定时器
      randomInterval: null, // 随机请求时间间隔
      fishes: [], // 保存所有人机鱼的数组
      atimer: null, // 技能时长定时器 id
      atotalSecond: 5,
      asecond: 5,
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
      return store.getters.masterRoleId
    },
    playerRole() {
      return store.getters.playerRoleId
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
      role.imgS = roleId == 1 ? 'fish1' : 'fish2'
      img.onload = () => {
        this.ctx.drawImage(img, role.x, role.y, this.w, this.h)
      }
      role.img = img
    },
    drawFish() {
      this.ctx.clearRect(0, 0, this.canvas.width, this.canvas.height)
      const setImageSource = (role, direction) => {
      const fishType = role.imgS === 'fish1' ? fish1 : fish2
      const turnType = role.imgS === 'fish1' ? fish1_turn : fish2_turn

        role.img.src = direction === 'left' ? turnType : fishType
      }

      if (this.master.moving.left || this.master.moving.right) {
        setImageSource(this.master, this.master.moving.left ? 'left' : 'right')
      }

      if (this.player.moving.left || this.player.moving.right) {
        setImageSource(this.player, this.player.moving.left ? 'left' : 'right')
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
      if (event.key === '0' && !this.acceleration) {
        this.acceleration = true
        role.speed = 0.8
        this.accelerationTimer = setInterval(() => {
          this.s--
          if(this.s == 0) {
            this.master.speed = 0.5
            this.player.speed = 0.5
          } else if(this.s <= -10) {
            this.acceleration = false
            clearTimeout(this.accelerationTimer)
            this.accelerationTimer = null
            this.s = this.t
          }
        }, 1000)
      }
      if(event.key === '1' && this.atimer == null && this.asecond === this.atotalSecond) {
        if(this.isMaster) {
          if(this,this.masterRole == 1) {
            this.master.olddefense = this.master.defense
            this.master.defense += 2
            this.atimer = setInterval(() => {
              this.asecond--
              if (this.asecond == 0) {
                this.master.defense = this.master.olddefense
              } else if(this.asecond <= -20) {
                clearInterval(this.atimer)
                this.atimer = null // 重置定时器 id
                this.asecond = this.atotalSecond // 归位
              }
            }, 1000)
          } else {
            this.master.health += 10
            this.$refs.p1.style.width = `${this.master.health}%`
            this.socket.emit('health', this.roomId, -1)
            this.palyer.health += 10
            this.$refs.p2.style.width = `${this.player.health}%`
            this.atimer = setInterval(() => {
              this.asecond--
              if(this.asecond <= -15) {
                clearInterval(this.atimer)
                this.atimer = null // 重置定时器 id
                this.asecond = this.atotalSecond // 归位
              }
            }, 1000)
          }
        } else {
          if(this,this.playerRole == 1) {
            this.player.olddefense = this.player.defense
            this.player.defense += 2
            this.atimer = setInterval(() => {
              this.asecond--
              if (this.asecond == 0) {
                this.player.defense = this.player.olddefense
              } else if(this.asecond <= -15) {
                clearInterval(this.atimer)
                this.atimer = null // 重置定时器 id
                this.asecond = this.atotalSecond // 归位
              }
            }, 1000)
          } else {
            this.player.health += 10
            this.$refs.p2.style.width = `${this.player.health}%`
            this.socket.emit('health', this.roomId, -1)
            this.master.health += 10
            this.$refs.p1.style.width = `${this.master.health}%`
            this.atimer = setInterval(() => {
              this.asecond--
              if(this.asecond <= -15) {
                clearInterval(this.atimer)
                this.atimer = null // 重置定时器 id
                this.asecond = this.atotalSecond // 归位
              }
            }, 1000)
          }
        }
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
      } else if(role.y > this.canvas.height - 27) {
        role.y = this.canvas.height - 27
      }
      // 发送移动事件到其他玩家
      let pos = JSON.stringify({ x: role.x, y: role.y })
      this.socket.emit('move', this.roomId, pos)
      let roler = { x: role.x, y: role.y, img: role.img, defense: role.defense }
      for (let i = 0; i < this.fishes.length; i++) {
        const fish = this.fishes[i]
        if (this.checkCollision(roler, fish)) {
          if(roler.defense >= fish.defense) {
            this.eatFish(fish, fish.num)
            i-- // 更新索引，数组长度已经改变
          } else {
            if(this.isMaster && !fish.isIn) {
              fish.isIn = true
              this.master.health -= fish.defense
              this.socket.emit('health', this.roomId, this.master.health)
              this.$refs.p1.style.width = `${this.master.health}%`
            } else if(!this.isMaster && !fish.isIn){
              fish.isIn = true
              this.player.health -= fish.defense
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
        obj1.x < obj2.x + this.w - 5 &&
        obj1.x + this.w - 5 > obj2.x &&
        obj1.y < obj2.y + this.h - 5 &&
        obj1.y + this.h - 5 > obj2.y
      )
    },
    eatFish(fish, index) {
      this.socket.emit('eatFish', this.roomId, index)
      // 清除num==index的鱼的定时器
      clearInterval(this.fishes.find(f => f.num === index)?.fishTimer)
      // 将鱼的位置设定为画布外
      fish.x = -100
      fish.y = -100
      this.fishCtx.clearRect(0, 0, this.fishCanvas.width, this.fishCanvas.height)
      if(this.isMaster) {
        this.scoreMaster += fish.serialNum
        if(this.scoreMaster >= 30 && this.scoreMaster < 60) {
          this.master.defense = 8
          this.master.olddefense = 8
        } else if (this.scoreMaster >= 60 && this.scoreMaster < 90) {
          this.master.defense = 9
          this.master.olddefense = 9
        } else if (this.scoreMaster >= 90 && this.scoreMaster < 150) {
          this.master.defense = 10
          this.master.olddefense = 10
        } else if (this.scoreMaster >= 150 && this.scoreMaster < 210) {
          this.master.defense = 11
          this.master.olddefense = 11
        } else if(this.scoreMaster >= 210 && this.scoreMaster < 270) {
          this.master.defense = 12
          this.master.olddefense = 12
        } else if(this.scoreMaster >= 270 && this.scoreMaster < 360) {
          this.master.defense = 13
          this.master.olddefense = 13
        } else if(this.scoreMaster >= 360 && this.scoreMaster < 450) {
          this.master.defense = 14
          this.master.olddefense = 14
        } else if(this.scoreMaster >= 450 && this.scoreMaster < 600) {
          this.master.defense = 15
          this.master.olddefense = 15
        }
        store.commit('socket/setScore', { role: 'master', score: this.scoreMaster })
      } else {
        this.scorePlayer += fish.serialNum
        if(this.scorePlayer >= 30) {
          this.player.defense = 8
          this.player.olddefense = 8
        } else if(this.scorePlayer >= 60 && this.scorePlayer < 90) {
          this.player.defense = 9
          this.player.olddefense = 9
        } else if(this.scorePlayer >= 90 && this.scorePlayer < 150) {
          this.player.defense = 10
          this.player.olddefense = 10
        } else if(this.scorePlayer >= 150 && this.scorePlayer < 210) {
          this.player.defense = 11
          this.player.olddefense = 11
        } else if(this.scorePlayer >= 210 && this.scorePlayer < 270) {
          this.player.defense = 12
          this.player.olddefense = 12
        } else if(this.scorePlayer >= 270 && this.scorePlayer < 360) {
          this.player.defense = 13
          this.player.olddefense = 13
        } else if(this.scorePlayer >= 360 && this.scorePlayer < 450) {
          this.player.defense = 14
          this.player.olddefense = 14
        } else if(this.scorePlayer >= 450 && this.scorePlayer < 600) {
          this.player.defense = 15
          this.player.olddefense = 15
        }
        store.commit('socket/setScore', { role: 'player', score: this.scorePlayer })
      }
      const fishIndex = this.fishes.findIndex(f => f.num === index)
      if (fishIndex !== -1) {
        this.fishes.splice(fishIndex, 1)
      }
    },
    imgTran(num) {
      return require("@/assets/images/" + num + ".png")
    },
    imgTurnTran(num) {
      return require("@/assets/images/" + num + "_turn.png")
    }
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
      let { lr, serialNum, y, num } = data
      let x = lr === 0 ? -1*this.w : this.canvas.width + this.w // 从左或右边界出现
      let speed = 0.3
      if(y > 0.82) {
        y = 0.82
      }
      let defense = serialNum
      let fish = { x, y: y*this.canvas.height, speed, serialNum, lr, img: new Image(), fishTimer: null, isIn: false, defense, num }
      if(lr === 0) {
        fish.img.src = this.imgTran(serialNum)
      } else {
        fish.img.src = this.imgTurnTran(serialNum)
      }
      // 通过定时器移动鱼
      fish.fishTimer = setInterval(() => {
        fish.x += fish.speed * (lr === 0 ? 1 : -1)
        if(fish.x < -1*this.w || fish.x > this.canvas.width + this.w) {
          clearInterval(fish.fishTimer)
        }
        this.drawOtherFish(fish)
        // 如果鱼超出边界，可以在这里处理
      }, 1000 / 100) // 60帧每秒
      this.fishes.push(fish)
      this.randomInterval = Math.floor(Math.random() * (6000 - 3000 + 1) + 3000) // 随机间隔
    })
    // 监听 eatFish 事件
    this.socket.on('anotherEat', (userId, index) => {
      // 处理从服务器广播的鱼的消失信息
      if(userId !== this.loginId) {
        const fishIndex = this.fishes.findIndex(f => f.num === index)
        if (fishIndex !== -1) {
          if(this.isMaster) {
            this.scorePlayer += this.fishes[fishIndex].serialNum
            store.commit('socket/setScore', { role: 'player', score: this.scorePlayer })
          } else {
            this.scoreMaster += this.fishes[fishIndex].serialNum
            store.commit('socket/setScore', { role: 'master', score: this.scoreMaster })
          }
          this.fishes.splice(fishIndex, 1)
        }
      }
    })
    // 监听 health 事件
    this.socket.on('health', (userId, health) => {
      // 处理从服务器广播的血量信息
      if(userId !== this.loginId) {
        if(health == -1) {
          this.master.health += 10
          this.$refs.p1.style.width = `${this.master.health}%`
          this.player.health += 10
          this.$refs.p2.style.width = `${this.player.health}%`
          return
        }
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
    bus.$emit('stop-music')
    bus.$emit('play-menu-music')
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

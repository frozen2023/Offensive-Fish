<template>
  <div class="game" @keydown="handleKeyDown" @keyup="handleKeyUp" tabindex="0">
    <canvas id="fishCanvas" ref="fishCanvas">
      您的浏览器不支持canvas,请更换浏览器
    </canvas>
  </div>
</template>

<script>
import fish1_turn from '@/assets/images/1.png'
import fish2 from '@/assets/images/2.png'
import fish1 from '@/assets/images/1_turn.png'
import fish2_turn from '@/assets/images/2_turn.png'
import store from '@/store'
export default {
  name: 'game',
  data () {
    return {
      isMaster: false,
      canvas: null,
      ctx: null,
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
      h: 20
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
      // this.socket.emit('move', this.roomId, pos)

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
  },
  mounted() {
    this.canvas = this.$refs.fishCanvas
    this.ctx = this.canvas.getContext('2d')
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
    store.commit('socket/setSocket', this.socket)
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
  display: flex;
  width: 100vw;
  height: 100vh;
}
</style>

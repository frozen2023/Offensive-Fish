<template>
  <div id="app">
    <transition appear>
      <router-view :key="$route.path" />
    </transition>
    <audio loop autoplay ref="au1">
      <source src="@/assets/music/menu.mp3" type="audio/mp3">
    </audio>
    <audio loop autoplay ref="au2">
      <source src="@/assets/music/game.mp3" type="audio/mp3">
    </audio>
  </div>
</template>

<script>
import { bus } from '@/main.js'
export default {
  name: 'App',
  data() {
    return {
      isMusicPlaying: false
    }
  },
  methods: {
    playGameMusic() {
      if (!this.isMusicPlaying) {
        this.$refs.au2.loop = true
        this.$refs.au2.play()
        this.$refs.au2.volume = 0.5
        this.isMusicPlaying = true
      }
    },
    playMenuMusic() {
      if (!this.isMusicPlaying) {
        this.$refs.au1.loop = true
        this.$refs.au1.play()
        this.$refs.au1.volume = 0.5
        this.isMusicPlaying = true
      }
    },
    stopMusic() {
      if (this.isMusicPlaying) {
        if(!this.$refs.au2.paused) {
          this.$refs.au2.pause()
          this.$refs.au2.currentTime = 0
          this.isMusicPlaying = false
        } 
        else if(!this.$refs.au1.paused) {
          this.$refs.au1.pause()
          this.$refs.au1.currentTime = 0
          this.isMusicPlaying = false
        }
      }
    }
  },
  mounted() {
    bus.$on('play-menu-music', this.playMenuMusic)
    bus.$on('play-game-music', this.playGameMusic)
    bus.$on('stop-music', this.stopMusic)
  }
}
</script>

<style lang="less">
html{
  margin-right: calc(100% - 100vw);
  overflow-x: hidden;
  margin: 0;
}
html::-webkit-scrollbar{
  display: none;
}
</style>

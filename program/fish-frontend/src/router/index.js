import Vue from 'vue'
import VueRouter from 'vue-router'

import Layout from '@/views/layout/index.vue'
import Home from '@/views/layout/home.vue'
import friendList from '@/views/layout/friendList.vue'
import Character from '@/views/layout/character.vue'
import Rank from '@/views/layout/ranking.vue'

import loginIndex from '@/views/login/index.vue'
import Login from '@/views/login/login.vue'
import Register from '@/views/login/register.vue'
import Changepwd from '@/views/login/changePwd.vue'

import Play from '@/views/play/index.vue'
import Room from '@/views/play/createRoom.vue'
import Area from '@/views/play/selectArea.vue'
import Role from '@/views/play/selectRole.vue'
import Game from '@/views/play/game.vue'
import Settlement from '@/views/play/settlement.vue'

import authUrls from './authUrls.js'
import store from '@/store'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    component: Layout,
    redirect: 'home',
    children: [
      { path: 'home', component: Home, name: 'home' },
      { path: 'friendlist', component: friendList, name: 'friendList' },
      { path: 'character', component: Character, name: 'character' },
      { path: 'rank', component: Rank, name: 'rank' }
    ]
  },
  {
    path: '/auth',
    component: loginIndex,
    redirect: '/login',
    children: [
      { path: '/login', component: Login, name: 'login' },
      { path: '/register', component: Register, name: 'register'},
      { path: '/changepwd', component: Changepwd, name: 'changepwd'}
    ]
  },
  {
    path: '/play',
    component: Play,
    redirect: '/room',
    children: [
      { path: '/room', component: Room, name: 'room' },
      { path: '/area', component: Area, name: 'area' },
      { path: '/role', component: Role, name: 'role' },
      { path: '/game', component: Game, name: 'game' },
      { path: '/settlement', component: Settlement, name: 'settlement' }
    ]
  }
]

const router = new VueRouter({
  routes
})

// router.beforeEach((to, from, next) => {
//   if (!authUrls.includes(to.path)) {
//     // 非权限页面，放行
//     next()
//     return
//   }

//   // 权限页面，需要判断token
//   const token = store.getters.token
//   if (token) {
//     next()
//   } else {
//     Vue.prototype.$message({
//       message: '请先登录',
//       type: 'info'
//     })
//     next('/login')
//   }
// })

export default router

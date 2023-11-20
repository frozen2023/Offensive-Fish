import Vue from 'vue'
import Vuex from 'vuex'
import user from './modules/user.js'

Vue.use(Vuex)

export default new Vuex.Store({
  getters: {
    token (state) {
      return state.user.userInfo.token
    },
    loginId (state) {
      return state.user.userInfo.loginId
    },
    userName (state) {
      return state.user.userInfo.userName
    }
  },
  modules: {
    user,
  }
})

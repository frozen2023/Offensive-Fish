import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'

// 全局默认样式
import '@/assets/style/reset.css'
import '@/assets/style/global.css'
import '@/assets/style/variable.less'

Vue.config.productionTip = false

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')

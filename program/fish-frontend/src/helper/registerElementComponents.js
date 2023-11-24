import Vue from 'vue'
import 'element-ui/lib/theme-chalk/index.css'
import 'element-ui/lib/theme-chalk/base.css'
import {
  Message,
  Loading
} from 'element-ui'

Vue.prototype.$message = Message
Vue.prototype.$loading = Loading.service

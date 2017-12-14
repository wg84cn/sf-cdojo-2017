import babelpolyfill from 'babel-polyfill'
import Vue from 'vue'
import App from './App'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-default/index.css'

import VueRouter from 'vue-router'
import store from './vuex'
import Vuex from 'vuex'

import routes from './routes'

import 'font-awesome/css/font-awesome.min.css'
import * as filters from './filters/index' // 全局filter

import 'url-search-params-polyfill'

Vue.use(ElementUI)
Vue.use(VueRouter)
Vue.use(Vuex)

Object.keys(filters).forEach(key => {
  Vue.filter(key, filters[key])
})

const router = new VueRouter({
  routes
})

router.beforeEach((to, from, next) => {

    if (to.path == '/') {
        next({path: '/main'})
    }
    next()

})

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')


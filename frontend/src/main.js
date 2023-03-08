import Vue from 'vue'
import Antd from 'ant-design-vue'
import store from './store'
import * as consts from '@/const'
import request from 'utils/request'
import db from 'utils/localstorage'
import VueApexCharts from 'vue-apexcharts'

import 'ant-design-vue/dist/antd.less'
import Febs from './FEBS'
import router from './router'

import 'utils/install'

import htmlToPdf from 'utils/htmlToPdf'

Vue.config.productionTip = false
Vue.use(Antd)
Vue.use(db)
Vue.use(VueApexCharts)
// 使用Vue.use()方法就会调用工具方法中的install方法
Vue.use(htmlToPdf)

Vue.component('apexchart', VueApexCharts)

Vue.use({
  install (Vue) {
    Vue.prototype.$db = db
  }
})

Vue.prototype.$const = consts // 枚举常量
console.log(consts, 8888888888888)
Vue.prototype.$post = request.post
Vue.prototype.$get = request.get
Vue.prototype.$put = request.put
Vue.prototype.$delete = request.delete
Vue.prototype.$export = request.export
Vue.prototype.$download = request.download
Vue.prototype.$upload = request.upload
Vue.prototype.$baseUrl = request.baseURL
Vue.prototype.$EventBus = new Vue() //增加事件跳转 hsc 2021 09 26

/* eslint-disable no-new */
new Vue({
  router,
  store,
  render: h => h(Febs)
}).$mount('#febs')

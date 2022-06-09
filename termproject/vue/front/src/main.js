/* eslint-disable */

// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import vuetify from '@/plugins/vuetify' // path to vuetify export
import axios from 'axios'
import store from './store'
import VueSession from 'vue-session'
import BootstrapVue from 'bootstrap-vue'
import 'bootstrap-vue/dist/bootstrap-vue.css'

Vue.use(BootstrapVue)
Vue.use(VueSession)

// localStorage
// var sessionOptions = {
//   persist: true
// }
// Vue.use(VueSession, sessionOptions)

Vue.prototype.$axios = axios
Vue.config.productionTip = false

/* eslint-disable */
new Vue({
    el: '#app',
    vuetify,
    router,
    store,
    components: { App },
    template: '<App/>'
})
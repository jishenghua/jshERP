import Vue from 'vue'
import Vuex from 'vuex'

import app from './modules/app'
import dict from './modules/dict'
import user from './modules/user'
import permission from './modules/permission'
import enhance from './modules/enhance'
import getters from './getters'

Vue.use(Vuex)

export default new Vuex.Store({
  modules: {
    app,
    dict,
    user,
    permission,
    enhance
  },
  state: {

  },
  mutations: {

  },
  actions: {

  },
  getters
})

import Vue from 'vue';
import Vuex from 'vuex';
import app from './modules/app.js';
import getters from './getters';

Vue.use(Vuex);

// 创建 store 实例
export default new Vuex.Store({
	modules: {
		app
	},
	getters
})
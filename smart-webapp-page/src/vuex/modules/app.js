
const app = {
	state: {
		diction:[],
		serverTypeOptions:[],
		dbTypeOptions:[],
		onlineDays:'',
		testVersions:'',
		osTypeOptions:'',
	},
	mutations:{
		SERVERTYPE: (state,message) => {
			state.serverTypeOptions = message;
			// console.log('message-SERVERTYPE', state, message);
		},
		DBTYPE: (state,message) => {
			state.dbTypeOptions=message;
			// console.log('message-DBTYPE', state,message);
		},
		ONLINEDAYS: (state,message) => {
			state.onlineDays=message;
			// console.log('message-DBTYPE', state,message);
		},
		TESTVERSIONS: (state,message) => {
			state.testVersions=message;
			// console.log('message-DBTYPE', state,message);
		},
		OSTYPE: (state,message) => {
			state.osTypeOptions=message;
		},

	},
	actions:{
		dbTypeEvent:({commit}) => {
			commit('DBTYPE')
		},
		serverTypeEvent: ({commit},view) => {
			console.log('view',view);
			commit('SERVERTYPE',view)
		},
		lineDayEvent: ({commit},view) => {
			console.log('view',view);
			commit('ONLINEDAYS',view)
		},
		versionEvent: ({commit},view) => {
			console.log('view',view);
			commit('TESTVERSIONS',view)
		},
		osTypeEvent: ({commit},view) => {
			console.log('view',view);
			commit('OSTYPE',view)
		},
	}

}

export default app


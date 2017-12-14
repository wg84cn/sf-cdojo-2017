import Login from './views/Login.vue'
import NotFound from './views/404.vue'
import Home from './views/Home.vue'
import Main from './views/Main.vue'


// 日常测试
const dailyTable = resolve => require(['./views/DailyTest/dailyTable.vue'], resolve)

//上线测试
const onLineTable = resolve => require(['./views/onLineTest/onLineTable.vue'], resolve)


let routes = [{
		path: '/login',
		component: Login,
		name: '',
		hidden: true
	},
	{
		path: '/404',
		component: NotFound,
		name: '',
		hidden: true
	},
	{
		path: '/',
		component: Home,
		name: '',
		iconCls: 'icon-home',
		leaf: true, //只有一个节点
		children: [{
				path: '/main',
				component: Main,
				name: '系统列表'
			}
		]
	},
	{
		path: '/',
		component: Home,
		name: '',
		iconCls: 'icon-daily',
		leaf: true, //只有一个节点
		children: [{
				path: '/daily',
				component: dailyTable,
				name: '日常测试结果'
			}
		]
	},
	{
		path: '/',
		component: Home,
		name: '',
		iconCls: 'icon-onLine',
		leaf: true, //只有一个节点
		children: [{
				path: '/onLine',
				component: onLineTable,
				name: '上线测试结果'
			}
		]
	},
	{
		path: '*',
		hidden: true,
		redirect: {
			path: '/404'
		}
	}
];

export default routes;
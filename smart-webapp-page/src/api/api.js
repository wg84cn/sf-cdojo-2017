import axios from 'axios';
import {
	MessageBox
} from 'element-ui'


var base2 = '';

if (process.env.NODE_ENV === 'development'){

	base2 = 'http://10.202.39.30:8080' // 测试环境

}

axios.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded';

axios.defaults.withCredentials=true;


axios.defaults.transformRequest.push(function (data, headers) {
	return data;
});
axios.defaults.transformResponse.push(function (data, headers) {
	if(headers.connection === "close") {
	}
	return data;
});
axios.interceptors.request.use(function (config) {
	return config;
}, function (error) {
	return Promise.reject(error);
});

// Add a response interceptor
axios.interceptors.response.use(function (response) {
	// Do something with response data
	var reg = /(http:\/\/[\w\.\-:]+)(?:\/|$)/;
	var originUrl =  response.config.url;
	var responseURL =  response.request.responseURL;

	var domainConfig = reg.test(originUrl);
	var domainRequest = reg.test(responseURL);

	if(!domainConfig && !domainRequest) {
			return response;
	} else {
			domainConfig = domainConfig ? domainConfig[1] : "";
			domainRequest = domainRequest ? domainRequest[1]: "";
			if(domainRequest && domainConfig && domainRequest !== domainConfig) {//redirect 网址，跳转对应网址页面

					MessageBox.confirm('session过期,请重新登录', '登录过期提醒', {
						confirmButtonText: '确定',
						showCancelButton: false,
						closeOnClickModal: false,
						closeOnPressEscape: false,
						type: 'warning'
					}).then(() => {
						window.location.href = responseURL;
						return false;
					})

			} else {
					if(originUrl !== responseURL && responseURL.match(/\/\/cas\.sit\.sf\-express\.com/) ) {
							window.location.href = responseURL;
							return false;
					} else {
							return response;
					}
			}

	}
}, function (error) {
	if(error.response === undefined) {
			window.location.reload();
	}
	return Promise.reject(error);
});


export const deleteFile = params => {
	return axios.post(`${base2}/system/delete.pvt`, params).then(res => res.data);
};

export const listAdvice = params => {
	return axios.post(`${base2}/asat/addAdvice.pub`, params).then(res => res.data);
};

//字典
export const getDictionaryList = params => {
	return axios.get(`${base2}/dictionaryManage/getDictionaryList.pvt?t=`+Date.now(), {
		params: params
	});
};


// 判断是否第一次登录
export const isFirstLogin = params => {
	return axios.get(`${base2}/asat/portal/isFirstLogin.pvt?t=` + Date.now(), {
		params: params
	});
};


// 登录
export const login = params => {
	return axios.post(`${base2}/asat/portal/login.pvt`, params).then(res => res.data);
};

// 个人信息
export const sapPersonInfo = params => {
	return axios.get(`${base2}/asat/portal/sapPersonInfo.pvt?t=`+Date.now(), {
		params: params
	});
};

export const downloadManual = `${base2}/asat/portal/downloadManual.pvt`;

export const getORGNameByEmpNum = params => {
	return axios.get(`${base2}/asat/portal/getORGNameByEmpNum.pvt?t=`+Date.now(), {
		params: params
	});
};

// 主页列表
export const homePage = params => {
	return axios.get(`${base2}/system/findPage.pvt?t=`+Date.now(), {
		params: params
	});
};

export const saveSystemInfo = params => {
	return axios.post(`${base2}/system/saveSystemInfo.pvt`, params).then(res => res.data);
};

export const findDirector = params => {
	return axios.get(`${base2}/system/findOrgName.pvt?t=`+Date.now(), {
		params: params
	});
};

export const systemDet = params => {
	return axios.get(`${base2}/system/systemDetail.pvt?t=`+Date.now(), {
		params: params
	});
};

export const saveSystemInfoAndService = params => {
	return axios.post(`${base2}/service/saveService.pvt`, params).then(res => res.data);
};

export const isConnectionSSHServer = params => {
	return axios.post(`${base2}/service/connSSHServer.pvt`, params).then(res => res.data);
};


export const isConnectionDB = params => {
	return axios.post(`${base2}/service/connDB.pvt`, params).then(res => res.data);
};


export const testSSHAndDBBySites = params => {
	return axios.post(`${base2}/service/testSSHAndDBByServices.pvt`, params).then(res => res.data);
};

export const getSystemInfoDetail = params => {
	return axios.get(`${base2}/system/getSystemInfoDetail.pvt?t=`+Date.now(), {
		params: params
	});
};

export const updateSystemInfoAndSite = params => {
	return axios.post(`${base2}/system/updateSystemInfoAndSite.pvt`, params).then(res => res.data);
};

export const inputSapPersonInfo = params => {
	return axios.get(`${base2}/system/findPersonByCode.pvt?t=`+Date.now(), {
		params: params
	});
};

export const findPersonsByParam = params => {
	return axios.get(`${base2}/localUser/findPersonsByParam.pvt?t=`+Date.now(), {
		params: params
	});
};

// 日常校验cookie
export const checkCookie = params => {
	return axios.post(`${base2}/task/checkCookie.pvt`, params).then(res => res.data);
};

// 上线校验系统版本和系统上线日期
export const checkVersionAndOnlineDate = params => {
	return axios.get(`${base2}/task/checkVersionAndOnlineDate.pvt?t=`+Date.now(), {
		params: params
	});
};

export const getSiteInfoList = params => {
	return axios.get(`${base2}/service/getServiceInfoList.pvt?t=`+Date.now(), {
		params: params
	});
};

export const checkOnLineCookie = params => {
	return axios.post(`${base2}/task/checkCookies.pvt `, params).then(res => res.data);
};

// 日常测试
export const list = params => {
	return axios.get(`${base2}/task/list.pvt?t=`+Date.now(), {
		params: params
	});
};

export const taskDetail = params => {
	return axios.get(`${base2}/task/taskDetail.pvt?t=`+Date.now(), {
		params: params
	});
};

export const downloadTaskDetail = `${base2}/task/downloadTaskDetail.pvt?taskId=`;

export const downloadFile = `${base2}/task/downloadFile.pvt?id=`;

export const download = `${base2}/system/download.pvt?fileId=`;

export const uploadFile = `${base2}/system/upload.pvt`;


// 上线测试
export const onlineTestResult = params => {
	return axios.get(`${base2}/task/onlineTestResult.pvt?t=`+Date.now(), {
		params: params
	});
};

export const taskOnlineDetail = params => {
	return axios.post(`${base2}/task/taskOnlineDetail.pvt`, params).then(res => res.data);
};


export const downloadOnlineTaskDetail = `${base2}/task/downloadOnlineTaskDetail.pvt?taskSystemId=`;

export const progress = params => {
	return axios.get(`${base2}/task/progress.pvt?t=`+Date.now(), {
		params: params
	});
};

// 提交回归测试
export const submitRegressionTest = params => {
	return axios.post(`${base2}/task/submitRegressionTest.pvt`, params).then(res => res.data);
};

// 所有可扫描项信息
export const hasTask = params => {
	return axios.post(`${base2}/test/hasTask.pvt`, params).then(res => res.data);
};

export const allScanItem = params => {
	return axios.post(`${base2}/test/allScanItem.pvt`, params).then(res => res.data);
};

export const starting = params => {
	return axios.post(`${base2}/test/starting.pvt`, params).then(res => res.data);
};

export const dailyStarting = params => {
	return axios.post(`${base2}/test/dailyStarting.pvt`, params).then(res => res.data);
};

export const onlineStarting = params => {
	return axios.post(`${base2}/test/onlineStarting.pvt`, params).then(res => res.data);
};


// 系统详情修改信息接口
export const updateSystemInfo = params => {
	return axios.post(`${base2}/system/updateSystemInfo.pvt`, params).then(res => res.data);
};

export const updateSytemRole = params => {
	return axios.post(`${base2}/system/updateSystemRole.pvt`, params).then(res => res.data);
};

export const updateServiceInfo = params => {
	return axios.post(`${base2}/service/updateServiceInfo.pvt`, params).then(res => res.data);
};

export const updateServiceServers = params => {
	return axios.post(`${base2}/service/updateServiceServers.pvt`, params).then(res => res.data);
};

export const updateServiceDB = params => {
	return axios.post(`${base2}/service/updateServiceDB.pvt`, params).then(res => res.data);
};


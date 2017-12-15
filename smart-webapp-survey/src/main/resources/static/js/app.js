/**
 * 演示程序当前的 “注册/登录” 等操作，是基于 “本地存储” 完成的
 * 当您要参考这个演示程序进行相关 app 的开发时，
 * 请注意将相关方法调整成 “基于服务端Service” 的实现。
 **/
(function($, owner) {
	/**
	 * 用户登录
	 **/
	owner.login = function(loginInfo, callback) {
		callback = callback || $.noop;
		loginInfo = loginInfo || {};
		loginInfo.account = loginInfo.account || '';
		loginInfo.password = loginInfo.password || '';
		if (loginInfo.account.length < 5) {
			return callback('账号最短为 5 个字符');
		}
		if (loginInfo.password.length < 6) {
			return callback('密码最短为 6 个字符');
		}
		var users = JSON.parse(localStorage.getItem('$users') || '[]');
		var authed = users.some(function(user) {
			return loginInfo.account == user.account && loginInfo.password == user.password;
		});
		if (authed) {
			return owner.createState(loginInfo.account, callback);
		} else {
			return callback('用户名或密码错误');
		}
	};

	owner.createState = function(name, callback) {
		var state = owner.getState();
		state.account = name;
		state.token = "token123456789";
		owner.setState(state);
		return callback();
	};

	/**
	 * 新用户注册
	 **/
	owner.reg = function(regInfo, callback) {
		callback = callback || $.noop;
		regInfo = regInfo || {};
		regInfo.account = regInfo.account || '';
		regInfo.password = regInfo.password || '';
		if (regInfo.account.length < 5) {
			return callback('用户名最短需要 5 个字符');
		}
		if (regInfo.password.length < 6) {
			return callback('密码最短需要 6 个字符');
		}
		if (!checkEmail(regInfo.email)) {
			return callback('邮箱地址不合法');
		}
		var users = JSON.parse(localStorage.getItem('$users') || '[]');
		users.push(regInfo);
		localStorage.setItem('$users', JSON.stringify(users));
		return callback();
	};

	/**
	 * 获取当前状态
	 **/
	owner.getState = function() {
		var stateText = localStorage.getItem('$state') || "{}";
		return JSON.parse(stateText);
	};

	/**
	 * 设置当前状态
	 **/
	owner.setState = function(state) {
		state = state || {};
		localStorage.setItem('$state', JSON.stringify(state));
		//var settings = owner.getSettings();
		//settings.gestures = '';
		//owner.setSettings(settings);
	};

	var checkEmail = function(email) {
		email = email || '';
		return (email.length > 3 && email.indexOf('@') > -1);
	};

	/**
	 * 找回密码
	 **/
	owner.forgetPassword = function(email, callback) {
		callback = callback || $.noop;
		if (!checkEmail(email)) {
			return callback('邮箱地址不合法');
		}
		return callback(null, '新的随机密码已经发送到您的邮箱，请查收邮件。');
	};

	/**
	 * 获取应用本地配置
	 **/
	owner.setSettings = function(settings) {
		settings = settings || {};
		localStorage.setItem('$settings', JSON.stringify(settings));
	}

	/**
	 * 设置应用本地配置
	 **/
	owner.getSettings = function() {
			var settingsText = localStorage.getItem('$settings') || "{}";
			return JSON.parse(settingsText);
		}
		/**
		 * 获取本地是否安装客户端
		 **/
	owner.isInstalled = function(id) {
		if (id === 'qihoo' && mui.os.plus) {
			return true;
		}
		if (mui.os.android) {
			var main = plus.android.runtimeMainActivity();
			var packageManager = main.getPackageManager();
			var PackageManager = plus.android.importClass(packageManager)
			var packageName = {
				"qq": "com.tencent.mobileqq",
				"weixin": "com.tencent.mm",
				"sinaweibo": "com.sina.weibo"
			}
			try {
				return packageManager.getPackageInfo(packageName[id], PackageManager.GET_ACTIVITIES);
			} catch (e) {}
		} else {
			switch (id) {
				case "qq":
					var TencentOAuth = plus.ios.import("TencentOAuth");
					return TencentOAuth.iphoneQQInstalled();
				case "weixin":
					var WXApi = plus.ios.import("WXApi");
					return WXApi.isWXAppInstalled()
				case "sinaweibo":
					var SinaAPI = plus.ios.import("WeiboSDK");
					return SinaAPI.isWeiboAppInstalled()
				default:
					break;
			}
		}
	}
}(mui, window.app = {}));


var common = {};

//用于调整时间输出
common.adjustTime = function (time, type) {
    type = type || "YYYY-MM-DD";
    var ckTime = this.checkTime(time);//转为毫秒
    if (!ckTime) {
        return "";
    }
    ckTime = this._output(ckTime, type);
    return (ckTime);
};
//转为毫秒
common.checkTime = function (time) {
	/* var ret = "";
	 var temp;
	 if (typeof time === "string" || typeof time === "number") {
	 temp = parseInt(time);
	 if (isNaN(temp)) {
	 ret = false;
	 } else {
	 ret = (+new Date(time));
	 }
	 } else if (typeof time === "object" && time.constructor === Date) {
	 temp = (+new Date(time));
	 if (String(temp) === "Invalid Date") {
	 ret = false;
	 } else {
	 ret = temp;
	 }
	 } else {
	 ret = false;
	 }

	 if (!ret) {
	 console.warn("时间格式出问题", time);
	 }
	 return ret;*/
    if(String( new Date(time) ) !== "Invalid Date") {
        return +new Date(time);
    } else if(String( new Date(+time) ) !== "Invalid Date"){
        return +new Date(+time);
    } else {
        console.warn("时间格式出问题", time);
        return false;
    }
};
//用于调整时间输出
common.shiftTime = function (time, shifted, type) {
    if(typeof shifted === "String") {
        console.error("时间位移功能，不能支持位移参数：", shifted);
    }
    var ckTime = this.checkTime(time);//转为毫秒
    if (!ckTime) {
        return "";
    }
    var handleTime = this.adjustTime(time, "YYYY-MM-DD");
    shifted = shifted || "";
    type = type || "YYYY-MM-DD";
    var dayMap = [{
        str: "([-+])(\\d+)[Mm]",
        func: function (time, month, isPlus) {
            //调整时间量
            month = +month;
            var connect = isPlus ?  "+" : "-";
            var m = +common.adjustTime( time, "MM");
            var y = +common.adjustTime( time, "YYYY");
            var d = +common.adjustTime( time, "DD");
            var maxMonth = 12;
            y += Math.ceil( ( eval(m + connect +month) ) / maxMonth - 1 );//todo: wrong
            if(eval(m + connect +month) > maxMonth) {
                m = eval(m + connect + month) % maxMonth || maxMonth;
            } else {
                var remain = eval(m + connect + month);
                while(remain < 0 ) {
                    remain += maxMonth;
                }
                m = remain || maxMonth;
            }
            return common.adjustTime( (y + "-" + m + "-" + d), type);
        }
    }, {
        str: "([-+])(\\d+)[Dd]",
        func: function (time, day, isPlus) {
            //调整时间量
            day = +day;
            var connect = isPlus ?  "+" : "-";
            var m = +common.adjustTime( time, "MM");
            var y = +common.adjustTime( time, "YYYY");
            var d = +common.adjustTime( time, "DD");
            var maxMonth = 12;
            var getDayNumByMon = function (year, month) {
                var allDays = [31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31]
                function isLeapYear(year) {  return (year % 4 == 0) && (year % 100 != 0 || year % 400 == 0);  }
                if(isLeapYear(year) && month === 2) {
                    return 29;
                } else {
                    return allDays[month - 1]
                }
            };
            if(isPlus) {
                d += day;
                if(d <= getDayNumByMon(y, m)) {
                    return common.adjustTime( (y + "-" + m + "-" + d), type);
                }
                while(d > getDayNumByMon(y, m)) {
                    if(getDayNumByMon(y, m) - d <= 0) {
                        d -= getDayNumByMon(y, m);
                        m++;
                        if(m > maxMonth) {
                            m = 1;
                            y++;
                        }
                    } else {
                        break;
                    }
                }
                return common.adjustTime( (y + "-" + m + "-" + d), type);
            } else {
                d -= day;
                if(d > 0) {
                    return common.adjustTime( (y + "-" + m + "-" + d), type);
                }
                while(d <= 0) {
                    m--;
                    if(m <= 0) {
                        m = maxMonth;
                        y--;
                    }
                    d += getDayNumByMon(y, m);
                }
                return common.adjustTime( (y + "-" + m + "-" + d), type);
            }
        }
    }];
    var realTime = null;
    dayMap.some(function (t) {
        var reg = new RegExp(t.str);
        var ret = shifted.match(reg);
        if(ret) {
            realTime = t.func(handleTime, ret[2], ret[1] === "+" ? true : false);
            return true;
        }
    });

    return (realTime);
};
//将毫秒输出为指定格式 YYYY-MM-DD hh:mm:ss
common._output = function (time, type) {
    time = new Date(time);
    var o = {
        "M+": time.getMonth() + 1,                 //月份
        "D+": time.getDate(),                    //日
        "h+": time.getHours(),                   //小时
        "m+": time.getMinutes(),                 //分
        "s+": time.getSeconds(),                 //秒
        "q+": Math.floor((time.getMonth() + 3) / 3), //季度
        "S": time.getMilliseconds()             //毫秒
    };
    var day = ["星期一", "星期二", "星期三", "星期四", "星期五", "星期六", "星期日"];

    if (/(day)/.test(type)) {
        type = type.replace(RegExp.$1, (day[time.getDay() - 1]));
    }
    if (/(Y+)/.test(type)) {
        type = type.replace(RegExp.$1, (time.getFullYear() + "").substr(4 - RegExp.$1.length));
    }
    for (var k in o) {
        if (new RegExp("(" + k + ")").test(type)) {
            type = type.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
        }
    }
    return type;
};
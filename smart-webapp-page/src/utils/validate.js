

//验证url是否正确，true/false
export function url(url) {
  return (/(http|ftp|https):\/\/[\w\-_]+(\.[\w\-_]+)+([\w\-\.,@?^=%&:/~\+#]*[\w\-\@?^=%&/~\+#])?/i).test(url)
}

//验证手机号码是否正确， true/false
export function tel(tel) {
  return (/^1[3|4|5|8][0-9]\d{4,8}$/).test(tel)
}

//验证邮箱是否正确， true/false
export function eamil(tel) {
    return (/^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/).test(tel)
}

//验证身份证是否正确， true/false
export function identityCard(cardNum) {
    return (/^(^[1-9]\d{7}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}$)|(^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])((\d{4})|\d{3}[Xx])$)$/).test(cardNum)
}

// 验证密码 大小写和数字，8位及以上。
export function passWord(pwd) {
    return (/(?![0-9A-Z]+$)(?![0-9a-z]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,20}$/).test(pwd)
}

//URI 解码
export function decode(value) {
  return decodeURIComponent(value);
}

//URI 编码
export function encode(value) {
  return encodeURIComponent(value)
}

//判断是否是object对象
export function isObject(value) {
  return !!value && Object.prototype.toString.call(value) === '[object Object]';
}

//判断是否是数组
export function isArray(value) {
  return Object.prototype.toString.call(value) === '[object Array]';
}

// 千位分隔符
export default function (value) {
    return value.toString().replace(/\B(?=(\d{3})+$)/g,',');
}

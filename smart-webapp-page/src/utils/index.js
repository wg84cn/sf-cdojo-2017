/**
 * 
 * @param {*string} time 
 * @param {*string} cFormat 
 */
export function parseTime(time, cFormat) {
  if (arguments.length === 0) {
    return null
  }
  const format = cFormat || '{y}-{m}-{d} {h}:{i}:{s}'
  let date
  if (typeof time === 'object') {
    date = time
  } else {
    if (('' + time).length === 10) time = parseInt(time) * 1000
    date = new Date(time)
  }
  const formatObj = {
    y: date.getFullYear(),
    m: date.getMonth() + 1,
    d: date.getDate(),
    h: date.getHours(),
    i: date.getMinutes(),
    s: date.getSeconds(),
    a: date.getDay()
  }
  const time_str = format.replace(/{(y|m|d|h|i|s|a)+}/g, (result, key) => {
    let value = formatObj[key]
    if (key === 'a') return ['一', '二', '三', '四', '五', '六', '日'][value - 1]
    if (result.length > 0 && value < 10) {
      value = '0' + value
    }
    return value || 0
  })
  return time_str
}

export function _throttle(fn, time) {

  let _self = fn,
    timer,
    firstTime = true //记录是否是第一次执行的flag

  return function () {
    let args = arguments, //解决闭包传参问题
      _me = this //解决上下文丢失问题

    if (firstTime) { //若是第一次，则直接执行
      _self.apply(_me, args)
      return firstTime = false
    }
    if (timer) { //定时器存在，说明有事件监听器在执行，直接返回
      return false
    }

    timer = setTimeout(function () {
      clearTimeout(timer)
      timer = null
      _self.apply(_me, args)
    }, time || 500)
  }

}

export function debounce(func, wait, immediate) {
  let timeout, args, context, timestamp, result

  const later = function () {

    const last = +new Date() - timestamp

    if (last < wait && last > 0) {
      timeout = setTimeout(later, wait - last)
    } else {
      timeout = null
      if (!immediate) {
        result = func.apply(context, args)
        if (!timeout) context = args = null
      }
    }
  }

  return function (...args) {
    context = this
    timestamp = +new Date()
    const callNow = immediate && !timeout

    if (!timeout) timeout = setTimeout(later, wait)
    if (callNow) {
      result = func.apply(context, args)
      context = args = null
    }

    return result
  }
}

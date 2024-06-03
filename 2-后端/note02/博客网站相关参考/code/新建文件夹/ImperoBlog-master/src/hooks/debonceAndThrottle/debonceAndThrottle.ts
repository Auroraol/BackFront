// 防抖动h函数  参数 方法 时间
// 1. func: 一个函数，表示需要执行的函数。
// 2. wait: 一个数字，表示延迟执行的时间间隔（毫秒）
export const debonce = (func: Function, wait: number) => {
    let timer: any;
    return (...args: any[]) => {
        clearTimeout(timer)
        timer = setTimeout(() => {
            func(...args);
        }, wait);
    }
}

export const throttle = (func: Function, wait: number) => {
    let timer;
    return (...args: any[]) => {
        timer = setTimeout(() => {
            func(...args)
            timer = null
        }, wait);
    }
}
import axios from 'axios'
import router from '../router'

var request = axios.create({
    baseURL: window._apiUrl.serverUrl,
    timeout: 10000,
    headers: {
        'Content-Type': 'application/json'
    }
})

var formRequest = axios.create({
    baseURL: window._apiUrl.serverUrl,
    timeout: 10000,
    headers: {
        'Content-Type': 'multipart/form-data'
    }
})

export function get(url, params) {
    request.defaults.headers.Authorization = localStorage.getItem('hhl-token');
    return new Promise((resolve, reject) => {
        request.get(url, {
            params: params
        }).then(res => {
            resolve(res.data);
        }).catch(err => {
            console.log("error:",err)
            reject(err);
        })
    })
}

export function rest(url, params) {
    request.defaults.headers.Authorization = localStorage.getItem('hhl-token');
    return new Promise((resolve, reject) => {
        request.get(url+'/'+params).then(res => {
            resolve(res.data);
        }).catch(err => {
            reject(err);
        })
    })
}

export function post(url, params) {
    request.defaults.headers.Authorization = localStorage.getItem('hhl-token');
    return new Promise((resolve, reject) => {
        request.post(url, JSON.stringify(params))
            .then(res => {
                resolve(res.data);
            }).catch(err => {
                reject(err);
            })
    })
}

export function form(url, formData) {
    formRequest.defaults.headers.Authorization = localStorage.getItem('hhl-token');
    return new Promise((resolve, reject) => {
        formRequest.post(url, formData).then(res => {
            localStorage.setItem("hhl-token",res.headers.token);
            resolve(res);
        }).catch(err => {
            reject(err)
        })
    })
}

request.interceptors.response.use(
    response => {
        console.log(response.data.code)
        if (response.data.code == 401) {
            console.log(router)
            router.push('/about')
        }
        if (response.data.code == 403) {
            // let option = {
            //     type: 'error',
            //     message: '您不具备访问该页面的权限，如需操作，请联系管理员为您分配权限！'
            // }
            // Message(option)
        }
        return response;
    }
)
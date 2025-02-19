import axios from 'axios'
import { ElMessage } from 'element-plus';

 // 创建axios实例
 const axiosInstance = axios.create({
     // 超时(默认300000 = 30秒)
     timeout: 300000,
     // 设置Content-Type，规定了前后端的交互使用json
     headers: {'Content-Type': 'application/json;charset=utf-8'}
 })

 axiosInstance.defaults.baseURL = 'http://localhost:8080/'
  
 // 请求拦截器
 axiosInstance.interceptors.request.use(function (config) {
    // 发送请求的相关逻辑
    //获取客户端的token
    let token = $cookies.get("token"); 
    // 判断token存在再做配置
    if (token) {
      config.headers['Authorization'] = 'Bearer ' + token
    }
    return config
  }, function (error) {
    return Promise.reject(error)
  })


  // 响应拦截器
  axiosInstance.interceptors.response.use(
  response => {
    console.log("响应到的数据：",response.data)
    //超出接口频率限制
    if(response.data.code == "437"){
      ElMessage.error('你的手速太快了，请稍后重试');
      return new Promise(() => {}); 
    }
    // if(response)
    return response;
  },
  error => {
    // 对响应错误进行处理
    // 可以在这里对请求失败、网络错误等进行统一处理
    // return Promise.reject(error);
  }
);

export default axiosInstance

// if (err.response.status == 405) {
//   ElMessage("身份已失效，请重新登录")
//   //跳转页面
//   this.$router.push({
//       path: "/login",
//   })

//   return
// }
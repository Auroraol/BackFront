# 总结

+ async  —> 直接定义promise

+ await —>   等待方法(一般也是promise)执行完 返回结果 这时候只用接收 就好了

+ .then —>  也是等待方法执行完 只是 await 返回的直接是成功数据(错误数据用catch捕获)  .then返回的是个方法 方法里面带有 数据

# async函数返回resolve和reject

在 `async` 函数中，不需要显式地调用 `resolve` 或 `reject`，因为 `async` 函数会自动处理它们。

**返回 “解决” 和 “拒绝” 的值的方法：**

1. **解决 (resolve)**:

   - 在 `async` 函数中直接返回一个值，这会自动将该值包装为一个解决的 `Promise`。

   ```javascript
   async function resolveExample() {
       return "Resolved value";  // 这等价于 Promise.resolve("Resolved value")
   }
   ```

2. **拒绝 (reject)**:

   - 在 `async` 函数中抛出一个错误，这会自动将该错误包装为一个拒绝的 `Promise`。

   ```javascript
   async function rejectExample() {
       throw new Error("Rejected value");  
       //一、 等价于 Promise.reject(new Error("Rejected value")) 
       //二、 try...catch中catch (error) { throw error;)
   }
   ```

**使用这些函数：**

```js
resolveExample()
    .then(value => {
        console.log(value);  // 输出: "Resolved value"
    });

rejectExample()
    .catch(error => {
        console.log(error.message);  // 输出: "Rejected value"
    });
```

# 使用`try...catch...finally`

> await抛出异常或者返回状态变为“rejected”后，await下面的代码不会继续执行了

1. **分开处理错误：** 如果你想针对不同的 `await` 语句执行不同的错误处理逻辑，可以将每个 `await` 语句放在单独的 `try...catch` 块中。

```js
try {
  const result1 = await asyncFunction1();    // 
  // 处理 result1
} catch (error) {
  // 处理 asyncFunction1 的错误
}


try {
  const result2 = await asyncFunction2();
  // 如果还行继续抛
  return //等价  return Promise.resolve(); 

  // 处理 result2
} catch (error) {
  // 处理 asyncFunction2 的错误
    
  //如果还行继续抛  
  throw error; // 等价return Promise.reject(error) 
  
}
```

1. **统一处理错误：** 如果你想统一处理所有 `await` 语句可能抛出的错误，可以在整个 `try` 块中执行统一的错误处理逻辑。

```js
try {
  const result1 = await asyncFunction1();
  const result2 = await asyncFunction2();
  // 继续处理其他结果
} catch (error) {
  // 统一处理所有错误
}
```

**例子**

```js
const confirm = async (e) => {
    const eTarget = e.currentTarget;
    try {
      await basicFormRef.value.submitForm();
      showDisabled(eTarget);
      if (title.value === '新增') {
        await createCallback(formData);
      } else if (title.value === '修改') {
        await updateCallback(formData);
      }
      ElMessage.success(`${message[title.value]}`);
      close();
      emit('searchData');
    } catch (error) {
      console.error(error);
    } finally {
      hiddenDisabled(eTarget);
    }
    console.log('我仍然可以执行');
};

```

# 应用: 封装 axios.ts

![image-20240401223739973](async&await.assets/image-20240401223739973.png)

```ts
import axios, { AxiosRequestConfig, AxiosResponse } from "axios";
import { getAccessToken } from "/@/utils/auth";
import { ElMessageBox, ElMessage } from "element-plus";

const axiosInstance = axios.create({
  timeout: 10000,
  // axios中请求配置有baseURL选项,表示请求URL公共部分,每个请求将会带该部分
  // baseURL: import.meta.env.VITE_API_URL,//配置了跨域这里不用写会冲突
});

// 请求拦截
axiosInstance.interceptors.request.use(
  (config) => {
    return config;
  },
  (error) => {
    // 当请求失败时做一些处理 抛出错误
    console.log(error);
    return Promise.reject(error);
  }
);

// 响应拦截器
axiosInstance.interceptors.response.use(
  (response) => {
    const res = response.data;   // 响应数据
    // 确保前端得到的res都是成功响应
    if (res.code !== 200000) {
      // 凭证无效或过期
      if (res.code === 400007 || res.code === 4000010) {
        // useStore.dispatch('user/resetToken')
        ElMessageBox.confirm("登录信息已过期", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        })
          .then(() => {
            location.reload();
          })
          .catch(() => {
            location.reload();
          });
      } else {
        // 其他, 返回失败message
        ElMessage({
          message: res.message || "Error",
          type: "error",
          duration: 5 * 1000,
        });
      }
      return Promise.reject(new Error(res.message || "Error"));
    } else {
      return res;
    }
  },
  (error) => {
    console.log("err" + error);
    ElMessage({
      message: error.message,
      type: "error",
      duration: 5 * 1000,
    });
    return Promise.reject(error);
  }
);

// 给请求头添加 access_token
const setHeaderToken = (isNeedToken: boolean) => {
  // 请求头携带token
  const accessToken = isNeedToken ? getAccessToken() : null;
  if (isNeedToken) {
    // api 请求需要携带 access_token
    if (!accessToken) {
      console.log("不存在 access_token 则跳转回登录页");
    }
    // instance.defaults.headers.common['accessToken'] 中的accessToken叫啥由后端决定
    axiosInstance.defaults.headers.common[
      "Authorization"
    ] = `Bearer ${accessToken}`;
  }
};

// 定义一个泛型函数 request，用于发送 HTTP 请求
const request = <ResponseType = unknown>(
  url: string,
  options?: AxiosRequestConfig<unknown>,
  isNeedToken: boolean = false // 默认不需要 token
): Promise<ResponseType> => {
  // 返回一个 Promise 对象，Promise 的泛型参数是 ResponseType，即期望的响应数据类型
  return new Promise((resolve, reject) => {
    // 使用 axiosInstance 发送 HTTP 请求
    setHeaderToken(isNeedToken);
    axiosInstance({
      url,
      ...options, // 将传入的 options 合并到请求配置中
    })
      .then(
        res => {
        // 请求成功时，将解析后的响应数据传递给 Promise 的 resolve 函数
        //这里的res得到响应数据  而res.data得到的是响应数据中的data属性数据
        resolve(res.data as ResponseType);
        //console.log(res.data);
        // if (res.data && res.data.code === 200000) {
        //   resolve(res.data);
        // } else {
        //   reject(new Error('Invalid response format'));
        // }

      })
      .catch((err) => {
        // 请求失败时，将错误信息传递给 Promise 的 reject 函数
        reject(err);
      });
  });
};

// 有些 api 并不需要用户授权使用，则无需携带 access_token；默认不携带，需要传则设置第三个参数为 true
const get = (url, params = {}, isNeedToken = false) => {
  setHeaderToken(isNeedToken);
  return axiosInstance({
    method: "get",
    url,
    params,
  });
};

const post = (url, params = {}, isNeedToken = false) => {
  setHeaderToken(isNeedToken);
  return axiosInstance({
    method: "post",
    url,
    data: params,
  });
};

export { axiosInstance, request, get, post };

/*
注: 上述封装抛出的是响应数据中的data属性

使用
①axiosInstance
和axios使用一致

②request
// 定义数据响应的类型, 不定义则自动匹配,就没有自动补全提示
interface DataResponseType {
  // 在这里定义响应数据的结构
}

// 可选的 Axios 请求配置
const options: AxiosRequestConfig = {
  method: "GET", // 请求方法，例如 GET、POST 等
  params: { // 请求参数，可以是 params、data 等
    // 具体参数
  },
  // 其他 Axios 请求配置，例如 headers、timeout 等
};

// 发送请求并指定响应数据的类型
request<DataResponseType>(url, options, isNeedToken)
  .then((data) => {
    // 请求成功时，处理返回的数据
    console.log("响应数据:", data);
  })
  .catch((error) => {
    // 请求失败时，处理错误信息
    console.error("请求失败:", error);
  });

③get
get(url, params, isNeedToken)
  .then(response => {
    // 处理请求成功的响应
    console.log('GET 请求成功:', response);
  })
  .catch(error => {
    // 处理请求失败
    console.error('GET 请求失败:', error);
  });

④post
post(url, data, isNeedToken)
  .then(response => {
    // 处理请求成功的响应
    console.log('POST 请求成功:', response);
  })
  .catch(error => {
    // 处理请求失败
    console.error('POST 请求失败:', error);
  });
*/

```




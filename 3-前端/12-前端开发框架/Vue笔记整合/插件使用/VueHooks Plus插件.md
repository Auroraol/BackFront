官网:  [✨ 特性 | VueHook Plus (gitee.io)](https://inhiblab-core.gitee.io/docs/hooks/guide/)

# [useRequest](https://inhiblab-core.gitee.io/docs/hooks/useRequest/basic/#userequest-基础用法)

介绍 `useRequest` 最核心，最基础的能力。

## API

```ts
const {
  loading: Readonly<Ref<boolean>>,
  data?: Readonly<Ref<TData>>,
  error?: Readonly<Ref<Error>>,
  params: Readonly<Ref<TParams | []>>,
  run: (...params: TParams) => void,
  runAsync: (...params: TParams) => Promise<TData>,
  refresh: () => void,
  refreshAsync: () => Promise<TData>,
  mutate: (data?: TData | ((oldData?: TData) => (TData | undefined))) => void,
  cancel: () => void,
} = useRequest<TData, TParams>(
  service: (...args: TParams) => Promise<TData>,
  {
    manual?: boolean,
    defaultParams?: TParams,
    formatResult?: (response: TData) => unknown,
    onBefore?: (params: TParams) => void,
    onSuccess?: (data: TData, params: TParams) => void,
    onError?: (e: Error, params: TParams) => void,
    onFinally?: (params: TParams, data?: TData, e?: Error) => void,
  }
);
```

### 返回值

| 参数         | 说明                                                         | 类型                                                         |
| ------------ | ------------------------------------------------------------ | ------------------------------------------------------------ |
| data         | service 返回的数据/ 一个只读的 `Ref`，表示请求成功后返回的数据 | `Readonly<Ref<TData>>` | `undefined`                         |
| error        | service 抛出的异常/ 一个只读的 `Ref`，表示请求失败时的错误信息 | `Readonly<Ref<Error>>` | `undefined`                         |
| loading      | service 是否正在执行/ 一个只读的 `Ref`，表示请求是否正在进行中 | `Readonly<>Ref<boolean>`                                     |
| params       | 当次执行的 service 的参数数组/ 一个只读的 `Ref`，表示当前请求的参数。比如你触发了 `run(1, 2, 3)`，则 params 等于 `[1, 2, 3]` | `Readonly<Ref<TParams | []>>`                                |
| run          | 手动触发 service 执行，参数会传递给 service。异常自动处理，通过 `onError` 反馈 /用于手动触发请求 | `(...params: TParams) => void`                               |
| runAsync     | 与 `run` 用法一致，但返回的是 Promise，需要自行处理异常。/一个异步方法，用于手动触发请求并返回一个 Promise | `(...params: TParams) => Promise<TData>`                     |
| refresh      | 使用上一次的 params，重新调用 `run`/  一个方法，用于重新发起请求 | `() => void`                                                 |
| refreshAsync | 使用上一次的 params，重新调用 `runAsync`/ 一个异步方法，用于重新发起请求并返回一个 Promise | `() => Promise<TData>`                                       |
| mutate       | 直接修改 `data`/一个方法，用于手动更新数据或进行数据变更操作 | `(data?: TData | ((oldData?: TData) => (TData | undefined))) => void` |
| cancel       | 取消当前正在进行的请求/一个方法，用于取消当前的请求          | `() => void`                                                 |

### 必选参数

| 参数    | 说明                                                         | 类型                                  |
| :------ | ------------------------------------------------------------ | ------------------------------------- |
| service | `service` 是一个函数，它接受类型为 `TParams` 的参数，并返回一个 `Promise`，该 `Promise` 的解决值的类型为 `TData`。 | (...args: TParams) => Promise<TData>, |

### 可选参数

| 参数          | 说明                                                         | 类型                                                 | 默认值  |
| ------------- | ------------------------------------------------------------ | ---------------------------------------------------- | ------- |
| initialData   | 初始化的数据                                                 | `TData` | `undefined`                                |         |
| manual        | 默认 `false`。 即在初始化时自动执行 service。如果设置为 `true`，则需要手动调用 `run` 或 `runAsync` 触发执行。 | `boolean`                                            | `false` |
| defaultParams | 首次默认执行时，传递给 service 的参数                        | `TParams`                                            | -       |
| formatResult  | 格式化请求结果，建议使用 `useFormatResult`                   | `(response: TData) => any`                           | -       |
| onBefore      | service 执行前触发                                           | `(params: TParams) => void`                          | -       |
| onSuccess     | service resolve 时触发                                       | `(data: TData, params: TParams) => void`             | -       |
| onError       | service reject 时触发                                        | `(e: Error, params: TParams) => void`                | -       |
| onFinally     | service 执行完成时触发                                       | `(params: TParams, data?: TData, e?: Error) => void` |         |

## 使用

### 自动触发

#### 基础知识

默认情况下，`useRequest` 第一个参数是一个异步函数，在组件初始化时，会自动执行该异步函数。同时自动管理该异步函数的 `loading` , `data` , `error` 等状态。

```
const { data, error, loading } = useRequest(service)
```

默认发送获取请求

```ts
<template>
  <div>name：{{ loading ? 'loading' : data }}</div>
  {{ data }}
</template>

<script lang="ts" setup>
  import { useRequest } from 'vue-hooks-plus'

	// 参数 params: { desc: string }  表示params对象
  function getUsername(params: { desc: string }): Promise<string> {
    return new Promise(resolve => {
      setTimeout(() => {
        resolve(`vue-hooks-plus ${params.desc}`)
      }, 1000)
    })
  }

  let param: { desc: string } = { desc: 'good' };
  const { data, loading } = useRequest(() => getUsername(param),{
    devKey:'demo'  // 开发者密钥
  })
</script>
```

![image-20240104221929216](VueHooks%20Plus%E6%8F%92%E4%BB%B6.assets/image-20240104221929216.png)

#### **例子**

**data.d.t**

```ts
// 模块 TS 类型
// 写这个的目的是为了在编写代码时提供类型安全性、智能提示以及代码可读性。

// 定义单个对象的类型
export type PostType = {
  userId: number;
  id: number;
  title: string;
  body: string;
};

// 定义整个响应数据的类型
export type ApiResponse = PostType[];
```

**services.ts**

```tsx
// 模块 API
import { request } from '/@/utils/network/axios'
import { ApiResponse} from './data'

//
export async function getUserInfo(userId: string){
  return request<ApiResponse>('http://jsonplaceholder.typicode.com/posts', {
    params: {
      userId,
    },
  })
}


/* GET http://jsonplaceholder.typicode.com/posts?userId=2  
响应结果如下:
[
    {
        "userId": 2,
        "id": 11,
        "title": "et ea vero quia laudantium autem",
        "body": "delectus reiciendis molestiae occaecati non minima eveniet qui voluptatibus\naccusamus in eum beatae sit\nvel qui neque voluptates ut commodi qui incidunt\nut animi commodi"
    },
    {
        "userId": 2,
        "id": 12,
        "title": "in quibusdam tempore odit est dolorem",
        "body": "itaque id aut magnam\npraesentium quia et ea odit et ea voluptas et\nsapiente quia nihil amet occaecati quia id voluptatem\nincidunt ea est distinctio odio"
    },
    {
        "userId": 2,
        "id": 13,
        "title": "dolorum ut in voluptas mollitia et saepe quo animi",
        "body": "aut dicta possimus sint mollitia voluptas commodi quo doloremque\niste corrupti reiciendis voluptatem eius rerum\nsit cumque quod eligendi laborum minima\nperferendis recusandae assumenda consectetur porro architecto ipsum ipsam"
    },
    {
        "userId": 2,
        "id": 14,
        "title": "voluptatem eligendi optio",
        "body": "fuga et accusamus dolorum perferendis illo voluptas\nnon doloremque neque facere\nad qui dolorum molestiae beatae\nsed aut voluptas totam sit illum"
    },
]
*/
```

**test.vue**

```ts
<template>
  <div>
    <h1>Post Titles</h1>
    <ul>
      <li v-for="post in data">
        {{ post.title }}
        {{ post.body }}
        {{ post.id }}
        {{ post.userId }}
        <hr />
      </li>
      {{
        response
      }}
    </ul>
  </div>
</template>

<script lang="ts" setup>
import { ref } from "vue";
import { useRequest } from "vue-hooks-plus";
import { getUserInfo } from "./services";

const { data, error, loading } = useRequest(() => getUserInfo("2"));

//注意: 在自动触发的情况下, ts中不能使用data,error,...., 能直接在template使用
console.log(data.value?.at(0)?.title); //undefined
console.log(data.value?.at(0)?.body); //undefined
console.log(data.value?.at(0)?.id); //undefined
console.log(data.value?.at(0)?.userId); //undefined
</script>
```

![image-20240105082652593](VueHooks%20Plus%E6%8F%92%E4%BB%B6.assets/image-20240105082652593.png)

![image-20240105082806211](VueHooks%20Plus%E6%8F%92%E4%BB%B6.assets/image-20240105082806211.png)

### 手动触发

如果设置了 `options.manual = true`，则 `useRequest` 不会默认执行，需要通过 `run` 或者 `runAsync` 来触发执行。

```ts
const { loading, run, runAsync } = useRequest(service, {
  manual: true,
})
```

`run` 与 `runAsync` 的区别在于：

- `run` 是一个普通的同步函数，我们会自动捕获异常，你可以通过 `options.onError` 来处理异常时的行为。
- `runAsync` 是一个返回 `Promise` 的异步函数，如果使用 `runAsync` 来调用，则意味着你需要自己捕获异常。

#### run

run主动发送请求在这个例子中，我们通过 run 来修改用户名，通过 onSuccess 和 onError 来处理成功和失败。

```ts
<template>
  <div>
    name: {{ loading ? 'loading' : data }}
  </div>
  <div style="margin-top:8px">
    <input v-model="value">
    <vhp-button style="margin-left: 8px;" @click="run({ desc: value })">Edit</vhp-button>
  </div>
</template>

<script lang="ts" setup>
  import { ref } from 'vue'
  import { useRequest } from 'vue-hooks-plus'

  // 模拟一个异步请求函数
  function getUsername(params: { desc: string }): Promise<string> {
    return new Promise((resolve, reject) => {
      setTimeout(() => {
        if (Math.random() > 0.5) 
            resolve(`vue-hooks-plus ${params.desc}`);
        reject('error');
      }, 1000);
    });
  }

  // 使用 ref 创建响应式变量
  const value = ref('vue-hooks-plus');

  // 使用 useRequest Hook 进行异步请求状态管理
  const { data, loading, run } = useRequest(getUsername, {
    manual: true, // 手动触发请求
    devKey: "demo1", // 开发者密钥
    onSuccess: data => {
      alert(data);
    },
    onError: error => {
      alert(error);
    },
  });

  // 手动触发请求 调用语法: run(异步请求函数所需参数)
  // run({ desc: 'nice' })
</script>
```

<img src="VueHooks%20Plus%E6%8F%92%E4%BB%B6.assets/image-20240104222205956.png" alt="image-20240104222205956" style="zoom: 80%;" />

<img src="VueHooks%20Plus%E6%8F%92%E4%BB%B6.assets/image-20240104222248613.png" alt="image-20240104222248613" style="zoom: 67%;" />

#### runAsync

在这个例子中，我们通过 runAsync 来修改用户名，此时必须通过 catch 来自行处理异常。

```ts
<template>
  <div>
    name: {{ loading ? 'loading' : data }}
  </div>
  <div style="margin-top:8px">
    <input v-model="value">
    <vhp-button style="margin-left: 8px;" @click="handleClick">Edit</vhp-button>
  </div>
</template>

<script lang="ts" setup>
  import { ref } from 'vue'
  import { useRequest } from 'vue-hooks-plus'

  // 模拟一个异步请求函数
  function getUsername(params: { desc: string }): Promise<string> {
    return new Promise((resolve, reject) => {
      setTimeout(() => {
        if (Math.random() > 0.5) resolve(`vue-hooks-plus ${params.desc}`);
        reject('error');
      }, 1000);
    });
  }

  // 使用 ref 创建响应式变量
  const value = ref('vue-hooks-plus');

  // 使用 useRequest Hook 进行异步请求状态管理
  const { data, loading, runAsync } = useRequest(getUsername, {
    manual: true, // 手动触发请求
    devKey: "demo2", // 开发者密钥
  });

  // 点击按钮时的处理方法
  const handleClick = async () => {
    try {
      // 使用 runAsync 方法触发异步请求，并等待结果
      await runAsync({ desc: value.value });
      alert('success');
    } catch (error) {
      alert('error');
    }
  }

  // 手动触发请求 调用语法: runAsync(异步请求函数所需参数)
  // runAsync({ desc: 'nice' })
</script>
```

<img src="VueHooks%20Plus%E6%8F%92%E4%BB%B6.assets/image-20240104222205956.png" alt="image-20240104222205956" style="zoom:80%;" />

<img src="VueHooks%20Plus%E6%8F%92%E4%BB%B6.assets/image-20240104222348672.png" alt="image-20240104222348672" style="zoom:67%;" />

#### **例子**

**data.d.ts**

```tsx
// 模块 TS 类型
// 写这个的目的是为了在编写代码时提供类型安全性、智能提示以及代码可读性。  注意: 只有手动触发的才能补全

// 定义单个对象的类型
export type HitokotoType = {
  id: number;
  uuid: string;
  hitokoto: string;
  type: string;
  from: string;
  from_who: string | null;
  creator: string;
  creator_uid: number;
  reviewer: number;
  commit_from: string;
  created_at: string;
  length: number;
}
```

**services.ts**

```tsx
// 模块 API
import { request } from '/@/utils/network/axios'
import { HitokotoType } from './data'

export async function getUserInfo(c: string):Promise<HitokotoType>{
  return request<HitokotoType>('https://v1.hitokoto.cn/', {
    params: {
      c,
    },
  })
}

/*
/* GET https://v1.hitokoto.cn/?c=b  
响应结果如下:
{
    "id": 5963,
    "uuid": "b25ebe3d-d031-493c-a129-61ba3b7c0a23",
    "hitokoto": "此时此刻的咱啊，能和汝在一起，是最幸福的了。",
    "type": "b",
    "from": "狼与香辛料",
    "from_who": "支仓冻砂",
    "creator": "人活着就是为了贤狼赫萝",
    "creator_uid": 5896,
    "reviewer": 4756,
    "commit_from": "web",
    "created_at": "1586916630",
    "length": 22
}
*/

```

**test.vue**

```vue
<template>
  <div>
    {{data.hitokoto}}
  </div>
</template>

<script lang="ts" setup>
import { ref } from 'vue';
import { useRequest } from 'vue-hooks-plus';
import { getUserInfo} from './services';

const { data, error, loading, run } =  useRequest(getUserInfo,{
   manual: true, // 手动触发请求
    devKey: "demo1", // 开发者密钥
    onSuccess: data => {
      //注意: 在手动触发的情况下, ts中使用data,error,....中的属性
      alert(data.hitokoto); //能用 // 有自动补全提示
    },
    onError: error => {
      alert(error);
    },
});

run('b')
</script>
```



### 生命周期

`useRequest` 提供了以下几个生命周期配置项，供你在异步函数的不同阶段做一些处理。

- `onBefore`：请求之前触发
- `onSuccess`：请求成功触发
- `onError`：请求失败触发
- `onFinally`：请求完成触发

```ts
<template>
  <div>name：{{ loading ? 'loading' : data }}</div>
  <div style="margin-top:8px">
    <input v-model="value">
    <vhp-button style="margin-left: 8px;" @click="run({ desc: value })">Edit</vhp-button>
  </div>
  <div>
    <p v-for="msg in step" :key="msg">{{ msg }}</p>
  </div>
</template>

<script lang="ts" setup>
  import { ref } from 'vue'
  import { useRequest } from 'vue-hooks-plus'

  function getUsername(params: { desc: string }): Promise<string> {
    return new Promise((resolve, reject) => {
      setTimeout(() => {
        if (Math.random() > 0.5) resolve(`vue-hooks-plus ${params.desc}`)
        reject('error')
      }, 1000)
    })
  }
  const value = ref('')
  const step = ref<string[]>([])
  const { data: data, loading, run } = useRequest(getUsername, {
    manual: true,
    devKey:"demo3",
    onBefore: () => {
      step.value = []
      step.value.push('start request')
    },
    onSuccess: () => {
      step.value.push('start request success')
    },
    onError: () => {
      step.value.push('start request error')
    },
    onFinally: () => {
      step.value.push('start request finally')
    },
  })
</script>
```

### 刷新（重复上一次请求）

`useRequest` 提供了 `refresh` 和 `refreshAsync` 方法，使我们可以使用上一次的参数，重新发起请求。

假如在读取用户信息的场景中

1. 我们读取了 ID 为 1 的用户信息 `run(1)`
2. 我们通过某种手段更新了用户信息
3. 我们想重新发起上一次的请求，那我们就可以使用 `refresh` 来代替 `run(1)`，这在复杂参数的场景中是非常有用的

```ts
<template>
  <vhp-button style="margin-top: 16px;" @click="() => refresh()">refresh</vhp-button>
  <div style="margin-top: 16px;">{{ loading ? 'loading..' : data }}</div>
</template>

<script lang="ts" setup>
  import { useRequest } from 'vue-hooks-plus'

  // 模拟一个异步请求函数
  function getUsername(): Promise<string> {
    return new Promise(resolve => {
      setTimeout(() => {
        resolve(String(Date.now()))
      }, 1000)
    })
  }

  //刷新-->refresh
  const { data, run, loading, refresh } = useRequest(() => getUsername(), {
    manual: true,
    devKey:"demo4",
  })
  run()
</script>
```

![image-20240105084549838](VueHooks%20Plus%E6%8F%92%E4%BB%B6.assets/image-20240105084549838.png)

### 立即变更数据

**mutate**

`useRequest` 提供了 `mutate`, 支持立即修改 `useRequest` 返回的 `data` 参数。

支持 `mutate(newData)` 和 `mutate((oldData) => newData)` 两种写法。

下面的示例，我们演示了一种 `mutate` 的应用场景。

我们修改了用户名，但是我们不希望等编辑接口调用成功之后，才给用户反馈。而是直接修改页面数据，同时在背后去调用修改接口，等修改接口返回之后，另外提供反馈。(同时修改原始数据)

**错误回滚 `1.7.7.alpha.4`**

当你使用 `mutate`时，有可能在乐观数据展示给用户后，远程数据更改却失败了。在这种情况下，你可以启用 `rollbackOnError`，将本地缓存恢复到之前的状态，确保用户看到的是正确的数据。

```vue
<template>
  <div>name：{{ data }}</div>
  <div style="margin-top:8px">
    <input v-model="dataValue" />
    <vhp-button style="margin-left: 8px;" @click="handleClick">Edit</vhp-button>
  </div>
  <div style="margin-top:8px">
    <p v-for="msg in step" :key="msg">{{ msg }}</p>
  </div>
</template>

<script lang="ts" setup>
  import { ref } from 'vue'
  import { useRequest } from 'vue-hooks-plus'

    // 异步操作执行成功 返回request-${params.desc}字符串
  function getUsername(params: { desc: string }): Promise<string> {
    return new Promise((resolve, reject) => {
      setTimeout(() => {
        if (Math.random() > 0.5) resolve(`request-${params.desc}`)
        reject('error')
      }, 2000)
    })
  }
    
  const dataValue = ref('')
  const step = ref<string[]>([])
  
  const { data: data, run, mutate } = useRequest(getUsername, {
    manual: true,
    devKey: 'demo5',
    rollbackOnError: true,
    onError: () => {
      alert('error')
    },
  })
  const handleClick = () => {
    mutate(dataValue.value)   // 立即修改 //同时修改原始数据
    run({ desc: dataValue.value })
  }
</script>
```

### 取消响应

`useRequest` 提供了 `cancel` 函数，用于**忽略**当前 promise 返回的数据和错误

**注意：调用 `cancel` 函数并不会取消 promise 的执行**

同时 `useRequest` 会在以下时机自动忽略响应：

- 组件卸载时，正在进行的 promise
- 竞态取消，当上一次 promise 还没返回时，又发起了下一次 promise，则会忽略上一次 promise 的响应

```tsx
<template>
  <div>name：{{ loading ? 'loading..' : data }}</div>
  <div style="margin-top:8px">
    <input v-model="dataValue">
    <vhp-button style="margin-left: 8px;" @click="handleClick">Edit</vhp-button>
    <vhp-button style="margin-left: 8px;" @click="handleCancel">Cancel</vhp-button>
  </div>
</template>

<script lang="ts" setup>
  import { ref } from 'vue'
  import { useRequest } from 'vue-hooks-plus'

  function getUsername(params: { desc: string }): Promise<string> {
    return new Promise((resolve, reject) => {
      setTimeout(() => {
        if (Math.random() > 0.5) resolve(`request-${params.desc}`)
        reject('error')
      }, 2000)
    })
  }
  const dataValue = ref('')
  const { data, loading, run, cancel } = useRequest(getUsername, {
    manual: true,
    devKey:"demo6",
    onError: () => {
      alert('error')
    },
  })

  const handleClick = () => {
    run({ desc: dataValue.value })
  }

  const handleCancel = () => {
    cancel()   // 在loading过程中取消当前动作响应
  }
</script>
```

![image-20240105091736684](VueHooks%20Plus%E6%8F%92%E4%BB%B6.assets/image-20240105091736684.png)

### [参数管理](https://inhiblab-core.gitee.io/docs/hooks/useRequest/basic/#参数管理)

`useRequest` 返回的 `params` 会记录当次调用 `service` 的参数数组。比如你触发了 `run(1, 2, 3)`，则 `params` 等于 `[1, 2, 3]` 。

如果我们设置了 `options.manual = false`及自动触发，则首次调用 `service` 的参数可以通过 `options.defaultParams` 来设置。

```ts
<template>
  <div>name：{{ loading ? 'loading' : data }}</div>
  <div style="margin-top:8px">
    <input v-model="value">
    <vhp-button style="margin-left: 8px;" @click="run({ desc: value })">Edit</vhp-button>
  </div>
  <br>
  <div> Params: {{ JSON.stringify(params) }} </div>
</template>

<script lang="ts" setup>
  import { ref } from 'vue'
  import { useRequest } from 'vue-hooks-plus'
  
  // 模拟一个异步请求函数
  function getUsername(params: { desc: string }): Promise<string> {
    return new Promise((resolve, reject) => {
      setTimeout(() => {
        resolve(`vue-hooks-plus ${params.desc}`)
      }, 1000)
    })
  }
  const value = ref('vue-hooks-plus')
  // 自动触发
  const { data: data, loading, run, params } = useRequest(getUsername, {
    //自动触发下,通过defaultParams可以给异步请求函数传递参数
    defaultParams: [
      {
        desc: 'nice',
      },
    ],
    devKey:"demo7",
  })
</script>
```





# [useFormatResult](https://inhiblab-core.gitee.io/docs/hooks/useFormatResult/#useformatresult)

格式化数据的 hook。

## API

```
const formatData = useFormatResult(data, callback)
```

## 返回值

| 参数       | 说明           | 类型                           |
| ---------- | -------------- | ------------------------------ |
| formatData | 格式化后的数据 | `ComputedRef<FData>` | `FData` |

## 必选参数

| 参数     | 说明             | 类型                     | 默认值 |
| -------- | ---------------- | ------------------------ | ------ |
| data     | 需要格式化的数据 | `TData` | `Ref<TData>`   | -      |
| callback | 格式化函数       | `(data: TData) => FData` | -      |

**官方案例**

```js
<template>
  <div>name: {{ name }}</div>
  <div>age: {{ age }}</div>
  <div>double age: {{ formatAge }}</div>
</template>

<script lang="ts" setup>
  import { ref } from 'vue'
  import { useFormatResult, useInterval } from 'vue-hooks-plus'

  const age = ref(0)
  // 参数:  原始数据  格式化函数
  const name = useFormatResult({ name: 'vue-hooks-plus' }, data => {
    return data.name
  })

  // 参数:  原始数据  格式化函数
  const formatAge = useFormatResult(age, data => {
    return data * 2
  })

  useInterval(() => {
    age.value += 1
  }, 1000)
</script>
```

![image-20240105095600116](VueHooks%20Plus%E6%8F%92%E4%BB%B6.assets/image-20240105095600116.png)

# 其他用法见官网:crown:
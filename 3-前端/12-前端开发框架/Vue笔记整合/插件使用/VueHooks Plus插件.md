官网:  [✨ 特性 | VueHook Plus (gitee.io)](https://inhiblab-core.gitee.io/docs/hooks/guide/)

# API

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

## 返回值

| 参数         | 说明                                                         | 类型                                                         |
| ------------ | ------------------------------------------------------------ | ------------------------------------------------------------ |
| data         | service 返回的数据/ 一个只读的 `Ref`，表示请求成功后返回的数据 | `Readonly<Ref<TData>>` | `undefined`                         |
| error        | service 抛出的异常/ 一个只读的 `Ref`，表示请求失败时的错误信息 | `Readonly<Ref<Error>>` | `undefined`                         |
| loading      | service 是否正在执行/ 一个只读的 `Ref`，表示请求是否正在进行中 | `Readonly<>Ref<boolean>`                                     |
| params       | 当次执行的 service 的参数数组/ 一个只读的 `Ref`，表示当前请求的参数。比如你触发了 `run(1, 2, 3)`，则 params 等于 `[1, 2, 3]` | `Readonly<Ref<TParams | []>>`                                |
| run          | 手动触发 service 执行，参数会传递给 service异常自动处理，通过 `onError` 反馈 /用于手动触发请求 | `(...params: TParams) => void`                               |
| runAsync     | 与 `run` 用法一致，但返回的是 Promise，需要自行处理异常。/一个异步方法，用于手动触发请求并返回一个 Promise | `(...params: TParams) => Promise<TData>`                     |
| refresh      | 使用上一次的 params，重新调用 `run`/一个方法，用于重新发起请求 | `() => void`                                                 |
| refreshAsync | 使用上一次的 params，重新调用 `runAsync`/一个异步方法，用于重新发起请求并返回一个 Promise | `() => Promise<TData>`                                       |
| mutate       | 直接修改 `data`/一个方法，用于手动更新数据或进行数据变更操作 | `(data?: TData | ((oldData?: TData) => (TData | undefined))) => void` |
| cancel       | 取消当前正在进行的请求/一个方法，用于取消当前的请求          | `() => void`                                                 |

## 必选参数

| 参数    | 说明                                                         | 类型                                  |
| :------ | ------------------------------------------------------------ | ------------------------------------- |
| service | `service` 是一个函数，它接受类型为 `TParams` 的参数，并返回一个 `Promise`，该 `Promise` 的解决值的类型为 `TData`。 | (...args: TParams) => Promise<TData>, |

## 可选参数

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

# 使用

## 自动触发

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





## 手动触发

如果设置了 `options.manual = true`，则 `useRequest` 不会默认执行，需要通过 `run` 或者 `runAsync` 来触发执行。

```ts
const { loading, run, runAsync } = useRequest(service, {
  manual: true,
})
```

`run` 与 `runAsync` 的区别在于：

- `run` 是一个普通的同步函数，我们会自动捕获异常，你可以通过 `options.onError` 来处理异常时的行为。
- `runAsync` 是一个返回 `Promise` 的异步函数，如果使用 `runAsync` 来调用，则意味着你需要自己捕获异常。

### run

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

### runAsync

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

## 生命周期

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


<template>
  <div>
     <!-- el-breadcrumb 组件用于展示面包屑导航 -->
    <el-breadcrumb separator="/">
       <!-- 如果当前路由不是根路由，显示首页的面包屑项 -->
      <el-breadcrumb-item to="/" v-if="route.path !== '/'"
        >首页</el-breadcrumb-item
      >
      <!-- 遍历生成每个路由匹配项对应的面包屑项 -->
      <el-breadcrumb-item
        v-for="(item) in navArray"
        :key="item.path"
        :to="{ path: item.path }"
      >
        {{ item.meta.title }}
      </el-breadcrumb-item>
    </el-breadcrumb>
  </div>
</template>
<script lang="ts" setup>
import { useRoute, useRouter } from "vue-router";
import { watch, ref, onMounted } from "vue";

// 使用 ref 创建一个响应式的数组 navArray，用于存储当前路由匹配的路由记录
const navArray = ref([]);
// 使用 useRoute 获取当前的路由对象
const route = useRoute();
// 使用 useRouter 获取路由实例
const router = useRouter();
// 监听路由记录
watch(() => route.matched,(newValue, oldValue) => {
    navArray.value = newValue;
  }
);
</script>

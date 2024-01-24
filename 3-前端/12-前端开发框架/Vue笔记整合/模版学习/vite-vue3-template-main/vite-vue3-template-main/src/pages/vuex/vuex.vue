<template>
  <hr />
  <router-link to="/home">跳转 home</router-link> <br />

  <hr />

  <!--这里是vuex的数据，不要直接在这里使用 不然修改vuex数据页面不能及时相应，需要在计算属性中处理下
   在vue3.0中，可以直接写，因为 proxy 可以监听到任意数据的变化
 -->

  <!-- vuex 分模块数据 直接取值-->

  <div>vuex 分模块直接取值---{{ $store.state.user.test }}</div>
  <br />

  <hr />

  <!-- vuex 分模块数据 同通过计算属性取值-->

  <div>vuex vuex分模块通过计算属性取值---{{ getTest }}</div>
  <br />

  <hr />

  <!-- vuex 分模块数据 直接取值getters-->

  <div>vuex 分模块数据 直接取值getters---{{ $store.getters.getCount }}</div>
  <br />

  <hr />

  <!-- 通过计算属性可以及时变化 -->

  <p>vuex index.ts通过计算属性取值---{{ count }}</p>
  <br />

  <hr />
  <button @click="addClick">增加</button>
  <button @click="handlerUser">触发vuex分模块数据事件</button>
</template>

<script setup lang="ts">
import { order } from '/@/network/api';
import { useStore } from 'vuex';
let store = useStore();
let data = ref('');
const count = computed(() => store.state.count);
const getTest = computed(() => store.state.user.test);

const addClick = () => {
  store.commit('increment');
};
// 触发vuex分模块数据
const handlerUser = () => {
  store.dispatch('user/changeTest');
};
</script>
<style lang="scss"></style>

<template>
  <div style="height: 100%">
    <el-container class="layout-container-demo" style="height: 100%">
      <el-aside :width="aside_witdh" style="height: 100%">
        <Aside :isCollapse="isCollapse"></Aside>
      </el-aside>

      <el-container style="height: 100%">
        <el-header
          style="
            text-align: right;
            font-size: 12px;
            background-color: #409eff 1px solid;
          "
        >
          <Header @doCollapse="doCollapse" :isExpanded="isExpanded"></Header>
        </el-header>

        <el-main style="height: 100%"> 
          <Main></Main>
           <!-- <router-view/> -->
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script setup lang="js">
// import { Menu as IconMenu, Message, Setting } from "@element-plus/icons-vue";
import Aside from "./Aside.vue";
import Header from "./Header.vue";
import { ref } from "vue";

  const isCollapse = ref(false);
  const aside_witdh = ref("200px");
  const isExpanded = ref(false);
  let doCollapse = () => {
      isCollapse.value = !isCollapse.value; //JS中操作数据xxx.value
        if(!isCollapse.value){// 展开
            aside_witdh.value='200px'
           isExpanded.value=false;
        }else{//关起、关闭、收起
           aside_witdh.value='64px'
           isExpanded.value=true;
           console.log(aside_witdh);
        }
  }
</script>
<style>
/*
	找到html标签、body标签，和挂载的标签
	都给他们统一设置样式
*/
html,
body,
#app,
.el-container {
  /*设置内部填充为0，几个布局元素之间没有间距*/
  padding: 0px;
  /*外部间距也是如此设置*/
  margin: 0px;
  /*统一设置高度为100%*/
  height: 100%;
}
</style>

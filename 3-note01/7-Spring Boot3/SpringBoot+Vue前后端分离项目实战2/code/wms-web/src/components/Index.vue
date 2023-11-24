<template>
  <div style="height: 100%">
    <el-container class="layout-container-demo" style="height: 100%">
      <el-aside width="200px" style="height: 100%">
        <Aside></Aside>
      </el-aside>

      <el-container style="height: 100%">
        <el-header style="text-align: right; font-size: 12px">
          <Header></Header>
        </el-header>

        <el-main style="height: 100%">
          <!-- <Main></Main> -->
          <el-scrollbar>
            <el-table :data="tableData">
              <el-table-column prop="date" label="Date" width="140" />
              <el-table-column prop="name" label="Name" width="120" />
              <el-table-column prop="address" label="Address" />
            </el-table>
          </el-scrollbar>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script setup lang="js">
// import { ref } from "vue";
import { Menu as IconMenu, Message, Setting } from "@element-plus/icons-vue";
import Aside from "./Aside.vue";
// import Main from './Main.vue'
import Header from "./Header.vue";
import { ref } from "vue";
const item = {
  date: "2016-05-02",
  name: "Tom",
  address: "No. 189, Grove St, Los Angeles",
};
const tableData = ref(Array.from({ length: 12 }).fill(item));
</script>

<style>
/* .layout-container-demo .el-header {
  position: relative;
  background-color: var(--el-color-primary-light-7);
  color: var(--el-text-color-primary);
} */
/* .layout-container-demo .el-aside {
  color: var(--el-text-color-primary);
  background: var(--el-color-primary-light-8);
}
.layout-container-demo .el-menu {
  border-right: none;
}
.layout-container-demo .el-main {
  padding: 0;
} */

/*
	找到html标签、body标签，和挂载的标签
	都给他们统一设置样式
*/
html,body,#app,.el-container{
        /*设置内部填充为0，几个布局元素之间没有间距*/
        padding: 0px;
         /*外部间距也是如此设置*/
        margin: 0px;
        /*统一设置高度为100%*/
        height: 100%;
    }

    
</style>

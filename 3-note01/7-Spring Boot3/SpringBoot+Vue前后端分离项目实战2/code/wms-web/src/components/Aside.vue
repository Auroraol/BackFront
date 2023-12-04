<template>
  <el-menu
     background-color="#545c64"
            text-color="#fff"
            active-text-color="#ffd04b"
            style="height: 100%;"
            default-active="/Home"
            :collapse="isCollapse"
            :collapse-transition="false"
            router
  >
    <el-menu-item  index="/Home">
      <el-icon><House /></el-icon>
        <span>首页</span>
      </el-menu-item>
    <el-menu-item index="/One">
     <el-icon><Flag /></el-icon>
      <span>导航1</span>
    </el-menu-item>
    <el-menu-item index="/Two">
     <el-icon><Location /></el-icon>
      <span>导航2</span>
    </el-menu-item>
  </el-menu>
    
</template>

<script setup lang="js">

defineProps({
   isCollapse:Boolean
})


</script>

<style></style>

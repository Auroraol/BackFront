<template>
  <el-row>
    <el-col :span="20">
      <div style="text-align: center; font-size: 34px; margin-top: 5px;">
        <span>欢迎来到仓库管理系统</span>
      </div></el-col
    >
    <el-col :span="4"
      ><div class="toolbar">
        <el-dropdown>
            <el-icon><ArrowDownBold /></el-icon>
          <template #dropdown>
            <el-dropdown-menu>
              <el-dropdown-item>个人中心</el-dropdown-item>
              <el-dropdown-item>退出登录</el-dropdown-item>
            </el-dropdown-menu>
          </template>
        </el-dropdown>
      </div></el-col
    >
  </el-row>
</template>

<script setup lang="ts"></script>

<style>
.toolbar {
  display: inline-flex;
  align-items: center;
  justify-content: center;
  height: 100%;
  right: 20px;
}
</style>

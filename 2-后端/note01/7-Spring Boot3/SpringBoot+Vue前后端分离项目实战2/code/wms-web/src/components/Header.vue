<template>
  <el-row>
    <el-col :span="20">
      <div
        style="
          display: flex;
          align-items: center;
          justify-content: flex-start;
          text-align: center;
          font-size: 34px;
          margin-top: 5px;
        "
      >
        <el-icon @click="collapse">
          <!-- 根据 isExpanded 属性显示不同的图标 vue3中if是动态渲染的-->
          <template v-if="isExpanded">
            <Expand />
          </template>
          <template v-else>
            <Fold />
          </template>
        </el-icon>
        <span style="margin-left: 400px">欢迎来到仓库管理系统</span>
      </div>
    </el-col>
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

<script setup lang="ts">
const emit = defineEmits(["doCollapse"]); //定义emit

let collapse = () => {
  emit("doCollapse");
};
defineProps({
  isExpanded: Boolean,
});
</script>

<style>
.toolbar {
  display: inline-flex;
  align-items: center;
  justify-content: center;
  height: 100%;
  right: 20px;
}
</style>

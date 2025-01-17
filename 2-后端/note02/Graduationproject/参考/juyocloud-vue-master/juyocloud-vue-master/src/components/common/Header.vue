<template>
    <el-header class="page-header">

      <div class="flex-grow"/>

      <div class="tools-right">
        <el-tooltip
            v-if="storageConfigStore.globalConfig.showLogin"
            placement="bottom"
        >
          <template #content> 进入后台 </template>
          <a href="/admin" target="_blank" class="toAdminBtn">
            <svg-icon
                class="text-2xl text-gray-500"
                name="login"
            ></svg-icon>
          </a>
        </el-tooltip>
      </div>

    </el-header>
</template>

<script setup>
import useStorageConfigStore from '@/stores/storage-config'
let storageConfigStore = useStorageConfigStore()

</script>

<style scoped lang="stylus">
.page-header
  display flex
  align-items: center;
  flex-wrap nowrap
  width: 100%;
  padding: 0 18px;
  border-bottom: 1px solid #f1f1f3;
  .flex-grow
    flex-grow: 1;

.tools-right
  padding: 0 20px;
  .toAdminBtn
    display inline-block
    padding: 5px;
    cursor pointer
    text-align: center;
    line-height: 1.5;
    border-radius 4px
    background-color: #f3f3f3;
    &:hover
      background-color: #e0e0e0;


</style>

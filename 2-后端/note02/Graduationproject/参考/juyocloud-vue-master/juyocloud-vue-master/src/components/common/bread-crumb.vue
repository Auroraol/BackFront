<template>
  <div class="bread-crumb">
    <el-breadcrumb separator="/" separator-class="ArrowRight">
      <el-breadcrumb-item :to="rootPath">{{ '全部文件' }}</el-breadcrumb-item>
      <el-breadcrumb-item
          v-for="item in breadcrumbData"
          :key="item.fullPath"
          :to="{ path: encodeAllIgnoreSlashes(item.fullPath) }"
      >
        {{ item.name }}
      </el-breadcrumb-item>
    </el-breadcrumb>
  </div>
</template>

<script setup>

// 面包屑数据和操作
import useBreadcrumb from '@/composables/header/useHeaderBreadcrumb'
const { buildBreadcrumbData, rootPath, breadcrumbData } = useBreadcrumb()

import useStorageConfigStore from '@/stores/storage-config'
let storageConfigStore = useStorageConfigStore()

import useFileDataStore from '@/stores/file-data'
let fileDataStore = useFileDataStore()

import useCommon from '@/composables/useCommon'
const { isNotMobile, isMobile, encodeAllIgnoreSlashes } = useCommon()
// 存储源列表.
import useHeaderStorageList from '@/composables/header/useHeaderStorageList'
const { loadStorageSourceList, currentStorageKey, storageList } = useHeaderStorageList()

onMounted(() => {
  loadStorageSourceList().then(() => {
    buildBreadcrumbData()
  })
})

</script>

<style lang="stylus">
.bread-crumb
  margin: 10px 0;
  border-bottom: 1px solid #f1f1f3;
  .el-breadcrumb__item
    .el-breadcrumb__inner
      padding: 8px 0;
      cursor pointer
</style>
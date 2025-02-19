<template>
  <div v-if="loadedConfig" style="height: 100%;overflow: hidden">
    <el-container class="layout-container" style="height: 100%">
      <sider/>

      <el-container direction="vertical" class="page-content">
        <Header/>

        <el-main class="page-main-wrap" style="height: 100%;">
          <router-view/>
        </el-main>

      </el-container>

    </el-container>
  </div>
</template>

<script setup>
import { loadGlobalSiteConfigReq } from '@/api/home'

import useStorageConfigStore from '@/stores/storage-config'
import common from '@/common'
let storageConfigStore = useStorageConfigStore()

// 初始化时，加载全局设置
onBeforeMount(() => {
  loadGlobalSiteSetting()
})

let router = useRouter()

let loadedConfig = ref(false)

const loadGlobalSiteSetting = () => {
  loadGlobalSiteConfigReq()
      .then((res) => {
        // 如果未安装, 则跳转到安装页
        if (!res.data.installed) {
          router.push('/install')
          return
        }
        storageConfigStore.updateGlobalConfig(res.data)

        if (res.data.customAudioSuffix) {
          common.constant.fileTypeMap.audio =
              res.data.customAudioSuffix.split(',')
        }
        if (res.data.customImageSuffix) {
          common.constant.fileTypeMap.image =
              res.data.customImageSuffix.split(',')
        }
        if (res.data.customTextSuffix) {
          common.constant.fileTypeMap.text = res.data.customTextSuffix.split(',')
        }
        if (res.data.customVideoSuffix) {
          common.constant.fileTypeMap.video =
              res.data.customVideoSuffix.split(',')
        }
        loadedConfig.value = true
      })
      .catch((err) => {
        if (err.message === 'Network Error') {
          ElMessage.error('加载失败，无法连接到服务端，请联系管理员.')
        }
      })
}
</script>

<style scoped lang="stylus">
.layout-container
  .page-content
    height: calc(100% - 40px);

</style>

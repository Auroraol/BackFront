<template>

  <div>
    <div class="bg fixed w-full h-full" style="background-color: #fafafa;z-index: -1;"></div>
    <div style="height: 120px" class="share-header">
      <router-link to="/home">
        <img :src="logo" alt="juyovo">
      </router-link>
    </div>
    <div v-if="isShow">

      <div
          class="lg:h-auto max-w-screen-lg bg-white shadow-xl sm:rounded-xl sm:w-auto mx-auto"
          style="max-width: 38rem;margin-top: 80px;"
      >
        <div class="w-full" style="padding: 2rem">
          <div class="flex flex-col">
            <div class="mt-0 mb-9 flex items-center justify-between">
              <div class="flex">
                <el-icon
                    style="margin-right: 10px;"
                    :size="25">
                  <svg-icon :name="'file-type-' + data.icon"></svg-icon>
                </el-icon>
                <h2
                    class="text-xl xl:text-xl"
                >
                  {{data.name}}
                </h2>
              </div>
              <div
                  class="size text-gray-400"
                  style="font-size: 14px;font-weight: 100;margin-bottom: -8px;"
              >
                {{ common.fileSizeFilter(data, null, data.size) }}
              </div>
            </div>

            <div
                style="background-color:#f1f6fe;font-size: 14px;line-height: 2;"
                class="w-full text-gray-400 p-3 ">
              <div class="time">
                <i-ep-clock class="inline-block mr-1"/>
                <span>{{data.time}}</span>
              </div>

              <div class="share-valid-time">
                过期时间：
                <span>永久有效</span>

              </div>

            </div>

            <div class="mt-8">
              <el-button
                  class="float-right"
                  type="primary"
                  style="width: 62px;height: 36px;font-weight: bold;"
                  @click="openUrl(data.url)"
              >下载</el-button>
              <el-button
                  plain
                  type="primary"
                  class="float-right"
                  style="width: 62px;height: 36px;margin-right: 12px;"
                  @click="openPreview(data, true)"
              >预览</el-button>
            </div>
          </div>
        </div>
      </div>
    </div>

    <div v-if="!isShow">
      <div
          class="lg:h-auto max-w-screen-lg sm:rounded-xl sm:w-auto mx-auto"
          style="max-width: 38rem;margin-top: 80px;"
      >
        <div class="w-full text-center" style="padding: 2rem">
          <h1 class="text-3xl text-gray-400" style="margin-bottom: 30px;">你要的链接飞走啦！</h1>

          <img style="width: 100%;height: 200px;" :src="notFound" alt="notfound">
        </div>
      </div>
    </div>

    <preview/>
  </div>

</template>

<script setup>
import common from "@/common";
import logo from '@/assets/images/JCLOGO.png'
import {getShareData} from "@/api/common";
import notFound from '@/assets/images/notFound.svg'
import useFileData from "@/composables/file/useFileData";

const {
  openRow,
} = useFileData()

const router = useRouter()
const fileId = router.currentRoute.value.params.fileId
const data = ref({})
const isShow = ref(false)

const openPreview = (data,flag)=>{
  if(data.preview){
    openRow(data,flag)
  }else{
    ElMessage.warning("文件不支持预览")
  }

}

const openUrl = (url)=>{
  window.open(url)
}

onMounted(()=>{
  getShareData(fileId).then((res)=>{
    if(res.code === 0){
      isShow.value = true
      data.value = res.data
      document.title = data.value.name + " | 橘柚云盘"
      if (!data.value.icon) {
        data.value['icon'] = common.getFileIconName(res.data)
      }
      if (data.value.preview !== null) {
        // 获取文件类型
        let fileType = common.getFileType(data.value.name)
        if (fileType) {
          // 获取文件是否可预览
          data.value['fileType'] = fileType
          data.value.preview =
              common.constant.previewFileType.indexOf(fileType) !== -1
        } else {
          data.value.preview = false
        }
      }
    }else{
      isShow.value = false
    }
  })
})
</script>

<style scoped lang="stylus">
.share-header
  display flex
  align-items: center;
  justify-content: center;
  width: 100%
  img
    height: 60px
</style>

<template>
  <div>
    <!-- 文件拖拽提示框-->
    <div
        v-if="storageConfigStore.permission.upload"
        v-show="dropState"
        id="dropBox"
        ref="dropBoxRef"
        class="drop-view"
    >
      <div class="title">拖拽上传到 {{ currentPath }}</div>

      <div class="drop-sub">
        <span>将文件拖拽到此处</span>
      </div>
    </div>

    <div class="jy-uploader" v-show="showCloseBtn">

      <div class="upload-content">

        <div class="status-wrap">
          <div class="status-cont">
            <div class="status-head">
              上传进度
            </div>
            <div class="status-flod" @click="switchShowList"></div>

            <div class="close-status">

              <span style="margin-right: 10px;" @click="clearALlFinishedUploadFile" class="status-close-icon">
              <el-tooltip
                  effect="dark"
                  content="清理已完成任务"
                  placement="top"
              >
              <el-icon :size="20">
              <i-ep-close/>
            </el-icon>
              </el-tooltip>
            </span>
            </div>
          </div>
        </div>
        <el-collapse-transition>
          <div class="uploader-wrap" v-show="showList">
            <div class="jy-upload-list">
              <div class="space-y-2.5">
                <div
                    v-for="item in uploadProgressInfoSorted"
                    :key="item.index"
                    class="flex flex-row w-full relative rounded-lg"
                >
                  <div class="mr-2 p-1.5">
                    <svg-icon
                        class="text-5xl mt-1 py-1.5 sm:py-1"
                        :name="'file-type-' + common.getFileIconName(item)"
                    >
                    </svg-icon>
                  </div>

                  <div
                      class="p-1.5 py-2.5 sm:py-3 w-full flex flex-col justify-between"
                  >
                    <div class="flex justify-between">
                      <div class="flex sm:space-x-5 flex-col sm:flex-row">
                        <div class="font-medium text-sm max-w-[80%] line-clamp-1">
                          {{ item.name }}
                        </div>
                        <div
                            class="text-gray-400 text-xs leading-5 line-clamp-1 active:line-clamp-none"
                        >
                          <span
                              v-if="item.status === 'uploading' && !item.msg"
                              class="text-blue-500 box animate__animated animate__fadeIn"
                          >{{ item.speed }} / 秒</span
                          >

                          <svg-icon
                              v-else-if="item.status === 'finished'"
                              name="check"
                              class="inline text-green-500 box animate__animated animate__fadeIn"
                          />
                          <span
                              v-else-if="item.status === 'waiting'"
                              class="text-yellow-500 box animate__animated animate__fadeIn"
                          >{{ item.msg }}</span
                          >
                          <span
                              v-else-if="item.status === 'error'"
                              class="text-red-500 box animate__animated animate__fadeIn"
                          >{{ item.msg }}</span
                          >
                        </div>
                      </div>
                      <div>
                        <div v-if="item.status === 'uploading'">
                          <svg-icon
                              name="tool-close2"
                              class="top-0.5 relative inline text-gray-500 mr-1 text-lg cursor-pointer rounded-full"
                              @click="cancelUpload(item)"
                          />
                        </div>
                        <div v-else-if="item.status === 'finished'">
                          <svg-icon
                              name="delete"
                              class="inline text-red-400 mr-1 text-base cursor-pointer rounded-full"
                              @click="removeUploadFileByIndex(item.index)"
                          />
                        </div>
                        <div v-else-if="item.status === 'error'">
                          <svg-icon
                              name="refresh"
                              class="inline text-red-500 mr-1 text-base cursor-pointer rounded-full"
                              @click="retryUpload(item)"
                          />
                        </div>
                        <div v-else-if="item.status === 'waiting'">
                          <svg-icon
                              name="delete"
                              class="inline text-red-400 mr-1 text-base cursor-pointer rounded-full box"
                              @click="removeUploadFileByIndex(item.index)"
                          />
                        </div>
                      </div>
                    </div>
                    <div>
                      <el-progress
                          v-if="item.status === 'finished'"
                          :show-text="false"
                          :percentage="item.progress"
                          status="success"
                      ></el-progress>
                      <el-progress
                          v-else-if="item.status === 'uploading'"
                          :show-text="false"
                          :percentage="item.progress"
                      ></el-progress>
                      <el-progress
                          v-else-if="item.status === 'error'"
                          :show-text="false"
                          :percentage="100"
                          status="exception"
                      ></el-progress>
                      <el-progress
                          v-else-if="item.status === 'waiting'"
                          :show-text="false"
                          :percentage="0"
                      ></el-progress>
                    </div>
                  </div>
                </div>
              </div>
            </div>

            <span class="upload-footer" @click="switchShowList">
          收起
        </span>
          </div>
        </el-collapse-transition>
      </div>

    </div>
  </div>
</template>

<script setup>
import common from '@/common'

let router = useRouter()
let route = useRoute()

const showList = ref(true)

const switchShowList = () => {
  showList.value = !showList.value
}

const showCloseBtn = computed(() => {
  return uploadingFileList.length >= 1
})

import useRouterData from '@/composables/useRouterData'

let currentPath = computed(()=>{
  return useRouterData().currentPath.value
})

import useFileUpload from '@/composables/file/useFileUpload'

const {
  visible,
  uploadMode,
  cancelUpload,
  uploadingFileList,
  uploadProgressInfoSorted,
  dropState,
  listenDropFile,
  clearALlFinishedUploadFile,
  removeUploadFileByIndex,
  retryUpload,
} = useFileUpload()

import useStorageConfigStore from '@/stores/storage-config'

let storageConfigStore = useStorageConfigStore()

import useFileDataStore from '@/stores/file-data'

let fileDataStore = useFileDataStore()

// 监听拖拽上传
const dropBoxRef = ref()
onMounted(() => {
  listenDropFile()
})
</script>

<style scoped lang="stylus">
.drop-view
  position: fixed;
  width: 100%
  height: 100%
  z-index: 9999;
  background-color: rgba(120, 120, 120, .2);
  backdrop-filter blur(30px)
  left: 0
  bottom: 0
  display flex
  justify-content: center;
  align-items: center;
  flex-direction row
  .title
    position: absolute;
    top 5rem
    left: 50%;
    transform translateX(-50%)
    color #ffffff
    font-size 1.5rem

  .drop-sub
    display flex
    justify-content: center;
    align-items: center;
    height: 55%
    width: 65%
    border-style dashed
    border-width 3px
    border-radius 1rem
    border-color #d6d7da
    background-color: rgba(255, 255, 255, .6);
    font-weight: bold;
    font-size: 1.8rem;
    line-height: 2rem;
    color: #9ea2ad

.jy-uploader
  position: absolute;
  bottom: 2rem
  right: 2rem
  z-index: 99;

  .upload-content
    position: relative;
    width: 360px;
    max-height 480px
    box-shadow: 0 2px 8px 0 rgba(0, 0, 0, .16);
    border-radius 8px
    background-color: #fff;
    overflow hidden

    .uploader-wrap
      width: inherit;

      .jy-upload-list
        height: 400px
        width: 100%
        padding: 0 13px;
        padding-bottom: 36px

        .file-list
          .file-list-item
            display flex
            align-items: center;
            margin-top: 10px;

            .file-icon
              margin-right: 15px;
              padding-left: 5px;

            .uploader-file
              display flex
              align-items: center;
              width: 100%

              .upload-status
                flex-grow 1

              .file-info
                display flex
                justify-content: center;
                flex-direction column

                .file-name
                  font-size 14px
                  line-height: 1.6;
                  width: 200px
                  color #333
                  white-space: nowrap;
                  overflow hidden
                  text-overflow: ellipsis;

                .file-size
                  font-size 13px
                  line-height: 1.5;
                  color rgba(37, 38, 43, .3)

      .upload-footer
        position: absolute;
        bottom: 0
        text-align: center;
        width: 100%
        height: 38px
        line-height: 38px;
        font-size 14px
        color #56565a
        cursor pointer
        transition color, background-color .3s ease
        user-select none
        background-color: #fff;

        &:hover
          color #409eff
          background-color: #f8f9fc;


    .uploadBtn
      position: absolute;
      clip rect(0, 0, 0, 0)
      display none
      visibility hidden

    .status-wrap
      position: relative;
      height: 54px;
      transition background-color .3s
      user-select none

      &:hover
        background-color: #f5f5f6;

      .status-cont
        position: relative;
        display flex
        align-items: center;
        padding-left: 20px;
        cursor: pointer;
        width: 100%
        height: 100%

        .status-flod
          position: absolute;
          width: 100%
          height: 100%
          cursor pointer
          top: 0
          z-index: 8;

        .close-status
          position: absolute;
          display flex
          align-items: center;
          right: 26px
          top: 0
          height: 100%

          .status-close-icon
            display flex
            justify-content: center;
            align-items: center;
            border-radius 50%
            width: 30px;
            height: 30px
            cursor pointer
            z-index: 9;
            transition background-color .3s ease

            &:hover
              background-color: #e5e5e7;

</style>
<template>
  <div class="toolbar">

    <el-upload
        ref="uploadRef"
        :http-request="beforeUpload"
        :show-file-list="false"
        multiple
        style="display: none;visibility: hidden"
    >
      <button ref="uploadBtn" style="display: none!important;"/>
    </el-upload>

    <el-dropdown
        v-show="route.params.storageKey"
        style="margin-right: 10px;"
    >

      <el-button type="primary" :icon="Upload" @click="openUploadDialog">上传</el-button>
      <template #dropdown>
        <el-dropdown-menu>
          <el-dropdown-item v-if="storageConfigStore.permission.upload &&
										storageConfigStore.permission.newFolder" @click="openUploadDialog">
            上传文件
          </el-dropdown-item>
          <el-dropdown-item @click="openUploadFolderDialog">上传文件夹</el-dropdown-item>
        </el-dropdown-menu>
      </template>
    </el-dropdown>
    <el-button-group>

      <el-button type="primary" :icon="Plus" @click="showNewFolder = true">新建文件夹</el-button>
      <el-button type="primary" @click="batchDelete" :icon="Delete">批量删除</el-button>
    </el-button-group>
  </div>

  <el-dialog v-model="showNewFolder" title="新建文件夹" width="320px" style="border-radius: 8px">

    <el-icon class="fold-icon" :size="130">
      <svg-icon name="newFolder"/>
    </el-icon>

    <el-input type="text" size="large" v-model.trim="foldName"/>
    <template #footer>
      <span class="dialog-footer">
        <el-button
            :disabled="!storageConfigStore.permission.newFolder"
            style="background-color: #3766ff;border: none;border-radius: 5px" type="primary"
            @click="createFolder(foldName)"
        >确定</el-button
        >
      </span>
    </template>
  </el-dialog>
</template>

<script setup>
import {
  Delete,
  Upload,
  Plus,
} from '@element-plus/icons-vue'

const {proxy} = getCurrentInstance();

const showNewFolder = ref(false)
const foldName = ref('')

import useStorageConfigStore from "@/stores/storage-config";
import useFileUpload from "@/composables/file/useFileUpload";

let router = useRouter()
let route = useRoute()

let storageConfigStore = useStorageConfigStore()
let activeIndex = ref(router.currentRoute.value.path)
const {
  beforeUpload,
  uploadProgressInfoStatistics,
} = useFileUpload()
const uploadBtn = ref(null)

const openUploadDialog = () => {
  document.getElementsByClassName(
      'el-upload__input'
  )[0].webkitdirectory = false
  uploadBtn.value.click()
}

const openUploadFolderDialog = () => {
  document.getElementsByClassName(
      'el-upload__input'
  )[0].webkitdirectory = true
  uploadBtn.value.click()

}
import useFileOperator from '@/composables/file/useFileOperator'

const {newFolder, batchDelete} = useFileOperator()

const createFolder = (name) => {
  newFolder(name).then(() => {
    showNewFolder.value = false
    foldName.value = ''
    ElMessage.success('创建文件夹成功')
  })
}

watch(() => router.currentRoute.value.path, (newVal, oldVal) => {
  activeIndex.value = newVal
})

</script>

<style scoped lang="stylus">
.toolbar
  margin-bottom 20px

.fold-icon
  width: 100%;
  margin-bottom: 20px;
  text-align: center;
</style>
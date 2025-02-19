<template>
  <!-- 视频播放器 -->
  <el-dialog
      v-model="dialogVideoVisible"
      draggable
      align-center
      class="juyo-video-dialog"
      :destroy-on-close="true"
  >
    <video-player v-if="dialogVideoVisible" ref="videoPlayer" />
  </el-dialog>

  <!-- 文本编辑器 -->
  <el-dialog
      v-model="dialogTextVisible"
      draggable
      class="juyo-text-dialog juyo-dialog-mini-close"
      :destroy-on-close="true"
      align-center
      :title="fileDataStore.currentClickRow.name"
  >
    <TextViewer
        v-if="
						dialogTextVisible &&
						fileDataStore.currentClickRow.name.indexOf('.md') === -1
					"
        :file-name="fileDataStore.currentClickRow.name"
        :file-url="fileDataStore.currentClickRow.url"
    />
    <MarkdownViewer
        v-if="
						dialogTextVisible &&
						fileDataStore.currentClickRow.name.indexOf('.md') !== -1
					"
        :file-name="fileDataStore.currentClickRow.name"
        :file-url="fileDataStore.currentClickRow.url"
    />
  </el-dialog>

  <!-- pdf 在线预览 -->
  <el-dialog
      v-model="dialogPdfVisible"
      draggable
      align-center
      class="juyo-pdf-dialog"
      :title="fileDataStore.currentClickRow.name"
  >
    <PdfViewer
        v-if="dialogPdfVisible"
        :file-name="fileDataStore.currentClickRow.name"
        :file-url="fileDataStore.currentClickRow.url"
    />
  </el-dialog>

  <!-- office 在线预览 -->
  <el-dialog
      v-model="dialogOfficeVisible"
      draggable
      align-center
      class="juyo-office-dialog"
      :title="fileDataStore.currentClickRow.name"
  >
    <OfficeViewer
        v-if="dialogOfficeVisible"
        :file-name="fileDataStore.currentClickRow.name"
        :file-url="fileDataStore.currentClickRow.url"
    />
  </el-dialog>

  <!-- 3d 在线预览 -->
  <el-dialog
      v-model="dialog3dVisible"
      draggable
      align-center
      class="juyo-3d-dialog"
      :title="fileDataStore.currentClickRow.name"
  >
    <Three3dPreview
        v-if="dialog3dVisible"
        :file-name="fileDataStore.currentClickRow.name"
        :file-url="fileDataStore.currentClickRow.url"
    />
  </el-dialog>

  <!-- 生成直链 -->
  <Link></Link>

  <!-- 音频播放器 -->
  <audio-player></audio-player>
</template>

<script setup>
import useFileDataStore from "@/stores/file-data";
import useStorageConfigStore from '@/stores/storage-config'

// 文件预览相关
import useFilePreview from '@/composables/file/useFilePreview'

let fileDataStore = useFileDataStore()
let storageConfigStore = useStorageConfigStore()

const {
  dialogVideoVisible,
  dialogTextVisible,
  dialogPdfVisible,
  dialogOfficeVisible,
  dialog3dVisible,
} = useFilePreview()

</script>

<style scoped>

</style>
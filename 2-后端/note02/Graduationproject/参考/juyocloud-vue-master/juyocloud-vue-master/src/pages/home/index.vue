<template>

  <div class="page-main" @contextmenu="showFileMenu">
    <toolbar-header/>
    <bread-crumb/>

    <div class="jy-pan-body">

      <div class="jy-pan-body-contain">
        <!--文件列表-->
        <el-table
            ref="fileTableRef"
            id="ListTable"
            class="pan-table"
            element-loading-text="拼命加载中"
            element-loading-background="rgba(255, 255, 255, 0.6)"
            empty-text="空列表"
            :row-class-name="tableRowClassName"
            v-if="!fileDataStore.imgMode"
            :data="fileDataStore.fileList"
            @row-contextmenu="showFileMenu"
            @row-click="tableClickRow"
            @sort-change="sortChangeMethod"
            @row-dblclick="tableDbClickRow"
            @cell-mouse-enter="tableHoverRow"
            @cell-mouse-leave="tableLeaveRow"
            @selection-change="selectRowsChange"
        >

          <!--      选择框-->
          <el-table-column
              type="selection"
              width="32"
              :selectable="checkSelectable"
          ></el-table-column>

          <!--      文件名-->
          <el-table-column
              prop="name"
              sortable="custom"
              label-class-name="table-header-left"
              class-name="juyo-table-col-name"
          >
            <template #header>
              <span>文件名</span>
            </template>
            <template #default="scope">

              <div>
                <svg-icon :name="'file-type-' + scope.row.icon"></svg-icon>
                <a class="name-link">{{ scope.row.name }}</a>
              </div>
            </template>
          </el-table-column>

          <!--      修改时间-->
          <el-table-column
              prop="time"
              sortable="custom"
              class-name="juyo-table-col-time"
              min-width="25%"
          >
            <template #header>
              <span>修改时间</span>
            </template>
            <template #default="scope">
              <div v-show="skeletonLoading">
                <el-skeleton animated>
                  <template #template>
                    <el-skeleton-item variant="text" style="width: 60%"/>
                  </template>
                </el-skeleton>
              </div>
              <div v-show="!skeletonLoading">
                {{ scope.row.time }}
              </div>
            </template>
          </el-table-column>

          <!--      大小-->
          <el-table-column
              prop="size"
              class-name="juyo-table-col-size"
              sortable="custom"
              min-width="20%"
          >
            <template #header>
              <span>大小</span>
            </template>
            <template #default="scope">
              <div v-show="skeletonLoading">
                <el-skeleton animated>
                  <template #template>
                    <el-skeleton-item variant="text" style="width: 30%"/>
                  </template>
                </el-skeleton>
              </div>
              <div v-show="!skeletonLoading">
                {{ common.fileSizeFilter(scope.row, null, scope.row.size) }}
              </div>
            </template>
          </el-table-column>


        </el-table>

        <!-- 右键菜单 -->
        <Contextmenu
            ref="contextmenu"
            auto-ajust-placement
        >
          <template v-if="contextMenuTargetFile">
            <ContextmenuItem
                v-show="storageConfigStore.permission.open"
                @click="openRow(selectRow)"
            >
              <el-icon class="contextmenu-icon">
                <FolderOpened/>
              </el-icon>
              <label>打开</label>
            </ContextmenuItem>

            <ContextmenuItem
                v-show="storageConfigStore.permission.download"
                @click="batchDownloadFile"
            >
              <el-icon class="contextmenu-icon">
                <i-custom-download
                    v-if="selectStatistics.isSingleSelect"
                    class="font-bold"
                />
                <i-custom-download-mult v-else/>
              </el-icon>
              <label>
                {{ selectStatistics.isSingleSelect ? '下载' : '批量下载' }}
              </label>
            </ContextmenuItem>

            <ContextmenuItem
                v-show="storageConfigStore.permission.link"
                @click="openLinkDialog"
            >
              <el-icon class="contextmenu-icon">
                <svg-icon class="inline" name="link"></svg-icon>
              </el-icon>
              <label>分享</label>
            </ContextmenuItem>
            <ContextmenuDivider
                v-show="
							storageConfigStore.permission.rename ||
							storageConfigStore.permission.delete
						"
            ></ContextmenuDivider>

            <ContextmenuItem
                v-show="storageConfigStore.permission.rename"
                @click="rename"
            >
              <el-icon class="contextmenu-icon">
                <svg-icon name="edit"/>
              </el-icon>
              <label>重命名</label>
            </ContextmenuItem>

            <ContextmenuItem @click="moveTo">
              <el-icon class="contextmenu-icon">
                <svg-icon class="inline" name="move"></svg-icon>
              </el-icon>
              <label>移动</label>
            </ContextmenuItem>

            <ContextmenuItem
                @click="copyTo">
              <el-icon class="contextmenu-icon">
                <i-ep-copyDocument/>
              </el-icon>
              <label>复制</label>
            </ContextmenuItem>

            <ContextmenuItem
                v-if="storageConfigStore.permission.delete"
                @click="batchDelete"
            >
              <el-icon class="contextmenu-icon" :size="14">
                <i-ep-delete/>
              </el-icon>
              <label
              >删除</label
              >
            </ContextmenuItem>

          </template>


          <template v-if="contextMenuTargetBlank">
            <ContextmenuItem @click="reload">
              <el-icon class="contextmenu-icon">
                <i-custom-refresh/>
              </el-icon>
              <label>刷新</label>
            </ContextmenuItem>
          </template>

        </Contextmenu>

        <!-- 悬浮菜单 -->
        <transition
            enter-active-class="animate__animated animate__fadeInUp animate__faster"
            leave-active-class="animate__animated animate__fadeOutDown animate__faster"
        >
          <div
              v-show="
						selectRows.length > 0 &&
						!fileDataStore.imgMode &&
						linkVisible === false
					"
              class="juyo-index-hover-tools"
          >
            <div class="juyo-index-hover-body">
              <template v-if="storageConfigStore.permission.preview">
                <el-tooltip
                    :show-arrow="false"
                    :offset="15"
                    effect="dark"
                    content="预览"
                    placement="top"
                >
                  <svg-icon
                      name="tool-preview"
                      @click="openRow(selectRow)"
                  ></svg-icon>
                </el-tooltip>
              </template>

              <template v-if="storageConfigStore.permission.download">
                <el-tooltip
                    :show-arrow="false"
                    :offset="15"
                    effect="dark"
                    content="下载"
                    placement="top"
                >
                  <svg-icon
                      name="tool-download"
                      @click="batchDownloadFile"
                  ></svg-icon>
                </el-tooltip>
              </template>

              <template v-if="storageConfigStore.permission.link">
                <el-tooltip
                    :show-arrow="false"
                    :offset="15"
                    effect="dark"
                    content="分享"
                    placement="top"
                >
                  <svg-icon name="tool-link" @click="openLinkDialog"></svg-icon>
                </el-tooltip>
              </template>

              <template v-if="storageConfigStore.permission.rename">
                <el-tooltip
                    :show-arrow="false"
                    :offset="15"
                    effect="dark"
                    content="重命名"
                    placement="top"
                >
                  <svg-icon name="tool-edit" @click="rename"></svg-icon>
                </el-tooltip>
              </template>
              <el-tooltip
                  :show-arrow="false"
                  :offset="15"
                  effect="dark"
                  content="移动"
                  placement="top">
                <svg-icon @click="moveTo" name="tool-move"></svg-icon>
              </el-tooltip>

              <template v-if="storageConfigStore.permission.delete">
                <el-tooltip
                    :show-arrow="false"
                    :offset="15"
                    effect="dark"
                    content="删除"
                    placement="top"
                >
                  <svg-icon name="tool-delete" @click="batchDelete"></svg-icon>
                </el-tooltip>
              </template>

              <el-tooltip
                  :show-arrow="false"
                  :offset="15"
                  :disabled="selectRows.length === 0"
                  effect="dark"
                  content="取消选择"
                  placement="top"
              >
                <svg-icon name="tool-close" @click="clearSelection"></svg-icon>
              </el-tooltip>
            </div>
          </div>
        </transition>

        <!-- 上传框 -->
        <upload/>

        <preview/>

        <!-- 回到顶部 -->
        <back-top
            v-show="globalConfigStore.juyoConfig.gallery.showBackTop"
        ></back-top>

        <!-- 弹窗文档 -->
        <el-dialog
            v-if="
					storageConfigStore.globalConfig.showDocument &&
					storageConfigStore.folderConfig.readmeText &&
					storageConfigStore.folderConfig.readmeDisplayMode === 'dialog' &&
					showDialog(storageConfigStore.folderConfig.readmeText)
				"
            draggable
            class="juyo-readme-dialog juyo-dialog-mini-close juyo-dialog-hidden-title"
            :model-value="true"
            @close="readmeDialogClose"
        >
          <v-md-preview
              :text="storageConfigStore.folderConfig.readmeText"
          ></v-md-preview>
        </el-dialog>

        <!-- 底部文档 -->
        <el-card
            v-if="
					storageConfigStore.globalConfig.showDocument &&
					storageConfigStore.folderConfig.readmeText &&
					storageConfigStore.folderConfig.readmeDisplayMode === 'bottom'
				"
            class="mt-5"
        >
          <v-md-preview
              :text="storageConfigStore.folderConfig.readmeText"
          ></v-md-preview>
        </el-card>


      </div>

    </div>

  </div>
</template>

<script setup>
// element 图标
import {FolderOpened} from '@element-plus/icons-vue'
import useFileDataStore from "@/stores/file-data";
import useFileSelect from "@/composables/file/useFileSelect";
import useFileData from "@/composables/file/useFileData";
import common from "@/common";
import useStorageConfigStore from '@/stores/storage-config'

// 右键菜单
import {Contextmenu, ContextmenuDivider, ContextmenuItem} from 'v-contextmenu'
import useFileContextMenu from '@/composables/file/useFileContextMenu'

// 文件操作相关
import useFileOperator from '@/composables/file/useFileOperator'
import useTableOperator from "@/composables/file/useTableOperator";
import useFileLink from "@/composables/file/useFileLink";
// 文件预览相关
import useGlobalConfigStore from '@/stores/global-config'
import useRouterData from '@/composables/useRouterData'
import md5 from 'md5'
import MarkdownViewerAsyncLoading from '@/components/file/preview/MarkdownViewerAsyncLoading.vue'

let fileDataStore = useFileDataStore()
let storageConfigStore = useStorageConfigStore()
let globalConfigStore = useGlobalConfigStore()
const currentInstance = getCurrentInstance()
const {rename, batchDownloadFile, moveTo, copyTo, newFolder, batchDelete} = useFileOperator()
const {showFileMenu, contextMenuTargetFile, contextMenuTargetBlank} = useFileContextMenu(currentInstance)
const {tableClickRow, tableDbClickRow, tableHoverRow, tableLeaveRow} = useTableOperator()

// 直链打开
const {openLinkDialog, visible: linkVisible} = useFileLink()

let route = useRoute()
let router = useRouter()


const {
  openRow,
  sortChangeMethod,
  skeletonLoading,
  loadFile,
  loadFileConfig,
} = useFileData()

let {
  checkSelectable,
  selectRowsChange,
  selectRow,
  selectRows,
  selectStatistics,
  tableRowClassName,
  clearSelection,
} = useFileSelect(currentInstance)

// markdown viewer 组件懒加载, 节约首屏打开时间
const VMdPreview = defineAsyncComponent({
  loader: () => {
    return new Promise((resolve, reject) => {
      ;(async function () {
        try {
          const res = await import('@kangc/v-md-editor/lib/preview')
          import('@kangc/v-md-editor/lib/style/preview.css')
          import('@kangc/v-md-editor/lib/theme/style/github.css')
          const hljs = await import('highlight.js')
          const githubTheme = await import(
              '@kangc/v-md-editor/lib/theme/github.js'
              )
          res.use(githubTheme, {
            Hljs: hljs,
          })
          resolve(res)
        } catch (error) {
          reject(error)
        }
      })()
    })
  },
  loadingComponent: MarkdownViewerAsyncLoading,
})

const reload = () => {
  loadFile()
}

// 切换存储源或路径时，重新加载文件列表
watch(
    () => [route.params.storageKey, route.params.fullpath],
    () => {
      loadFileAndConfig()
    }
)

onBeforeMount(() => {
  loadFileAndConfig()
})

const readmeDialogClose = () => {
  ElMessageBox.confirm('在公告变更前是否不再显示此公告?', '提示', {
    confirmButtonText: '是',
    cancelButtonText: '否',
    draggable: true,
    callback: (action) => {
      if (action === 'confirm') {
        let key = storageKey.value + '_' + currentPath.value
        readmeDialogCache.value[key] = md5(
            storageConfigStore.folderConfig.readmeText
        )
      }
    },
  })
}

const readmeDialogCache = useStorage(`juyo-readme-dialog-cache`, {})
let { storageKey, currentPath } = useRouterData()

const showDialog = (readmeText) => {
  for (let key of Object.keys(readmeDialogCache.value)) {
    if (
        key === storageKey.value + '_' + currentPath.value &&
        readmeDialogCache.value[key] === md5(readmeText)
    ) {
      return false
    }
  }

  return true
}

const loadFileAndConfig = () => {
  if (route.params.storageKey) {
    loadFile()
    loadFileConfig()
  }
}


</script>

<style lang="stylus">
.v-contextmenu
  width: 128px;
  border-radius 6px

  .v-contextmenu-item
    font-size 13px
    padding: 10px 14px;
    color #03081a

    &:hover
      background-color: #f5f6fa;

    .contextmenu-icon
      margin-right: 12px;

.el-table.pan-table
  user-select none

  .el-table__body-wrapper
    .juyo-table-row
      .el-table-column--selection > .cell
        padding: 0 12px;

      .juyo-table-col-name
        padding: 10px 0;

        .icon
          font-size 1.5rem
          line-height: 2rem;
          margin-right: 15px;

        .name-link
          cursor pointer
          overflow: hidden;
          white-space: nowrap;
          text-overflow: ellipsis;

          &:hover
            color #409eff

      .juyo-table-col-time, .juyo-table-col-size
        cursor default


.jy-pan-body
  .juyo-index-hover-tools
    position: absolute;
    z-index: 10
    left: 0
    right: 0
    margin-left: auto;
    margin-right: auto;
    width: fit-content;
    bottom: 2.5rem;

    .juyo-index-hover-body
      background-color: #313136;
      width: fit-content;
      padding-left: 1.25rem;
      padding-right: 1.25rem;
      height: 3rem
      padding-top: 0.5rem;
      padding-bottom: 0.5rem;
      color: #fff;
      border-radius: 0.25rem;
      font-size: 1.5rem;
      line-height: 2rem;
      margin-right: auto
      margin-left: auto

      & > :not([hidden]) ~ :not([hidden])
        --tw-space-x-reverse: 0;
        margin-right: calc(1.5rem * var(--tw-space-x-reverse));
        margin-left: calc(1.5rem * calc(1 - var(--tw-space-x-reverse)));

      svg
        display inline
        color #fff
        cursor pointer
        outline: 2px solid transparent;
        outline-offset: 2px;
        vertical-align: -0.15em;

        &:hover
          color #409eff
.el-dialog
  border-radius 20px
.el-overlay
  background-color: rgba(0,0,0,0.22)
  backdrop-filter blur(10px)
</style>

<style lang="scss" scoped>

// dialog 相关
.juyo-index-body {
  // juyo dialog body 高度
  .juyo-dialog-body_height {
    @apply h-[80vh] sm:h-[85vh] overflow-auto;
  }

  // juyo dialog 宽度大屏
  .juyo-dialog-wide-screen {
    @apply w-11/12;
  }

  // 迷你关闭模式
  :deep(.juyo-dialog-mini-close) {
    .el-dialog__header .el-icon {
      @apply text-white;
    }
    .el-dialog__headerbtn {
      @apply mt-0 -right-3 -top-0.5 h-5 w-5
      bg-gray-600 hover:bg-blue-500
      rounded-full box-content border-2 border-solid border-white;

      :deep(svg) {
        @apply text-white font-bold;
      }
    }
  }

  // 隐藏标题模式
  :deep(.juyo-dialog-hidden-title) {
    .el-dialog__header {
      @apply p-0;
    }
    .el-dialog__title {
      @apply hidden #{'!important'};
    }
  }

  /* dialog 高度减少，标题居中 */
  :deep(.el-dialog__header) {
    @apply -mt-3 py-1 text-center ml-2;

    /* 修正去除边框后关闭按钮错位的问题 */
    .el-dialog__headerbtn {
      @apply -mt-3.5;
    }

    // 弹窗标题最多一行
    .el-dialog__title {
      @apply line-clamp-1;
    }
  }

  /* 去除 dialog 打开后默认滚动条 */
  :deep(.el-overlay-dialog) {
    @apply overflow-hidden;
  }

  // dialog 距离顶部的高度
  :deep(.el-dialog) {
    @apply mt-8 sm:mt-10 #{!important};
  }

  // 视频弹窗样式
  :deep(.juyo-video-dialog) {
    @extend .juyo-dialog-wide-screen;
    .el-dialog__body {
      @apply p-0; // 去除所有间距
    }
  }

  // 文本弹窗
  :deep(.juyo-text-dialog) {
    @extend .juyo-dialog-wide-screen;
    .el-dialog__body {
      @apply py-4 px-1; // 左右间距和上下间距
    }
  }

  // pdf 弹窗
  :deep(.juyo-pdf-dialog) {
    .el-dialog__body {
      @extend .juyo-dialog-body_height;
    }
  }

  // readme 弹窗
  :deep(.juyo-readme-dialog) {
    .el-dialog__body {
      @extend .juyo-dialog-body_height;
    }
  }

  // office 弹窗
  :deep(.juyo-office-dialog) {
    @extend .juyo-dialog-wide-screen;
    .el-dialog__body {
      @extend .juyo-dialog-body_height;
      @apply p-0;
    }
  }
}
</style>
<route lang="yaml">
meta:
  layout: Layout
  name: Home
</route>
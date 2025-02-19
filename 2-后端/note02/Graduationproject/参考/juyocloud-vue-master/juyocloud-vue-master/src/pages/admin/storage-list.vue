<template>
  <div>
    <div>
      <h3 class="text-xl leading-6 font-medium text-gray-400 mb-3 mt-3">存储源信息</h3>
    </div>

    <el-card v-loading="loading" shadow="never" body-style="padding-top: 0;padding-bottom: 0;">
      <ul
          role="list"
          style="margin: 38px 0;"
          class="storage-container grid grid-cols-1 gap-6 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-4"
      >
        <li
            v-for="storage in storageListSearchResult"
            :key="storage.id"
            class="storage-item group col-span-1 flex flex-col text-center rounded-xl hover:shadow-md"
            @click="editStorage(storage)"
            style="cursor: pointer;background-color:#fafafa;"
        >
          <div class="flex-1 flex flex-col p-8 pb-4 relative">
            <svg-icon
                name="target"
                class="absolute left-3 top-3 text-sm text-blue-500 opacity-0 group-hover:opacity-100"
                @click="openStorage(storage.key)"
            ></svg-icon>
            <el-dropdown
                class="right-5 top-0.5 p-3 inline-block z-20"
                @command="handleOperator"
                style="position:absolute;"
            >
						<span
                class="relative w-0 text-sm text-gray-700 font-medium border border-transparent rounded-bl-lg"
            >
									<span class="text-sm font-medium">
										<el-icon :size="18">
											<MoreFilled/>
										</el-icon>
									</span>
								</span>

              <template #dropdown>
                <el-dropdown-menu>
                  <el-dropdown-item
                      :icon="Edit"
                      :command="{ operator: 'edit', storage }"
                  >编辑
                  </el-dropdown-item
                  >
                  <el-dropdown-item
                      v-if="!storage.enable"
                      :icon="VideoPlay"
                      :command="{ operator: 'enable', storage }"
                  >启用
                  </el-dropdown-item
                  >
                  <el-dropdown-item
                      v-else
                      :icon="VideoPause"
                      :command="{ operator: 'enable', storage }"
                  >停用
                  </el-dropdown-item
                  >
                  <div class="divider"></div>
                  <el-dropdown-item
                      :icon="Document"
                      :command="{ operator: 'readmeManager', storage }"
                  >目录文档
                  </el-dropdown-item
                  >
                  <el-dropdown-item
                      :icon="View"
                      :command="{ operator: 'filterManager', storage }"
                  >文件过滤
                  </el-dropdown-item
                  >
                  <el-dropdown-item
                      :icon="Key"
                      :command="{ operator: 'pwdManager', storage }"
                  >密码设置
                  </el-dropdown-item
                  >
                  <div class="divider"></div>
                  <el-dropdown-item
                      :icon="Delete"
                      :command="{ operator: 'delete', storage }"
                  >删除
                  </el-dropdown-item
                  >
                </el-dropdown-menu>
              </template>
            </el-dropdown>
            <div class="rounded-full w-fit mx-auto mt-5">
              <img
                  class="w-24 h-24 flex-shrink-0 mx-auto"
                  :src="getImg(storage.type.key)"
              />
            </div>
            <h3 class="mt-2 text-gray-900 text-sm font-medium">
              {{ storage.name }}
            </h3>
            <dl class="mt-1 flex-grow flex flex-col justify-between">
              <dd class="text-gray-500 text-sm line-clamp-1">
                {{ storage.remark }}
              </dd>
              <dd class="mt-2 space-x-1">
                <el-tag type="">{{ storage.type.description }}</el-tag>
                <el-tag
                    v-show="storage.enable"
                    type="success"
                    @click="switchEnableStatus(storage)"
                >启用
                </el-tag
                >
                <el-tag
                    v-show="!storage.enable"
                    type="danger"
                    @click="switchEnableStatus(storage)"
                >停用
                </el-tag
                >
                <el-tag v-show="storage.enableCache" type="success"
                >缓存
                </el-tag
                >

                <el-popover placement="top" :width="250" trigger="hover">
                  <div>
                    <div class="text-sm font-medium">
                      <svg-icon name="check" class="inline text-green-500"/>
                      刷新令牌成功
                    </div>
                    <div class="text-xs text-gray-500">
                      上次刷新时间:
                      {{ storage?.refreshTokenInfo?.lastRefreshTime }}
                    </div>
                  </div>
                  <template #reference>
                    <el-tag
                        v-show="storage?.refreshTokenInfo?.success"
                        type="success"
                    >刷新令牌成功
                    </el-tag
                    >
                  </template>
                </el-popover>
                <el-popover placement="top" :width="250" trigger="hover">
                  <div>
                    <div class="text-sm font-medium">
                      <svg-icon name="error" class="inline text-red-500"/>
                      刷新令牌失败
                    </div>
                    <div class="text-xs text-gray-500">
                      上次刷新时间:
                      {{ storage?.refreshTokenInfo?.lastRefreshTime }}
                    </div>
                    <div class="text-xs text-red-500">
                      失败信息: {{ storage?.refreshTokenInfo?.msg }}
                    </div>
                  </div>
                  <template #reference>
                    <el-tag
                        v-show="storage?.refreshTokenInfo?.success === false"
                        type="danger"
                    >刷新令牌失败
                    </el-tag
                    >
                  </template>
                </el-popover>
              </dd>
            </dl>
          </div>

        </li>
        <li
            class="add-storage-btn cursor-pointer col-span-1 flex flex-col text-center bg-white rounded-lg border hover:shadow"
            @click="addStorage"
        >
          <div class="flex-1 flex flex-col p-8 mx-auto justify-center">
            <Plus class="h-20 text-gray-300"/>
          </div>
        </li>
      </ul>
    </el-card>
  </div>
</template>

<script setup>
import {
  Search,
  Delete,
  Edit,
  Key,
  Lock,
  VideoPause,
  VideoPlay,
  View,
  Document,
  Plus,
  MoreFilled,
} from '@element-plus/icons-vue'

let router = useRouter()

import useStorageList from '@/composables/admin/storage/storage-list'

const {
  init,
  loading,
  searchKey,
  storageListSearchResult,
  handleOperator,
  editStorage,
  addStorage,
  deleteStorage,
  switchEnableStatus,
} = useStorageList(router)

onMounted(() => {
  init()
})

const openStorage = (key) => {
  window.open(`/${key}`)
}

const getImg = (name) => {
  const path = `/src/assets/icons/${name}.svg`
  const modules = import.meta.globEager('/src/assets/icons/*')
  if (modules[path]) {
    return modules[path].default
  }
}
</script>

<style scoped>
.el-row {
  padding: 20px;
}

.el-form-item {
  margin-right: 50px;
}

.card-title {
  color: rgba(0, 0, 0, 0.45);
  font-size: 14px;
}

.card-content {
  color: rgba(0, 0, 0, 0.85);
  font-size: 25px;
  line-height: 30px;
}

.card-title-button {
  float: right;
  padding: 3px 0;
}

.table-search-input {
  width: 300px;
  float: right;
}

#filterForm .el-row {
  padding: 0;
}

#cacheDialog >>> .el-dialog__body {
  padding: 20px;
}

.table-edit-icon {
  margin-left: 5px;
  color: #409eff;
  cursor: pointer;
}

.current-layout {
  color: #409eff;
}

.storage-container li {
  @apply h-[17rem];
}
</style>

<route lang="yaml">
meta:
  layout: admin
  name: 存储源管理
</route>

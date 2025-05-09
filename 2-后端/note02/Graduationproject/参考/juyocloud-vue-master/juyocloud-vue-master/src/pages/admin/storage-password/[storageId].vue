<template>
	<el-card body-style="padding: 0;">
    <z-form
        v-loading="loading"
        :model="passwordList"
        class="zfile-admin-password-form"
    >
      <template #form-title>
        <div class="flex justify-items-center">
          <router-link to="/admin/storage-list">
            <svg-icon
                class="inline mr-2 cursor-pointer"
                name="file-type-back"
            ></svg-icon>
          </router-link>
          <span>密码文件夹（{{ storageItem?.name }}）</span>
        </div>
      </template>
      <template #form-sub-title>
        <el-alert :closable="false" type="info">
          <div class="rules-tips">Glob 表达式规则：</div>
          <div class="rules-tips">
            <b>单层根目录加密</b>: 如
            <span class="select-all code">/</span> 表示根路径下需要密码访问.
          </div>
          <div class="rules-tips">
            <b>单层子目录加密</b>: 如
            <span class="select-all code">/music/*</span> 表示根目录下的 music
            文件夹需要密码访问, 子文件夹不加密.
          </div>
          <div class="rules-tips">
            <b>嵌套子目录加密</b>: 如
            <span class="select-all code">/music/**</span> 表示根目录下的 music
            文件夹及其所有子文件夹都需要密码访问.
          </div>
          <div class="rules-tips">
            注：系统匹配到第一个符合的规则就会取密码进行校验，并返回结果，所以请调整好规则顺序，下方规则可进行拖拽排序。
          </div>
          <div class="rules-tips-link">
            <a
                target="_blank"
                class="link"
                href="http://www.ruanyifeng.com/blog/2018/09/bash-wildcards.html"
            ><Collection class="inline mr-1"></Collection
            ><span>参考文章 (Wikipedia)</span></a
            >
            <a
                target="_blank"
                class="link"
                href="http://www.ruanyifeng.com/blog/2018/09/bash-wildcards.html"
            ><Collection class="inline mr-1"></Collection>参考文章 (阮一峰)</a
            >
            <a
                target="_blank"
                class="link"
                href="https://github.com/whinc/blog/issues/18"
            ><Collection class="inline mr-1"></Collection>参考文章 (Github)</a
            >
          </div>
        </el-alert>
      </template>

      <z-form-item
          v-for="(item, index) in passwordList"
          :key="item"
          :required="true"
          class="expression-item"
      >
        <div class="sm:flex sm:space-x-2 sm:border-b-0 sm:pb-0 border-b pb-2">
          <el-tooltip
              content="此处可填写表达书描述，用于辅助记忆，防止时间过长后不知道表达式含义."
              placement="top"
          >
            <el-input
                v-model="item.description"
                :prefix-icon="Notebook"
                placeholder="请输入表达式描述"
            ></el-input>
          </el-tooltip>
          <el-input
              v-model="item.expression"
              :prefix-icon="FolderIcon"
              placeholder="请输入表达式"
          ></el-input>
          <el-input
              v-model="item.password"
              :prefix-icon="KeyIcon"
              placeholder="请输入密码"
              type="password"
              show-password
          ></el-input>
          <el-button
              type="danger"
              :icon="Delete"
              @click="deletePasswordItem(index)"
          ></el-button>
        </div>
      </z-form-item>

      <z-form-item>
        <el-button
            type="primary"
            size="default"
            :icon="Plus"
            @click="addPasswordItem"
        >添加更多</el-button
        >
      </z-form-item>

      <template #footer>
        <el-button
            type="primary"
            size="default"
            :icon="BadgeCheckIcon"
            @click="savePasswordData"
        >保存设置</el-button
        >
      </template>
    </z-form>
  </el-card>
</template>

<script setup>
import { Plus, Delete, Collection, Notebook } from '@element-plus/icons-vue'
import { BadgeCheckIcon } from '@heroicons/vue/solid'
import { KeyIcon, FolderIcon } from '@heroicons/vue/outline'
import ZForm from '/src/components/form/ZForm.vue'
import ZFormItem from '/src/components/form/ZFormItem.vue'

let route = useRoute()
let router = useRouter()
let currentStorageId = route.params.storageId

import useStoragePassword from '@/composables/admin/storage/storage-password.js'
import { loadStorageItemReq } from '@/api/admin-storage'
const {
	loading,
	loadPasswordData,
	passwordList,
	addPasswordItem,
	deletePasswordItem,
	savePasswordData,
} = useStoragePassword(router, route)

onMounted(() => {
	loadPasswordData()
	loadStorageItem()
})

const storageItem = ref()
// 加载指定存储源的数据
const loadStorageItem = () => {
	loadStorageItemReq(currentStorageId).then((res) => {
		res.data.type = res.data.type.key
		storageItem.value = res.data
	})
}
</script>

<style lang="scss" scoped>
.expression-item {
	:deep(.el-input__wrapper) {
		@apply w-full sm:w-36 md:w-48 lg:w-64 xl:w-80;
	}
}

.zfile-admin-password-form {
	:deep(.z-form-sub-title) {
		max-width: 100%;
	}

	.rules-tips {
		@apply text-sm font-bold p-1;
	}

	.rules-tips-link {
		@apply space-x-5 mt-2;
		svg {
			height: 1rem;
			line-height: 1.25rem;
			vertical-align: text-bottom;
		}
	}
}
</style>

<route lang="yaml">
meta:
  layout: admin
  name: 编辑存储源
</route>

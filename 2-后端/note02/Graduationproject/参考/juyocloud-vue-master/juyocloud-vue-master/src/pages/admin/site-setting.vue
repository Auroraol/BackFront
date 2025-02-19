<template>
  <div>
    <div>
      <h3 class="text-xl leading-6 font-medium text-gray-400 mb-3 mt-3">基本设置</h3>
    </div>

    <el-card shadow="never" body-style="padding-top: 0;">
      <z-form
          style="padding: 5px;"
          v-if="data"
          ref="siteSettingForm"
          v-loading="saveLoading"
          :model="data"
          :rules="dataRules"
      >
        <z-form-item prop="siteName" label="站点名称">
          <el-input id="siteName" v-model="data.siteName"/>
        </z-form-item>

        <z-form-item prop="domain" label="后端站点域名">
          <el-input id="domain" v-model="data.domain" />
          <template #tips
          >此地址用于生成直链及本次存储下载使用，请务必保持和服务端地址一样.</template
          >
        </z-form-item>

        <z-form-item prop="frontDomain" label="前端站点域名">
          <el-input v-model="data.frontDomain"/>
          <template #tips
          >前后端分离后，需配置此地址，会影响 401、403、404 页面的跳转.</template
          >
        </z-form-item>

        <z-form-item prop="avatar" label="头像地址">
          <el-input id="avatar" v-model="data.avatar"/>
          <template #tips
          >用于管理员页面右上角头像地址，推荐尺寸为 35 * 35.</template
          >
        </z-form-item>

        <z-form-item label="备案号">
          <el-input id="icp" v-model="data.icp"/>
        </z-form-item>

        <z-form-item label="最大同时上传文件数">
          <el-input-number
              id="maxFileUploads"
              v-model="data.maxFileUploads"
              :min="1"
              :max="99"
          />
        </z-form-item>

        <template #footer>
          <el-button
              type="primary"
              size="default"
              :icon="BadgeCheckIcon"
              @click="saveForm"
          >保存设置</el-button
          >
        </template>
      </z-form>
    </el-card>
  </div>
</template>

<script setup>
import { Tickets, Link, Avatar } from '@element-plus/icons-vue'
import { BadgeCheckIcon, ShieldCheckIcon } from '@heroicons/vue/solid'

import useGlobalConfigStore from '@/stores/global-config'
let globalConfigStore = useGlobalConfigStore()

import useSiteSetting from '@/composables/admin/site/useSiteSetting'
const { data, dataLoading, saveData, saveLoading } = useSiteSetting()

let dataRules = ref({
	siteName: [{ required: true, message: '请输入站点名称' }],
	domain: [
		{ required: true, message: '请输入后端站点域名' },
		{ type: 'url', message: '请输入正确的域名' },
	],
	frontDomain: [{ type: 'url', message: '请输入正确的域名' }],
	avatar: [
		{ type: 'url', message: '请输入正确的头像地址，需以 http 或 https 开头' },
	],
})

const siteSettingForm = ref()
const saveForm = () => {
	siteSettingForm.value.validate((checked) => {
		if (checked) {
			saveData()
		}
	})
}

watch(
	() => dataLoading.value,
	(newVal, oldValue) => {
		// 如果是刚加载完成, 则检测域名配置是否正确.
		if (oldValue === true) {
			let serverDomain =
				globalConfigStore.juyoConfig.baseUrl || window.location.origin
			let siteDomain = data.value.domain

			if (serverDomain !== siteDomain) {
				ElMessageBox.confirm(
					`检测到服务端地址为 ${serverDomain}，当前配置站点域名为 ${siteDomain}，是否自动进行修正？（不修正可能会影响下载、文件夹加密和文档预览功能）`,
					'提示',
					{
						confirmButtonText: '确定',
						cancelButtonText: '取消',
						type: 'warning',
						callback: (action) => {
							if (action === 'confirm') {
								data.value.domain = serverDomain
								saveForm()
							}
						},
					}
				)
			} else {
				let siteFrontDomain = data.value.frontDomain
				let origin = window.location.origin
				if (
					globalConfigStore.juyoConfig.baseUrl !== '' &&
					siteFrontDomain !== origin
				) {
					ElMessageBox.confirm(
						`检测到当前为前后端分离模式，访问域名为 ${origin}，当前配置前端站点域名为 ${siteFrontDomain}，是否自动进行修正？（不修正可能会防盗链功能）`,
						'提示',
						{
							confirmButtonText: '确定',
							cancelButtonText: '取消',
							type: 'warning',
							callback: (action) => {
								if (action === 'confirm') {
									data.value.frontDomain = origin
									saveForm()
								}
							},
						}
					)
				}
			}
		}
	}
)
</script>

<style lang="scss" scoped>
.el-button {
	height: 35px;
}
.el-input {
	:deep(input) {
		height: 35px;
	}
}
</style>

<route lang="yaml">
meta:
  layout: admin
  name: 基本设置
</route>

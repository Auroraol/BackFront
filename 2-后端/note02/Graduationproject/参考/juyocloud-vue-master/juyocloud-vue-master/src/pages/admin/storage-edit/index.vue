<template>
	<el-card body-style="padding: 0;">
    <z-form
        ref="storageEditForm"
        :model="storageItem"
        class="juyo-storage-edit"
        :rules="rules"
    >
      <template #form-title>
        <div class="flex justify-items-center">
          <router-link to="/admin/storage-list">
            <svg-icon
                class="inline mr-2 cursor-pointer"
                name="file-type-back"
            ></svg-icon>
          </router-link>
          <span>存储源信息</span>
        </div>
      </template>
      <template #form-sub-title>
        请维护您的存储源信息
      </template>

      <z-form-item label="存储源名称" prop="name">
        <el-input v-model="storageItem.name" />
      </z-form-item>

      <z-form-item label="存储源别名" prop="key">
        <el-input v-model="storageItem.key" />
        <template #tips>
          存储源别名，用于 URL 中展示, 如 {{ systemConfig?.domain
          }}{{ storageItem.key || '{存储源别名}' }}
        </template>
      </z-form-item>

      <z-form-item label="存储源备注">
        <el-input
            v-model="storageItem.remark"
            type="textarea"
            :rows="3"
            placeholder="请输入存储源备注"
        />
        <template #tips>
          存储源备注信息, 用于辅助管理员区分不同的存储源, 此字段仅管理员可见
        </template>
      </z-form-item>

      <z-form-item label="存储策略" prop="type">
        <el-select
            v-model="storageItem.type"
            :disabled="isEditMode"
            filterable
            placeholder="请选择存储策略"
        >
          <el-option
              v-for="item in supportStorageType"
              :key="item.key"
              :label="item.description"
              :value="item.key"
          >
          </el-option>
        </el-select>
      </z-form-item>

      <z-form-item label="是否启用">
        <el-switch v-model="storageItem.enable" />
        <template #tips> 如不启用，则在前台不显示，且不可访问. </template>
      </z-form-item>

      <z-form-item label="启用文件操作">
        <el-switch v-model="storageItem.enableFileOperator" />
        <template #tips> 是否启用文件上传，编辑，删除等操作. </template>
      </z-form-item>

      <z-form-item
          v-show="storageItem.enableFileOperator"
          label="允许匿名文件操作"
      >
        <el-switch v-model="storageItem.enableFileAnnoOperator" />
        <template #tips>
          开启后所有人都可进行文件操作，反之仅管理员登录后可操作
        </template>
      </z-form-item>

      <z-form-item label="是否默认打开画廊模式">
        <el-switch v-model="storageItem.defaultSwitchToImgMode" />
        <template #tips>
          启用后，每次切换到此存储源，是否默认打开画廊模式
        </template>
      </z-form-item>

      <z-form-item
          v-for="item in storageSourceParamList"
          :key="item.name"
          :label="item.name"
          :required="item.required"
          :prop="'storageSourceAllParam.' + item.key"
      >
        <!-- Endpoint -->
        <el-select
            v-if="
					item.key === 'endPoint' && region.hasOwnProperty(storageItem.type)
				"
            v-model="storageItem.storageSourceAllParam.endPoint"
            filterable
            allow-create
            default-first-option
            style="width: 100%"
        >
          <el-option
              v-for="endPoint in region[storageItem.type]"
              :key="endPoint.name"
              :label="endPoint.name"
              :value="endPoint.val"
          >
            <span style="float: left">{{ endPoint.name }}</span>
            <span style="float: right; color: #8492a6; font-size: 13px">{{
                endPoint.val
              }}</span>
          </el-option>
        </el-select>

        <!-- sharepoint 网站列表 -->
        <el-select
            v-else-if="item.key === 'siteId' && isSharePoint(storageItem.type)"
            v-model="storageItem.storageSourceAllParam.siteId"
            filterable
            placeholder="请选择站点列表"
            class="input-with-select"
        >
          <el-option
              v-for="option in sharepointSites"
              :key="option.id"
              :label="option.displayName"
              :value="option.id"
          >
					<span style="float: left">
						{{ option.displayName }}
						<el-tag
                v-if="option.displayName === 'Communication site'"
                type="success"
            >默认网站</el-tag
            >
					</span>
            <el-popover
                placement="right"
                width="400"
                trigger="hover"
                :content="option.webUrl"
            >
              <template #reference>
                <el-link
                    target="_blank"
                    :icon="Link"
                    :href="option.webUrl"
                    :underline="false"
                    class="float-right"
                    type="primary"
                >查看网站</el-link
                >
              </template>
            </el-popover>
          </el-option>
        </el-select>

        <!-- sharepoint 网站子目录 -->
        <el-select
            v-else-if="item.key === 'listId' && isSharePoint(storageItem.type)"
            v-model="storageItem.storageSourceAllParam.listId"
            filterable
            placeholder="请选择网站子目录"
            class="input-with-select"
        >
          <el-option
              v-for="option in sharepointSiteLists"
              :key="option.id"
              :label="option.displayName"
              :value="option.id"
          >
					<span style="float: left">
						{{ option.displayName }}
						<el-tag
                v-if="option.displayName === 'Communication site'"
                type="success"
            >默认网站</el-tag
            >
					</span>
            <el-popover
                placement="right"
                width="400"
                trigger="hover"
                :content="option.webUrl"
            >
              <template #reference>
                <el-link
                    target="_blank"
                    :icon="Link"
                    :href="option.webUrl"
                    :underline="false"
                    class="float-right"
                    type="primary"
                >查看网站</el-link
                >
              </template>
            </el-popover>
          </el-option>
        </el-select>

        <!-- gd 网盘选择 -->
        <el-select
            v-else-if="item.key === 'driveId' && drivesList?.length > 0"
            v-model="storageItem.storageSourceAllParam.driveId"
            filterable
            placeholder="请选择驱动器列表"
            class="input-with-select"
        >
          <el-option value="" label="默认空间">
            <span class="float-left"> 默认空间 </span>
            <span class="float-right">
						<el-tag type="primary" class="float-right"> 个人盘 </el-tag>
					</span>
          </el-option>
          <el-option
              v-for="option in drivesList"
              :key="option.id"
              :label="option.name"
              :value="option.id"
          >
					<span class="float-left">
						{{ option.name }}
					</span>
            <el-popover
                placement="left"
                width="400"
                trigger="hover"
                :content="option.id"
            >
              <template #reference>
                <el-tag type="success" class="float-right"> 团队盘 </el-tag>
              </template>
            </el-popover>
          </el-option>
        </el-select>

        <!-- s3 存储器列表 -->
        <el-select
            v-else-if="item.key === 'bucketName' && bucketList?.length > 0"
            v-model="storageItem.storageSourceAllParam.bucketName"
            filterable
            placeholder="请选择存储器列表"
            class="input-with-select"
        >
          <el-option
              v-for="option in bucketList"
              :key="option.name"
              :label="option.name"
              :value="option.name"
          >
					<span class="float-left">
						{{ option.name }}
					</span>
            <span class="float-right">
						{{ option.date }}
					</span>
          </el-option>
        </el-select>

        <!-- 通用下拉选择 -->
        <template v-else-if="item.type === 'select'">
          <el-select
              v-model="storageItem.storageSourceAllParam[item.key]"
              filterable
              default-first-option
          >
            <el-option
                v-for="option in item.options"
                :label="option.label"
                :value="option.value"
            ></el-option>
          </el-select>
        </template>

        <!-- 通用 switch 开关 -->
        <template v-else-if="item.type === 'switch'">
          <el-switch v-model="storageItem.storageSourceAllParam[item.key]" />
        </template>

        <!-- 通用 input -->
        <template v-else-if="item.type === 'input'">
          <el-input v-model="storageItem.storageSourceAllParam[item.key]" />
        </template>

        <div v-else>
          服务端配置错误, 字段 {{ item.key }} 类型为 {{ item.type }},
          不支持此类型的渲染.
        </div>

        <template #tips>
          <!-- 通用链接 -->
          <div v-if="item.link">
					<span v-if="item.key === 'accessToken'">
						<el-link
                target="_blank"
                :icon="Link"
                :href="
								item.link +
								'?clientId=' +
								encodeURIComponent(
									storageItem.storageSourceAllParam['clientId']
								) +
								'&clientSecret=' +
								encodeURIComponent(
									storageItem.storageSourceAllParam['clientSecret']
								) +
								'&redirectUri=' +
								encodeURIComponent(
									storageItem.storageSourceAllParam['redirectUri']
								)
							"
            >
							{{ item.linkName }}
						</el-link>
						<svg-icon
                class="inline cursor-pointer ml-2 text-lg relative top-0.5"
                name="copy"
                @click="
								copyText(
									item.link +
										'?clientId=' +
										encodeURIComponent(
											storageItem.storageSourceAllParam['clientId']
										) +
										'&clientSecret=' +
										encodeURIComponent(
											storageItem.storageSourceAllParam['clientSecret']
										) +
										'&redirectUri=' +
										encodeURIComponent(
											storageItem.storageSourceAllParam['redirectUri']
										)
								)
							"
            ></svg-icon>
					</span>
            <el-link v-else target="_blank" :icon="Link" :href="item.link">{{
                item.linkName
              }}</el-link>
          </div>

          <!-- 通用描述信息 -->
          <div v-if="item.description" v-html="item.description"></div>
          <!--  onedrive 动态地址提示 -->
          <div
              v-if="
						item.key === 'redirectUri' &&
						item.description &&
						['onedrive', 'sharepoint'].includes(storageItem.type)
					"
          >
            如：{{ systemConfig.domain }}onedrive/callback
            <svg-icon
                class="inline cursor-pointer ml-1"
                name="copy"
                @click="copyText(systemConfig.domain + 'onedrive/callback')"
            ></svg-icon>
          </div>
          <div
              v-if="
						item.key === 'redirectUri' &&
						item.description &&
						['onedrive-china', 'sharepoint-china'].includes(storageItem.type)
					"
          >
            如：{{ systemConfig.domain }}onedrive/china-callback
            <svg-icon
                class="inline cursor-pointer ml-1"
                name="copy"
                @click="copyText(systemConfig.domain + 'onedrive/china-callback')"
            ></svg-icon>
          </div>
          <div
              v-if="
						item.key === 'redirectUri' &&
						item.description &&
						storageItem.type === 'google-drive'
					"
          >
            如：{{ systemConfig.domain }}gd/callback
            <svg-icon
                class="inline cursor-pointer ml-1"
                name="copy"
                @click="copyText(systemConfig.domain + 'gd/callback')"
            ></svg-icon>
          </div>
        </template>
      </z-form-item>

      <template #footer>
        <el-button
            :loading="loading"
            type="primary"
            size="default"
            :icon="BadgeCheckIcon"
            @click="submitForm"
        >保存设置</el-button
        >
      </template>
    </z-form>
  </el-card>
</template>

<script setup>
import common from '@/common'

import { Link } from '@element-plus/icons-vue'
import { BadgeCheckIcon } from '@heroicons/vue/solid'

import region from '@/tool/region'

import ZForm from '/src/components/form/ZForm.vue'
import ZFormItem from '/src/components/form/ZFormItem.vue'

import {
	loadStorageItemReq,
	loadSupportStorageReq,
	storageParamsReq,
	saveStorageSettingReq,
	existStorageKeyReq,
} from '@/api/admin-storage'
import { ElMessageBox } from 'element-plus'
import {
	loadSharePointSiteListsReq,
	loadSharePointSitesReq,
} from '@/api/sharepoint'
import { loadS3BucketsReq } from '@/api/s3'
import { loadGDDrivesReq } from '@/api/gd'
import { loadConfigReq } from '@/api/admin-setting'
import { toClipboard } from '@soerenmartius/vue3-clipboard'

const systemConfig = ref()

let router = useRouter()
let route = useRoute()

// 当前正在编辑的存储源 key
let currentEditStorageKey = null

let storageEditForm = ref()
const submitForm = () => {
	storageEditForm.value.validate((checked) => {
		if (checked) {
			loading.value = true
			saveStorageSettingReq(storageItem.value)
				.then(() => {
					isEditMode.value = true
					ElMessageBox.confirm('存储策略保存成功', '提示', {
						confirmButtonText: '确定',
						cancelButtonText: '取消',
						type: 'success',
						callback: (action) => {
							if (action === 'confirm') {
								router.push('/admin/storage-list')
							}
						},
					})
				})
				.finally(() => {
					loading.value = false
				})
		}
	})
}

/**
 * 初始化支持的存储源列表和存储源数据
 */
let useInitData = () => {
	// 存储源数据
	let storageItem = ref({
		orderNum: null,
		name: '',
		type: '',
		remark: '',
		alias: '',
		defaultSwitchToImgMode: false,
		enable: true,
		enableFileOperator: true,
		enableFileAnnoOperator: false,
		searchEnable: false,
		searchIgnoreCase: false,
		searchMode: 'SEARCH_CACHE',
		enableCache: false,
		autoRefreshCache: false,
		storageSourceAllParam: {
			endPoint: '',
			pathStyle: '',
			isPrivate: false,
			accessKey: null,
			secretKey: null,
			bucketName: null,
			host: null,
			port: null,
			filePath: null,
			accessToken: null,
			refreshToken: null,
			secretId: null,
			username: null,
			password: null,
			basePath: '',
			domain: '',
			listId: '',
			redirectUri: '',
			siteId: '',
			proxyDomain: '',
			downloadLinkType: '',
			clientId: '',
			clientSecret: '',
			region: '',
			autoConfigCors: false,
			driveId: '',
		},
	})

	// 加载指定存储源的数据
	const loadStorageItem = (storageId) => {
		loadStorageItemReq(storageId).then((res) => {
			res.data.type = res.data.type.key
			storageItem.value = res.data
			currentEditStorageKey = res.data.key
		})
	}

	let isEditMode = ref(false)

	// 支持的所有存储策略
	let supportStorageType = ref([])
	onMounted(() => {
		// 获取支持的所有存储引擎
		loadSupportStorageReq().then((response) => {
			supportStorageType.value = response.data
		})

		loadConfigReq().then((res) => {
			systemConfig.value = res.data
			if (!systemConfig.value.domain.endsWith('/')) {
				systemConfig.value.domain += '/'
			}
		})

		// 获取 url 参数中的存储源 id, 如果没 id, 表示是新增, 不初始化加载数据
		let storageId = route.params.storageId
		if (storageId) {
			isEditMode.value = true
			loadStorageItem(storageId)
		}
	})

	// loading
	let loading = ref(false)
	// 表单校验规则
	let rules = ref({
		name: [{ required: true, message: '请输入存储源名称' }],
		key: [
			{
				validator: (rule, value, callback) => {
					if (value === undefined || value === null || value === '') {
						callback()
						return
					}

					let systemNames = [
						'admin',
						'file',
						'login',
						'install',
						's',
						'onedrive',
						'api',
						'sharepoint',
						's3',
					]
					if (systemNames.includes(value)) {
						callback(new Error('不可占用系统级名称，请修改。'))
						return
					}

					let reg = /^[\w\-]+$/
					if (!reg.test(value)) {
						callback(new Error('只允许使用字母、数字、下划线、横杠'))
						return
					}

					if (currentEditStorageKey === value) {
						callback()
						return
					}
					existStorageKeyReq({ storageKey: value }).then((res) => {
						if (res.data) {
							callback(new Error('该存储源别名已存在，请修改。'))
						} else {
							callback()
						}
					})
				},
			},
		],
		type: [{ required: true, message: '存储策略不能为空' }],
		'storageSourceAllParam.domain': [
			{
				type: 'url',
				message: '请输入正确的域名，需以 http:// 或 https:// 开头',
			},
			{
				validator: (rule, value, callback) => {
					if (value === undefined || value === null || value === '') {
						callback()
						return
					}

					if (
						window.location.protocol === 'https:' &&
						value.indexOf('http://') === 0
					) {
						callback(
							new Error(
								'检测到当前 juyo 站点是 https 协议, 受浏览器限制, 此处也必须是 https 协议, 否则可能无法正常使用.'
							)
						)
						return
					}

					callback()
				},
			},
		],
	})

	return { storageItem, supportStorageType, loading, rules, isEditMode }
}
let { storageItem, supportStorageType, loading, rules, isEditMode } =
	useInitData()

let useS3Util = () => {
	let bucketList = ref([])

	let s3Credentials = computed(() => {
		return {
			accessKey:
				storageItem.value.storageSourceAllParam.accessKey ||
				storageItem.value.storageSourceAllParam.secretId,
			secretKey: storageItem.value.storageSourceAllParam.secretKey,
			endPoint: storageItem.value.storageSourceAllParam.endPoint,
			region: storageItem.value.storageSourceAllParam.region,
		}
	})

	watch(
		() => s3Credentials.value,
		(val) => {
			if (!common.storageType.s3Type.includes(storageItem.value.type)) {
				return
			}
			if (val.accessKey && val.secretKey && val.endPoint) {
				loadSharePointSites(val)
			}
		}
	)

	const loadSharePointSites = (s3Credentials) => {
		loadS3BucketsReq(s3Credentials)
			.then((res) => {
				bucketList.value = res.data
			})
			.catch((e) => {
				bucketList.value = []
			})
	}

	return { bucketList }
}

let { bucketList } = useS3Util()

let useGDUtil = () => {
	let drivesList = ref([])

	let param = computed(() => {
		return {
			accessToken: storageItem.value.storageSourceAllParam.accessToken,
		}
	})

	watch(
		() => param.value,
		(val) => {
			if (storageItem.value.type !== 'google-drive') {
				return
			}
			loadDrives(val)
		}
	)

	const loadDrives = (param) => {
		loadGDDrivesReq(param)
			.then((res) => {
				drivesList.value = res.data
			})
			.catch((e) => {
				drivesList.value = []
			})
	}

	return { drivesList }
}

let { drivesList } = useGDUtil()

/**
 * sharepoint 工具
 */
let useSharePointUtil = () => {
	let sharepointSites = ref([])
	let sharepointSiteLists = ref([])

	const isSharePoint = (type) => {
		return type.indexOf('sharepoint') !== -1
	}

	const getSharePointType = (storageType) => {
		return storageType === 'sharepoint' ? 'Standard' : 'China'
	}

	watch(
		() => storageItem.value.storageSourceAllParam.accessToken,
		(newVal) => {
			if (newVal) {
				loadSharePointSites()
			}
		}
	)

	const loadSharePointSites = () => {
		let storageType = storageItem.value.type
		if (isSharePoint(storageType)) {
			let param = {
				type: getSharePointType(storageType),
				accessToken: storageItem.value.storageSourceAllParam.accessToken,
			}

			loadSharePointSitesReq(param).then((res) => {
				sharepointSites.value = res.data
			})
		}
	}

	watch(
		() => storageItem.value.storageSourceAllParam.siteId,
		(newVal) => {
			if (newVal) {
				loadSharePointSiteLists()
			}
		}
	)

	const loadSharePointSiteLists = () => {
		let storageType = storageItem.value.type
		if (isSharePoint(storageType)) {
			let param = {
				type: getSharePointType(storageType),
				accessToken: storageItem.value.storageSourceAllParam.accessToken,
				siteId: storageItem.value.storageSourceAllParam.siteId,
			}

			loadSharePointSiteListsReq(param).then((res) => {
				sharepointSiteLists.value = res.data
			})
		}
	}

	return { sharepointSites, sharepointSiteLists, isSharePoint }
}
let { sharepointSites, sharepointSiteLists, isSharePoint } = useSharePointUtil()

/**
 * 获取存储策略的参数列表, 及监听切换存储策略时, 自动重新加载参数列表.
 */
let useLoadStorageSourceParamList = () => {
	// 监听切换存储策略
	watch(
		() => storageItem.value.type,
		(newVal) => {
			loadStorageSourceParamList(newVal)
		}
	)

	// 获取指定存储策略的所有参数
	let storageSourceParamList = ref([])
	const loadStorageSourceParamList = (storageType) => {
		if (!storageType) {
			return
		}
		storageParamsReq({ storageType }).then((res) => {
			storageSourceParamList.value = res.data
			for (let storageSourceParam of storageSourceParamList.value) {
				if (!isEditMode.value && storageSourceParam.defaultValue) {
					if (storageSourceParam.defaultValue === 'true') {
						storageSourceParam.defaultValue = true
					} else if (storageSourceParam.defaultValue === 'false') {
						storageSourceParam.defaultValue = false
					}
					storageItem.value.storageSourceAllParam[storageSourceParam.key] =
						storageSourceParam.defaultValue
				} else if (
					storageSourceParam.key === 'redirectUri' &&
					!storageItem.value.storageSourceAllParam[storageSourceParam.key]
				) {
					storageItem.value.storageSourceAllParam[storageSourceParam.key] =
						storageSourceParam.defaultValue
				}
			}
		})
	}

	return { storageSourceParamList }
}
let { storageSourceParamList } = useLoadStorageSourceParamList()

/**
 *  复制
 */
let copyText = (text) => {
	toClipboard(text).then(() => {
		ElMessage.success('复制成功')
	})
}
</script>

<style scoped>
.el-drive-form-col {
	padding-left: 0 !important;
}

.juyo-site-id-input-site-type-select {
	width: 100px;
}

.juyo-info-tooltip {
	line-height: 32px;
}

.juyo-storage-edit >>> .z-form-item-input > .el-select {
	width: 100%;
}
</style>

<route lang="yaml">
meta:
  layout: admin
  name: 新增存储源
</route>

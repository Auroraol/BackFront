<template>
	<div class="juyo-admin-down-link">
    <div>
      <h3 class="text-xl leading-6 font-medium text-gray-400 mb-3 mt-3">直链日志</h3>
    </div>

		<el-card shadow="never">
			<div class="flex justify-between">
				<div
					v-if="data"
					class="flex space-x-1.5 justify-center items-center cursor-pointer"
				>
					<span class="text-gray-400 text-sm font-bold">记录下载日志：</span>
					<el-switch
						v-model="data.recordDownloadLog"
						active-text="是"
						inline-prompt
						inactive-text="否"
						@change="saveData"
					></el-switch>

				</div>
        <el-button type="primary" plain @click="logDownload" :icon="Download">下载日志</el-button>

      </div>

			<div class="mt-4">
				<el-form v-model="searchParam" inline>
					<el-form-item label="存储源">
						<el-select
							v-model="searchParam.storageKey"
							clearable
							:teleported="false"
						>
							<el-option
								v-for="item in storageList"
								:key="item.id"
								:label="item.name"
								:value="item.key"
							>
								<div class="flex justify-between space-x-20">
									<span>{{ item.name }}</span>
									<span class="text-gray-400">{{ item.type.description }}</span>
								</div>
							</el-option>
						</el-select>
					</el-form-item>
					<el-form-item label="短链 Key">
						<el-input v-model="searchParam.shortKey"></el-input>
					</el-form-item>
					<el-form-item label="文件名">
						<el-input v-model="searchParam.path"></el-input>
					</el-form-item>
					<el-form-item label="请求 IP">
						<el-input v-model="searchParam.ip"></el-input>
					</el-form-item>
					<el-form-item label="UserAgent">
						<el-input v-model="searchParam.userAgent"></el-input>
					</el-form-item>
					<el-form-item label="Referer">
						<el-input v-model="searchParam.referer"></el-input>
					</el-form-item>
					<el-form-item label="创建时间">
						<el-date-picker
							v-model="searchParam.date"
							type="daterange"
							value-format="YYYY-MM-DD HH:mm:ss"
							:default-time="[
								new Date(2000, 1, 1, 0, 0, 0),
								new Date(2000, 2, 1, 23, 59, 59),
							]"
							range-separator="至"
							start-placeholder="开始时间"
							end-placeholder="结束时间"
						/>
					</el-form-item>

				</el-form>

        <div style="margin: 10px 0;">
          <el-button type="primary" :icon="Search" @click="init">查询</el-button>
          <el-button :icon="Delete" type="danger" @click="batchDeleteLink">批量删除</el-button>
        </div>

				<el-table border ref="linkTableRef" size="large" :data="pageData">
					<el-table-column type="selection" width="55" />
					<el-table-column width="120" label="存储源名称">
						<template #default="scope">
							<el-tooltip
								:content="scope.row.storageType?.description"
								placement="right"
							>
								<span>{{ scope.row.storageName }}</span>
							</el-tooltip>
						</template>
					</el-table-column>
					<el-table-column
						show-tooltip-when-overflow
						prop="shortKey"
						label="短链 key"
					>
						<template #default="scope">
							<div class="space-x-2">
								<span>{{ scope.row.shortKey }}</span>
								<svg-icon
									class="inline cursor-pointer"
									name="copy"
									@click="copyText(scope.row.shortKey)"
								></svg-icon>
								<svg-icon
									class="inline cursor-pointer text-blue-500 text-sm"
									name="target"
									@click="openLink(scope.row.shortKey)"
								></svg-icon>
							</div>
						</template>
					</el-table-column>
					<el-table-column show-tooltip-when-overflow prop="path" label="路径">
					</el-table-column>
					<el-table-column
						width="100"
						show-tooltip-when-overflow
						prop="ip"
						label="请求 ip"
					>
					</el-table-column>
					<el-table-column
						width="180"
						show-tooltip-when-overflow
						prop="userAgent"
						label="UserAgent"
					>
					</el-table-column>
					<el-table-column
						width="150"
						show-tooltip-when-overflow
						prop="referer"
						label="Referer"
					>
					</el-table-column>
					<el-table-column width="180" prop="createTime" label="创建时间">
					</el-table-column>
					<el-table-column width="120" label="操作">
						<template #default="scope">
							<el-popconfirm
								title="是否确认删除?"
								@confirm="deleteLink(scope.row.id)"
							>
								<template #reference>
									<el-button :icon="Delete" type="danger">删除</el-button>
								</template>
							</el-popconfirm>
						</template>
					</el-table-column>
				</el-table>

				<el-config-provider :locale="zhCn">
					<el-pagination
						v-model:current-page="searchParam.page"
						class="mt-3 justify-end"
						:page-size="searchParam.limit"
						background
						layout="total, sizes, prev, pager, next, jumper"
						:default-current-page="10"
						:page-sizes="[10, 50, 100, 200, 1000, 99999999]"
						:total="searchParam.total"
						@current-change="handleCurrentChange"
						@size-change="handleSizeChange"
					/>
				</el-config-provider>
			</div>
		</el-card>
	</div>
</template>

<script setup>
import useLinkSetting from '@/composables/admin/link/useLinkSetting'
const { data, saveData, saveLoading } = useLinkSetting()
let router = useRouter()
let route = useRoute()

const {  logDownload } = useAdminLayout(router, route)
import {
	batchDeleteDownloadLog,
	deleteDownloadLog,
	getDownloadLogList,
} from '@/api/admin-download-link'
import zhCn from 'element-plus/lib/locale/lang/zh-cn'

import { Search, Delete, Download } from '@element-plus/icons-vue'
import { loadStorageListReq } from '@/api/admin-storage'
import { toClipboard } from '@soerenmartius/vue3-clipboard'
import { loadConfigReq } from '@/api/admin-setting'
import useAdminLayout from "@/composables/admin/layout/admin-layout";

const searchParam = reactive({
	shortKey: '',
	storageKey: null,
	page: 1,
	limit: 10,
	path: '',
	ip: '',
	userAgent: '',
	referer: '',
	date: '',
	dateFrom: '',
	dateTo: '',
	total: 0,
})

const handleSizeChange = (val) => {
	searchParam.limit = val
	searchParam.page = 1
	init()
}

const handleCurrentChange = (val) => {
	searchParam.page = val
	init()
}

const pageData = ref()

const init = () => {
	if (searchParam.date instanceof Array) {
		searchParam.dateFrom = searchParam.date[0]
		searchParam.dateTo = searchParam.date[1]
	} else {
		searchParam.dateFrom = ''
		searchParam.dateTo = ''
	}
	getDownloadLogList(searchParam).then((res) => {
		pageData.value = res.data
		searchParam.total = res.dataCount
	})
}

onMounted(() => {
	init()
	loadSourceList()
	loadSystemConfig()
})

const systemConfig = ref()
const loadSystemConfig = () => {
	loadConfigReq().then((res) => {
		systemConfig.value = res.data
	})
}

const storageList = ref()
const loadSourceList = () => {
	loadStorageListReq().then((response) => {
		storageList.value = response.data
	})
}

const deleteLink = (id) => {
	deleteDownloadLog(id).then((res) => {
		ElMessage.success('删除成功')
		init()
	})
}

const linkTableRef = ref()

const batchDeleteLink = () => {
	let selectionRows = linkTableRef.value.getSelectionRows()
	if (selectionRows.length === 0) {
		ElMessage.warning('请至少选择一条数据')
		return
	}

	ElMessageBox.confirm('是否确认删除？', '提示', {
		type: 'warning',
	}).then(() => {
		let ids = selectionRows.map((item) => item.id)
		batchDeleteDownloadLog({ ids: ids }).then((res) => {
			ElMessage.success('删除成功')
			init()
		})
	})
}

/**
 *  复制直链
 */
let copyText = (text) => {
	toClipboard(text).then(() => {
		ElMessage.success('复制成功')
	})
}

/**
 * 打开短链
 */
let openLink = (shortLink) => {
	window.open(`${systemConfig.value.domain}/s/${shortLink}`)
}
</script>

<style scoped lang="scss">
.juyo-admin-down-link {
	:deep(.el-select-dropdown__item) {
		padding-right: 15px;
	}

	:deep(.el-dialog__header) {
		text-align: center;
	}

	:deep(.el-dialog__body) {
		height: 80vh;
		overflow-y: auto;
	}
}
</style>

<route lang="yaml">
meta:
  layout: admin
  name: 直链日志
</route>

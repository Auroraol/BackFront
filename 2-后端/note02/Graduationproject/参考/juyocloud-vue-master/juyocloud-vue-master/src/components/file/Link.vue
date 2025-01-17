<template>
	<div v-if="visible" class="juyo-file-download-link-body">
		<el-dialog
			v-model="visible"
			:destroy-on-close="true"
			:title="data.row.name"
      align-center
			:class="
				selectFiles.length > 1
					? 'juyo-file-download-link-dialog-multiple'
					: 'juyo-file-download-link-dialog-single'
			"
			draggable
			top="5vh"
			@close="visible = false;showQrcode = false"
		>

      <div>
        <div
            v-if="selectFiles.length > 1"
            style="float:left;color:#777d88;">
          点击行可复制链接
        </div>

        <div style="float: right;">
          <el-button
              v-show="selectFiles.length > 1"
              @click="exportExcel"
          >导出</el-button
          >
          <el-button
              v-show="selectFiles.length > 1"
              type="primary"
              @click="batchCopyLinkField('pathLink')"
          >
            批量复制链接
          </el-button>
        </div>
      </div>

			<el-table
				v-if="selectFiles.length > 1"
				v-loading="loading"
				element-loading-text="生成中..."
				class="juyo-download-link-table"
				:max-height="height * 0.7"
				:data="datas"
				@cell-click="handleCellClick"
			>
				<el-table-column prop="name" show-tooltip-when-overflow>
					<template #header="scope">
						文件名
					</template>
					<template #default="scope">
						{{ scope.row.name }}
					</template>
				</el-table-column>

				<el-table-column
					v-if="storageConfigStore.permission.pathLink"
					prop="pathLink"
					show-overflow-tooltip
				>
					<template #header="scope">
						直链
					</template>
					<template #default="scope">
						{{ scope.row.pathLink }}
					</template>
				</el-table-column>

				<el-table-column
					v-if="storageConfigStore.permission.shortLink"
					prop="shortLink"
					show-overflow-tooltip
					width="250"
				>
					<template #header="scope">
						链接
					</template>
					<template #default="scope">
						{{ scope.row.shortLink }}
					</template>
				</el-table-column>


      </el-table>

			<el-row justify="center" v-if="selectFiles.length === 1 && data" class="md:space-y-6">
				<el-image class="w-40" style="margin-bottom: 12px;" v-if="showQrcode" ref="qrcodeRef" :src="data.currentImg" />
        <el-button v-show="!showQrcode" round style="margin: auto;" @click="showQrcode = true">生成二维码</el-button>
				<div class="w-full">
          <div style="margin: 12px 0">
            <el-icon color="green">
              <i-ep-circleCheckFilled/>
            </el-icon>
            成功创建分享链接
          </div>
					<el-form>
						<el-form-item v-if="storageConfigStore.permission.shortLink">
							<el-tooltip
								append-to=".juyo-file-download-link-body"
								popper-class="juyo-link-tips"
								placement="left"
								content="点击复制链接"
							>
								<el-input
									v-model="data.shortLink"
									type="small"
                  size="large"
									@click="copyText(data.shortLink)"
								>
								</el-input>
							</el-tooltip>
						</el-form-item>
					</el-form>

          <div class="file-info">
            <div class="size">{{data.row.size}}</div>
            <div class="size">{{data.row.time}}</div>
          </div>
				</div>
			</el-row>

		</el-dialog>
	</div>
</template>

<script setup>
import { Calendar, Coin, Link, Document } from '@element-plus/icons-vue'

// 组件传参及回写
const props = defineProps({
	rowData: {
		type: Object,
	},
})

import useStorageConfigStore from '@/stores/storage-config'
let storageConfigStore = useStorageConfigStore()

let router = useRouter()
let route = useRoute()

import useFileSelect from '@/composables/file/useFileSelect'
let { selectFiles } = useFileSelect()

import useFileLink from '@/composables/file/useFileLink'
let { visible, loading, copyText, data, datas, generateALlLink } = useFileLink()

const showQrcode = ref(false)

watch(
	() => visible.value,
	(value) => {
		if (value) {
			if (selectFiles.value.length === 0) {
				ElMessage.warning('请至少选择一个文件')
			} else {
				if (!storageConfigStore.permission.link) {
					ElMessage.error('没有权限生成直链或短链')
					return
				}
				generateALlLink(selectFiles.value)
			}
		} else {
			datas.value = []
		}
	}
)

const batchCopyLinkField = (filed) => {
	let links = []
	let fields = filed.split('.')
	datas.value.forEach((row) => {
		let value = row
		fields.forEach((item) => {
			value = row.name + ":   " +value[item]
		})
		links.push(value)
	})
	copyText(links.join('\n'))
}

import useCommon from '@/composables/useCommon'
const { height } = useCommon()

import FileSaver from 'file-saver'
import { utils, write } from 'xlsx'

const exportExcel = () => {
	let xlsxParam = { raw: true }
	// 设置当前日期
	let time = new Date()
	let year = time.getFullYear()
	let month = time.getMonth() + 1
	let day = time.getDate()
	let name = year + '' + month + '' + day
	/* generate workbook object from table */
	//  .excelTable要导出的是哪一个表格   注意这里的 excelTable 是要导出的表格的类名
	let wb = utils.table_to_book(
		document.querySelector('.juyo-download-link-table'),
		xlsxParam
	)

	let colsWidth = [{ wch: 50 }, { wch: 50 }, { wch: 50 }]
	datas.value.forEach((item) => {
		let col1Length = item.name.length
		let col2Length = item.pathLink.length
		let col3Length = item.shortLink.length

		if (col1Length > colsWidth[0].wch) {
			colsWidth[0].wch = col1Length
		}
		if (col2Length > colsWidth[1].wch) {
			colsWidth[1].wch = col2Length + 20
		}
		if (col3Length > colsWidth[2].wch) {
			colsWidth[2].wch = col3Length
		}
	})

	wb.Sheets.Sheet1['!cols'] = colsWidth

	/* get binary string as output */
	let wbout = write(wb, {
		bookType: 'xlsx',
		bookSST: true,
		type: 'array',
	})
	try {
		//  name+'.xlsx'表示导出的excel表格名字
		FileSaver.saveAs(
			new Blob([wbout], { type: 'application/octet-stream' }),
			name + 'juyo 直链导出.xlsx'
		)
	} catch (e) {
		if (typeof console !== 'undefined') {
			ElMessage.error(e + wbout)
		}
	}
	return wbout
}

const handleCellClick = (row, column, cell, event) => {
  let value = row.name + ":   " + row.shortLink
	copyText(value)
}
</script>

<style scoped lang="scss">
.juyo-file-download-link-body {
	:deep(.juyo-file-download-link-dialog-single) {
		@apply w-10/12 md:w-96;

		.el-form-item {
			margin-bottom: 8px;
		}
	}

	:deep(.juyo-file-download-link-dialog-multiple) {
    width: 55rem;
	}

	:deep(.juyo-link-tips) {
		@apply hidden md:block;
	}
}
</style>

<style lang="stylus">
.file-info
  display flex
  justify-content: space-between;
  margin-top: 20px;
  div
    background-color: #f6f6f6;
    padding: 5px 5px;
    border-radius 4px
    color #97979d
</style>

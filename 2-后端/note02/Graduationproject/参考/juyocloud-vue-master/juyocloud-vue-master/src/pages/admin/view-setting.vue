<template>
	<div>
    <div>
      <h3 class="text-xl leading-6 font-medium text-gray-400 mb-3 mt-3">显示设置</h3>
    </div>

		<el-card shadow="never" body-style="padding: 0;padding-bottom: 20px;">
      <z-form
          v-if="data"
          v-loading="saveLoading"
          :model="data"
          element-loading-text="保存中..."
          style="padding-top: 0;padding-bottom: 0;"
      >
        <z-form-item label="根目录显示所有存储源">
          <el-switch v-model="data.rootShowStorage" />
          <template #tips>
            根目录是否显示所有存储源, 如果为 true, 则根目录显示所有存储源列表,
            如果为 false, 则会自动跳转到第一个存储源.
          </template>
        </z-form-item>

        <z-form-item label="文件操作习惯">
          <el-radio v-model="data.fileClickMode" label="click">单击进入</el-radio>
          <el-radio v-model="data.fileClickMode" label="dbclick"
          >双击进入</el-radio
          >
          <template #tips>
            控制文件和文件夹的点击习惯, 单击/双击打开文件夹或预览文件
          </template>
        </z-form-item>

        <z-form-item label="视频文件后缀">
          <el-input v-model="data.customVideoSuffix"></el-input>
          <template #tips>
            自定义识别为视频格式的文件后缀，多个用逗号分开，如 'mp4,avi,mkv',
            在此列表中的将调用播放器打开（能否播放要取决于浏览器是否支持，现代浏览器一般只支持封装格式为
            h.264 (mp4) 的编码格式）
          </template>
        </z-form-item>

        <z-form-item label="图像文件后缀">
          <el-input v-model="data.customImageSuffix"></el-input>
        </z-form-item>

        <z-form-item label="音频文件后缀">
          <el-input v-model="data.customAudioSuffix"></el-input>
        </z-form-item>

        <z-form-item label="文本文件后缀">
          <el-input v-model="data.customTextSuffix"></el-input>
        </z-form-item>

        <z-form-item label="显示文档区">
          <el-switch v-model="data.showDocument" />
          <el-tooltip placement="right">
            <template #content> 在文件列表下，显示当前文件夹的目录文档 </template>
            <i class="el-icon-info zfile-info-tooltip"></i>
          </el-tooltip>
        </z-form-item>

        <z-form-item label="显示公告">
          <el-switch v-model="data.showAnnouncement" />
          <el-tooltip placement="right">
            <template #content> 网站顶部，显示公告内容，支持 HTML 语法 </template>
            <i class="el-icon-info zfile-info-tooltip"></i>
          </el-tooltip>
        </z-form-item>

        <z-form-item label="公告内容">
          <template #tips>
            支持 Markdown 语法, 左右分栏, 所见即所得, 可以使用 HTML 语法
          </template>
        </z-form-item>
        <br>
        <v-md-editor v-model="data.announcement" height="400px"></v-md-editor>


        <z-form-item label="自定义 CSS">
          <el-input
              v-model="data.customCss"
              type="textarea"
              :autosize="{ minRows: 3 }"
              placeholder="请输入自定义 CSS 内容"
          >
          </el-input>
          <template #tips>
            自定义 CSS 内容, 无须写 &#60;style&#62;&#60;/style&#62; 标签
          </template>
        </z-form-item>

        <z-form-item label="自定义 JS">
          <el-input
              v-model="data.customJs"
              type="textarea"
              :autosize="{ minRows: 3 }"
              placeholder="请输入自定义 JS 内容"
          >
          </el-input>
          <template #tips>
            自定义 JS 脚本, &#60;script&#62;&#60;/script&#62;
            可写可不写，会自动兼容.
          </template>
        </z-form-item>

        <template #footer>
          <el-button
              type="primary"
              size="default"
              :icon="BadgeCheckIcon"
              @click="saveData"
          >保存设置</el-button
          >
        </template>
      </z-form>
    </el-card>
	</div>
</template>

<script setup>
// markdown editor 组件懒加载, 节约首屏打开时间
const VMdEditor = defineAsyncComponent(() => {
	return new Promise((resolve, reject) => {
		;(async function () {
			try {
				const res = await import('@kangc/v-md-editor')
				await import('@kangc/v-md-editor/lib/style/base-editor.css')
				await import('@kangc/v-md-editor/lib/theme/style/vuepress.css')

				const vuepressTheme = await import(
					'@kangc/v-md-editor/lib/theme/vuepress'
				)
				const Prism = await import('prismjs')

				res.use(vuepressTheme, {
					Prism,
				})

				resolve(res)
			} catch (error) {
				reject(error)
			}
		})()
	})
})

import { BadgeCheckIcon } from '@heroicons/vue/solid'

import useViewSetting from '@/composables/admin/view/useViewSetting'
const { data, saveData, saveLoading } = useViewSetting()
</script>

<style scoped>
.zfile-info-tooltip {
	line-height: 32px;
}
</style>

<route lang="yaml">
meta:
  layout: admin
  name: 显示设置
</route>

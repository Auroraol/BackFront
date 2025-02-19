<template>
	<div class="juyo-admin-down-link-body">
    <div>
      <h3 class="text-xl leading-6 font-medium text-gray-400 mb-3 mt-3">直链设置</h3>
    </div>

    <el-card shadow="never" body-style="padding: 0;">
      <z-form
          v-if="data"
          v-loading="saveLoading"
          :model="data"
          element-loading-text="保存中..."
      >
        <z-form-item label="直链地址前缀">
          <el-input v-model="data.directLinkPrefix"></el-input>
          <template #tips>
            直链地址前缀, 如 http(s)://ip:port/<span
              class="text-red-400 font-bold"
          >{{ data.directLinkPrefix }}</span
          >/path/filename
          </template>
        </z-form-item>

        <z-form-item label="显示生成直/短链功能">
          <el-switch v-model="data.showLinkBtn" />
          <template #tips>
            仅控制是否显示直/短链生成按钮，不影响使用允许使用直/短链下载
          </template>
        </z-form-item>

        <z-form-item label="记录下载日志">
          <el-switch v-model="data.recordDownloadLog" />
          <template #tips> 是否记录直链下载日志 </template>
        </z-form-item>

        <z-form-item label="是否允许使用短链">
          <el-switch v-model="data.showShortLink" />
          <template #tips>
            控制是否生成直链时显示短链路径及是否允许使用短链进行下载
          </template>
        </z-form-item>

        <z-form-item label="是否允许使用直链（路径）">
          <el-switch v-model="data.showPathLink" />
          <template #tips>
            控制是否生成直链时显示直链路径及是否允许使用直链进行下载
          </template>
        </z-form-item>

        <z-form-item label="是否允许路径直链可直接访问">
          <el-switch v-model="data.allowPathLinkAnonAccess" />
          <template #tips>
					<span
          >是否允许未通过 "生成直链" 功能的直链可访问 (仅表示可访问,
						如未开启上方 "是否允许使用直链（路径）"
						功能，则即使访问了也不允许下载.)</span
          >
            <br /><br />
            <span
            >因路径直链的格式是文件路径和文件名，所以很容易被猜到并访问,
						如您不想未生成直链就可直接访问，可考虑使用此功能控制.</span
            >
          </template>
        </z-form-item>

        <z-form-item label="Referer 防盗链">
          <el-radio v-model="data.refererType" label="off"
          >不启用 Referer 防盗链</el-radio
          >
          <el-radio v-model="data.refererType" label="white_list"
          >启用白名单</el-radio
          >
          <el-radio v-model="data.refererType" label="black_list"
          >启用黑名单</el-radio
          >
          <template #tips>
            防盗链支持访问文件直链或短链时校验，如用户直接访问直链跳转后的存储源原始链接，无法进行校验和拦截。
          </template>
        </z-form-item>

        <z-form-item v-show="data.refererType !== 'no'" label="Referer 配置">
          <el-radio v-model="data.refererAllowEmpty" :label="true"
          >允许 Referer 为空</el-radio
          >
          <el-radio v-model="data.refererAllowEmpty" :label="false"
          >禁止 Referer 为空</el-radio
          >
        </z-form-item>

        <z-form-item v-show="data.refererType !== 'no'" label="白名单">
          <el-input
              v-model="data.refererValue"
              type="textarea"
              :rows="6"
              placeholder="每行输入一个域名"
          >
          </el-input>
          <template v-if="data.refererType === 'white_list'" #tips>
            每行输入一个域名，支持 * 通配符，如白名单 *juyovo.com 将只允许
            juyovo.com、www.juyovo.com、demo.juyovo.com 等网站访问。
          </template>
          <template v-if="data.refererType === 'black_list'" #tips>
            每行输入一个域名，支持 * 通配符，如黑名单 *juyovo.com 将禁止所有如
            juyovo.com、www.juyovo.com、demo.juyovo.com 等网站访问。
          </template>
        </z-form-item>

        <template #footer>
				<span class="dialog-footer">
					<el-button type="primary" @click="saveData">保存</el-button>
				</span>
        </template>
      </z-form>
    </el-card>
	</div>
</template>

<script setup>
import useLinkSetting from '@/composables/admin/link/useLinkSetting'
const { data, saveData, saveLoading } = useLinkSetting()
</script>

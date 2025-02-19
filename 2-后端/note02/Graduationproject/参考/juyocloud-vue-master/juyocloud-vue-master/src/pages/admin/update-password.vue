<template>
	<z-form
		ref="updatePasswordForm"
		v-loading="updateLoading"
		:model="passwordData"
		:rules="passwordDataRules"
	>
		<template #form-title> 密码信息 </template>
		<template #form-sub-title>
			此处可以修改您的管理员登录账号密码，请妥善保管
		</template>

		<z-form-item prop="username" label="管理员账号">
			<el-input
				id="username"
				v-model.trim="passwordData.username"
				:prefix-icon="User"
			/>
		</z-form-item>

		<z-form-item prop="password" label="新密码">
			<el-input
				id="password"
				v-model.trim="passwordData.password"
				:prefix-icon="Key"
			/>
		</z-form-item>

		<z-form-item prop="repassword" label="重复新密码">
			<el-input
				id="repassword"
				v-model.trim="passwordData.repassword"
				:prefix-icon="Key"
			/>
		</z-form-item>

		<template #footer>
			<el-button
				type="primary"
				size="default"
				:icon="BadgeCheckIcon"
				@click="submitForm"
				>保存设置</el-button
			>
		</template>
	</z-form>
</template>

<script setup>
import { User, Key } from '@element-plus/icons-vue'
import { BadgeCheckIcon } from '@heroicons/vue/solid'

import usePassword from '@/composables/admin/password/usePassword'
const { passwordData, updateLoading, updatePassword, passwordDataRules } =
	usePassword()

let updatePasswordForm = ref()

const submitForm = () => {
	updatePassword(updatePasswordForm)
}
</script>

<style scoped></style>

<route lang="yaml">
meta:
  layout: admin
  name: 直链管理
</route>

<template>
	<div
		class="h-full min-h-screen bg-gray-100 text-gray-900 flex justify-center items-center"
	>
		<div
			class="h-full lg:h-auto max-w-screen-lg bg-white shadow-xl sm:rounded-xl flex flex-1"
      style="max-width: 38rem;margin-top: -8rem;"
		>
			<div class="w-full p-6" style="padding: 2rem 3rem;">
				<div class="mt-0 flex flex-col items-center">
					<h1
						class="text-2xl xl:text-3xl font-extrabold box animate__animated animate__fadeInDown mt-2 mb-10"
					>
            让我们先来设置下
					</h1>
					<div class="w-full flex-1">

						<el-form
							ref="installFormRef"
							:rules="formRules"
							:model="formData"
							class="juyo-install-form"
							size="large"
              label-width="200px"
              label-position="left"
							@submit.native.prevent
						>
							<el-form-item
                label="站点名称："
								class="animate__animated animate__fadeInUp"
								prop="siteName"
							>
								<el-input
									v-model="formData.siteName"
								/>
							</el-form-item>

							<el-form-item
								class="box animate__animated animate__fadeInUp"
								prop="username"
                label="管理员账号："
							>
								<el-input
									v-model.trim="formData.username"
								/>
							</el-form-item>

							<el-form-item
								class="box animate__animated animate__fadeInUp"
								prop="password"
                label="管理员密码："
							>
								<el-input
									v-model.trim="formData.password"
									type="password"
									show-password
								/>
							</el-form-item>

							<el-form-item
								class="box animate__animated animate__fadeInUp"
								prop="domain"
                label="站点地址/域名："
							>
								<el-input
									v-model.trim="formData.domain"
								/>
							</el-form-item>

								<el-button
									native-type="submit"
									:loading="loading"
									class="w-full mt-4 mb-2"
									type="primary"
                  size="large"
									@click="submitForm"
								>
									立刻开始
								</el-button>
						</el-form>
					</div>
				</div>
			</div>
		</div>
	</div>
</template>

<script setup>
import { installReq, installStatusReq } from '@/api/install'

import { Key, Link, Tickets, User } from '@element-plus/icons-vue'
import { BadgeCheckIcon } from '@heroicons/vue/solid'
import useGlobalConfigStore from '@/stores/global-config'

let router = useRouter()

let loading = ref(false)
let installFormRef = ref()

let formData = reactive({
	siteName: '',
	username: '',
	password: '',
	domain: '',
})

let formRules = ref({
	siteName: [
		{ required: true, message: '请输入站点名称', trigger: ['change'] },
	],
	username: [
		{
			required: true,
			message: '请输入管理员账号',
			trigger: ['change'],
		},
	],
	password: [
		{
			required: true,
			message: '请输入管理员密码',
			trigger: ['change'],
		},
	],
	domain: [
		{
			required: true,
			type: 'url',
			message: '请输入正确的域名，需以 http:// 或 https:// 开头',
			trigger: 'change',
		},
	],
})

const submitForm = async () => {
	await installFormRef.value.validate((checked) => {
		if (checked) {
			loading.value = true
			installReq(formData)
				.then((response) => {
					ElMessage({
						message: '初始化成功',
						type: response.code === 0 ? 'success' : 'error',
						duration: 1500,
						onClose() {
							router.push('/')
						},
					})
				})
				.finally(() => {
					loading.value = false
				})
		} else {
			ElMessage.warning('请正确输入初始化值!')
		}
	})
}

onBeforeMount(() => {
	const globalConfigStore = useGlobalConfigStore()
	formData.domain = globalConfigStore.juyoConfig.baseUrl || window.location.origin
	installStatusReq().then((response) => {
		if (response.data) {
			router.push('/')
		}
	})
})
</script>

<style lang="scss" scoped>
.juyo-install-logo {
	background-image: url('@/assets/icons/install-step.svg');
}

.juyo-install-form {
	--juyo-install-input-height: 38px;

	:deep(.el-input__inner) {
		height: var(--juyo-install-input-height);
	}

	:deep(.el-button--large) {
		--el-button-size: var(--juyo-install-input-height);
		height: var(--el-button-size);
	}

	// 防止谷歌浏览器记住密码后样式错乱
	:deep(input:-internal-autofill-selected) {
		-webkit-text-fill-color: var(
			--el-input-text-color,
			var(--el-text-color-regular)
		);
		transition: background-color 1000s ease-out 0.5s;
	}
}
</style>

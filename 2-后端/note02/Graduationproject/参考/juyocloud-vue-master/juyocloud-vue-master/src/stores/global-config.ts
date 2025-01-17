import { defineStore } from 'pinia'

// 全局配置信息, 初始化自 /public/juyocloud.config.json 文件中.
const useGlobalConfigStore = defineStore('globalConfigStore', {
	state: () => {
		return {
			juyoConfig: {
				baseUrl: '',
				router: {
					mode: 'history',
				},
				skeleton: {
					enable: false,
					show: 'always',
					size: 20,
				},
				gallery: {
					mobileColumn: 5,
					column: 3,
					columnSpacing: 50,
					rowSpacing: 10,
					showInfo: true,
					showInfoMode: 'hover',
					roundedBorder: true,
					showBackTop: true,
				},
				imagePreview: {
					mode: 'only',
					gallery: true,
				},
				officePreview: {},
				maxFileUploads: 5
			},
		}
	},
	actions: {
		updateJuyoConfig(val: any) {
			this.juyoConfig = val
		},
	},
})

export default useGlobalConfigStore

import { defineStore } from 'pinia'

// @ts-ignore
import useFileSelect from '@/composables/file/useFileSelect'

let { selectStatistics } = useFileSelect()

// 当前存储源的配置信息，数据来源为服务端配置。请求存储源后会获取其配置信息。
const useStorageConfigStore = defineStore('storageConfigStore', {
	state: () => {
		return {
			globalConfig: {
				siteName: '',
				directLinkPrefix: '',
				infoEnable: false,
				showLinkBtn: true,
				recordDownloadLog: false,
				showShortLink: false,
				showPathLink: false,
				tableSize: 'small',
				rootShowStorage: false,
				fileClickMode: 'click',
				showDocument: false,
				debugMode: false,
				domain: '',
				icp: '',
				avatar: '',
				announcement: '',
				layout: 'full',
				showAnnouncement: false,
				searchEnable: true,
				showLogin: true
			},
			folderConfig: {
				readmeText: null,
				readmeDisplayMode: null,
				defaultSwitchToImgMode: false,
				enableFileOperator: true,
				shareFile : true
			},
			isLogin: false
		}
	},
	getters: {
		permission: (state) => {
			return {
				open:
					selectStatistics.value.isSingleSelect &&
					selectStatistics.value.isAllFolder,
				preview:
					selectStatistics.value.isAllFile &&
					selectStatistics.value.isSingleSelect,
				download: selectStatistics.value.isAllFile,
				link:
					selectStatistics.value.isAllFile &&
					state.globalConfig.showLinkBtn &&
					(state.globalConfig.showShortLink || state.globalConfig.showPathLink),
				rename:
					state.folderConfig.enableFileOperator &&
					selectStatistics.value.isSingleSelect,
				delete: state.folderConfig.enableFileOperator,
				newFolder: state.folderConfig.enableFileOperator,
				upload: state.folderConfig.enableFileOperator,
				pathLink: state.globalConfig.showPathLink,
				shortLink: state.globalConfig.showShortLink,
				shareFile: state.folderConfig.shareFile,
				isLogin: state.isLogin
			}
		},
	},
	actions: {
		updateGlobalConfig(val: any) {
			this.globalConfig = val
		},
		updateFolderConfig(val: any) {
			this.folderConfig = val
		},
		updateLoginStatus(val: any) {
			this.isLogin = val
		}
	},
})

export default useStorageConfigStore

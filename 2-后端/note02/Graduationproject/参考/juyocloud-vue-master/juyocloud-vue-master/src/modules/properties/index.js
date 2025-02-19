import {router} from '@/modules/router'

export default function install(Vue) {

    // 文件路径，此处是id值，Long类型
    Vue.config.globalProperties.filePath = function () {
        return {
            current() {
                return router.currentRoute.value.query.pid
            },
        }
    };

    // 文件下载
    Vue.config.globalProperties.getDownloadPath = fileId => url("/transfer/download/" + fileId)

    // 获取图片略缩图
    Vue.config.globalProperties.getThumbnailPath = fileId => url('/transfer/thumbnail/' + fileId)


    // 获取 office 文件在线预览路径
    Vue.config.globalProperties.getFileOnlineViewPathByOffice = fileId => {
        // 本地磁盘存储 - 在本地开发环境中，本地磁盘存储的文件是无法预览的，因为 office 要求文件可以在 Internet 访问
        let fileUrl = `${location.protocol}//${location.host}/` + url(`/transfer/download/${fileId}`)
        return `https://view.officeapps.live.com/op/embed.aspx?src=${fileUrl}`
    }

    // 是否退出登录
    Vue.config.globalProperties.isLoginPage = function (){
        return router.currentRoute.value.name === 'Login'
    }

}

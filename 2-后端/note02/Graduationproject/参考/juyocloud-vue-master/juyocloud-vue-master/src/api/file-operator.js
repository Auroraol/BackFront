import axios from '@/http/request'
import useFilePwd from '@/composables/file/useFilePwd'

let {getPathPwd} = useFilePwd()

// 新建文件夹
export const newFolderReq = (data) => {
    return axios({
        url: `/file/operator/mkdir`,
        method: 'post',
        data,
    })
}
// 批量删除文件/文件夹
export const batchDeleteReq = (data) => {
    return axios({
        url: `/file/operator/delete/batch`,
        method: 'post',
        data,
    })
}

// 重命名文件
export const renameFileReq = (data) => {
    data.password = getPathPwd(data.path)
    return axios({
        url: `/file/operator/rename/file`,
        method: 'post',
        data,
    })
}

// 重命名文件夹
export const renameFolderReq = (data) => {
    data.password = getPathPwd(data.path)
    return axios({
        url: `/file/operator/rename/folder`,
        method: 'post',
        data,
    })
}

// 获取文件上传链接
export const uploadFileReq = (data) => {
    data.password = getPathPwd(data.path)
    return axios({
        url: `/file/operator/upload/file`,
        method: 'post',
        data,
    })
}

// 上传文件
export const uploadChunkFile = (data) => {
    return axios({
        url: `/transfer/uploadFile`,
        method: 'post',
        data,
    })
}

// 合并切片
export const mergeFile = (data) => {
    return axios({
        url: `/transfer/merge`,
        method: 'post',
        data,
    })
}

// 获取上传进度
export const checkChunkFile = (data) => {
    return axios({
        url: `/transfer/checkChunkFile`,
        method: 'post',
        data,
    })
}

export function mergeSimpleUpload() {
    return Promise.resolve()
}



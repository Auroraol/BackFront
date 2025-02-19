import request from "./index.js";

// 获取文件列表
export function getFileListAPI(data) {
    return request({
        url: '/file/getFileList',
        method: 'get',
        params: data 
    })
}

// 获取查询文件列表
export function getSearchFileListAPI(data) {
    return request({
        url: '/file/getSearchFileList',
        method: 'get',
        params: data 
    })
}
//删除文件
export function deleteFileAPI(data) {
    return request({
        url: '/file/deleteFile',
        method: 'post',
        params: data 
    })
}

//下载文件
export function downloadFileAPI(data) {
    return request({
        url: '/file/downloadFile',
        method: 'post',
        params: data, 
        responseType: "blob",
        headers: {
            'Content-Type': 'application/json; charset=UTF-8',
            'Content-Range': 'bytes 2-5/10',
        }
    })
}

//创建文件夹
export function createFolderAPI(data) {
    return request({
        url: '/file/createFolder',
        method: 'post',
        params: data, 
    })
}
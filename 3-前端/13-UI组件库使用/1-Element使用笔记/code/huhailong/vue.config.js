const timeStamp = new Date().getTime();
module.exports = {
    publicPath: "/",
    assetsDir: "static", // 输出的资源，所在的文件夹
    filenameHashing: false,
    configureWebpack: {
        output: { // 输出重构  打包编译后的 文件名称  【模块名称.版本号.时间戳】
            filename: `static/js/js[name].${timeStamp}.js`,
            chunkFilename: `static/js/chunk.[id].${timeStamp}.js`,
        },
    },
    css: {
        extract: {
            filename: `static/css/[name].${timeStamp}.css`,
            chunkFilename: `static/css/chunk.[id].${timeStamp}.css`,
        }
    }
}
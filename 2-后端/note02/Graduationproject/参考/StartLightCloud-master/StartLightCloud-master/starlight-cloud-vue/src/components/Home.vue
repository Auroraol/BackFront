<template>
    <div class="home">

        <div class="main-ui">

            <el-container>

                <el-aside width="50%" class="main-ui-aside">

                    <div class="information">

                        <br>

                        <el-avatar :src=this.user.profile style="background-color: #211E34;" />

                        <div style="font-size: large;color: white;">

                            {{ user.displayName }}

                            <div>

                                <el-tag class="role-tag" color="#1B1829"
                                    :style="user.role == 1 ? 'color:#FCBC52' : 'color:white'">{{ user.role == 1 ? "管理员" :
                                        "普通用户" }}

                                </el-tag>

                            </div>

                        </div>

                    </div>

                    <br>

                    <div>

                        <el-button text @click="this.clickMyCloudButton">

                            <el-icon>

                                <MostlyCloudy />

                            </el-icon>我的云盘</el-button>

                    </div>

                    <br>

                    <div>

                        <el-button text @click="this.clickCenterButton">

                            <el-icon>

                                <User />

                            </el-icon>个人中心

                        </el-button>

                    </div>

                    <br>

                    <div>

                        <el-button text @click="this.clickShopButton">

                            <el-icon>

                                <ShoppingBag />

                            </el-icon>云盘商城</el-button>

                    </div>

                    <br>

                    <div style="margin-top: 0px">

                        <el-button color="#312C4D" bg @click="outLogin">

                            <el-icon>

                                <Back />

                            </el-icon>退出登录</el-button>

                    </div>

                    <el-progress type="circle" :percentage="parseFloat(this.cloud.percentage)" style="margin-top: 86px;"
                        color="#FCBC52" />

                    <el-tooltip class="box-item" effect="dark" content="存储占用：上传文件后会消耗存储并无法回退" placement="top">

                        <div style="color: #696969;">

                            云盘存储占用情况

                        </div>

                    </el-tooltip>

                </el-aside>



                <el-container>

                    <el-header class="main-ui-header">

                        <el-button-group class="navigation-LR" v-show="navigationButton"
                            style="position: relative;float: left;margin-top: 15px;margin-right: 10px;">

                            <el-button type="primary" color="#312C4D" @click="navigationBackward">

                                <el-icon>

                                    <ArrowLeft />

                                </el-icon>

                            </el-button>



                            <el-button type="primary" color="#312C4D" @click="navigationHome">

                                <el-icon>

                                    <House />

                                </el-icon>

                            </el-button>

                        </el-button-group>

                        <el-dialog v-model="createFolderDialogVisible" width="15%" title="创建文件夹" class="createFloderDialog">



                            <div>

                                <el-input v-model="this.createFolder.folderName" maxlength="15" placeholder="对新建文件夹进行命名"
                                    show-word-limit type="text" style="border-top-right-radius: 5%;" />

                            </div>

                            <template #footer>

                                <span class="createFolderDialog-footer">

                                    <el-button style="background-color: #312D4C;color: aliceblue;border-color:#312D4C;"
                                        @click="cancelCreateFolderDialog">取消</el-button>

                                    <el-button style="background-color: #FCBC52;color: black;border-color:#FCBC52;"
                                        type="primary" @click="confirmCreateFolder">

                                        确定

                                    </el-button>

                                </span>
                            </template>
                        </el-dialog>

                        <el-button color="#FBBC4D" style="margin-bottom: 20;margin-top: 15px;float: left;"
                            @click="openCreateFolderDialog" v-if="showCreateFolderButton">
                            <el-icon>
                                <FolderAdd />
                            </el-icon>新建
                        </el-button>

                        <el-button color="#FBBC4D" style="margin-bottom: 20;margin-top: 15px;float: left;"
                            @click="freshResourceList(true)" v-if="showFreshResourceListButton">
                            <el-icon>
                                <Refresh />
                            </el-icon>刷新
                        </el-button>

                        <el-button color="#FBBC4D" style="margin-bottom: 20;margin-top: 15px;float: left;"
                            @click="downloadFile" v-if="showDownloadFileButton" :disabled="banDownloadFileButton">
                            <el-icon>
                                <Download />
                            </el-icon>下载
                        </el-button>

                        <el-button color="#FBBC4D" style="margin-bottom: 20;margin-top: 15px;float: left;" @click="moveFile"
                            v-if="showMoveFileButton" :disabled="banMoveFileButton">
                            <el-icon>
                                <Download />
                            </el-icon>移动
                        </el-button>

                        <el-upload class="upload-resource" action="http://localhost:8080/file/upload"
                            :show-file-list="false" :auto-upload="false" :data="this.user" name="file" ref="upload"
                            :on-change="fileHandleChange" :headers="headerObj" :on-error="uploadError"
                            :on-success="fileUploadOver" :on-progress="fileUploadProgress" :before-upload="beforeUpload">
                            <el-button color="#FBBC4D" v-if="showUploadFileButton">
                                <el-icon>
                                    <Upload />
                                </el-icon>上传
                            </el-button>
                        </el-upload>

                        <el-button color="#FBBC4D" style="margin-left: 2px ;margin-bottom: 20;margin-top: 15px;float: left;"
                            @click="deleteResourceList" v-if="this.showDeleteFileButton" :disabled="banDeleteFileButton">
                            <el-icon>
                                <Delete />
                            </el-icon>删除
                        </el-button>

                        <span v-show="currentPosition"
                            style="color:gray;float: left;margin-left: 50px;font-size:1px;margin-top: 12px;"
                            v-bind:title="this.currentPath">
                            当前位置
                            <div style="color:white;font-size: 1%;position:absolute;">
                                {{ this.displayPath }}
                            </div>
                        </span>
                        <el-input v-show="research" style="width: 20%;margin-left: 80%;margin-top: -8%;"
                            v-model="inputSearch" @keyup.enter.native="searchFileList" placeholder="搜索" class="input-search"
                            suffix-icon="Search" />
                    </el-header>
                    <el-main class="main-ui-main">
                        <component :is="currentComponent"
                            :user="{ userId: this.user.user_id, profile: this.user.profile, phoneNumber: this.user.phoneNumber, money: this.user.money, displayRAM: this.user.displayRAM, lastLogin: this.user.lastLogin }">
                        </component>
                        <router-view />

                        <el-table v-show="this.showCloudTable" class="el-table-fileList" :data="fileList"
                            style="overflow:auto;height:435px;width: 99%;background-color: #211E34;"
                            :header-cell-style="{ color: 'white', backgroundColor: '#1B1829' }" ref="table"
                            @cell-click="accessFolder" :cell-style="resourceStyleBack" size="small" :border="true"
                            @selection-change="resourceStyleBackHandleSelection" empty-text="赶紧上传文件试试吧！">
                            <el-table-column type="selection" width="28" />
                            <el-table-column property="file_type" width="33" :formatter="this.showFileTypeIcon" />
                            <el-table-column property="file_name" label="文件名" width="270" />
                            <el-table-column property="updated_at" label="修改时间" width="210" sortable
                                :default-sort="{ prop: 'updated_at', order: 'ascending' }" />
                            <el-table-column property="file_type" label="类型" width="100" sortable
                                :default-sort="{ prop: 'file_type', order: 'ascending' }" />
                            <el-table-column property="file_size" label="大小" width="111" sortable
                                :default-sort="{ prop: 'file_size', order: 'ascending' }" />
                        </el-table>

                    </el-main>
                    <el-footer class="main-ui-footer">
                        <div style="margin-top: 10px;">
                            <div style=" color: orange;font-size: small;margin-bottom: 2px;">
                                上传进度 ({{ this.download.current }}/{{ this.download.total }})
                            </div>
                            <el-progress :text-inside="true" :stroke-width="16"
                                :percentage="this.download.downloadPercentage" status="warning" />
                        </div>
                    </el-footer>
                </el-container>
            </el-container>
        </div>

    </div>
</template>

<script>
import { ElMessage } from 'element-plus'
import { getFileListAPI, deleteFileAPI, downloadFileAPI, createFolderAPI, getSearchFileListAPI } from "../api/file.js"
import { getUserInfoAPI, getProfileAPI } from "../api/user.js"
import center from "./Center.vue";
import shop from "./Shop.vue";

const navigationNodeIdStack = []
const navigationFileNameStack = []

export default {
    name: 'home',
    data() {
        return {
            showCloudTable: true,
            currentComponent: null,

            formNodeId: "",
            formFilename: "",
            createFolderDialogVisible: false,
            fileCount: 0,
            banMoveFileButton: false,
            banDownloadFileButton: false,
            banDeleteFileButton: false,
            research: true,
            currentPosition: true,
            navigationButton: true,
            showMoveFileButton: false,
            showFreshResourceListButton: true,
            showCreateFolderButton: true,
            showDownloadFileButton: false,
            showDeleteFileButton: false,
            showUploadFileButton: true,
            inputSearch: "",
            currentPath: "我的云盘",
            displayPath: this.currentPath,
            cloud: {
                percentage: 0.00
            },
            createFolder: {
                userId: null,
                folderName: "",
                currentNodeId: null,
            },
            headerObj: {
                Authorization: this.$cookies.get("token") //token
            },
            download: {
                downloadPercentage: 0, //下载进度
                total: 0,
                current: 0,
            },
            user: {
                profile: "",
                username: window.localStorage.getItem('username'),
                user_id: window.localStorage.getItem('user_id'),
                Authorization: this.$cookies.get("token"),
                role: window.localStorage.getItem('role'),
                currentNodeId: window.localStorage.getItem('user_id'), //默认进入用户id结点
                currentNodeFileName: "default",
                RAM: 0,
                displayRAM: 0,
                lastLogin: window.localStorage.getItem("lastLogin"),
                money: 10.00,
            },
            fileList: [],
            fileSelectionList: [],
        }
    },
    component: {
        center,
        shop,
    },
    mounted() {
        this.freshResourceList()
        //将用户id作为云盘文件层第0个压栈
        if (navigationNodeIdStack.length == 0) {
            navigationNodeIdStack.push(this.user.user_id)
            navigationFileNameStack.push("我的云盘")
        }

        //后续可处理路径名（备用）
        this.displayPath = this.currentPath
    },
    methods: {
        //获取用户头像
        updateProfile() {
            getProfileAPI(this.user).then(res => {
                this.user.profile = res.data.data
            }).catch(err => {
                console.log("报错了profile:")
                console.log(err)
            })
        },
        //点击我的云盘按钮
        clickMyCloudButton() {
            this.showCloudTable = true
            this.currentComponent = null

            //还原上面所有按钮
            // this.navigationButton = true
            // this.showCreateFolderButton = true
            // this.showFreshResourceListButton = true
            // this.showUploadFileButton = true
            // this.currentPosition = true
            // this.research = true
        },
        //点击个人中心按钮
        clickCenterButton() {
            this.showCloudTable = false
            this.currentComponent = center

            //隐藏上面所有按钮
            // this.navigationButton = false
            // this.showCreateFolderButton = false
            // this.showFreshResourceListButton = false
            // this.showUploadFileButton = false
            // this.currentPosition = false
            // this.research = false
        },
        //点击云盘商城按钮
        clickShopButton() {
            this.showCloudTable = false
            this.currentComponent = shop

            //隐藏上面所有按钮
            // this.navigationButton = false
            // this.showCreateFolderButton = false
            // this.showFreshResourceListButton = false
            // this.showUploadFileButton = false
            // this.currentPosition = false
            // this.research = false
        },
        navigationBackward() {
            //如果第0层为我的云盘，则失效
            if (navigationFileNameStack[navigationFileNameStack.length - 1] == "我的云盘") {
                console.log("can't access")
                ElMessage.warning("不能再后退了")
                return
            }
            if (navigationNodeIdStack.length == 0) {
                navigationNodeIdStack.push(this.user.user_id)
                navigationFileNameStack.push("我的云盘")
                return
            }
            //弹出栈顶作为预备结点
            this.formNodeId = navigationNodeIdStack.pop(navigationNodeIdStack.length - 1)
            this.formFilename = navigationFileNameStack.pop(navigationFileNameStack.length - 1)
            console.log("弹了", this.formFilename, "作为预备结点")
            //再次取出栈顶作为当前结点
            let currentNodeId = navigationNodeIdStack[navigationNodeIdStack.length - 1]
            let currentNodeFileName = navigationFileNameStack[navigationFileNameStack.length - 1]
            //设置当前结点
            this.user.currentNodeId = currentNodeId
            this.user.currentNodeFileName = currentNodeFileName

            //更新路径
            this.currentPath = "我的云盘"
            for (let i = 1; i < navigationFileNameStack.length; i++) {
                this.currentPath = this.currentPath.concat("/" + navigationFileNameStack[i])
            }

            //后续可处理路径名（备用）
            this.displayPath = this.currentPath

            console.log(navigationFileNameStack)

            //刷新页面
            this.freshResourceList()
        },
        navigationHome() {
            //全部恢复默认
            this.user.currentNodeId = this.user.user_id
            this.user.currentNodeFileName = this.user.currentNodeFileName
            this.formNodeId = ""
            this.formFilename = ""

            //更新路径
            this.currentPath = "我的云盘"
            //后续可处理路径名（备用）
            this.displayPath = this.currentPath

            getFileListAPI(this.user).then(res => {
                this.freshResourceList()
            }).catch(err => {
                console.log(err)
            })
        },
        searchFileList() {
            console.log("searchFileList")

            let searchFileListObj = {
                userId: this.user.user_id,
                prompt: this.inputSearch,
                nodeId: this.user.currentNodeId
            }

            getSearchFileListAPI(searchFileListObj).then(res => {
                let result = JSON.parse(res.data.data)
                this.fileList = result

                //所有大小转换为可视单位
                if (this.fileList != null) {
                    this.fileList.forEach(element => {
                        element.file_size = this.formatByte(element.file_size)
                        element.updated_at = element.updated_at.replace("T", " ").replace("+08:00", "")
                        //如果为0B则直接显示-
                        if (element.file_size == "0 B") {
                            element.file_size = "-"
                        }
                    });
                }


                //刷新页面文件数量
                this.fileCount = this.fileList.length

                //修改当前展示的路径
                this.displayPath = "搜索/"

            }).catch(err => {
                console.log(err)
            })

        },
        accessFolder(data) {
            //只有文件夹点击才有效
            if (data.file_type != "文件夹") {
                return false
            }

            //把文件夹的结点id放入user信息里传给后端刷新
            navigationNodeIdStack.push(data.node_id)
            navigationFileNameStack.push(data.file_name)
            this.user.currentNodeId = navigationNodeIdStack[navigationNodeIdStack.length - 1]
            this.user.currentNodeFileName = navigationFileNameStack[navigationFileNameStack.length - 1]

            this.currentPath = this.currentPath.concat("/" + navigationFileNameStack[navigationFileNameStack.length - 1])

            getFileListAPI(this.user).then(res => {
                this.freshResourceList()
            }).catch(err => {
                console.log(err)
            })

            //后续可处理路径名（备用）
            this.displayPath = this.currentPath

            //如果长度大于20则直接省略
            if (this.currentPath.length > 20) {

                this.displayPath = "我的云盘/.."
                console.log("ok", this.displayPath)

            }
        },
        cancelCreateFolderDialog() {
            console.log("cancel")
            this.createFolder.folderName = ""
            this.createFolderDialogVisible = false
        },
        confirmCreateFolder() {
            console.log("confirm")

            //载入信息
            this.createFolder.userId = this.user.user_id
            this.createFolder.currentNodeId = this.user.currentNodeId

            createFolderAPI(this.createFolder).then(res => {
                console.log("cf:", res.data)
                let code = res.data.code
                if (code == 206) {
                    ElMessage.success("创建成功")
                } else if (code == 411) {
                    ElMessage.error("创建失败，文件夹已存在")
                }
                //刷新列表
                this.freshResourceList()

            }).catch(err => {
                if (err.response.status == 405) {
                    ElMessage("身份已失效，请重新登录")
                    //跳转页面
                    this.$router.push({
                        path: "/login",
                    })

                    return
                }

                console.log("报错了：")
                console.log(err)
            })

            this.createFolder.folderName = ""
            this.createFolderDialogVisible = false
        },
        moveFile() {
            ElMessage.warning("功能暂未开发，请等待更新")
        },
        openCreateFolderDialog() {
            console.log("new")
            console.log(this.$cookies.get("role"))
            this.createFolderDialogVisible = true
        },
        downloadFile() {

            //如果勾选了文件夹则不允许下载
            for (let i = 0; i < this.fileSelectionList[0].length; i++) {
                if (this.fileSelectionList[0][i].file_type == "文件夹") {
                    ElMessage.error("请取消勾选文件夹后再下载")
                    console.log(this.fileSelectionList)
                    return
                }
            }

            ElMessage.success("正在启动下载进程，请耐心等候")

            //禁止点击下载按钮
            this.banDownloadFileButton = true;
            //禁止删除按钮
            this.banDeleteFileButton = true;
            //禁止移动按钮
            this.banMoveFileButton = true;

            //汇总模型
            let sendNodeIdList = {
                nodeIdList: [{}],
                fileNameList: [{}],
                create_id: null,
                count: 0,
            }

            //单模型
            let singleSendNodeIdList = {
                nodeId: null,
                fileName: null,
                create_id: null,
            }

            for (let i = 0; i < this.fileSelectionList[0].length; i++) {
                sendNodeIdList.nodeIdList.push(this.fileSelectionList[0][i].node_id)
                sendNodeIdList.fileNameList.push(this.fileSelectionList[0][i].file_name + '.' + this.fileSelectionList[0][i].file_type)
            }

            //携带用户id
            sendNodeIdList.create_id = this.user.user_id
            sendNodeIdList.count = sendNodeIdList.nodeIdList.length
            console.log("post：", sendNodeIdList)

            for (let i = 1; i < sendNodeIdList.count; i++) {
                //汇总模型转单模型
                singleSendNodeIdList.nodeId = sendNodeIdList.nodeIdList[i]
                singleSendNodeIdList.fileName = sendNodeIdList.fileNameList[i]
                singleSendNodeIdList.create_id = sendNodeIdList.create_id

                downloadFileAPI(singleSendNodeIdList).then(res => {

                    console.log('下载的文件', res.headers['content-disposition'])

                    //headers获取具体文件名
                    let fileNameTemp = res.headers['content-disposition']
                    var fileName = fileNameTemp.replace("attachment; filename=", "")

                    let b = new Blob([res.data]) //拿到文件流下载对象

                    console.log("b:", b)
                    let url = window.URL.createObjectURL(b) //生成文件流下载链接
                    let a = document.createElement('a') //创建一个a标签用来跳转
                    a.href = url // a标签 href  赋值 链接  
                    a.download = fileName //设置下载文件的文件名和文件格式
                    document.body.appendChild(a) //将标签DOM，放置页面
                    a.click()
                    window.URL.revokeObjectURL(url) //释放 url 对象内存
                    document.body.removeChild(a) //移除标签节点

                    //当全部下载完成后恢复下载按钮
                    this.banDownloadFileButton = false;
                    //当全部下载完成后恢复删除按钮
                    this.banDeleteFileButton = false;
                    //当全部下载完成后恢复移动按钮
                    this.banMoveFileButton = false;
                    //刷新列表
                    this.freshResourceList()

                }).catch(err => {
                    // if (err.response.status == 405) {
                    //     ElMessage("身份已失效，请重新登录")
                    //     //跳转页面
                    //     this.$router.push({
                    //         path: "/login",
                    //     })
                    //     return
                    // }
                    console.log("报错了：", err)
                })
            }

        },
        deleteResourceList() {
            if (this.fileSelectionList[0].length == 0) {
                ElMessage.error("请选择文件")
                return
            }

            let deleteNodeIdList = {
                nodeIdList: [{}],
                fileNameList: [{}],
                count: 0, //此为nodeIdList和fileNameList的文件数量
                folderNodeIdList: [{}],
                folderCount: 0, //此为folderNodeIdList的文件夹数量
                create_id: null,
            }
            for (let i = 0; i < this.fileSelectionList[0].length; i++) {
                //如果要删除的包括文件夹，则单独将文件夹加入文件夹列表
                if (this.fileSelectionList[0][i].file_type == "文件夹") {
                    deleteNodeIdList.folderNodeIdList.push(this.fileSelectionList[0][i].node_id)
                } else {
                    deleteNodeIdList.nodeIdList.push(this.fileSelectionList[0][i].node_id)
                }
                deleteNodeIdList.fileNameList.push(this.fileSelectionList[0][i].file_name + "." + this.fileSelectionList[0][i].file_type)
            }


            //携带用户id
            deleteNodeIdList.create_id = this.user.user_id
            deleteNodeIdList.count = deleteNodeIdList.nodeIdList.length - 1
            deleteNodeIdList.folderCount = deleteNodeIdList.folderNodeIdList.length - 1
            console.log(deleteNodeIdList)

            deleteFileAPI(deleteNodeIdList).then(res => {
                let code = res.data.code
                if (code == 205) {
                    ElMessage.success("文件删除成功")
                } else if (code == 410) {
                    ElMessage.error("文件删除失败")
                }
                //刷新列表
                this.freshResourceList()

            }).catch(err => {
                console.log(err)
                if (err.response.status == 405) {
                    ElMessage("身份已失效，请重新登录")
                    //跳转页面
                    this.$router.push({
                        path: "/login",
                    })

                    return
                }

                console.log("报错了：")
                console.log(err)
            })

        },
        resourceStyleBackHandleSelection(val) {
            //清空选择表里的值
            this.fileSelectionList = []
            //重新推入新选择的值
            this.fileSelectionList.push(val)

            //展示对应按钮
            if (this.fileSelectionList[0].length == 0) {
                this.showDeleteFileButton = false
                this.showDownloadFileButton = false
                this.showUploadFileButton = true
                this.showCreateFolderButton = true
                this.showFreshResourceListButton = true
                this.showMoveFileButton = false
            } else {
                this.showDeleteFileButton = true
                this.showDownloadFileButton = true
                this.showUploadFileButton = false
                this.showCreateFolderButton = false
                this.showFreshResourceListButton = false
                this.showMoveFileButton = true
            }
        },
        outLogin() {
            this.$cookies.remove("token")
            ElMessage("退出登录")
            this.$router.push({
                path: "/login",
            })
        },
        resourceStyleBack() {
            var backgroundColor = "#211E34"
            var color = 'white'
            return { backgroundColor, color };
        },
        showFileTypeIcon(row) {
            if (row.file_type == "png" || row.file_type == "jpg" || row.file_type == "jpeg") {
                return "📸"
            } else if (row.file_type == "txt") {
                return "📓"
            } else if (row.file_type == "mp4" || row.file_type == "mov") {
                return "📹"
            } else if (row.file_type == "zip" || row.file_type == "7z" || row.file_type == "rar") {
                return "📦"
            } else if (row.file_type == "mp3") {
                return "💿"
            } else if (row.file_type == "msi" || row.file_type == "exe") {
                return "⬇️"
            } else if (row.file_type == "doc" || row.file_type == "docx" || row.file_type == "ppt" || row.file_type == "xlsx") {
                return "📄"
            } else if (row.file_type == "文件夹") {
                return "📁"
            } else {
                return "❓"
            }
        },
        formatByte(val) {
            if (isNaN(val)) return "";
            var list = ["B", "KB", "MB", "GB", "TB", "PB", "EB", "ZB", "YB"];
            var temp = Math.floor(Math.log(val) / Math.log(2));
            if (temp < 1) temp = 0;
            var num = Math.floor(temp / 10);
            val = val / Math.pow(2, 10 * num);
            if (val.toString().length > val.toFixed(2).toString().length) val = val.toFixed(2);
            return val + " " + list[num];
        },
        beforeUpload(file) {
            if ((file.size / 1024 / 1024) > 1000) {
                ElMessage.error('上传失败，文件大小不能超过200MB')
                return false
            }
        },
        fileHandleChange(file) {
            this.$nextTick(() => {

                // //进行切片操作
                // //定义分片大小
                // let chunkSize = 1024 * 1024 * 5
                // //创建一个数组用来存储每一片文件流数据
                // let chunkList = []
                // //如果文件大小 < 5m则只有一片，不用切
                // if(file.Size < chunkSize){
                //     chunkList.push(file.raw.slice(0))
                // }else{
                //     var start = 0, end = 0
                //     while(true){
                //         end += chunkSize // 结束位置 = 结束位置 + 每片大小
                //         let blob = file.raw.slice(start, end) // 文件流从开始位置截取到结束位置
                //         start += chunkSize// 截取完，开始位置后移

                //         if (!blob.size) {  // 如果截取不到了就退出
                //             break;
                //         }

                //         chunkList.push(blob)  // 把截取的每一片数据保存到数组

                //     }

                //     console.log(chunkList.length)   //片数
                // }

                this.$refs.upload.submit()
            })
        },
        uploadError(err) {
            console.log("文件上传失败")
            //获取失败状态码
            let code = JSON.parse(err.message).code
            if (code == 405) {
                ElMessage("身份已失效，请重新登录")
                //跳转页面
                this.$router.push({
                    path: "/login",
                })
            }
            return
        },
        fileUploadOver(response) {
            let code = response.code
            if (code == 202) {
                ElMessage.success("文件上传成功")
            } else if (code == 406) {
                ElMessage.error("文件上传失败")
            } else if (code == 203) {
                ElMessage.success("更新文件成功")
            } else if (code == 407) {
                ElMessage.error("更新文件失败")
            } else if (code == 408) {
                ElMessage.error("文件上传失败，存储空间已满")
            } else if (code == 208) {
                ElMessage("上传文件异常")
            }
            //刷新文件列表
            this.freshResourceList()
            return
        },
        fileUploadProgress(data) {
            this.download.downloadPercentage = Math.round((data.percent + Number.EPSILON) * 100) / 100

            this.download.total = this.formatByte(data.total)
            this.download.current = this.formatByte(data.loaded)

            // if (data.percent == 100) {
            //     ElMessage.success("🔔叮叮叮，上传完毕")
            // }
        },
        freshResourceList(showDialog) {
            getFileListAPI(this.user).then(res => {
                let result = JSON.parse(res.data.data)
                this.fileList = result

                //所有大小转换为可视单位
                if (this.fileList != null) {
                    this.fileList.forEach(element => {
                        element.file_size = this.formatByte(element.file_size)
                        element.updated_at = element.updated_at.replace("T", " ").replace("+08:00", "")
                        //如果为0B则直接显示-
                        if (element.file_size == "0 B") {
                            element.file_size = "-"
                        }
                    });
                }

                if (showDialog == true) {
                    ElMessage.success("刷新成功")
                }

                //刷新页面文件数量
                this.fileCount = this.fileList.length

            }).catch(err => {
                if (err.response == null) {
                    return
                }

                if (err.response.status == 405) {
                    ElMessage("身份已失效，请重新登录")
                    //跳转页面
                    this.$router.push({
                        path: "/login",
                    })

                    return
                }

                console.log("报错了：")
                console.log(err)
            })

            //初始个人数据
            getUserInfoAPI(this.user).then(res => {
                //初始RAM数据
                let RAM = res.data.ram
                let TotalRAM = res.data.total_ram
                this.user.RAM = RAM
                this.user.displayRAM = res.data.display_ram
                this.user.phoneNumber = res.data.phone_number
                this.user.displayName = res.data.display_name

                // 刷新存储展示进度条
                let num = (TotalRAM - RAM) / TotalRAM * 100
                this.cloud.percentage = num.toFixed(2)

                //初始星币数
                this.user.money = res.data.can_money

            }).catch(err => {

                console.log("报错了：")
                console.log(err)
            })

            //获取用户头像
            getProfileAPI(this.user).then(res => {
                console.log(this.user)
                this.user.profile = res.data.data
            }).catch(err => {
                console.log("报错了profile:")
                console.log(err)
            })


        }

    }
}
</script>
<style scoped>
.main-ui {
    background-color: rgb(5, 5, 5);
    width: 1000px;
    height: 600px;
    background-color: #1b1829;
    border-radius: 20px;
    box-shadow: 0 0 8px #1b1829;
    -webkit-user-select: none;
    -moz-user-select: none;
    -ms-user-select: none;
    user-select: none
}

.upload-resource {
    margin-left: 12px;
    margin-bottom: 20;
    margin-top: 15px;
    float: left;
}

.main-ui-aside {
    background-color: #211E34;
    width: 200px;
    border-top-left-radius: 20px;
    border-bottom-left-radius: 20px;
    height: 600px;
    border-right: 1px solid #0d0c14;
}

.main-ui-main {
    height: 200px;
}

.main-ui-footer {
    background-color: #211E34;
    border-bottom-right-radius: 20px;
}

.main-ui-header {
    background-color: #211E34;
    border-top-right-radius: 20px;
}

.deleteResourceDialog button:first-child {
    background-color: #312D4C;
}

::v-deep(.el-dialog.createFloderDialog) {
    --el-bg-color: #201c30 !important;
    width: 350px;
    border-radius: 10px;
}

::v-deep(.el-dialog__header) {
    --el-text-color-primary: #ffffff;
}</style>
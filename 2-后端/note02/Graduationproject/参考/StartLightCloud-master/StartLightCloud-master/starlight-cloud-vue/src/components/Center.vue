<template>
    <div class="center-information">
        <div class="wallet-title" style="font-size: large;color: #211E34;">
            恭喜发现彩蛋-FxShdadowTG
        </div>

        <el-row :gutter="15" class="center-information-el-row">
            <el-col :span="5" class="center-money">
                <div >
                    我的星币
                    <div style="color: #FBBC4D;font-size: 200%;">
                        {{ user.money }}
                    </div>
                </div>
            </el-col>
            <el-col :span="5" class="center-cloud">
                存储空间
                <div style="color: #FBBC4D;font-size: 200%;">
                    {{ user.displayRAM }} GB
                </div>
            </el-col>
            <el-col :span="10" class="center-loginTime">
                上次登录时间
                <div>
                    <div style="color: aliceblue;font-size: 190%;">
                    {{ user.lastLogin }}
                    </div>
                </div>
                    
            </el-col>

            <el-col :span="5" class="center-message" @click="this.openMessage()">
                <div >
                    我的消息
                    <div style="color: #FBBC4D;font-size: 200%;">
                        {{ this.user.messageCount }}
                    </div>
                </div>
            </el-col>

            
            <el-col :span="5" class="center-account-option" @click="this.openAccountOption()">
                <div >
                    账户设置
                    <div style="color: #FBBC4D;font-size: 200%;">
                        <el-icon><Operation /></el-icon>
                    </div>
                </div>
            </el-col>
            
        </el-row>


        <el-dialog
            :custom-class="'my-messageDialog-class'"
            v-model="messageDialogVisible"
            title="我的消息"
            width="30%"
        >
        <div>
            目前已收到了 {{messageList.length}} 条消息
            <el-scrollbar height="325px">
                <p v-for="message in messageList" :key="message" style="background-color: #FBBC4D;" class="scrollbar-message-messageList">
                    
                    <el-collapse v-model="activeName" accordion class="el-collapse-main">
                        <el-collapse-item :title="message.title" style="background-color: #FBBC4D;">
                        <div style="font-size: 15px;color: aliceblue;float: left;padding-left: 10px;padding-right: 10px;">
                            {{ message.content }}
                            <div style="color: gray;">
                                <p></p>
                                {{ message.sendTime}}
                                <div>
                                    -{{ message.senderName}}
                                </div>

                            </div>
                        </div>
                        <template #title>
                        <span style="color: white;padding-left: 10px;">
                            <el-icon color="#FBBC4D"><Promotion /></el-icon>
                            {{ message.title }}
                        </span>
                        </template>
                        </el-collapse-item>
                    </el-collapse>

                </p>
            </el-scrollbar>
        </div>

        </el-dialog>

        <el-dialog
            :custom-class="'my-accountDialog-class'"
            v-model="accountDialogVisible"
            title="我的账户"
            width="400px"
        >
        <div>

            <span>
                <el-avatar :src=user.profile size="large" />
                <div>
                    
                    <el-upload action="http://127.0.0.1:8080/user/uploadProfile" :data="user" :show-file-list="false" :on-success="uploadProfileSuccess">
                        <el-button size="small" color="#312C4D">修改头像</el-button>
                    </el-upload>
                
                </div>

                <p></p>

            </span>

            <p></p>

            <span>
                UID: {{ user.userId }}
                <div>
                绑定号码: {{ user.phoneNumber }}
                </div>
            </span>

            <p></p>

            <!-- <div>
                <el-button type="success">提交</el-button>
                <el-button type="info" @click="this.accountDialogVisible=false">关闭</el-button>
            </div> -->
        </div>

        </el-dialog>


    </div>
</template>

<script>
import { ElMessage } from 'element-plus'

export default {
    name: 'center',
    data() {
        return {
            messageDialogVisible: false,
            accountDialogVisible: false,
            messageList: [
                {
                    title: "哔哩哔哩隐私政策的修订通知",
                    content: "亲爱的用户，根据业务开展的实际情况，哔哩哔哩近期更新了《哔哩哔哩隐私政策》中的相关内容。你可以前往哔哩哔哩客户端【我的-设置-隐私政策-哔哩哔哩隐私政策全文】查看更新后的主要提示以及全部内容。",
                    sendTime: "2023年5月31日",
                    senderName: "哔哩哔哩站",
                },
                {
                    title: "评论违规处理通知",
                    content: "您好，您在ChatGPT爆红，人工智能机器人要来抢“饭碗”了？下发布的评论『G**镜**收**档**，**包**多**站**可**/**随**问**T**文』被举报涉嫌违规，经核实已被移除。请自觉遵守国家相关法律法规及《社区规则》，良好的社区氛围需要大家一起维护。",
                    sendTime: "2023年5月31日",
                    senderName: "哔哩哔哩站",
                },
                {
                    title: "叮！恭喜你触发了限时奖励！",
                    content: "潜力UP专属额外奖励来啦！截止2023年02月15日 14:10前，完成投稿任务可获得1元现金。",
                    sendTime: "2023年5月31日",
                    senderName: "哔哩哔哩站",
                },
                {
                    title: "本周创作热点已送达！",
                    content: "流量密码都在这了！投稿就有机会登上热门>>",
                    sendTime: "2023年5月31日",
                    senderName: "哔哩哔哩站",
                },
                {
                    title: "叮！恭喜你触发了限时奖励！",
                    content: "潜力UP专属额外奖励来啦！截止2023年07月07日 02:53前，完成投稿天数任务可获得3元现金。",
                    sendTime: "2023年5月31日",
                    senderName: "哔哩哔哩站",
                },
                {
                    title: "你的专属创作灵感已送达！",
                    content: "为你精选独家灵感，投稿有机会获得创作奖励哦！",
                    sendTime: "2023年5月31日",
                    senderName: "哔哩哔哩站",
                },
                {
                    title: "终于！你的B站专属年度报告来了！",
                    content: "2077你最关注的TA是？哪些视频让你N刷不断？又是什么被你刻入了DNA？戳链接，回顾你和B站的2077>>",
                    sendTime: "2023年5月31日",
                    senderName: "哔哩哔哩站",
                }
            ],
            user: {
                messageCount: 1,
                userId: ""
            }
        }
    },
    methods: {
        uploadProfileSuccess(args) {
            console.log(args)
            if(args.code == "216"){
                ElMessage.success("修改头像成功，即将刷新页面")
                //刷新页面
                const timer = setTimeout(function () {
                    location.reload();
                    clearTimeout(timer);
                }, 2000);
            }else if(args.code == "437"){
                ElMessage.error('你的手速太快了，请稍后重试');  //这里的回调比较特殊，所以写死代码，不走响应拦截器那套
            }
            return

        },
        //打开账户设置
        openAccountOption() {
            this.accountDialogVisible = true
        },
        //打开我的消息
        openMessage() {
            this.messageDialogVisible = true
        }
    },
    props:{
        user: {
            type: Object,
            required: true,
            default: () => ({})
    }
    }
}
</script>

<style>

.el-collapse-item::v-deep .el-collapse-header {
  color: #fff;
}

.scrollbar-message-messageList{
    background-color: #353147;
    border-radius: 2px;
}

.center-information{
    background-color: #211E34;
    height: 100%;
}
.center-money{
    color: aliceblue;
    background-color:#312C4D;
    line-height: 90px;
    height: 25%;
    width: 25%;
    margin-left: 4%;
    border-radius: 5px;
    text-align: center;
    position:relative;
}
.center-cloud{
    color: aliceblue;
    background-color:#312C4D;
    line-height: 90px;
    height: 25%;
    width: 25%;
    margin-left: 4%;
    border-radius: 5px;
    text-align: center;
    position:relative;
}

.center-loginTime{
    color: aliceblue;
    background-color:#312C4D;
    line-height: 90px;
    height: 25%;
    width: 25%;
    margin-left: 4%;
    border-radius: 5px;
    text-align: center;
    position:relative;
}

.center-message{
    margin-top: 27px;
    color: aliceblue;
    background-color:#312C4D;
    line-height: 90px;
    height: 25%;
    width: 25%;
    margin-left: 4%;
    border-radius: 5px;
    text-align: center;
    position:relative;
    /* border-color: #FBBC4D;
    border-style: solid; */
    cursor:pointer
}

.center-account-option{
    margin-top: 27px;
    color: aliceblue;
    background-color:#312C4D;
    line-height: 90px;
    height: 25%;
    width: 25%;
    margin-left: 4%;
    border-radius: 5px;
    text-align: center;
    position:relative;
    /* border-color: #FBBC4D;
    border-style: solid; */
    cursor:pointer
}



.my-messageDialog-class {
    background-color: #201c30;
    padding-bottom: 10px;
    --el-bg-color: #201c30 !important;
    height: 55%;
}

.my-accountDialog-class{
    background-color: #201c30;
    padding-bottom: 10px;
    --el-bg-color: #201c30 !important;
    height: 300px;
}


</style>

<template>
    <div class="login">
        <div class="common-layout">
            <el-container>
                <el-aside width="200px">
                    <el-image style="height: 448px;border-top-left-radius: 20px;border-bottom-left-radius: 20px;"
                        :src = registerIconUrl
                        :fit="cover" />
                </el-aside>
                <el-container>
                    <el-header>
                        <div style="color:aliceblue;margin-top: 15px">
                            账号注册
                        </div>

                    </el-header>
                    <el-main>
                        <div class="information">
                            <el-form :model="this.userLoginInformation" class="el-form-information-login">

                                <div class="information-username">
                                    <el-input size="smaller" v-model="this.userRegisterInformation.username"
                                        placeholder="用户名" />
                                </div>

                                <br>

                                <div class="information-password">
                                    <el-input size="smaller" show-password v-model="this.userRegisterInformation.password"
                                        placeholder="密码" />
                                </div>
                                <div class="information-confirm-password">
                                    <el-input size="smaller" show-password v-model="this.confirmPassword"
                                        placeholder="确认密码" />
                                </div>
                                <div class="information-email">
                                    <el-input size="smaller" v-model="this.userRegisterInformation.email"
                                        placeholder="邮箱" />
                                </div>
                                <br>
                                <div class="information-email-verifyCode">
                                    <span style="display: flex;">
                                        <el-input style="margin-right: 10px;width: 50%;" size="smaller"
                                            v-model="this.userRegisterInformation.emailVerifyCode" placeholder="验证码" />
                                        <el-button color="#626aef" :dark="isDark" @click="getEmailVerifyCode"
                                            :disabled=showGetEmailVerifyCodeButton>{{ this.emailCodeText }}</el-button>
                                    </span>
                                </div>
                                <!-- <div class="information-phoneNumber">
                    <el-input size="smaller" v-model="this.userRegisterInformation.phone_number" placeholder="请输入手机号" />
                </div> -->


                                <div style="padding-top: 20px;color: grey;font-size: 10px;">
                                    注册即接受 星光用户协议 和 隐私政策
                                </div>
                                <!-- <div class="information-verifyCode">
                        <el-input size="smaller" v-model="this.verifyCode" placeholder="验证码" />
                        <PicAuthCode ref="code"></PicAuthCode>
                    </div> -->

                            </el-form>

                        </div>
                    </el-main>
                    <el-footer>
                        <span class="button">
                            <el-button class="register-button" type="primary" @click="register()"
                                style="margin-left: 45px;">注册</el-button>
                        </span>
                        <span>
                            <el-link href="http://localhost:5173/login" type="primary"
                                style="float: right;padding-top: 15px;">前往登录</el-link>
                        </span>

                    </el-footer>
                </el-container>
            </el-container>
        </div>

        <br>

    </div>
</template>

<script>
import { userRegisterAPI } from "../api/user.js"
import { ElMessage } from 'element-plus'
import PicAuthCode from "./PicAuthCode.vue"
import { getRegisterVerifyCodeAPI } from "../api/user.js"
import registerIconUrl from '../assets/registerIcon.png';

export default {
    name: 'login',
    data() {
        return {
            //图标
            registerIconUrl: registerIconUrl,

            userRegisterInformation: {
                username: "",   //用户名
                password: "",   //密码
                email: "",
                emailVerifyCode: "",
            },
            sendEmailCodeCountdown: "",
            emailCodeText: "获取验证码",
            showGetEmailVerifyCodeButton: false,
            confirmPassword: "",
            verifyCode: "",
        }
    },
    components: {
        PicAuthCode,
    },
    methods: {
        startCountDown() {
            // this.emailCodeText = this.emailCodeText + "(" + this.emailCodeCountdown + ")";
            this.timer = setInterval(() => {
                this.emailCodeText = "已发送"
                this.emailCodeText = this.emailCodeText + "(" + this.sendEmailCodeCountdown + ")";
                this.sendEmailCodeCountdown--;
                if (this.sendEmailCodeCountdown < 0) {
                    this.showGetEmailVerifyCodeButton = false
                    this.emailCodeText = "获取验证码"
                    clearInterval(this.timer);
                    this.timer = null;
                }
            }, 1000);
        },
        getEmailVerifyCode() {
            //校验邮箱格式是否符合
            //清除空格(防止bug)
            this.userRegisterInformation.username.trim()
            //校验数据
            if (this.userRegisterInformation.username.match(/^[\w-]{6,15}$/) == null) {
                ElMessage.error('用户名不符合要求，需要在6-15位内并且为A-z,0-9,_,-的字符')
                return
            } else if (this.userRegisterInformation.password.match(/^[\w-]{6,15}$/) == null) {
                ElMessage.error('密码不符合要求，需要在6-15位内并且为A-z,0-9,_,-的字符')
                return
            } else if (this.userRegisterInformation.password !== this.confirmPassword) {
                ElMessage.error('两次密码不一致')
                return
            } else if (this.userRegisterInformation.email.match(/^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/) == null) {
                ElMessage.error('邮箱不符合要求，请检查后重试')
                return
            }

            let user = {
                username: this.userRegisterInformation.username,
                email: this.userRegisterInformation.email,
            }

            getRegisterVerifyCodeAPI(user).then(res => {
                console.log("oooo", res.data)
                if (res.data.code == "430") {
                    ElMessage.error('用户名已存在，获取验证码失败')
                    return
                }else if(res.data.code == "435") {
                    ElMessage.error('邮箱已绑定其它用户，获取验证码失败')
                    return
                }else if(res.data.code == "431" || res.data.code == "432") {
                    ElMessage.error('获取验证码异常，请稍后重试')
                    return
                }else if(res.data.code == "434") {
                    ElMessage.success('验证码错误或已失效')
                    return
                }else if(res.data.code == "218") {
                    ElMessage.success('获取验证码成功')
                    this.sendEmailCodeCountdown = res.data.data
                    this.showGetEmailVerifyCodeButton = true
                    this.startCountDown()
                    return
                }

            }).catch(err => {
                console.log(err)
            })
        },
        register() {
            console.log("okok1")
            //清除空格(防止bug)
            this.userRegisterInformation.username.trim()
            //校验数据
            if (this.userRegisterInformation.username.match(/^[\w-]{6,15}$/) == null) {
                ElMessage.error('用户名不符合要求，需要在6-15位内并且为A-z,0-9,_,-的字符')
                return
            } else if (this.userRegisterInformation.password.match(/^[\w-]{6,15}$/) == null) {
                ElMessage.error('密码不符合要求，需要在6-15位内并且为A-z,0-9,_,-的字符')
                return
            } else if (this.userRegisterInformation.password !== this.confirmPassword) {
                ElMessage.error('两次密码不一致')
                return
            }else if (this.userRegisterInformation.email.match(/^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/) == null) {
                ElMessage.error('邮箱不符合要求，请检查后重试')
                return
            } else if (this.userRegisterInformation.emailVerifyCode == "") {
                ElMessage.error('验证码不能为空')
                return
            } 

            console.log("okok2")

            // else if (this.verifyCode != this.$refs.code.identifyCode.toLocaleLowerCase()){
            //     ElMessage.error('验证码错误')
            //     //验证码一旦失败则刷新网页
            //     this.$router.go(0)
            //     return
            // }

            userRegisterAPI(this.userRegisterInformation).then(res => {
                //如果获取的数据为true则成功登录并跳转页面

                if (res.data.code == 403) {
                    ElMessage('注册异常')
                    return
                } else if (res.data.code == 401) {
                    ElMessage({
                        message: '注册失败，用户名已存在',
                        type: 'success',
                    })
                }  else if (res.data.code == 434) {
                    ElMessage({
                        message: '注册失败，验证码错误',
                        type: 'error',
                    })
                } else if (res.data.code == 201) {
                    ElMessage({
                        message: '注册成功',
                        type: 'success',
                    })

                    //跳转登录页面
                    this.$router.push({
                        path: "/login",
                    })

                }
            }).catch(err => {
                console.log(err)
            })
        }

    },
}

</script>

<style scoped>
.login {
    width: 700px;
    height: 450px;
    border-radius: 20px;
    background-color: #211E34;
    -webkit-user-select: none;
    -moz-user-select: none;
    -ms-user-select: none;
    user-select: none
}

.information {
    width: 400px;
    margin: 0 auto;
}


.information-confirm-password {
    margin-top: 25px;
}

.information-phoneNumber {
    margin-top: 25px;
}

.information-email {
    margin-top: 25px;
}

.information-verifyCode {
    width: 50%;
    margin-left: 25%;
}

.information-email-verifyCode {
    width: 50%;
    margin-left: 25%;
}

.login-button {
    box-shadow: 0 0 10px rgb(86, 196, 13);
}

.register-button {
    box-shadow: 0 0 10px rgb(46, 115, 243);
}
</style>
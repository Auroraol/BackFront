<template>
    <div class="login">
        <div class="information">
            <el-container>
                <el-header>
                    <div style="color: aliceblue;margin-top: 15px">
                        用户名密码登录
                    </div>
                </el-header>
                <el-main>
                    <el-form :model="this.userLoginInformation" class="el-form-information-login" style="margin-left: 15px">

                        <div class="information-username">
                            <el-input size="smaller" v-model="this.userLoginInformation.username" placeholder="用户名" />
                        </div>

                        <br>

                        <div class="information-password">
                            <el-input size="smaller" show-password v-model="this.userLoginInformation.password"
                                placeholder="密码" />
                        </div>
                    </el-form>


                    <br>

                    <el-link href="http://localhost:5173/register" type="primary" style="float: right;font-size: smaller;margin-right: 15px;">立即注册</el-link>
                    <br>
                    <div class="button">
                        <el-button class="login-button" type="success" @click="login()">登录</el-button>
                    </div>

                </el-main>

                <el-footer>
                    <div style="color: gray;margin-top: 15px;font-size: small;">
                        作者渠道
                    </div>
                    <hr style="border-color: #211E34;">
                    <div style="justify-content: center;margin-left: 90px;margin-top: 13px;">
                        <el-row :gutter="15">
                            <el-col :span="5">
                                <a href="https://space.bilibili.com/76969706" target="_blank">
                                    <el-image style="border-radius: 10px; height: 30px; width: 30px; border-color: #f32c71; border-style: solid;" :src="bilibiliIconUrl" fit="fill"/>
                                </a>
                            </el-col>
                            <el-col :span="5">
                                <a href="https://github.com/FxShadowTG" target="_blank">
                                <el-image style="border-radius: 10px;height: 30px;width: 30px;border-color: #ffffff;border-style:solid;" :src="githubIconUrl" fit="fill"/>
                            </a>
                            </el-col>
                            <el-col :span="5">
                                <a href="https://gitee.com/FxShadowTG" target="_blank">
                                <el-image style="border-radius: 10px;height: 30px;width: 30px;border-color: #ffffff;border-style:solid;" :src="giteeIconUrl" fit="fill"/>
                                </a>
                            </el-col>
                        </el-row> 
                    </div>
                </el-footer>
            </el-container>

        </div>

    </div>
</template>

<script>
import { userLoginAPI } from "../api/user.js"
import { ElMessage } from 'element-plus'
import PicAuthCode from "./PicAuthCode.vue"
import bilibiliIconUrl from '../assets/bilibiliIcon.png';
import githubIconUrl from '../assets/githubIcon.png';
import giteeIconUrl from '../assets/giteeIcon.png';

export default {
    name: 'login',
    data() {
        return {
            bilibiliIconUrl: bilibiliIconUrl,
            githubIconUrl: githubIconUrl,
            giteeIconUrl: giteeIconUrl,
            userLoginInformation: {
                username: "admin",   //用户名
                password: "123456",   //密码
            },
        }
    },
    components: {
        PicAuthCode,
    },
    methods: {
        login() {

            //校验数据（如果不为管理员则进入校验）
            if (this.userLoginInformation.username != "admin") {

                if (this.userLoginInformation.username.match(/^[\w-]{6,15}$/) == null) {
                    ElMessage.error('用户名不符合要求，需要在6-15位内并且为A-z,0-9,_,-的字符')
                    return
                } else if (this.userLoginInformation.password.match(/^[\w-]{6,15}$/) == null) {
                    ElMessage.error('密码不符合要求，需要在6-15位内并且为A-z,0-9,_,-的字符')
                    return
                }

            }

            userLoginAPI(this.userLoginInformation).then(res => {
                //如果获取的状态码为200则成功登录并跳转页面
                console.log(res.data)

                if (res.data.code == 400) {
                    ElMessage.error('用户名或密码错误，请检查后重试')
                    return
                } else if (res.data.code == 402) {
                    ElMessage('账号登录频繁，请稍后再试')
                    return
                } else if (res.data.code == 417) {
                    ElMessage('账号登录异常')
                    return
                } else if (res.data.code == 200) {
                    ElMessage({
                        message: '登录成功',
                        type: 'success',
                    })

                    //将获取到的token写入cookie（cookie设置了永不过期）
                    this.$cookies.set("token", res.data.token, -1);
                    console.log(res.data)
                    //将用户名字，id，权限等级，最后登录时间写入localStorage
                    window.localStorage.setItem('username', this.userLoginInformation.username)
                    window.localStorage.setItem('user_id', res.data.user_id)
                    window.localStorage.setItem('role', res.data.role)
                    window.localStorage.setItem('lastLogin', res.data.last_login)

                    //跳转页面
                    this.$router.push({
                        path: "/home",
                    })
                    return

                }

                ElMessage('登录出现异常，请重试')

                return

            }).catch(err => {
                console.log(err)
            })
        }
    }
}
</script>

<style scoped>
.login {
    width: 350px;
    height: 370px;
    border-radius: 20px;
    background-color: #211E34;
    -webkit-user-select: none;
    -moz-user-select: none;
    -ms-user-select: none;
    user-select: none
}

.information {
    width: 250;
    margin: 0 auto;

}

.information-username {
    width: 95%;

}

.information-password {
    width: 95%;

}

.login-button {
    box-shadow: 0 0 10px rgb(86, 196, 13);
}

.register-button {
    box-shadow: 0 0 10px rgb(46, 115, 243);
}
</style>
<template>
  <div class="h-full">
    <div class="login-container">

      <div class="login-from-content">
        <div class="login-wrap">
          <div class="login-logo">
            <img style="width: 216px;"
                 :src="logo"
                 alt="">
          </div>
          <div class="login-from">
            <div class="main">
              <p style="text-align: center;color:#666;margin-bottom: 20px;font-size: 20px" class="animate__animated animate__fadeInDown">后台登录</p>

              <el-form
                  ref="loginFormRef"
                  :rules="formRules"
                  :model="formData"
              >
                <el-form-item prop="username"
                              class="animate__animated animate__fadeInUp">
                  <el-input class="input" autofocus v-model="formData.username" placeholder="用户名" type="text" clearable/>
                </el-form-item>

                <el-form-item prop="password"
                              class="animate__animated animate__fadeInUp">
                  <el-input autocomplete="on" class="input" v-model="formData.password" placeholder="登录密码" type="password"
                            show-password/>
                </el-form-item>

                <el-form-item
                    v-if="loginVerifyMode === '2fa'"
                    class="box animate__animated animate__fadeInUp"
                    prop="verifyCode"
                >
                  <el-input
                      v-model.trim="formData.verifyCode"
                      placeholder="双因素认证验证码"
                      :prefix-icon="Check"
                  />
                </el-form-item>

                <el-form-item
                    v-else-if="loginVerifyMode === 'image'"
                    class="box animate__animated animate__fadeInUp"
                    prop="verifyCode"
                >
                  <div class="flex space-x-5 w-full">
                    <el-input
                        v-model.trim="formData.verifyCode"
                        class="flex-1"
                        placeholder="请输入验证码"
                        :prefix-icon="PhotographIcon"
                    />
                    <el-image
                        :src="loginVerifyCodeImgData?.imgBase64"
                        @click="loadLoginVerifyCodeImgData()"
                    ></el-image>
                  </div>
                </el-form-item>

                <el-form-item
                    class="box animate__animated animate__fadeInUp float-right"
                >
                  <el-tooltip class="item" effect="dark" placement="left">
                    <template #content>
                      管理员不怕忘记密码
                    </template>
                    <el-link
                        :icon="QuestionFilled"
                        :underline="false"
                        class="juyo-float-right"
                    >&nbsp;忘记密码</el-link
                    >
                  </el-tooltip>
                </el-form-item>

              </el-form>

              <el-button
                  class="sign"
                  type="primary"
                  @click="submitForm"
                  :loading="loading"
              >登录</el-button>

            </div>
          </div>

        </div>
      </div>
    </div>

  </div>
</template>

<script setup>
import {
  loginReq,
  loginVerifyModeReq,
  loginVerifyImgReq,
  checkLoginReq
} from '@/api/login'
import storageConfigStore from '@/stores/storage-config'
import logo from '@/assets/images/JCLOGO.png'

let router = useRouter()
let loading = ref(false)
let loginFormRef = ref(null)
import { User, Key, QuestionFilled, Check } from '@element-plus/icons-vue'
import { PhotographIcon } from '@heroicons/vue/outline'
import {installStatusReq} from "@/api/install";

let formData = ref({
  username: '',
  password: '',
  verifyCode: ''
})

let formRules = {
  username: [
    {required: true, message: '用户名不能为空', trigger: ['change']},
  ],
  password: [
    {required: true, message: '密码不能为空', trigger: ['change']},
  ],
}

const loginVerifyMode = ref('')

loginVerifyModeReq().then((res) => {
  loginVerifyMode.value = res.data
})

const loginVerifyCodeImgData = ref({})
const loadLoginVerifyCodeImgData = () => {
  loginVerifyImgReq().then((res) => {
    loginVerifyCodeImgData.value = res.data
    formData.value.verifyCodeUUID = res.data.uuid
  })
}

const submitForm = () => {
  loginFormRef.value.validate((checked) => {
    if (checked) {
      loading.value = true
      loginReq(formData.value).then((res) => {
        if (res.code === 0) {
          window.localStorage.setItem('jc-token', res.data)
          storageConfigStore().updateLoginStatus(true)
          ElMessage({
            message: "登录成功！",
            type: 'success',
            duration: 1000,
            onClose() {
              router.push('/home')
            },
          })
        } else {

          ElMessage({
            message: res.msg,
            type: 'error',
            duration: 1000,
          })
        }

      }).catch(() => {
        loading.value = false
        loadLoginVerifyCodeImgData()
      })
    } else {
      ElMessage.warning('请输入账号密码!')
    }
  })
}

onMounted(() => {
  installStatusReq().then((response) => {
    if (!response.data) {
      router.push('/install')
    }
  })

  // 如果已登录, 则自动跳转到 home.
  checkLoginReq().then((response) => {
    if (response.data) {
      router.push('/home')
    }
  })
})

</script>

<style scoped lang="stylus">
.login-container
  display flex
  flex-direction row
  min-height: 100vh;
  width: 100%
  justify-content: center;
  align-items: flex-start;
  background: #ecefff;

  .login-banner
    display flex
    align-items: center;
    flex-direction: column;
    width: 60vw;
    height: 35vw;
    margin 130px 25px

  .login-from-content
    margin-top: 130px
    display flex
    align-items: center;

    .login-wrap
      .login-logo
        display flex
        justify-content: center;
        margin-bottom: 50px;

      .login-from
        margin-top: 20px;
        background-color: #fff;
        padding: 25px;
        overflow hidden
        min-width: 260px;
        min-height: 340px;
        border-radius: 12px;
        box-shadow 0 2px 12px rgba(70, 70, 70, .1)

        .main
          width: 335px;

          .input
            height: 45px;

          .sign
            width: 100%;
            height: 45px;
            margin-top: 30px;
            border-radius 10px
            font-size 16px


</style>

<route lang="yaml">
meta:
  name: 登录
</route>
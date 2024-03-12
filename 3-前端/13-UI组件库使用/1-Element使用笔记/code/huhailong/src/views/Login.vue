<template>
  <div class="login">
    <div v-blog-title data-title="欢迎登录｜注册"></div>
    <el-row class="login-row">
      <el-col
        :xl="{ span: 14, offset: 5 }"
        :lg="{ span: 14, offset: 5 }"
        :md="{ span: 22, offset: 1 }"
        :sm="{ span: 22, offset: 1 }"
        :xs="{ span: 22, offset: 1 }"
        class="login-col"
      >
        <el-card class="login-box">
          <el-row>
            <el-col :lg="10" :md="10">
              <div class="left">
                <div class="box-left-top">
                  <div class="login-title">Blog System</div>
                  <div class="lobin-content">
                    <p>
                      欢迎使用
                      ,这是我的个人网站，有一些功能需要登录后才可以使用，请登录或注册后继续！
                    </p>
                    <!-- <p>https://www.huhailong.vip</p> -->
                  </div>
                  <div v-if="showType == 'login'" class="register">
                    <span>还没有账号？</span>
                    <span @click="switchModl" class="link">马上注册</span>
                  </div>
                  <div v-if="showType != 'login'" class="register">
                    <span @click="switchModl" class="link"
                      ><i class="fa fa-arrow-left"></i>返回登录</span
                    >
                  </div>
                  <div class="myemail">
                    <p>
                      <i class="fa fa-envelope"></i>何烘：151412413@qq.com
                    </p>
                  </div>
                </div>
              </div>
            </el-col>
            <el-col :lg="14" :md="14">
              <div class="right" v-if="showType == 'login'">
                <div class="login-title">登录账号</div>
                <div class="login-input">
                  <el-form>
                    <el-form-item>
                      <template slot="label">
                        <label>
                          <i class="fa fa-user"></i>用户名<span
                            v-if="warningType == 'username'"
                            class="warning"
                          >
                            <i
                              class="fa fa-exclamation-triangle"
                              aria-hidden="true"
                            ></i
                            >{{ warningMesg }}</span
                          >
                        </label>
                      </template>
                      <el-input
                        @keypress.native.enter="login"
                        clearable
                        type="text"
                        placeholder="用户名"
                        v-model="username"
                        @focus="clearWarning"
                      ></el-input>
                    </el-form-item>
                    <el-form-item>
                      <template slot="label">
                        <label
                          ><i class="fa fa-lock"></i>密码<span
                            v-if="warningType == 'password'"
                            class="warning"
                            ><i
                              class="fa fa-exclamation-triangle"
                              aria-hidden="true"
                            ></i
                            >{{ warningMesg }}</span
                          ></label
                        >
                      </template>
                      <el-input
                        @keypress.native.enter="login"
                        clearable
                        type="password"
                        placeholder="输入密码"
                        v-model="password"
                        @focus="clearWarning"
                      ></el-input>
                    </el-form-item>
                    <el-form-item>
                      <template slot="label">
                        <label
                          ><i class="fa fa-keyboard-o"></i>验证码<span
                            v-if="warningType == 'captcha'"
                            class="warning"
                            ><i
                              class="fa fa-exclamation-triangle"
                              aria-hidden="true"
                            ></i
                            >{{ warningMesg }}</span
                          ></label
                        >
                        <img
                          v-if="showCaptcha"
                          width="80px;"
                          style="cursor: pointer; margin-left: 5px"
                          :src="captchaImage"
                        />
                      </template>
                      <el-input
                        @keypress.native.enter="login"
                        clearable
                        type="text"
                        placeholder="验证码"
                        v-model="captcha"
                        @focus="
                          clearWarning();
                          showCaptcha = true;
                        "
                        @blur="showCaptcha = false"
                      ></el-input>
                    </el-form-item>
                  </el-form>
                </div>

                <div class="forget">
                  <el-link @click="showType = 'forget'"
                    ><i class="fa fa-unlock"></i>忘记密码？</el-link
                  >
                </div>
                <div class="login-btn">
                  <el-button
                    @click="login"
                    type="primary"
                    style="width: 100%"
                    size="medium"
                    ><i class="fa fa-sign-in"></i>登录</el-button
                  >
                </div>
              </div>
              <!-- 注册 -->
              <div class="right" v-if="showType == 'register'">
                <div class="login-title">注册账号</div>
                <div class="login-input">
                  <el-form>
                    <el-form-item>
                      <template slot="label">
                        <label>
                          <i class="fa fa-user"></i>用户名<span
                            v-if="warningType == 'username'"
                            class="warning"
                          >
                            <i
                              class="fa fa-exclamation-triangle"
                              aria-hidden="true"
                            ></i
                            >{{ warningMesg }}</span
                          >
                        </label>
                      </template>
                      <el-input
                        clearable
                        type="text"
                        placeholder="用户名"
                        v-model="addUser.username"
                        @focus="clearWarning"
                      ></el-input>
                    </el-form-item>
                    <el-form-item>
                      <template slot="label">
                        <label>
                          <i class="fa fa-envelope"></i>邮箱<span
                            v-if="warningType == 'email'"
                            class="warning"
                          >
                            <i
                              class="fa fa-exclamation-triangle"
                              aria-hidden="true"
                            ></i
                            >{{ warningMesg }}</span
                          >
                        </label>
                      </template>
                      <el-input
                        clearable
                        type="text"
                        placeholder="注册邮箱"
                        v-model="addUser.userEmail"
                        @focus="clearWarning"
                      ></el-input>
                    </el-form-item>
                    <el-form-item>
                      <template slot="label">
                        <label
                          ><i class="fa fa-lock"></i>密码<span
                            v-if="warningType == 'password'"
                            class="warning"
                            ><i
                              class="fa fa-exclamation-triangle"
                              aria-hidden="true"
                            ></i
                            >{{ warningMesg }}</span
                          ></label
                        >
                      </template>
                      <el-input
                        clearable
                        type="password"
                        placeholder="输入密码"
                        v-model="addUser.password"
                        @focus="clearWarning"
                      ></el-input>
                    </el-form-item>
                    <el-form-item>
                      <template slot="label">
                        <label
                          ><i class="fa fa-check-square"></i>确认密码<span
                            v-if="warningType == 'compirePassword'"
                            class="warning"
                            ><i
                              class="fa fa-exclamation-triangle"
                              aria-hidden="true"
                            ></i
                            >{{ warningMesg }}</span
                          ></label
                        >
                      </template>
                      <el-input
                        clearable
                        type="password"
                        placeholder="确认密码"
                        v-model="addUser.compirePassword"
                        @focus="clearWarning"
                      ></el-input>
                    </el-form-item>
                  </el-form>
                </div>
                <div class="login-btn">
                  <el-button
                    @click="register"
                    type="primary"
                    style="width: 100%"
                    size="medium"
                    ><i class="fa fa-registered"></i>注册</el-button
                  >
                </div>
              </div>
              <!-- 忘记密码 -->
              <div class="right" v-if="showType == 'forget'">
                <div class="login-title">找回密码</div>
                <div class="login-input">
                  <el-form>
                    <el-form-item>
                      <template slot="label">
                        <label>
                          <i class="fa fa-user"></i>用户名<span
                            v-if="warningType == 'username'"
                            class="warning"
                          >
                            <i
                              class="fa fa-exclamation-triangle"
                              aria-hidden="true"
                            ></i
                            >{{ warningMesg }}</span
                          >
                        </label>
                      </template>
                      <el-input
                        clearable
                        type="text"
                        placeholder="用户名"
                        v-model="find.username"
                        @focus="clearWarning"
                      ></el-input>
                    </el-form-item>
                    <el-form-item>
                      <template slot="label">
                        <label
                          ><i class="fa fa-envelope"></i>注册时邮箱<span
                            v-if="warningType == 'email'"
                            class="warning"
                            ><i
                              class="fa fa-exclamation-triangle"
                              aria-hidden="true"
                            ></i
                            >{{ warningMesg }}</span
                          ></label
                        >
                      </template>
                      <el-input
                        clearable
                        type="email"
                        placeholder="输入邮箱"
                        v-model="find.userEmail"
                        @focus="clearWarning"
                      ></el-input>
                    </el-form-item>
                  </el-form>
                </div>
                <div class="login-btn">
                  <el-button
                    @click="findPassword"
                    type="primary"
                    style="width: 100%"
                    size="medium"
                    ><i class="fa fa-fighter-jet"></i>马上找回</el-button
                  >
                </div>
              </div>
            </el-col>
          </el-row>
        </el-card>
      </el-col>
    </el-row>
    <!-- <div class="footer">
      <span>www.huhailong.vip</span>
    </div> -->
  </div>
</template>
<script>
import * as formValid from "@/util/formValid.js";
import * as ssoApi from "@/api/sso/api.js";
export default {
  name: "Login",
  data() {
    return {
      username: "",
      password: "",
      captcha: "",
      captchaKey: "",
      warningType: "",
      warningMesg: "",
      showType: "login",
      captchaImage: "",
      addUser: {},
      showCaptcha: false,
      find: {},
    };
  },
  methods: {
    switchModl() {
      // this.$message({
      //   type: 'warning',
      //   message: '正在完善中，即将开放'
      // })
      this.clearWarning();
      this.showType = this.showType == "login" ? "register" : "login";
    },
    findPassword() {
      let validResult = this.formValid("findPassword");
      if (validResult.status) {
        ssoApi.findPassword(this.find).then((res) => {
          this.$message({
            type: res.status ? "success" : "error",
            message: res.message,
          });
        });
      }
    },
    clearWarning() {
      this.warningType = "";
      this.warningMesg = "";
    },
    login() {
      let validResult = this.formValid("login");
      if (validResult.status) {
        let data = new FormData();
        data.append("username", this.username);
        data.append("password", this.password);
        data.append("captcha", this.captcha);
        data.append("captchaKey", this.captchaKey);
        ssoApi.login(data).then((res) => {
          this.$message({
            type: res.data.status ? "success" : "error",
            message: res.data.message,
          });
          if (res.data.status) {
            localStorage.setItem("isLogin", true);
            this.getUserInfo();
          }
        });
      }
    },
    getUserInfo() {
      ssoApi.getUserInfo().then((res) => {
        if (res.status) {
          localStorage.setItem("hhl-info", JSON.stringify(res.data));
          this.$router.push(this.common.prePath);
        }
      });
    },
    register() {
      let validReslt = this.formValid("register");
      if (validReslt.status) {
        this.addUser.autoRegister = true;
        ssoApi.addUser(this.addUser).then((res) => {
          this.$alert(res.message, "提示", {
            confirmButtonText: "确定",
          });
        });
      }
    },
    formValid(type) {
      let validResult = {};
      if (type == "login") {
        validResult = formValid.checkLogin(
          this.username,
          this.password,
          this.captcha
        );
      }
      if (type == "register") {
        validResult = formValid.checkRegister(this.addUser);
      }
      if (type == "findPassword") {
        validResult = formValid.checkFindPassword(
          this.find.username,
          this.find.userEmail
        );
      }
      if (!validResult.status) {
        this.warningType = validResult.type;
        this.warningMesg = validResult.message;
      }
      return validResult;
    },
    getCaptchaImage() {
      ssoApi.getCaptcha().then((res) => {
        this.captchaKey = res.data.key;
        this.captchaImage = res.data.image;
      });
    },
  },
  mounted() {
    this.getCaptchaImage();
  },
};
</script>
<style lang="scss" scoped>
$subcolor: rgba(
  $color: #ffffff,
  $alpha: 0.7,
);
$fontColor: var(--fontColor, #344050);
$cardLeftFontColor: var(--cardLeftFontColor, #f9fafd);
$backgroundColor: var(--backgroundColor, #edf2f9);
$cardLeftColor: var(--cardLeftColor, #3385f3);
$cardRightColor: var(--cardRightColor, #ffffff);
$inputBorderColor: var(--inputBorderColor, #d8e2ef);
$labelColor: var(--labelColor, #5e6e82);
.login {
  height: 100%;
  background-color: $backgroundColor;
  .login-row {
    min-height: 100vh;
    display: flex;
    align-items: center;
  }
  .login-box {
    background: linear-gradient(45deg, #34495E, #41B883);  

    .left {
      .box-left-top {
        .login-title {
          padding-top: 2rem;
          color: $cardLeftFontColor;
          font-size: 26px;
          font-weight: bold;
          text-shadow: 1px 2px 3px #34495E;
        }
        .lobin-content {
          color: $subcolor;
          padding-top: 2rem;
          margin-bottom: 4rem;
          padding-left: 5px;
          padding-right: 5px;
        }
        .register {
          color: $subcolor;
          margin-bottom: 3rem;
          .link {
            cursor: pointer;
            color: $subcolor;
          }
          .link:hover {
            color: rgba($color: #ffffff, $alpha: 0.8);
          }
        }
        .myemail {
          color: $cardLeftFontColor;
          font-weight: bold;
          margin-top: 2rem;
        }
      }
    }
    .right {
      width: 100%;
      height: 100%;
      background-color: $cardRightColor;
      .login-title {
        text-align: left;
        font-size: 26px;
        font-weight: bold;
        padding: 2rem 1rem 1rem 1rem;
        width: 100%;
      }
      .login-input {
        padding: 0 1rem;
      }
      .login-btn {
        padding: 2rem 1rem;
      }
    }
  }
}
.footer {
  position: fixed;
  bottom: 0;
  background-color: #d8e2ef;
  width: 100%;
  padding-bottom: 1rem;
  text-align: center;
}
</style>
<style>
.login .el-card__body {
  padding: 0;
}
</style>
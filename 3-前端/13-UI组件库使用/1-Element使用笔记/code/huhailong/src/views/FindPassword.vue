<template>
  <div class="findPassword">
    <div v-blog-title data-title="密码找回"></div>
    <el-row class="box">
      <h1 style="color: white">密码找回</h1>

      <el-col :md="8" :lg="8" :sm="20" :xs="20">
        <el-card>
          <el-form
            :rules="rules"
            ref="ruleForm"
            status-icon
            :model="find"
            label-position="left"
          >
            <el-form-item label="设置密码" prop="password">
              <el-input
                v-model="find.password"
                placeholder="设置密码"
              ></el-input>
            </el-form-item>
            <el-form-item label="确认密码" prop="checkPass">
              <el-input
                v-model="find.checkPass"
                placeholder="确认密码"
              ></el-input>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="submitForm('ruleForm')"
                >提交</el-button
              >
            </el-form-item>
          </el-form>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>
<script>
import * as ssoApi from "@/api/sso/api.js";
export default {
  name: "findPassword",
  props: ["code"],
  data() {
    var validatePass = (rule, value, callback) => {
      let reg = /^(?=.*?[a-z)(?=.*>[A-Z])(?=.*?[0-9])[a-zA_Z0-9]{6,20}$/;
      if (!reg.test(this.find.password)) {
        callback(new Error("密码必须包含数字和字母且长度大于6小于20"));
      } else {
        if (this.find.checkPass !== "") {
          this.$refs.ruleForm.validateField("checkPass");
        }
        callback();
      }
    };
    var validatePass2 = (rule, value, callback) => {
      let reg = /^(?=.*?[a-z)(?=.*>[A-Z])(?=.*?[0-9])[a-zA_Z0-9]{6,20}$/;
      if (!reg.test(value)) {
        callback(new Error("请再次输入密码"));
      } else if (value !== this.find.password) {
        callback(new Error("俩次输入的密码不一致"));
      } else {
        callback();
      }
    };
    return {
      find: {},
      rules: {
        password: [{ validator: validatePass, trigger: "blur" }],
        checkPass: [{ validator: validatePass2, trigger: "blur" }],
      },
    };
  },
  methods: {
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.findPassword();
        } else {
          console.log("error submit!!");
          return false;
        }
      });
    },
    findPassword() {
      if (this.code == null || this.code == "") {
        this.$message({
          type: "warning",
          message: "授权码为空，请重新发送找回密码",
        });
        return;
      }
      let param = {
          newPassword: this.find.password,
          code: this.code
      }
      ssoApi.checkFindPasswordCode(param).then((res) => {
        if (res.status) {
          this.$router.push("/login");
        } else {
          this.$message({
            type: "error",
            message: res.message,
          });
        }
      });
    },
  },
  mounted() {
    console.log(this.code);
  },
};
</script>
<style scss scoped>
.findPassword {
  display: flex;
  align-items: center;
  justify-content: center;
  width: 100%;
  height: 100vh;
  background-color: #041e2f;
}
.box {
  width: 100%;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
}
</style>
<template lang="">
  <div>
    <!-- 搜索栏 -->
    <el-card id="search">
      <el-row>
        <el-col :span="4">
          <el-input
            v-model="searchModel.username"
            placeholder="用户名"
          ></el-input>
        </el-col>
        <el-col :span="4">
          <el-input v-model="searchModel.phone" placeholder="电话"></el-input>
        </el-col>
        <el-col :span="8" :offset="1">
          <el-button
            @click="getUserList"
            type="primary"
            round
            icon="el-icon-search"
            >查询</el-button
          >
        </el-col>
        <!-- 新增按钮 -->
        <el-col :span="7" align="right">
          <el-button
            @click="openEditUi(null)"
            type="primary"
            icon="el-icon-plus"
            circle
          ></el-button>
        </el-col>
      </el-row>
    </el-card>
    <!-- 结果列表 -->
    <el-card>
      <el-table :data="userList" stripe style="width: 100%">
        <el-table-column type="index" label="#" width="80">
          <!--得到索引号-->
          <template slot-scope="scope">
            {{
              (searchModel.pageNo - 1) * searchModel.pageSize + scope.$index + 1
            }}
          </template>
        </el-table-column>
        <el-table-column prop="id" label="用户ID" width="180">
        </el-table-column>
        <el-table-column prop="username" label="用户名" width="180">
        </el-table-column>
        <el-table-column prop="phone" label="电话" width="180">
        </el-table-column>
        <el-table-column prop="status" label="用户状态" width="180">
          <template slot-scope="scope">
            <el-tag v-if="scope.row.status == 1">正常</el-tag>
            <el-tag v-else type="danger">禁用</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="email" label="电子邮件"> </el-table-column>
        <el-table-column label="操作" width="180">
          <!--操作删除按钮-->
          <template slot-scope="scope">
            <el-button
              @click="openEditUi(scope.row.id)"
              type="primary"
              icon="el-icon-edit"
              size="mini"
              circle
            ></el-button>
            <el-button
              @click="deleteUser(scope.row)"
              type="danger"
              icon="el-icon-delete"
              size="mini"
              circle
            ></el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>
    <!-- 分页组件 -->
    <div style="display: flex; justify-content: center">
      <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page.sync="searchModel.pageNo"
        :page-sizes="[5, 10, 20, 50]"
        :page-size="100"
        layout="total, sizes, prev, pager, next"
        :total="total"
      >
      </el-pagination>
    </div>

    <!--新增用户/修改用户 -->
    <!--:sss="xxx"指令语法-->
    <el-dialog
      @close="clearFrom"
      :title="title"
      :visible.sync="dialogFormVisible"
    >
      <!-- 表单  ref用于给节点打标识,用来使用里面的内置函数-->
      <!--绑定数据 :model="userForm"  // 绑定数据的参数 prop="username"-->
      <el-form :model="userForm" :rules="rules" ref="userFormRef">
        <el-form-item
          label="用户名"
          prop="username"
          :label-width="formLabelWidth"
        >
          <el-input v-model="userForm.username" autocomplete="off"></el-input>
        </el-form-item>

        <el-form-item
          v-if="title === '新增用户'"
          label="登录密码"
          prop="password"
          :label-width="formLabelWidth"
        >
          <el-input
            type="password"
            v-model="userForm.password"
            autocomplete="off"
          ></el-input>
        </el-form-item>
        <el-form-item label="联系电话" :label-width="formLabelWidth">
          <el-input v-model="userForm.phone" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="用户状态" :label-width="formLabelWidth">
          <el-switch
            v-model="userForm.status"
            :active-value="1"
            :inactive-value="0"
          >
          </el-switch>
        </el-form-item>
        <el-form-item
          label="电子邮件"
          prop="email"
          :label-width="formLabelWidth"
        >
          <el-input v-model="userForm.email" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="saveUser"> 确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import userApi from "@/api/userManage"; //注册
export default {
  data() {
    // 自定义验证规则
    var checkEmail = (rule, value, callback) => {
      var reg =
        /^[a-zA-Z0-9]+([-_.][a-zA-Z0-9]+)*@[a-zA-Z0-9]+([-_.][a-zA-Z0-9]+)*\.[a-z]{2,}$/;
      if (!reg.test(value)) {
        return callback(new Error("邮箱格式错误"));
      }
      callback(); // 返回
    };
    return {
      searchModel: {
        pageNo: 1, //页号
        pageSize: 5, // 默认显示条数
      },
      userList: [],
      total: 0,
      //
      title: "",
      formLabelWidth: "120px",
      dialogFormVisible: false, //显示
      userForm: {}, //表单数据
      // 表单验证规则
      rules: {
        uname: [
          { required: true, message: "请输入用户名", trigger: "blur" },
          {
            min: 3,
            max: 50,
            message: "长度在 3 到 50 个字符",
            trigger: "blur",
          },
        ],
        password: [
          { required: true, message: "请输入登录初始密码", trigger: "blur" },
          {
            min: 6,
            max: 16,
            message: "长度在 6 到 16 个字符",
            trigger: "blur",
          },
        ],
        email: [
          { required: true, message: "请输入电子邮件", trigger: "blur" },
          { validator: checkEmail, trigger: "blur" },
        ],
      },
    };
  },
  methods: {
    handleSizeChange(pageSize) {
      this.searchModel.pageSize = pageSize;
      this.getUserList();
    },
    handleCurrentChange(pageNo) {
      this.searchModel.pageNo = pageNo;
      this.getUserList();
    },

    getUserList() {
      userApi.getUserList(this.searchModel).then((response) => {
        this.userList = response.data.rows;
        this.total = response.data.total;
      });
    },
    openEditUi(id) {
      if (id == null) {
        this.title = "新增用户";
      } else {
        this.title = "修改用户";
        //根据id查询用户数据
        userApi.getUserById(id).then((response) => {
          this.userForm = response.data;
        });
      }
      this.dialogFormVisible = true;
    },
    // 清楚表单数据
    clearFrom() {
      this.userForm = {};
      // 清楚表单验证结果
      // ：this.$refs.xxxx读取节点
      this.$refs.userFormRef.clearValidate(); //Element表单的函数clearValidate
    },
    // 点击保存
    saveUser() {
      //Element表单的函数 validate
      this.$refs.userFormRef.validate((valid) => {
        if (valid) {
          console.log(this.userForm);
          if (this.title == "修改用户") {
            //不修改改密码
            userApi.updateUser(this.userForm).then((response) => {
              // 成功提示
              this.$message({
                message: response.message,
                type: "success",
              });
              // 关闭对话框
              this.dialogFormVisible = false;
              // 刷新表格
              this.getUserList();
            });
          } else if ((this.title = "新增用户")) {
            // 提交请求给后台
            userApi.addUser(this.userForm).then((response) => {
              // 成功提示
              this.$message({
                message: response.message,
                type: "success",
              });
              // 关闭对话框
              this.dialogFormVisible = false;
              // 刷新表格
              this.getUserList();
            });
          } else {
            console.log("error submit!!");
            return false;
          }
        }
      });
    },

    // 删除
    deleteUser(user){
      this.$confirm(`您确认删除用户 ${user.username} ?`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
          userApi.deleteUserById(user.id).then(response => {
            this.$message({
              message: response.message,
              type: "success",
            });
          this.getUserList();
        });
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        });
      });
    },
  },

  // 初始化
  mounted() {
    this.getUserList();
  }
};
</script>
<style lang=""></style>

<template>
  <div>
    <!-- 搜索栏 -->
    <el-card id="search">
      <el-row>
        <el-col :span="18">
          <el-input placeholder="角色名" v-model="searchModel.roleName" clearable> </el-input>
          <el-button @click="getRoleList" type="primary" icon="el-icon-search" round>查询</el-button>
        </el-col>
        <el-col :span="6" align="right">
          <el-button @click="openEditUI(null)" type="primary" icon="el-icon-plus" circle></el-button>
        </el-col>
      </el-row>
    </el-card>

    <!-- 结果列表 -->
    <el-card>
 
        <el-table :data="roleList" stripe style="width: 100%">
          <el-table-column label="#" width="80">
            <template slot-scope="scope">
              {{(searchModel.pageNo-1) * searchModel.pageSize + scope.$index + 1}}
            </template>
          </el-table-column>
          <el-table-column prop="roleId" label="角色编号" width="180">
          </el-table-column>
          <el-table-column prop="roleName" label="角色名称" width="180">
          </el-table-column>
          <el-table-column prop="roleDesc" label="角色描述" >
          </el-table-column>
          <el-table-column   label="操作" width="180">
            <template slot-scope="scope">
              <el-button @click="openEditUI(scope.row.roleId)" type="primary" icon="el-icon-edit" circle size="mini"></el-button>
              <el-button @click="deleteRole(scope.row)" type="danger" icon="el-icon-delete" circle size="mini"></el-button>
            </template>
          </el-table-column>
        </el-table> 
 
    </el-card>
    <el-pagination
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :current-page="searchModel.pageNo"
      :page-sizes="[5, 10, 20, 50]"
      :page-size="searchModel.pageSize"
      layout="total, sizes, prev, pager, next, jumper"
      :total="total">
    </el-pagination>

    <!-- 对话框 -->
    <el-dialog @close="clearForm" :title="title" :visible.sync="dialogFormVisible" :close-on-click-modal="false">
      <el-form :model="roleForm" ref="roleFormRef" :rules="rules">
        <el-form-item prop="roleName" label="角色名称" :label-width="formLabelWidth">
          <el-input v-model="roleForm.roleName" autocomplete="off"></el-input>
        </el-form-item>
        
        <el-form-item prop="roleDesc" label="角色描述" :label-width="formLabelWidth">
          <el-input v-model="roleForm.roleDesc" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="saveRole">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>


<script>
import roleApi from '@/api/roleManage'
export default {
  data(){
    
    return{
      formLabelWidth: '130px',
      roleForm: {},
      dialogFormVisible: false,
      title: '',
      searchModel: {
        pageNo: 1,
        pageSize: 10
      },
      roleList: [],
      total: 0,
      rules:{
        roleName: [
          { required: true, message: '请输入角色名称', trigger: 'blur' },
          { min: 3, max: 50, message: '长度在 3 到 50 个字符', trigger: 'blur' }
        ]
      }
    }
  },
  methods:{
    deleteRole(role){
      this.$confirm(`您确定删除角色 ${role.roleName} ？`, '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
      }).then(() => {
        roleApi.deleteRoleById(role.roleId).then(response => {
          this.$message({
            type: 'success',
            message: response.message
          });
          this.dialogFormVisible = false;
          this.getRoleList();
        });
        
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        });          
      });
    },
    saveRole(){
      // 触发表单验证
      this.$refs.roleFormRef.validate((valid) => {
        if (valid) {
          // 提交保存请求
          roleApi.saveRole(this.roleForm).then(response => {
            // 成功提示
            this.$message({
              message: response.message,
              type: 'success'
            });
            // 关闭对话框
            this.dialogFormVisible = false;
            // 刷新表格数据
            this.getRoleList();
          });
          
        } else {
          console.log('error submit!!');
          return false;
        }
      });
      
    },
    clearForm(){
      this.roleForm = {};
      this.$refs.roleFormRef.clearValidate();
    },
    openEditUI(id){
      if(id == null){
        this.title = '新增角色';
      }else{
        this.title = '修改角色';
        roleApi.getRoleById(id).then(response => {
          this.roleForm = response.data;
        });
      }
      this.dialogFormVisible = true;
    },
    handleSizeChange(pageSize){
      this.searchModel.pageSize = pageSize;
      this.getRoleList();
    },
    handleCurrentChange(pageNo){
      this.searchModel.pageNo = pageNo;
      this.getRoleList();
    },
    getRoleList(){
      roleApi.getRoleList(this.searchModel).then(response => {
        this.roleList = response.data.rows;
        this.total = response.data.total;
      });
    }
  },
  created(){
    this.getRoleList();  //显示list
  }
};
</script>

<style>
#search .el-input {
  width: 200px;
  margin-right: 10px;
}
.el-dialog .el-input{
  width: 85%;
}
</style>

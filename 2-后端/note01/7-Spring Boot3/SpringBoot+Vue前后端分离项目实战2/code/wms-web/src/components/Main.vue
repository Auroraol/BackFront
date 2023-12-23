<template>
  <!-- 搜索框 -->
  <div style="margin-bottom: 5px">
    <el-input
      v-model="name"
      placeholder="请输入名字"
      style="width: 200px"
      @keyup.enter.native="loadPost"
    />
    <el-select
      v-model="sex"
      filterable
      placeholder="请选择性别"
      style="margin-left: 5px"
    >
      <el-option
        v-for="item in sexs"
        :key="item.value"
        :label="item.label"
        :value="item.value"
      >
      </el-option>
    </el-select>
    <el-button type="primary" style="margin-left: 5px" @click="loadPost"
      >查询</el-button
    >
    <el-button type="success" @click="resetParam">重置</el-button>

    <el-button type="primary" style="margin-left: 5px" @click="add"
      >新增</el-button
    >
  </div>
  <!-- 表格 -->
  <el-table :data="tableData" style="width: 100%">
    <el-table-column prop="id" label="ID"> </el-table-column>
    <el-table-column prop="no" label="账号"> </el-table-column>
    <el-table-column prop="name" label="姓名"> </el-table-column>
    <el-table-column prop="age" label="年龄"> </el-table-column>
    <el-table-column prop="sex" label="性别">
      <template #default="scope">
        <el-tag
          :type="scope.row.sex === 1 ? 'primary' : 'success'"
          disable-transitions
          >{{ scope.row.sex === 1 ? "男" : "女" }}
        </el-tag>
      </template>
    </el-table-column>
    <el-table-column prop="roleId" label="角色">
      <template #default="scope">
        <el-tag
          :type="
            scope.row.roleId === 0
              ? 'danger'
              : scope.row.roleId === 1
              ? 'primary'
              : 'success'
          "
          disable-transitions
          >{{
            scope.row.roleId === 0
              ? "超级管理员"
              : scope.row.roleId === 1
              ? "管理员"
              : "用户"
          }}</el-tag
        >
      </template>
    </el-table-column>
    <el-table-column prop="phone" label="电话"> </el-table-column>

    <el-table-column prop="operate" label="操作">
      <template #default="scope">
        <el-button size="small" @click="handleEdit(scope.$index, scope.row)"
          >编辑</el-button
        >
        <el-button
          size="small"
          type="danger"
          @click="handleDelete(scope.$index, scope.row)"
          >删除</el-button
        >
      </template>
    </el-table-column>
  </el-table>
  <!-- 分页 -->
  <el-config-provider :locale="locale">
    <!-- <el-table mb-1 :data="[]" /> -->
    <el-pagination
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :current-page="pageNum"
      :page-sizes="[5, 10, 20, 30]"
      :page-size="pageSize"
      layout="total, sizes, prev, pager, next, jumper"
      :total="total"
    >
    </el-pagination>
    <!-- <el-pagination :total="100" /> -->
  </el-config-provider>
</template>

<script setup lang="ts">
import { onBeforeMount, computed, reactive, ref } from "vue";
let pageSize = ref(10);
let pageNum = ref(1);
let total = ref(0);
const form = reactive({
  id: "",
  no: "",
  name: "",
  password: "",
  age: "",
  phone: "",
  sex: "0",
  roleId: "1",
});

import zhCn from "element-plus/dist/locale/zh-cn.mjs";
import en from "element-plus/dist/locale/en.mjs";

const language = ref("zh-cn");
const locale = computed(() => (language.value === "zh-cn" ? zhCn : en));

// //声明对象的格式
// interface User {
//   date: string;
//   name: string;
//   address: string;
// }

// const handleEdit = (index: number, row: User) => {
//   console.log(index, row);
// };

// const handleDelete = (index: number, row: User) => {
//   console.log(index, row);
// };

// //定义一个对象数组
// const memberList = ref<Member[]>([
// 	 {
//                         value: '1',
//                         label: '男'
//                     }, {
//                         value: '0',
//                         label: '女'
//                     }
// ])
//   let tableData = reactive({
//                     id:'',
//                     no:'',
//                     name:'',
//                     password:'',
//                     age:'',
//                     phone:'',
//                     sex:'0',
//                     roleId:'1'
//    })
let loadPost = () => {
  $axios
    .post("/user/listPageC1", {
      pageSize: pageSize,
      pageNum: pageNum,
      param: {
        name: name,
        sex: sex,
      },
    })
    .then((res) => res.data)
    .then((res) => {
      console.log(res);
      if (res.code == 200) {
        tableData = res.data;
        total = res.total;
      } else {
        alert("获取数据失败");
      }
    });
}

onBeforeMount(() => {
    loadPost();
})
</script>

<style></style>

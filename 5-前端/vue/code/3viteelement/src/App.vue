<template>
  <el-form :model="form" ref="formRef" :rules="rules" label-width="120px">
        <el-form-item label="活动名称:" prop="ActName">
          <el-input v-model="form.ActName" placeholder="请输入活动名称" />
        </el-form-item>
        <el-form-item label="活动分类:" prop="ActCategory">
          <el-select v-model="form.ActCategory" placeholder="请选择活动类型">
            <el-option label="下单奖励红包" value="1" />
          </el-select>
        </el-form-item>
        <el-form-item label="活动有效时间:" prop="time">
          <el-date-picker
            v-model="form.time"
            type="daterange"
            range-separator="至"
            start-placeholder="开始时间"
            end-placeholder="结束时间"
            value-format="YYYY-MM-DD"
          />
        </el-form-item>
        <el-form-item label="活动金额:" prop="TotalAmount">
          <el-input v-model="form.TotalAmount" />
        </el-form-item>
        <el-form-item>
            <el-button type="primary" @click="createClick">创 建</el-button>
        </el-form-item>
      </el-form>
</template>

<script setup>
  import { reactive, ref } from "vue";
  const formRef = ref(null);
  const form = reactive({
    ActName: "",
    ActCategory: "",
    BeginTime: "",
    EndTime: "",
    TotalAmount: "",
    time: [],
  })
  // form表单的校验规则
  const rules = reactive({
    ActName: [{ required: true, message: "请输入活动名称", trigger: "blur" }],
    ActCategory: [
      {
        required: true,
        message: "请选择活动类型",
        trigger: "change",
      },
    ],
    TotalAmount: [
      {
        required: true,
        message: "请输入活动金额",
        trigger: "blur",
      },
    ],
    time: [
      {
        type: "array",
        required: true,
        message: "请选择活动有效时间",
        trigger: "change",
      },
    ],
  });

  // 创建活动事件
  const createClick = () => {
    formRef.value.validate(async (isValid, invalidFields) => {
      if (isValid) {
        console.log(tableData.value);
        if (tableData.value.length === 0) {
          ElMessage({
            message: "配置信息不能为空",
            type: "warning",
          });
          return;
        }
        const params = {
          ActName: form.ActName,
          ActCategory: form.ActCategory,
          BeginTime: form.time[0],
          EndTime: form.time[1],
          TotalAmount: form.TotalAmount,
          ActConfigs: tableData.value,
        };
        const loading = ElLoading.service({
          lock: true,
          text: "Loading",
          background: "Transparent",
        });
        const res = await actCreate(params);
        if (res.result) {
          loading.close();
          ElMessage({
            message: res.msg,
            type: "success",
          });
          formRef.value.resetFields(); //清空表单
        } else {
          loading.close();
          ElMessage.error(res.msg);
        }
      } else {
        console.log("验证不通过,不能提交,请检查");
      }
    });
  };
</script>
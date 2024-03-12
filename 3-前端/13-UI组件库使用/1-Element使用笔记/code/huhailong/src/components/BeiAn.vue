<template>
  <div class="beian">
    <el-row style="width:100%;">
      <el-col :lg="24">
        <img src="../assets/beian.png" />
        <a
          target="_blank"
          href="http://www.beian.gov.cn/portal/registerSystemInfo?recordcode=11011502003826"
          style="
            display: inline-block;
            text-decoration: none;
            height: 20px;
            line-height: 20px;
          "
          ><img src="" style="float: left" />
          <p
            style="
              float: left;
              height: 20px;
              line-height: 20px;
              margin: 0px 0px 0px 5px;
              color: #ffffff;
            "
          >
            京公网安备 11011502003826号
          </p></a
        >
      </el-col>
      <el-col :lg="24">
        <a
          href="https://beian.miit.gov.cn/"
          target="_blank"
          style="
            display: inline-block;
            text-decoration: none;
            height: 20px;
            line-height: 20px;
          "
          ><span style="height: 20px; line-height: 20px; color: #ffffff"
            >冀ICP备18032095号-2</span
          ></a
        >
      </el-col>
    </el-row>
  </div>
</template>
<script>
export default {
  name: "BeiAn",
};
</script>
<style lang="scss" scoped>
.beian {
  width: 100%;
  text-align: center;
  margin-bottom: 10px;
  display: flex;
  align-items: center;
  justify-content: center;
}
</style>
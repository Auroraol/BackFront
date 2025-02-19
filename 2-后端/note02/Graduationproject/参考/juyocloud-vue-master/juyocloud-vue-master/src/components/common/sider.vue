<template>
  <el-aside class="jy-menu">
    <el-scrollbar>
      <router-link to="/home">
        <div class="logo">
          <img :src="logo" style="height: 100%;" alt="橘柚云盘">
        </div>
      </router-link>

      <el-menu
          style="border-right: none;"
          active-text-color="#3766ff"
          :default-active="activeIndex"
          text-color="#636d7e"
          :default-openeds="[FILE_TYPE.ALL]"
          @select="handleSelect"
      >

        <el-sub-menu :index="FILE_TYPE.ALL">
          <template #title>
            <span>我的文件</span>
          </template>
          <el-menu-item-group>
            <el-menu-item :index="FILE_TYPE.IMAGE">
              <el-icon>
                <i-ep-picture/>
              </el-icon>
              <span>图片</span>
            </el-menu-item>
            <el-menu-item :index="FILE_TYPE.DOC">
              <el-icon>
                <i-ep-document/>
              </el-icon>
              <span>文档</span>
            </el-menu-item>
            <el-menu-item :index="FILE_TYPE.VIDEO">
              <el-icon>
                <i-ep-film/>
              </el-icon>
              <span>视频</span>
            </el-menu-item>
            <el-menu-item :index="FILE_TYPE.AUDIO">
              <el-icon>
                <i-ep-headset/>
              </el-icon>
              <span>音频</span>
            </el-menu-item>
            <el-menu-item :index="FILE_TYPE.OTHER">
              <el-icon>
                <i-ep-moreFilled/>
              </el-icon>
              <span>其他</span>
            </el-menu-item>
          </el-menu-item-group>
        </el-sub-menu>

        <el-menu
          @select="selectMenu"
        >
          <el-menu-item index="/share">
            <template #title>
              <el-icon>
                <i-ep-position/>
              </el-icon>
              <span>我的分享</span>
            </template>
          </el-menu-item>

          <el-menu-item index="/recyclebin">
            <template #title>
              <el-icon>
                <i-ep-delete/>
              </el-icon>
              <span>回收站</span>
            </template>
          </el-menu-item>
        </el-menu>
      </el-menu>
    </el-scrollbar>
  </el-aside>

</template>

<script setup>
import {FILE_TYPE} from  '@/common'
import logo from '@/assets/images/JCLOGO.png'

let router = useRouter()

let activeIndex = ref(router.currentRoute.value.path)

watch(() => router.currentRoute.value.path, (newVal, oldVal) => {
  activeIndex.value = newVal
})

const handleSelect = (data) => {
  router.push({
    path: '/home',
    query: {type: data}
  })
}

const selectMenu = (data) => {
  router.push({
    path: data
  })
}

</script>

<style scoped lang="stylus">
.jy-menu
  height: 100%
  padding: 0 10px;
  padding-top: 10px;
  width: 255px;
  border-right: 1px solid #f1f1f3;

  .logo
    padding: 23px;
    display flex
    justify-content: center;

  .el-menu
    border none

    .el-menu-item
      border-radius 10px
      height: 48px;
      user-select none

      &:hover
        background-color: #fafafc;

      &.is-active
        background-color: #f1f8ff;

    .el-menu-item:not(.el-sub-menu .el-menu-item)
      font-weight: bold;


</style>

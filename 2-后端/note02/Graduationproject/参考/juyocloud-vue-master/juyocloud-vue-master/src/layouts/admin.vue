<template>
  <div style="height: 100%;overflow: hidden">
    <el-container class="layout-container" style="height: 100%">
      <el-aside width="230px" style="height: 100%;" class="admin-menu">
        <router-link to="/home">
          <div class="logo">
            <img :src="logo"/>
          </div>
        </router-link>

        <el-menu
            @select="handleSelect"
            style="border: none"
        >
          <el-menu-item index="/admin/site-setting">
            <el-icon size="18px">
              <i-ep-setting class="mr-1"/>
            </el-icon>
            基本设置
          </el-menu-item>
          <el-menu-item index="/admin/storage-list">
            <el-icon size="18px">
              <i-ep-folder class="mr-1"/>
            </el-icon>
            存储源设置
          </el-menu-item>
          <el-menu-item index="/admin/view-setting">
            <el-icon size="18px">
              <i-ep-view class="mr-1"/>
            </el-icon>
            显示设置
          </el-menu-item>
          <el-sub-menu index="/admin/download-link">
            <template #title>
              <el-icon size="18px">
                <i-ep-link class="mr-1"/>
              </el-icon>
              直链管理
            </template>
            <el-menu-item-group>
              <el-menu-item index="/admin/download-link/setting">
                直链设置
              </el-menu-item>
            </el-menu-item-group>
          </el-sub-menu>
        </el-menu>
      </el-aside>
      <el-container direction="vertical" style="height: 100%;">
        <el-header class="page-header">

          <div class="flex-grow"/>
          <div
              class="absolute inset-y-0 right-0 flex items-center pr-2 sm:static sm:inset-auto sm:ml-6 sm:pr-0"
          >
            <!-- Profile dropdown -->
            <Menu as="div" class="ml-3 relative">
              <div>
                <MenuButton
                    class="bg-white rounded-full flex text-sm"
                >
                  <el-avatar
                      v-if="siteSetting?.data?.avatar"
                      :size="36"
                      :src="siteSetting.data.avatar"
                  />
                  <el-avatar
                      v-else
                      :size="36"
                      :src="avatorImg"
                  />
                </MenuButton>
              </div>
              <transition
                  enter-active-class="transition ease-out duration-200"
                  enter-from-class="transform opacity-0 scale-95"
                  enter-to-class="transform opacity-100 scale-100"
                  leave-active-class="transition ease-in duration-75"
                  leave-from-class="transform opacity-100 scale-100"
                  leave-to-class="transform opacity-0 scale-95"
              >
                <MenuItems
                    class="z-10 origin-top-right absolute right-0 mt-2 w-48 rounded-md shadow-lg py-1 bg-white ring-1 ring-black ring-opacity-5 focus:outline-none"
                >
                  <MenuItem v-slot="{ active }">
                    <router-link
                        to="/admin/security-setting"
                        :class="[
											active ? 'bg-gray-100' : '',
											'block px-4 py-2 text-sm text-gray-700',
										]"
                    >
                      安全设置
                    </router-link>
                  </MenuItem>
                  <MenuItem v-slot="{ active }">
                    <router-link
                        to="/admin/update-password"
                        :class="[
											active ? 'bg-gray-100' : '',
											'block px-4 py-2 text-sm text-gray-700',
										]"
                    >
                      修改密码
                    </router-link>
                  </MenuItem>
                  <MenuItem v-slot="{ active }" @click="logout">
                    <a
                        href="#"
                        :class="[
											active ? 'bg-gray-100' : '',
											'block px-4 py-2 text-sm text-gray-700',
										]"
                    >注销</a
                    >
                  </MenuItem>
                </MenuItems>
              </transition>
            </Menu>
          </div>

        </el-header>
        <el-main style="padding: 0;">
          <!--  main-->
          <div
              class="p-0 sm:p-5 pb-16 sm:pb-26 bg-gray-100 h-full overflow-y-auto"
          >
            <div
                class="max-w-8xl mx-auto overflow-hidden sm:rounded-lg"
            >
              <router-view class="box animate__animated animate__fadeIn"/>
            </div>
          </div>
        </el-main>
      </el-container>
    </el-container>

  </div>
</template>

<script setup>
import useAdminLayout from '@/composables/admin/layout/admin-layout'

import {
  Menu,
  MenuButton,
  MenuItem,
  MenuItems,
} from '@headlessui/vue'


import avatorImg from '@/assets/images/avator.png'
import logo from '@/assets/images/JCLOGO.png'

let router = useRouter()
let route = useRoute()

const handleSelect = (data) => {
  router.push({
    path: data,
  })
}

const {
  siteSetting,
  logout,
  rebuildTitle,
} = useAdminLayout(router, route)

/**
 * 记录当前切换到的路由, 并修改标题
 */
computed(() => route.path)

watch(
    () => route.path,
    () => {
      rebuildTitle()
    },
    {
      immediate: true,
    }
)
</script>

<style lang="stylus" scoped>
.page-header
  display flex
  align-items: center;
  flex-wrap nowrap
  width: 100%;
  padding: 0 18px;
  border-bottom: 1px solid #f1f1f3;

  .flex-grow
    flex-grow: 1;

.admin-menu
  height: 100%
  padding: 0 10px;
  padding-top: 10px;
  width: 255px;
  border-right: 1px solid #f1f1f3;

  .logo
    padding: 23px;
    width: 100%
    display flex
    justify-content: center;
    align-items: center;

.el-menu
  .el-menu-item
    border-radius 0
    font-size 14px
</style>

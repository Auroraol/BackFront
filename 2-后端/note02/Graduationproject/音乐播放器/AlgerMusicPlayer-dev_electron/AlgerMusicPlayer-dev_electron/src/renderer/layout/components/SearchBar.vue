<template>
  <div class="search-box flex">
    <div class="search-box-input flex-1">
      <n-input
        v-model:value="searchValue"
        size="medium"
        round
        :placeholder="hotSearchKeyword"
        class="border dark:border-gray-600 border-gray-200"
        @keydown.enter="search"
      >
        <template #prefix>
          <i class="iconfont icon-search"></i>
        </template>
        <template #suffix>
          <n-dropdown trigger="hover" :options="searchTypeOptions" @select="selectSearchType">
            <div class="w-20 px-3 flex justify-between items-center">
              <div>
                {{ searchTypeOptions.find((item) => item.key === store.state.searchType)?.label }}
              </div>
              <i class="iconfont icon-xiasanjiaoxing"></i>
            </div>
          </n-dropdown>
        </template>
      </n-input>
    </div>
    <n-popover trigger="hover" placement="bottom" :show-arrow="false" raw>
      <template #trigger>
        <div class="user-box">
          <n-avatar
            v-if="store.state.user"
            class="cursor-pointer"
            circle
            size="medium"
            :src="getImgUrl(store.state.user.avatarUrl)"
            @click="selectItem('user')"
          />
          <div v-else class="mx-2 rounded-full cursor-pointer text-sm" @click="toLogin">登录</div>
        </div>
      </template>
      <div class="user-popover">
        <div v-if="store.state.user" class="user-header" @click="selectItem('user')">
          <n-avatar circle size="small" :src="getImgUrl(store.state.user?.avatarUrl)" />
          <span class="username">{{ store.state.user?.nickname || 'Theodore' }}</span>
        </div>
        <div class="menu-items">
          <div v-if="!store.state.user" class="menu-item" @click="toLogin">
            <i class="iconfont ri-login-box-line"></i>
            <span>去登录</span>
          </div>
          <div v-if="store.state.user" class="menu-item" @click="selectItem('logout')">
            <i class="iconfont ri-logout-box-r-line"></i>
            <span>退出登录</span>
          </div>
          <!-- 切换主题 -->
          <div class="menu-item" @click="selectItem('set')">
            <i class="iconfont ri-settings-3-line"></i>
            <span>设置</span>
          </div>
          <div class="menu-item">
            <i class="iconfont" :class="isDarkTheme ? 'ri-moon-line' : 'ri-sun-line'"></i>
            <span>主题</span>
            <n-switch v-model:value="isDarkTheme" class="ml-auto">
              <template #checked>
                <i class="ri-moon-line"></i>
              </template>
              <template #unchecked>
                <i class="ri-sun-line"></i>
              </template>
            </n-switch>
          </div>
          <div class="menu-item" @click="restartApp">
            <i class="iconfont ri-restart-line"></i>
            <span>重启</span>
          </div>
          <div class="menu-item" @click="selectItem('refresh')">
            <i class="iconfont ri-refresh-line"></i>
            <span>刷新</span>
          </div>
          <div class="menu-item" @click="toGithubRelease">
            <i class="iconfont ri-github-fill"></i>
            <span>当前版本</span>
            <div class="version-info">
              <span class="version-number">{{ updateInfo.currentVersion }}</span>
              <n-tag v-if="updateInfo.hasUpdate" type="success" size="small" class="ml-1">
                New {{ updateInfo.latestVersion }}
              </n-tag>
            </div>
          </div>
        </div>
      </div>
    </n-popover>

    <coffee :alipay-q-r="alipay" :wechat-q-r="wechat">
      <div class="github" @click="toGithub">
        <i class="ri-github-fill"></i>
      </div>
    </coffee>
  </div>
</template>

<script lang="ts" setup>
import { computed, onMounted, ref, watchEffect } from 'vue';
import { useRouter } from 'vue-router';
import { useStore } from 'vuex';

import { getSearchKeyword } from '@/api/home';
import { getUserDetail, logout } from '@/api/login';
import alipay from '@/assets/alipay.png';
import wechat from '@/assets/wechat.png';
import Coffee from '@/components/Coffee.vue';
import { SEARCH_TYPES, USER_SET_OPTIONS } from '@/const/bar-const';
import { getImgUrl } from '@/utils';
import { checkUpdate, UpdateResult } from '@/utils/update';

import config from '../../../../package.json';

const router = useRouter();
const store = useStore();
const userSetOptions = ref(USER_SET_OPTIONS);

// 推荐热搜词
const hotSearchKeyword = ref('搜索点什么吧...');
const hotSearchValue = ref('');
const loadHotSearchKeyword = async () => {
  const { data } = await getSearchKeyword();
  hotSearchKeyword.value = data.data.showKeyword;
  hotSearchValue.value = data.data.realkeyword;
};

const loadPage = async () => {
  const token = localStorage.getItem('token');
  if (!token) return;
  const { data } = await getUserDetail();
  store.state.user = data.profile;
  localStorage.setItem('user', JSON.stringify(data.profile));
};

loadPage();

watchEffect(() => {
  if (store.state.user) {
    userSetOptions.value = USER_SET_OPTIONS;
  } else {
    userSetOptions.value = USER_SET_OPTIONS.filter((item) => item.key !== 'logout');
  }
});

const restartApp = () => {
  window.electron.ipcRenderer.send('restart');
};

const toLogin = () => {
  router.push('/login');
};

// 页面初始化
onMounted(() => {
  loadHotSearchKeyword();
  loadPage();
  checkForUpdates();
});

const isDarkTheme = computed({
  get: () => store.state.theme === 'dark',
  set: () => store.commit('toggleTheme')
});

// 搜索词
const searchValue = ref('');
const search = () => {
  const { value } = searchValue;
  if (value === '') {
    searchValue.value = hotSearchValue.value;
    return;
  }

  if (router.currentRoute.value.path === '/search') {
    store.state.searchValue = value;
    return;
  }

  router.push({
    path: '/search',
    query: {
      keyword: value,
      type: store.state.searchType
    }
  });
};

const selectSearchType = (key: number) => {
  store.state.searchType = key;
  if (searchValue.value) {
    search();
  }
};

const searchTypeOptions = ref(SEARCH_TYPES);

const selectItem = async (key: string) => {
  // switch 判断
  switch (key) {
    case 'logout':
      logout().then(() => {
        store.commit('logout');
        router.push('/login');
      });
      break;
    case 'login':
      router.push('/login');
      break;
    case 'set':
      router.push('/set');
      break;
    case 'user':
      router.push('/user');
      break;
    case 'refresh':
      window.location.reload();
      break;
    default:
  }
};

const toGithub = () => {
  window.open('https://github.com/algerkong/AlgerMusicPlayer', '_blank');
};

const updateInfo = ref<UpdateResult>({
  hasUpdate: false,
  latestVersion: '',
  currentVersion: config.version,
  releaseInfo: null
});

const checkForUpdates = async () => {
  try {
    const result = await checkUpdate(config.version);
    if (result) {
      updateInfo.value = result;
    }
  } catch (error) {
    console.error('检查更新失败:', error);
  }
};

const toGithubRelease = () => {
  if (updateInfo.value.hasUpdate) {
    store.commit('setShowUpdateModal', true);
  } else {
    window.open('https://github.com/algerkong/AlgerMusicPlayer/releases', '_blank');
  }
};
</script>

<style lang="scss" scoped>
.user-box {
  @apply ml-4 flex text-lg justify-center items-center rounded-full transition-colors duration-200;
  @apply border dark:border-gray-600 border-gray-200 hover:border-gray-400 dark:hover:border-gray-400;
  @apply bg-light dark:bg-gray-800;
}

.search-box {
  @apply pb-4 pr-4;
}

.search-box-input {
  @apply relative;

  :deep(.n-input) {
    @apply bg-gray-50 dark:bg-black;

    .n-input__input-el {
      @apply text-gray-900 dark:text-white;
    }

    .n-input__prefix {
      @apply text-gray-500 dark:text-gray-400;
    }
  }
}

.mobile {
  .search-box {
    @apply pl-4;
  }
}

.github {
  @apply cursor-pointer text-gray-900 dark:text-gray-100 hover:text-gray-600 dark:hover:text-gray-400 text-xl ml-4 rounded-full flex justify-center items-center px-2 h-full;
  @apply border dark:border-gray-600 border-gray-200 bg-light dark:bg-black;
}

.user-popover {
  @apply min-w-[220px] p-0 rounded-xl overflow-hidden;
  @apply bg-light dark:bg-black;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);

  .user-header {
    @apply flex items-center gap-2 p-3 cursor-pointer;
    @apply border-b dark:border-gray-700 border-gray-100 hover:bg-gray-100 dark:hover:bg-gray-700;

    .username {
      @apply text-sm font-medium text-gray-900 dark:text-gray-200;
    }
  }

  .menu-items {
    @apply py-1;

    .menu-item {
      @apply flex items-center px-3 py-1 text-sm cursor-pointer;
      @apply text-gray-700 dark:text-gray-300;
      transition: background-color 0.2s;

      &:hover {
        @apply bg-gray-100 dark:bg-gray-700;
      }

      i {
        @apply mr-1 text-lg text-gray-500 dark:text-gray-400;
      }

      .version-info {
        @apply ml-auto flex items-center;

        .version-number {
          @apply text-xs px-2 py-0.5 rounded;
          @apply bg-gray-100 dark:bg-gray-700 text-gray-600 dark:text-gray-300;
        }
      }
    }
  }
}
</style>

<template>
  <div class="search-item" :class="[item.type, shape]" @click="handleClick">
    <div class="search-item-img">
      <n-image
        class="w-full h-full"
        :src="getImgUrl(item.picUrl, item.type === 'mv' ? '320y180' : '200y200')"
        lazy
        preview-disabled
      />
      <div v-if="item.type === 'mv'" class="play">
        <i class="iconfont icon icon-play"></i>
      </div>
    </div>
    <div class="search-item-info">
      <p class="search-item-name">{{ item.name }}</p>
      <p class="search-item-artist">{{ item.desc }}</p>
    </div>

    <div v-if="item.type === '专辑'" class="search-item-size">
      <i class="ri-music-2-line"></i>
      <span>{{ item.size }}</span>
    </div>

    <music-list
      v-if="['专辑', 'playlist'].includes(item.type)"
      v-model:show="showPop"
      :name="item.name"
      :song-list="songList"
      :list-info="listInfo"
      :cover="false"
      :z-index="zIndex"
    />
    <mv-player
      v-if="item.type === 'mv'"
      v-model:show="showPop"
      :current-mv="getCurrentMv()"
      no-list
    />
  </div>
</template>

<script setup lang="ts">
import { useStore } from 'vuex';

import { getAlbum, getListDetail } from '@/api/list';
import MvPlayer from '@/components/MvPlayer.vue';
import { audioService } from '@/services/audioService';
import { IMvItem } from '@/type/mv';
import { getImgUrl } from '@/utils';

import MusicList from '../MusicList.vue';

const props = withDefaults(
  defineProps<{
    shape?: 'square' | 'rectangle';
    zIndex?: number;
    item: {
      picUrl: string;
      name: string;
      desc: string;
      type: string;
      [key: string]: any;
    };
  }>(),
  {
    shape: 'rectangle'
  }
);

const songList = ref<any[]>([]);

const showPop = ref(false);
const listInfo = ref<any>(null);

const getCurrentMv = () => {
  return {
    id: props.item.id,
    name: props.item.name
  } as unknown as IMvItem;
};

const store = useStore();

const handleClick = async () => {
  listInfo.value = null;
  if (props.item.type === '专辑') {
    showPop.value = true;
    const res = await getAlbum(props.item.id);
    songList.value = res.data.songs.map((song: any) => {
      song.al.picUrl = song.al.picUrl || props.item.picUrl;
      return song;
    });
    listInfo.value = {
      ...res.data.album,
      creator: {
        avatarUrl: res.data.album.artist.img1v1Url,
        nickname: `${res.data.album.artist.name} - ${res.data.album.company}`
      },
      description: res.data.album.description
    };
  }

  if (props.item.type === 'playlist') {
    showPop.value = true;
    const res = await getListDetail(props.item.id);
    songList.value = res.data.playlist.tracks;
    listInfo.value = res.data.playlist;
  }

  if (props.item.type === 'mv') {
    store.commit('setIsPlay', false);
    store.commit('setPlayMusic', false);
    audioService.getCurrentSound()?.pause();
    showPop.value = true;
  }
};
</script>

<style scoped lang="scss">
.search-item {
  @apply rounded-lg p-0 flex items-center hover:bg-transparent transition cursor-pointer border-none;

  &.square {
    @apply flex-col relative;

    .search-item-img {
      @apply w-full aspect-square mb-2 mr-0 rounded-lg overflow-hidden hover:shadow-xl transition-all duration-300 shadow-sm shadow-black/20 dark:shadow-white/20;
      img {
        @apply object-cover w-full h-full transition-transform duration-500;
      }
    }

    .search-item-info {
      @apply w-full text-left px-0;

      .search-item-name {
        @apply truncate mb-1 font-medium text-base text-gray-800 dark:text-gray-200;
      }

      .search-item-artist {
        @apply truncate text-sm text-gray-500 dark:text-gray-400;
      }
    }

    .search-item-size {
      @apply absolute top-2 right-2 text-xs text-white px-2 py-1 rounded-full bg-black/30 backdrop-blur-sm;
      i {
        @apply text-xs;
      }
    }
  }

  &.rectangle {
    @apply hover:bg-light-200 dark:hover:bg-dark-200 p-3;
    .search-item-img {
      @apply w-12 h-12 mr-4 rounded-lg overflow-hidden;
    }
  }

  .search-item-info {
    @apply flex-1 overflow-hidden;
    &-name {
      @apply text-white text-sm text-center;
    }
    &-artist {
      @apply text-gray-400 text-xs text-center;
    }
  }
}

.mv {
  &:hover {
    .play {
      @apply opacity-60;
    }
  }
  .search-item-img {
    width: 160px;
    height: 90px;
    @apply rounded-lg relative;
  }
  .play {
    @apply absolute top-1/2 left-1/2 transform -translate-x-1/2 -translate-y-1/2 opacity-0 transition-opacity;
    .icon {
      @apply text-white text-5xl;
    }
  }
}

.search-item-size {
  @apply flex items-center gap-2 text-gray-400;
}
</style>

<!-- components/Menu.vue -->

<template>
  <nav>
    <ul>
      <li v-show="isShow">{{ $isShow.name }} </li>
      <li><router-link to="/">Home</router-link></li>
      <li><router-link to="/movies">Movies</router-link></li>
    </ul>
  </nav>
</template>

<script setup>
import { useMovieListStore } from '/@/store/movie-list-store'

const isShow = useMovieListStore.getIsShow()
</script>

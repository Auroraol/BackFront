<template>
  <ul>
    <li v-for="movie in movies" :key="movie.id">
      <router-link :to="`/movies/${movie.id}`">{{ movie.title }}</router-link>
    </li>
  </ul>
</template>

<script setup>
import { onMounted, computed } from 'vue'
import { useMovieListStore } from '/@/store/movie-list-store'

onMounted(() => {
  useMovieListStore.fetchMovies()
})

const movies = computed(() => useMovieListStore.getMovies())
</script>
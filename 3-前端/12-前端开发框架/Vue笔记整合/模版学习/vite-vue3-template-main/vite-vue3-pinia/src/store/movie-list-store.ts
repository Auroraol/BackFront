// store/useMovieListStore.js

import { defineStore } from 'pinia'

export const useMovieListStore = defineStore('movie',{ 
  state: () => ({
    isShow: true,
    movies: [] as any[], // Adjust the type according to your actual movie data structure
  }),
  getters: {
    getIsShow(): boolean {
      return this.isShow
    },
    getMovies(): any[] { // Adjust the type according to your actual movie data structure
      return this.movies
    },
  },
  actions: {
    setIsShow(value: boolean): void {
      this.isShow = value
    },
    async fetchMovies(): Promise<void> {
      try {
        const response = await fetch('https://api.movies.com/movies')
        const data = await response.json()
        this.movies = data
      } catch (error) {
        console.error('Error fetching movies:', error)
        // Handle the error as needed
      }
    },
  },
})

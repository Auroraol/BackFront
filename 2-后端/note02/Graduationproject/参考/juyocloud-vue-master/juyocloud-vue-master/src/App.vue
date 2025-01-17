<script setup>
import useRouterData from '@/composables/useRouterData'

let router = useRouter()
let route = useRoute()
useRouterData(router, route)

</script>

<template>
  <router-view />
</template>

<style>
</style>

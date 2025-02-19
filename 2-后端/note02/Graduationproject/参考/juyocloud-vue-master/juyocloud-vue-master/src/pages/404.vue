<template>
	<div class="flex flex-wrap h-screen text-center justify-around items-center">
		<div class="font-blod desc">
			<div class="text-7xl mb-4">404</div>
			<div ref="content" class="text-3xl content">来到了</div>
			<button
				class="rounded-lg transition btn"
				@click="back"
			>
        🏠 返回首页
			</button>
		</div>

	</div>
</template>

<script setup>
import { init } from 'ityped'

const content = ref(null)

onMounted(() => {
  init(content.value, {
    showCursor: false,
    disableBackTyping: true,
    strings: ['没有数据的荒野!'],
  })
})

const router = useRouter()

const back = () => router.push('/')
</script>

<style>
.content {
	height: 40px;
}
.desc {
	flex: 1;
	width: 300px;
}

.btn {
	font-size: 20px;
	margin-top: 30px;
	padding: 12px 20px;
	--tw-shadow-color: 8, 145, 178;
	--tw-shadow: 0 4px 6px -1px rgba(var(--tw-shadow-color), 0.1),
		0 2px 4px -1px rgba(var(--tw-shadow-color), 0.06);
	box-shadow: 0 0 #0000, 0 0 #0000, 0 0 #0000, var(--tw-shadow);
}
</style>

<route lang="yaml">
meta:
  layout: notFound
</route>

<template>
	<div class="juyo-async-loading">
		<el-skeleton class="w-full h-[40vh] sm:h-[80vh]" animated>
			<template #template>
				<div class="flex justify-center items-center h-full loading">
					<svg-icon name="file-type-video" class="w-12 h-12"></svg-icon>
				</div>
			</template>
		</el-skeleton>
	</div>
</template>

<script setup>
onMounted(() => {
	document.querySelector('.el-dialog .el-dialog__title').innerHTML = '加载中...'
})
</script>

<style lang="scss" scoped>
.juyo-async-loading {
	:deep(.el-skeleton__item) {
		height: 100%;
	}

	.loading {
		background: linear-gradient(
				100deg,
				rgba(255, 255, 255, 0) 40%,
				rgba(255, 255, 255, 0.5) 50%,
				rgba(255, 255, 255, 0) 60%
			)
			#ededed;
		background-size: 200% 100%;
		background-position-x: 180%;
		animation: 1s loading ease-in-out infinite;
	}

	@keyframes loading {
		to {
			background-position-x: -20%;
		}
	}
}
</style>

<template>
	<div id="app">
		<router-link to="/login"></router-link>
		<div>
			<router-view />
		</div>
	</div>
</template>

<script>

export default {
	name: "app",
	components: {
	},
}
</script>

<style>
#app {
	font-family: Avenir, Helvetica, Arial, sans-serif;
	-webkit-font-smoothing: antialiased;
	-moz-osx-font-smoothing: grayscale;
	text-align: center;
	color: #2c3e50;
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
}
</style>

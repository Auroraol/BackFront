<template>
    <div class="project">
        <el-empty description="正在开发中，敬请期待"></el-empty>
    </div>
</template>
<script>
export default {
    name: 'Project',
    data(){return{}},
    methods:{},
    mounted(){}
}
</script>
<style lang="scss" scoped>
.project{
    min-height: calc(100vh - 8rem);
}
</style>
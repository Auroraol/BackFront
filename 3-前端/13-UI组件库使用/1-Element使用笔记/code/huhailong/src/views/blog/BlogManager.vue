<template>
    <div class="blogManager">
        <div class="toolBar">
            <el-input @clear="getBlogList" clearable v-model="keyword" @keydown.native.enter="getBlogList" style="width:300px;" type="text" placeholder="输入文章关键字" size="medium"></el-input>
            <el-button style="margin-left:10px;" size="medium" icon="el-icon-delete" @click="removeBlog" type="danger">删除</el-button>
        </div>
        <div class="content">
            <el-table @selection-change="handleSelectionChange" v-loading="loading" :data="blogList" border highlight-current-row height="550">
                <el-table-column align="center" type="selection" width="55"></el-table-column>
                <el-table-column label="文章标题" prop="title"></el-table-column>
                <el-table-column label="发布时间" prop="createTime"></el-table-column>
                <el-table-column label="发布作者" prop="source"></el-table-column>
                <el-table-column label="浏览次数" prop="viewCount"></el-table-column>
                <el-table-column align="center" label="操作">
                    <template slot-scope="scope">
                        <el-link @click="edit(scope.row.id)"><i class="el-icon-edit"></i>编辑</el-link>
                        <el-link type="danger" style="margin-left:10px;" @click="selectOneItem(scope.row.id)"><i class="el-icon-delete"></i>删除</el-link>
                    </template>
                </el-table-column>
            </el-table>
            <div style="margin-top:10px;">
                <el-pagination @current-change="goPage" background layout="prev, pager, next" :total="total"></el-pagination>
            </div>
        </div>
    </div>
</template>
<script>
import * as blogApi from '@/api/blog/api.js'
export default {
    name: 'BlogManager',
    data(){
        return{
            pageNum: 1,
            pageSize: 10,
            total: 0,
            keyword: '',
            blogList: [],
            loading: false,
            seletedList:[]
        }
    },
    methods:{
        goPage(num){
            this.pageNum = num;
            this.getBlogList();
        },
       getBlogList() {
            this.loading = true;
            blogApi.getBlog({pageNum: this.pageNum, pageSize: this.pageSize, keyword: this.keyword }).then((res) => {
                this.blogList = res.data.list;
                this.loading = false;
                this.total = res.data.total
            }).catch((err)=>{
                console.log(err)
                this.$message({
                type: 'error',
                message: err.message
                })
                this.loading = false
            });
        },
        handleSelectionChange(val){
            this.seletedList = [];
            val.forEach(element => {
                this.seletedList.push(element.id)
            });
        },
        selectOneItem(id){
            this.seletedList = [];
            this.seletedList.push(id);
            this.removeBlog();
        },
        removeBlog(){
            this.$confirm('此操作将永久删除文章, 是否继续?', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
            }).then(() => {
                blogApi.delBlog(this.seletedList).then(res=>{
                    if(!res.status){
                        this.$message({
                            type: 'success',
                            message: res.message,
                            offset: 70
                        });
                    }
                    this.getBlogList();
                })
            }).catch(() => {
                this.$message({
                    type: 'info',
                    message: '已取消删除',
                    offset: 70
                });          
            });
        },
        edit(id){
            this.$router.push({name:'EditBlog', params:{blogId: id}})
        }
        
    },
    mounted(){
        this.getBlogList();
    }
}
</script>
<style lang="scss" scoped>
.toolBar{
    width: calc(100% - 20px);
    padding: 10px;
    text-align: left;
}
.content{
    padding: 5px;
}
</style>
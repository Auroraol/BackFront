<template>
	<div id="users-recommend">
		<el-table
			:data="usersData"
			stripe
			style="width: 100%"
			:header-cell-style="{ fontWeight: 'normal', border: 'none' }"
		>
			<el-table-column align="center" prop="index" label="" width="80">
				<template #default="scope">
					<span
						style="font-size:16px;width:20px;display:inline-block;"
					>
						{{ scope.$index + 1 }}
					</span>
				</template>
			</el-table-column>
			<el-table-column
				align="center"
				prop="name"
				label="姓名"
				width="150"
			>
				<template #default="scope">
					<span
						style="cursor:pointer;"
						@click="recommendUserDetail(scope.$index)"
					>
						{{ scope.row.name }}
					</span>
				</template>
			</el-table-column>
			<el-table-column
				align="center"
				prop="gender"
				label="性别"
				width="100"
			>
				<template #default="scope">
					{{ scope.row.gender }}
				</template>
			</el-table-column>
			<el-table-column
				align="center"
				prop="age"
				label="年龄"
				width="100"
			></el-table-column>
			<el-table-column
				align="center"
				prop="area"
				label="所在区域"
				width="150"
			>
				<template #default="scope">
					{{ scope.row.area }}
				</template>
			</el-table-column>
			<el-table-column
				align="center"
				prop="des"
				label="自我简介"
				width="200"
			>
				<template #default="scope">
					{{ scope.row.des }}
				</template>
			</el-table-column>
			<el-table-column
				align="center"
				prop="registerTime"
				label="注册时间"
				width="160"
			>
				<template #default="scope">
					{{ scope.row.registerTime }}
				</template>
			</el-table-column>
		</el-table>
	</div>
</template>

<script>
import { getRecommendUsers } from "../network/request.js"
import { useStore } from "vuex"
export default {
	name: "recommendusers",
	data() {
		const item = {
			uid: "",
			name: "",
			gender: "",
			age: "",
			area: "",
			des: "",
			registerTime: "",
		}
		return {
			uid: useStore().state.user.uid,
			downUrl: "",
			usersData: [],
			tableData: Array(10).fill(item),
		}
	},
	beforeCreate() {
		const user = useStore().state.user
		console.log(user)
		var useruid = user.uid
		console.log(useruid)
		getRecommendUsers({ uid: useruid + "" }).then((response) => {
			this.usersData = response.data.data
			console.log(this.usersData)
		})
	},
	created() {
		const user = useStore().state.user
		console.log(user)
		var useruid = user.uid
		console.log(useruid)
		// 判断是否为游客
		if(parseInt(useruid) < 100){
			this.$router.push({
				path: "/login",
			})
			this.$message({
				type: "info",
				message: "请先登录或注册一个账号",
			})
		}
		// 页面加载时读取数据
		if (window.sessionStorage.getItem("user")) {
			console.log("页面加载")
			const session_user = JSON.parse(
				window.sessionStorage.getItem("user"),
			)
			console.log(session_user)
			this.$store.commit("SET_USER", session_user)
		}
		// 页面刷新时保存状态
		window.addEventListener("beforeunload", () => {
			console.log("页面刷新")
			console.log(this.$store.state.user)
			window.sessionStorage.setItem(
				"user",
				JSON.stringify(this.$store.state.user),
			)
		})
	},
	methods: {
		// 下一页
		handleCurrentChange(val) {
			console.log(`当前页: ${val}`)
			this.currentPage = val
			// 换页之后改变tableData，重新渲染
			this.usersData = this.usersData.slice(
				(this.currentPage - 1) * this.pageSize,
				this.currentPage * this.pageSize,
			)
			console.log(this.usersData)
		},
		// 跳转到相似用户详情信息页面
		recommendUserDetail(index) {
			console.log("选择用户的索引：" + index)
			const user = this.usersData[parseInt(index)]
			console.log(user)
			this.$router.push({
				name: "userRecord",
				params: {
					uid: user.uid,
					name: user.name,
					gender: user.gender,
					area: user.area,
					des: user.des,
				},
			})
		},
	},
}
</script>

<style scoped>
/* 字体图标鼠标覆盖变色 */
#users-recommend i.iconfont.icon-bofang:hover {
	cursor: pointer;
	color: #f56c6c;
}

#users-recommend {
	padding-top: 0;
	position: absolute;
	width: 960px !important;
	right: 0;
	left: 0;
	margin-left: auto;
	margin-right: auto;
}
</style>

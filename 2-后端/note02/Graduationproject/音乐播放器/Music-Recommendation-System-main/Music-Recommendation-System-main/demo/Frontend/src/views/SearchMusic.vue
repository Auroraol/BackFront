<template>
	<div id="songs-search">
		<el-table
			:data="songsData"
			stripe
			style="width: 100%;"
			:header-cell-style="{ fontWeight: 'normal', border: 'none' }"
		>
			<el-table-column align="center" prop="index" label="" width="120">
				<template #default="scope">
					<span
						style="font-size:16px;width:20px;display:inline-block;"
					>
						{{ scope.$index + 1 }}
					</span>
						<i
							class="iconfont icon-bofang"
							style="margin-left:10px;font-size:18px;width:15px;display:inline-block;"
							@click="startPlay(scope.$index)"
							:class="
								scope.row.downUrl == 'None' ? 'fail' : 'succ'
							"
						></i>
						<i
							class="iconfont icon-shoucang_huaban1"
							style="margin-left:10px;font-size:18px;width:15px;display:inline-block;"
							@click="addLove(scope.$index)"
							:class="[
								scope.row.downUrl == 'None' ? 'fail' : 'succ',
								scope.row.isLove === true ? 'active' : '',
							]"
						></i>
						<i
							class="el-icon-s-comment"
							style="margin-left:10px;font-size:18px;width:15px;display:inline-block;"
							@click="getComment(scope.$index)"
							:class="
								scope.row.downUrl == 'None' ? 'fail' : 'succ'
							"
						></i>
				</template>
			</el-table-column>
			<el-table-column
				align="center"
				prop="songName"
				label="歌曲名"
				width="220"
			>
				<template #default="scope">
					{{ scope.row.songName }}
				</template>
			</el-table-column>
			<el-table-column
				align="center"
				prop="singerName"
				label="歌手"
				width="220"
			>
				<template #default="scope">
					{{ scope.row.singerName }}
				</template>
			</el-table-column>
			<el-table-column
				align="center"
				prop="album"
				label="专辑"
				width="300"
			></el-table-column>
			<el-table-column
				align="center"
				prop="songTime"
				label="时长"
				width="200"
			>
				<template #default="scope">
					{{ scope.row.songTime }}
				</template>
			</el-table-column>
		</el-table>
	</div>
</template>
<script>
import { _getSearchSongs } from "../network/request.js"
export default {
	name: "searchMusic",
	data() {
		const item = {
			iid: "",
			songName: "",
			singerName: "",
			album: "",
			songTime: "",
			downUrl: "",
			picUrl: "",
		}
		return {
			songsData: Array(10).fill(item),
			// 搜索关键词
			keyword: "",
		}
	},
	created() {
		this.keyword = this.$route.query.keyword
		console.log(this.currentPage)
		// 得到搜索歌曲歌曲的详情信息
		_getSearchSongs({ keyword: this.keyword }).then((response) => {
			this.songsData = response.data.data
			console.log(this.songsData)
		})
	},
	methods: {
		startPlay(index) {
			console.log(index)
			var song = this.songsData[parseInt(index)]
			console.log(song)
			this.downUrl = song.downUrl
			console.log(this.downUrl)
			console.log(song.picUrl)
			var iid = song.iid
			var picUrl = song.picUrl
			var songNae = song.songName
			var singerName = song.singerName
			var mp3Url = this.downUrl
			// 将事件传递给子组件，第一个参数为自定义事件名，后续参数为传递过去的数据
			console.log("开始emit")
			this.$emit("playmusic", iid, mp3Url, picUrl, songNae, singerName)
			console.log("结束emit")
		},
		// 添加收藏歌曲
		addLove(index) {
			var isLove = this.songsData[parseInt(index)].isLove
			var songName = this.songsData[parseInt(index)].songName
			if (isLove === true) {
				this.songsData[parseInt(index)].isLove = false
				console.log("取消收藏")
				this.$message({
					type: "info",
					message: songName + " " + "取消收藏!",
				})
			} else {
				this.songsData[parseInt(index)].isLove = true
				console.log("收藏歌曲")
				this.$message({
					type: "success",
					message: songName + " " + "收藏成功!",
				})
			}
		},
		getComment(index){
			console.log(index)
			var song = this.songsData[parseInt(index)]
			console.log(song)
			this.$router.push({
				name: "comment",
				params: {
					iid: song.iid,
					songName: song.songName,
					singerName: song.singerName,
					album: song.album,
					picUrl: song.picUrl,
				},
			})
		},
	},
}
</script>
<style scoped>
#songs-search {
	position: absolute;
	width: 1060px !important;
	right: 0;
	left: 0;
	margin-left: auto;
	margin-right: auto;
}
/* 字体图标鼠标覆盖变色 */
#songs-search i.iconfont.icon-bofang:hover {
	cursor: pointer;
	color: #f56c6c;
}

/* 收藏歌曲激活状态 */
#songs-search .active {
	color: #f56c6c;
}

/* 字体图标鼠标覆盖变色 */
#songs-search i.iconfont.icon-shoucang_huaban1:hover {
	cursor: pointer;
}
</style>

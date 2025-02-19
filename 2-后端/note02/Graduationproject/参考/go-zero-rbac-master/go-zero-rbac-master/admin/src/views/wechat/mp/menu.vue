<!--
  - # 此文件版权属于ASO.DESIGN
  - # 文件：menu.vue  项目：aso-zero
  - # 作用：
  - # 当前修改时间：2021年07月18日 01:05:05
  - # 上次修改时间：2021年07月18日 00:29:53
  - # 作者：thunur
  - # 此文件不可非法传播、倒卖、共享，否则我们将追究相应的法律责任。
  - # 您如果已获得ASO.DESIGN授权可在原有基础上进行修改使用
  - # 如果您还没获得授权请联系我们 thunur@qq.com
  - # Copyright (c) 2021 aso.design
  -->

<template>
	<!--	<cl-scrollbar class="container">-->
	<div class="container">
		<div class="title">微信自定义菜单</div>
		<div class="container-menu__area">
			<div class="container-menu__area__title">公众号名称</div>
			<div class="container-menu__area__nav">
				<ul class="container-menu__area__nav__list">
					<li
						v-for="(item, index) in menu"
						class="container-menu__area__nav__list__li"
						:class="['container-menu__area__nav__list__li__' + menu.length]"
						:key="index"
					>
						<a
							href="javascript:void(0);"
							@click="activeSelected(index)"
							class="container-menu__area__nav__list__li__a"
							:class="{ active: index === selected }"
						>
							{{ item.name }}
						</a>
						<div
							class="container-menu__area__nav__list__li__submenu"
							:class="{ show: index === selected }"
						>
							<ul class="container-menu__area__nav__list__li__submenu__ul">
								<li
									v-for="(value, key) in item.sub_button"
									class="container-menu__area__nav__list__li__submenu__ul__li"
									:class="{ current: key === selectedTwo }"
									:key="key"
								>
									<a href="javascript:void(0);" @click="currentSelected(key)">
										<span>{{ value.name }}</span>
									</a>
								</li>
								<!-- 子菜单 -->
								<li
									class="container-menu__area__nav__list__li__submenu__ul__li container-menu__area__nav__list__li__submenu__add"
									:style="item.sub_button.length === 5 ? 'display:none' : ''"
								>
									<a
										href="javascript:void(0);"
										@click="twoLevelMenuAdd"
										title="最多添加5个子菜单"
									>
										<span><i class="el-icon-plus"></i></span>
									</a>
								</li>
							</ul>
							<i
								class="container-menu__area__nav__list__li__submenu__arrow container-menu__area__nav__list__li__submenu__arrow_out"
							></i>
							<i
								class="container-menu__area__nav__list__li__submenu__arrow container-menu__area__nav__list__li__submenu__arrow_in"
							></i>
						</div>
					</li>
					<!-- 一级菜单添加 -->
					<li
						class="container-menu__area__nav__list__li__add container-menu__area__nav__list__li"
						:class="[
							'container-menu__area__nav__list__li__' +
								(menu.length > 0 ? menu.length : '')
						]"
					>
						<a
							href="javascript:void(0);"
							@click="oneLevelMenuAdd"
							:class="[
								menu.length === 0
									? 'container-menu__area__nav__list__li__pre_add'
									: ''
							]"
							title="最多添加3个一级菜单"
						>
							<i class="el-icon-plus"></i>
							<span v-if="menu.length === 0">菜单名称</span>
						</a>
					</li>
				</ul>
			</div>
		</div>
		<div class="container-menu__edit">
			<i class="triangle"></i>
			<div
				class="container-menu__edit__tips"
				v-if="!menuObject || selected === -1"
			>
				<div class="container-menu__edit__tips__box">点击左侧菜单进行编辑操作</div>
			</div>
			<div class="container-menu__edit__area" v-else>
				<div class="container-menu__edit__area__title">
					<div class="container-menu__edit__area__title__area" v-if="doseSub">
						<el-button slot="reference" type="warning" size="mini" @click="deleteMenu">
							删除
						</el-button>
					</div>
					<div class="container-menu__edit__area__title__area" v-else>
						<el-popover placement="top" width="160" v-model="delTips">
							<p>继续将会清空子菜单数据！</p>
							<div style="text-align: right; margin: 0">
								<el-button size="mini" type="text" @click="delTips = false">
									取消
								</el-button>
								<el-button type="primary" size="mini" @click="deleteMenu">
									继续
								</el-button>
							</div>
							<el-button slot="reference" type="danger" size="mini">
								删除
							</el-button>
						</el-popover>
					</div>
					<span>{{ menuObject.name }}</span>
				</div>
				<el-form ref="ruleForm" :model="menuObject" :rules="rules" label-width="80px">
					<el-form-item
						class="item"
						label="菜单名称"
						prop="name"
						style="margin-top: 10px"
					>
						<el-input v-model="menuObject.name" size="medium" />
					</el-form-item>
					<div v-if="doseSub">
						<el-form-item
							class="item"
							label="菜单内容"
							style="margin-bottom:0"
							prop="type"
						>
							<el-radio-group v-model="menuObject.type">
								<el-radio label="click">发送消息</el-radio>
								<el-radio label="view">跳转网页</el-radio>
								<el-radio label="miniprogram">
									跳转小程序
								</el-radio>
							</el-radio-group>
						</el-form-item>
						<!-- 发送消息 -->
						<el-tabs
							type="border-card"
							v-model="menuObject.msgType"
							v-if="menuObject.type === 'click'"
							v-loading="loadData"
							@tab-click="handleClick"
						>
							<el-tab-pane name="picmsg">
								<span slot="label"> <i class="el-icon-files"></i> 图文消息</span>
								<div
									class="container-menu__edit__picmsg__area"
									v-if="menuObject.media_id > 0"
								>
									<div class="container-menu__edit__picmsg__area__list">
										<div
											class="container-menu__edit__picmsg__area__list__item"
											v-for="(item, index) in menuObject.media"
											:key="index"
											:class="{ 'has-thumb': index === 0 }"
										>
											<div class="bd">
												<div class="title">
													{{ item.title }}
												</div>
											</div>
											<div class="hd">
												<div class="thumb">
													<el-image
														fit="cover"
														:src="item.cover"
													></el-image>
												</div>
											</div>
										</div>
									</div>
									<a
										href="javascript:"
										@click="deletePicmsg"
										class="container-menu__edit__picmsg__area__delete"
									>
										删除
									</a>
								</div>
								<el-row v-else>
									<el-col :span="4">
										<cl-material-space @confirm="onPicMsgConfirm" />
									</el-col>
									<el-col :span="4">
										<cl-graphic-editing />
									</el-col>
								</el-row>
							</el-tab-pane>
							<el-tab-pane name="text">
								<span slot="label"
									><i class="el-icon-chat-dot-square"></i> 文字</span
								>
								<el-input
									type="textarea"
									:rows="4"
									placeholder="请输入文字消息"
									v-model="menuObject.text"
								>
								</el-input>
							</el-tab-pane>
							<!--<el-tab-pane name="pic">
								<span slot="label"
									><i class="el-icon-chat-dot-square" /> 图片</span
								>
								<div
									class="container-menu__edit__pic"
									v-if="menuObject.pic"
								>
									<div class="container-menu__edit__pic__area">
										<el-image fit="cover" :src="menuObject.pic" lazy />
									</div>
									<a
										href="javascript:"
										@click="deletePic"
										class="container-menu__edit__pic__delete"
										><svg
											width="17"
											height="13"
											viewBox="0 0 17 13"
											xmlns="http://www.w3.org/2000/svg"
										>
											<path
												d="M4.05 11H16a1 1 0 0 0 0-2H4.015l-.017-.988a.5.5 0 0 0-.811-.383L.565 9.711a.5.5 0 0 0 .014.793l2.693 1.989a.5.5 0 0 0 .797-.411L4.049 11zm9.02-9H2a1 1 0 1 0 0 2h11.035l-.017 1.002a.5.5 0 0 0 .794.413l2.684-1.953a.5.5 0 0 0 .014-.798L13.895.618a.5.5 0 0 0-.808.386L13.07 2z"
											></path>
										</svg>
										删除
									</a>
								</div>
								<el-row :gutter="20" v-else>
									<el-col :span="6">
										<cl-upload-space
											:showMaterial="true"
											:limit="1"
											:rename="true"
											:detail-data="true"
											@confirm="onFileConfirm"
										/>
									</el-col>
								</el-row>
							</el-tab-pane>-->
							<!--<el-tab-pane name="audio">
								<span slot="label"><i class="el-icon-microphone" /> 语音</span>
								<el-row :gutter="20">
									<el-col :span="6">
										<cl-upload-space
											:showMaterial="true"
											:limit="1"
											:rename="true"
											@confirm="onFileConfirm"
										/>
									</el-col>
								</el-row>
							</el-tab-pane>
							<el-tab-pane name="video">
								<span slot="label"
									><i class="el-icon-video-camera" /> 视频</span
								>
								<el-row :gutter="20">
									<el-col :span="6">
										<cl-upload-space
											:showMaterial="true"
											:limit="1"
											:rename="true"
											@confirm="onFileConfirm"
										/>
									</el-col>
								</el-row>
							</el-tab-pane>-->
						</el-tabs>
						<!-- 发送网页 -->
						<div
							class="container-menu__edit__area__url"
							v-if="menuObject.type === 'view'"
						>
							<div class="container-menu__edit__area__url__area">
								<p class="container-menu__edit__area__url__area__tips">
									订阅者点击该子菜单会跳到以下链接
								</p>
								<el-form-item
									prop="url"
									label="页面地址"
									size="medium"
									:rules="[
										{
											required: true,
											message: '请输入页面地址',
											trigger: 'blur'
										},
										{
											type: 'url',
											message: '请输入正确的页面地址',
											trigger: ['blur', 'change']
										}
									]"
								>
									<el-input v-model="menuObject.url"></el-input>
								</el-form-item>
							</div>
						</div>
						<!-- 发送小程序 -->
						<div
							class="container-menu__edit__area__weapp"
							v-if="menuObject.type === 'miniprogram'"
						>
							<div class="container-menu__edit__area__weapp__area">
								<p class="container-menu__edit__area__weapp__area__tips">
									订阅者点击该子菜单会跳到以下小程序
								</p>
								<el-form-item
									prop="app_id"
									label="APPID"
									size="medium"
									:rules="[
										{
											required: true,
											message: '请输入APPID',
											trigger: 'blur'
										}
									]"
								>
									<el-input v-model="menuObject.app_id"></el-input>
								</el-form-item>
								<el-form-item
									prop="page_path"
									label="页面路径"
									size="medium"
									:rules="[
										{
											required: true,
											message: '请输入页面路径',
											trigger: 'blur'
										}
									]"
								>
									<el-input v-model="menuObject.page_path"></el-input>
								</el-form-item>
								<el-form-item
									prop="url"
									label="备用地址"
									size="medium"
									:rules="[
										{
											required: true,
											message: '请输入备用地址',
											trigger: 'blur'
										},
										{
											type: 'url',
											message: '备用地址只支持网址',
											trigger: ['blur', 'change']
										}
									]"
								>
									<el-input v-model="menuObject.url"></el-input>
								</el-form-item>
							</div>
						</div>
					</div>
				</el-form>
			</div>
		</div>
		<div class="container__toolbar">
			<el-button type="primary" size="medium" :loading="loading" @click="submit">
				保存并发布到微信
			</el-button>
		</div>
	</div>
	<!--	</cl-scrollbar>-->
</template>
<script>
export default {
	name: "menuSetting",
	data() {
		return {
			loading: false,
			loadData: false,
			doseSub: true,
			isSub: false,
			// 图文是否已选择
			picMsgSelect: false,
			delTips: false,
			menu: [],
			selected: -1,
			selectedTwo: -1,
			count: 0,
			menuObject: {},
			rules: {
				name: [{ required: true, message: "请输入菜单名称", trigger: "blur" }],
				type: [{ required: true, message: "请选择菜单类型", trigger: "change" }]
			}
		};
	},
	mounted() {
		this.onload();
	},
	methods: {
		onload() {
			this.$service.wechat.menu.list().then(res => {
				this.menu = res;
				this.count = res.length;
			});
		},
		// 点击选项卡事件
		handleClick(tab) {
			this.menuObject.msgType = tab.name;
		},
		deleteMenu() {
			if (this.isSub) {
				this.menu[this.selected].sub_button.splice(this.selectedTwo, 1);
			} else {
				this.menu.splice(this.selected, 1);
				this.menuObject = {};
			}
			this.count--;
			this.delTips = false;
		},
		// 一级菜单点击
		activeSelected(index) {
			this.selected = index;
			if (this.selected === index) this.menuObject = this.menu[index];
			this.selectedTwo = null;
			this.doseSub = true;
			// 如果下级有子菜单那么设置为false，即不能编辑主菜单内容
			if (this.menu[index].sub_button.length > 0) {
				this.doseSub = false;
			}
			this.isSub = false;
			this.menuObject = this.menu[index];
			// 获取当前菜单配置的信息
			if (this.menuObject.media_id > 0 && this.doseSub) {
				this.onPicMsgConfirm(this.menuObject.media_id);
			}
		},
		// 点击二级菜单
		currentSelected(key) {
			this.selectedTwo = key;
			this.doseSub = true;
			this.isSub = true;
			this.menuObject = this.menu[this.selected].sub_button[key];
			if (this.menuObject.media_id > 0) {
				this.onPicMsgConfirm(this.menuObject.media_id);
			}
		},
		// 1级菜单添加
		oneLevelMenuAdd() {
			this.count++;
			let levelOneMenu = {
				// id: this.count,
				name: "菜单名称",
				type: "click",
				msgType: "picmsg",
				text: "",
				url: "",
				app_id: "",
				page_path: "",
				media_id: 0,
				media: [],
				sub_button: []
			};

			// 判断是否从0开始
			if (this.menu.length !== 0) {
				++this.selected;
			}
			this.isSub = false;
			this.menu.push(levelOneMenu);
			// this.activeSelected(this.selected);
		},
		// 二级菜单添加
		twoLevelMenuAdd() {
			this.count++;
			let levelTwoMenu = {
				// id: this.count,
				// pid: this.selected,
				name: "子菜单",
				type: "click",
				msgType: "picmsg",
				url: "",
				app_id: "",
				page_path: "",
				media_id: 0,
				media: [],
				text: ""
			};
			this.isSub = true;
			this.menu[this.selected].sub_button.push(levelTwoMenu);
			this.menuObject = levelTwoMenu;
		},

		// 选择图文回调
		onPicMsgConfirm(id) {
			this.loadData = true;
			this.menuObject.media_id = 0;
			this.menuObject.media = [];
			this.$service.wechat.material
				.list({ id })
				.then(res => {
					this.menuObject.media = res;
					this.picMsgSelect = true;
					this.menuObject.media_id = id;
				})
				.catch(err => {
					this.$message.error(err);
				});
			this.loadData = false;
		},
		// 选择文件、图片、音频、视频回调
		onFileConfirm(val) {
			this.menuObject.pic = val[0].url;
		},
		deletePicmsg() {
			this.menuObject.media_id = 0;
			this.menuObject.media = [];
			this.picMsgSelect = false;
		},
		// 删除图片消息
		deletePic() {
			this.menuObject.pic = "";
		},
		submit() {
			this.loading = true;
			this.$service.wechat.menu
				.add({
					data: this.menu
				})
				.then(() => {
					this.$message.success("保存成功");
					this.onload();
					this.loading = false;
				})
				.catch(err => this.$message.error(err));
			this.loading = false;
		}
	}
};
</script>
<style lang="scss" scoped>
.container {
	// 菜单区域
	&-menu__area {
		position: absolute;
		width: 294px;
		height: 480px;
		border: 1px solid #e7e7eb;
		background-image: url(data:image/png;base64,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);
		background-repeat: no-repeat;
		background-position: 0 0;
		-webkit-background-size: contain;
		background-size: contain;

		&__title {
			color: $color-white;
			text-align: center;
			padding-top: 30px;
			font-size: 15px;
			overflow: hidden;
			text-overflow: ellipsis;
			white-space: nowrap;
			word-wrap: normal;
			margin-left: 30px;
			margin-right: 30px;
		}

		&__nav {
			&__list {
				position: absolute;
				bottom: 0;
				left: 0;
				right: 0;
				background-image: url(data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAUAAAAAyCAIAAACib5WDAAAAGXRFWHRTb2Z0d2FyZQBBZG9iZSBJbWFnZVJlYWR5ccllPAAAAyBpVFh0WE1MOmNvbS5hZG9iZS54bXAAAAAAADw/eHBhY2tldCBiZWdpbj0i77u/IiBpZD0iVzVNME1wQ2VoaUh6cmVTek5UY3prYzlkIj8+IDx4OnhtcG1ldGEgeG1sbnM6eD0iYWRvYmU6bnM6bWV0YS8iIHg6eG1wdGs9IkFkb2JlIFhNUCBDb3JlIDUuMC1jMDYwIDYxLjEzNDc3NywgMjAxMC8wMi8xMi0xNzozMjowMCAgICAgICAgIj4gPHJkZjpSREYgeG1sbnM6cmRmPSJodHRwOi8vd3d3LnczLm9yZy8xOTk5LzAyLzIyLXJkZi1zeW50YXgtbnMjIj4gPHJkZjpEZXNjcmlwdGlvbiByZGY6YWJvdXQ9IiIgeG1sbnM6eG1wPSJodHRwOi8vbnMuYWRvYmUuY29tL3hhcC8xLjAvIiB4bWxuczp4bXBNTT0iaHR0cDovL25zLmFkb2JlLmNvbS94YXAvMS4wL21tLyIgeG1sbnM6c3RSZWY9Imh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC9zVHlwZS9SZXNvdXJjZVJlZiMiIHhtcDpDcmVhdG9yVG9vbD0iQWRvYmUgUGhvdG9zaG9wIENTNSBXaW5kb3dzIiB4bXBNTTpJbnN0YW5jZUlEPSJ4bXAuaWlkOjE1NEJCMUE0NzZGNDExRTVBOTBBQTZFOEFEMjc4NTkzIiB4bXBNTTpEb2N1bWVudElEPSJ4bXAuZGlkOjE1NEJCMUE1NzZGNDExRTVBOTBBQTZFOEFEMjc4NTkzIj4gPHhtcE1NOkRlcml2ZWRGcm9tIHN0UmVmOmluc3RhbmNlSUQ9InhtcC5paWQ6MTU0QkIxQTI3NkY0MTFFNUE5MEFBNkU4QUQyNzg1OTMiIHN0UmVmOmRvY3VtZW50SUQ9InhtcC5kaWQ6MTU0QkIxQTM3NkY0MTFFNUE5MEFBNkU4QUQyNzg1OTMiLz4gPC9yZGY6RGVzY3JpcHRpb24+IDwvcmRmOlJERj4gPC94OnhtcG1ldGE+IDw/eHBhY2tldCBlbmQ9InIiPz4yWLBJAAABuklEQVR42uzcu0ocURzA4XWxMIWiQhJwtVhxMW0wEkWj+AwWgm9gJfgggpVPoElEUwUCKRNFJaQWsygWXvAKXlBZGw8KIiIJmWFnGPg+pjiryMIffpxzRLemUqnkUlUul0ulUg74f3kjAAEDAgYEDAIGBAwIGBAwCBgQMCBgEHAMlZub8BglJK825s/vHxzOfl4Ii9GR4devXhooZGYHPjo+mfk0f3l5FZ6wCC8NFDKzA+fz+aHB/scvDRQyE3BzU2N4DBEyeYQGBAxU5wi9sbm1+ut3W2shznucnp296Sx1tBeNGxINeG39z+jIcPy3+Tj3RcCQ9BG6ob7+fjE5NR2eaOugtdBi1pD0Dvzg6vo68hpIOeAXdXWR10CV1Pz9c6F/LC4P9PfGf5ufSysf+nqe/ZbPhYZq3YGfiHD7BdI/Qrv9QuYDdvsFd2B3YEjjDgxk+Aidu/sd1T9vueEUPTE+ZrhgBwai7sA7u3tPvhJtaz0/vzBrSDrg7ndvv377/vAX0dFs7+y+7+4ya0g64I72ov8iAndgQMCAgEHAgIABAYOAAQEDAgYEDAIGBAwIGBAwCBhIy60AAwBiy5esmSYLKgAAAABJRU5ErkJggg==);
				background-repeat: no-repeat;
				background-position: 0 0;
				padding-left: 43px;
				border-top: 1px solid #e7e7eb;

				&__li {
					line-height: 50px;
					position: relative;
					text-align: center;

					a {
						border-left: 1px solid #e7e7eb;
						display: block;
						width: auto;
						overflow: hidden;
						text-overflow: ellipsis;
						white-space: nowrap;
						word-wrap: normal;
						color: #616161;
						text-decoration: none;
						padding: 0 10px;
						font-size: 13px;

						&:hover {
							background-color: #eeeeee;
							color: #000000;
						}

						&.active {
							border: 1px solid #44b549;
							color: #44b549;
							line-height: 48px;
						}
					}

					&__1 {
						width: 50%;
						float: left;
					}

					&__2,
					&__3 {
						width: 33.3333333%;
						float: left;
					}

					&__pre_add {
						border: 1px solid #44b549 !important;
						color: #44b549;
						line-height: 48px;
					}

					&__add {
						width: auto;
						float: none;
						overflow: hidden;
					}

					&__submenu {
						bottom: 60px;
						background-color: #fafafa;
						position: absolute;
						left: 0;
						width: 100%;
						border: 1px solid #d0d0d0;
						border-top: 0;
						display: none;

						&__arrow {
							position: absolute;
							left: 50%;
							margin-left: -6px;
						}

						&__arrow_out {
							bottom: -6px;
							display: inline-block;
							width: 0;
							height: 0;
							border-width: 6px;
							border-style: dashed;
							border-color: transparent;
							border-bottom-width: 0;
							border-top-color: #d0d0d0;
							border-top-style: solid;
						}

						&__arrow_in {
							bottom: -5px;
							display: inline-block;
							width: 0;
							height: 0;
							border-width: 6px;
							border-style: dashed;
							border-color: transparent;
							border-bottom-width: 0;
							border-top-color: #fafafa;
							border-top-style: solid;
						}

						&__ul {
							&__li {
								a {
									border-left: 0;
									font-size: 12px;

									span {
										display: block;
										border-top: 1px solid #e7e7eb;
										width: auto;
										overflow: hidden;
										text-overflow: ellipsis;
										white-space: nowrap;
										word-wrap: normal;
										cursor: pointer;
									}
								}

								&:first-child {
									border-top: 1px solid #d0d0d0;

									a {
										span {
											border-top: 0;
										}
									}
								}

								&.current {
									background-color: $color-white;
									border: 1px solid #44b549;
									position: relative;
									z-index: 1;
									line-height: 45px;
								}
							}
						}
					}
				}
			}
		}
	}

	// 编辑区域
	&-menu__edit {
		background-color: #f4f5f9;
		padding-left: 15px;
		padding-right: 15px;
		border: 1px solid #e7e7eb;
		min-height: 465px;
		position: relative;
		padding-bottom: 15px;
		margin-left: 318px;

		&__area {
			&__title {
				height: 40px;
				line-height: 40px;
				border-bottom: 1px solid #e7e7eb;

				&__area {
					float: right;
					color: #576b95;
					font-size: 14px;
					cursor: pointer;
				}

				span {
					font-size: 14px;
					color: #000;
				}
			}

			&__url,
			&__weapp {
				padding: 16px 20px;
				border: 1px solid #e7e7eb;
				background-color: $color-white;

				&__area {
					&__tips {
						padding-bottom: 10px;
						color: #9a9a9a;
					}
				}
			}
		}

		.triangle {
			display: block;
			position: absolute;
			left: -14px;
			bottom: 14px;
			width: 0;
			height: 0;
			border-right: 14px solid #e7e7eb;
			border-top: 14px solid transparent;
			border-bottom: 14px solid transparent;

			&:before {
				content: " ";
				position: absolute;
				left: 2px;
				top: -12px;
				display: block;
				width: 0;
				height: 0;
				border-right: 12px solid #f4f5f9;
				border-top: 12px solid transparent;
				border-bottom: 12px solid transparent;
			}
		}

		&__tips {
			display: flex;
			justify-items: center;
			align-items: center;
			width: 100%;
			height: 100%;
			position: absolute;
			top: 0;
			right: 0;
			left: 0;
			bottom: 0;
			text-align: center;

			&__box {
				width: 100%;
			}
		}

		// 已选择图文样式
		&__picmsg__area {
			color: #9a9a9a;
			overflow: hidden;

			&__list {
				width: 306px;
				display: inline-block;
				border: 1px solid #e7e7eb;
				background-color: $color-white;

				&__item {
					position: relative;
					padding: 15px;

					.bd,
					.hd {
						display: table-cell;
						vertical-align: middle;
						word-wrap: break-word;
						word-break: break-all;
					}

					.bd {
						width: 2000px;
						color: #353535;
					}

					.hd {
						.thumb {
							width: 48px;
							height: 48px;
							display: block;
							position: relative;

							.el-image {
								height: 100% !important;
							}
						}
					}

					&.has-thumb {
						padding-top: 56.25%;

						.bd {
							.title {
								position: absolute;
								left: 15px;
								right: 15px;
								bottom: 15px;
								overflow: hidden;
								text-overflow: ellipsis;
								display: -webkit-box;
								-webkit-box-orient: vertical;
								-webkit-line-clamp: 2;
								color: $color-white;
								font-weight: 400;
								z-index: 1;
							}
						}

						.hd {
							.thumb {
								position: absolute;
								top: 0;
								bottom: 0;
								left: 0;
								right: 0;
								width: auto;
								height: auto;
								background-size: cover;
								background-position: 50% 50%;
								background-repeat: no-repeat;
								background-color: #f6f8f9;
							}
						}
					}
				}
			}

			&__delete {
				display: inline-block;
				vertical-align: bottom;
				padding-left: 10px;
				margin-bottom: 5px;
				color: #576b95;
			}
		}

		// 图片样式
		&__pic {
			width: 320px;
			position: relative;

			&__delete {
				svg {
					fill: $color-white;
					margin-right: 10px;
					vertical-align: middle;
					margin-top: -2px;
				}

				position: absolute;
				top: 20px;
				right: 20px;
				background-color: rgba(0, 0, 0, 0.5);
				border: 0;
				padding: 6px 12px;
				border-radius: 4px;
				color: $color-white;

				&:hover {
					background-color: rgba(47, 47, 47, 0.5);
				}
			}
		}
	}

	&__toolbar {
		margin: 40px auto;
		text-align: center;
	}
}

.show {
	display: block;
}

.el-tabs {
	box-shadow: none;
}

.el-row {
	display: flex;
	justify-content: center;
	align-items: center;

	&:last-child {
		margin-bottom: 0;
	}

	.el-tabs__content {
		min-height: 300px;
	}
}
</style>

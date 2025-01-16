<!--
  - # 此文件版权属于ASO.DESIGN
  - # 文件：index.vue  项目：aso-zero
  - # 作用：
  - # 当前修改时间：2021年07月18日 01:05:05
  - # 上次修改时间：2021年07月18日 00:37:51
  - # 作者：thunur
  - # 此文件不可非法传播、倒卖、共享，否则我们将追究相应的法律责任。
  - # 您如果已获得ASO.DESIGN授权可在原有基础上进行修改使用
  - # 如果您还没获得授权请联系我们 thunur@qq.com
  - # Copyright (c) 2021 aso.design
  -->

<template>
	<div class="app-container-10 container-f0f2f5 app-main goods-list-container">
		<div class=" container-fff app-container-15 app-ele-border-radius-0">
			<div class="goods-tabs">
				<el-tabs type="border-card">
					<el-tab-pane label="销售中"></el-tab-pane>
					<el-tab-pane label="已售罄"></el-tab-pane>
					<el-tab-pane label="仓库中"></el-tab-pane>
				</el-tabs>
			</div>
			<el-button type="primary" size="small" @click="goCreatePage">发布商品</el-button>
			<!--搜索-->
			<div class="goods-search">
				<el-form v-model="formObj" :option="formOption">
					<template v-slot:btnList>
						<el-button type="primary" size="mini">确定</el-button>
						<el-button size="mini">导出报表</el-button>
						<el-button size="mini">查看已生成报表</el-button>
						<el-button type="text" size="mini">清空筛选条件</el-button>
					</template>
				</el-form>
			</div>
			<!--商品列表-->
			<div class="content-box">
				<el-table :data="list" :option="option" @paginationChance="paginationChance">
					<!--商品 价格-->
					<template v-slot:title="props">
						<div class="goods-name">
							<img
								:src="props.data.imageUrl"
								:alt="props.data.title"
								@click="
									lookBigImgCover = true;
									imagesUrl = props.data.imageUrl;
								"
							/>
							<div class="goods-name-item">
								<p>
									{{ props.data.title }}
									<span class="title-edit" />
								</p>
								<span>￥ {{ props.data.price }}</span>
							</div>
						</div>
					</template>

					<!--访问量-->
					<template v-slot:visitCountUv="props">
						<p>访客数:{{ props.data.visitCountUv }}</p>
						<p>浏览量:{{ props.data.visitCountPv }}</p>
					</template>

					<!--最右边操作-->
					<template>
						<el-button size="mini" type="text">复制</el-button>
					</template>
				</el-table>
			</div>
		</div>
		<!--查看大图-->
		<el-dialog :visible.sync="lookBigImgCover" width="40%">
			<img style="display: block;margin: 0 auto;max-width: 100%;" :src="imagesUrl" />
		</el-dialog>
	</div>
</template>

<script>
import List from "./list";

export default List;
</script>

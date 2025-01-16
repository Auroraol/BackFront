<template>
	<div class="app-container-10 container-f0f2f5 app-main goods-create-container">
		<div class="container-fff  app-container-15 app-ele-border-radius-0">
			<div class="goods-steps">
				<div class="goods-steps-item is-finish">
					<div class="goods-steps-step">1. 编辑基本信息</div>
				</div>
				<div class="goods-steps-item ">
					<div class="goods-steps-step">2. 编辑商品详情</div>
				</div>
			</div>
			<div class="goods-create-form">
				<el-form v-model="formObj" :option="formOption">
					<!--商品图片-->
					<template v-slot:goodsImg>
						<div v-for="(item, index) in 5" :key="index" class="goods-create-add-img">
							<img
								src="https://perfectdiary-public.oss-cn-shenzhen.aliyuncs.com/image/2019/07/679624aabc6e4e05bf15354db57a8cc4.png?x-oss-process=image/resize,m_fill,h_250,w_250"
								alt=""
							/>
							<span class="close-model">×</span>
						</div>
						<div class="goods-create-add-img">
							<span class="add-icon">+ 添加封面</span>
						</div>
					</template>
					<!--主图视频-->
					<template v-slot:goodsVideo>
						<div v-for="(item, index) in 1" :key="index" class="goods-create-add-img">
							<img
								src="https://perfectdiary-public.oss-cn-shenzhen.aliyuncs.com/image/2019/07/679624aabc6e4e05bf15354db57a8cc4.png?x-oss-process=image/resize,m_fill,h_250,w_250"
								alt=""
							/>
							<span class="close-model">×</span>
						</div>
						<div class="goods-create-add-img">
							<span class="add-icon">+ 添加视频</span>
						</div>
					</template>
					<!--新增分组-->
					<template v-slot:addNewGrouping>
						<el-button style="display: inline-block;margin-left:10px;" type="text"
							>刷新</el-button
						>
						<el-button
							style="display: inline-block"
							type="text"
							@click="$router.push({ name: 'goodsTag' })"
							>新建</el-button
						>
					</template>
					<!--商品规格-->
					<template v-slot:goodsSku>
						<div class="goods-sku-group">
							<div
								v-for="(attributeItem, attributeIndex) in formObj.attribute"
								:key="attributeIndex"
							>
								<div class="goods-sku-group-btn">
									<span>规格名：</span>
									<div class="sku-inline">
										<el-select
											v-model="attributeItem.groupId"
											size="small"
											filterable
											allow-create
											default-first-option
											placeholder="请选择规格"
											@blur="selectBlur(attributeItem, 1, $event)"
											@keyup.enter.native="
												selectBlur(attributeItem, 1, $event)
											"
											@change="selectChange(attributeItem, 1)"
										>
											<el-option
												v-for="groupItem in groupList"
												:key="groupItem.id"
												:label="groupItem.name"
												:value="groupItem.id"
												:disabled="groupItem.disabled"
												>{{ groupItem.name }}
											</el-option>
										</el-select>
									</div>
									<div v-if="attributeIndex === 0" class="sku-add-imgs">
										<el-checkbox v-model="isAddImg">添加规格图片</el-checkbox>
									</div>
									<span
										style="transform: translateY(-50%);top: 50%;right: 10px;"
										class="close-model"
										@click="
											formObj.attribute.splice(attributeIndex, 1);
											isCanSelect();
										"
										>×</span
									>
								</div>
								<div
									v-if="attributeItem.groupId && attributeItem.attributeValueList"
									class="goods-sku-group-list"
									style="padding-left: 61px;"
								>
									<span class="goods-gui-ge">规格值：</span>
									<div
										v-for="(attributeValueItem,
										attributeValueIndex) in attributeItem.attributeValueList"
										:key="attributeValueIndex"
										:class="{ active: attributeIndex === 0 && isAddImg }"
										class="sku-inline"
									>
										<div class="sku-inline-box">
											<!--规格值选项-->
											<el-select
												v-model="attributeValueItem.specValueId"
												size="small"
												filterable
												allow-create
												default-first-option
												placeholder="请选择规格值"
												@blur="selectBlur(attributeValueItem, 2, $event)"
												@keyup.enter.native="
													selectBlur(attributeValueItem, 2, $event)
												"
												@change="
													selectChange(
														attributeValueItem,
														2,
														attributeItem
													)
												"
											>
												<el-option
													v-for="attributeOptionItem in attributeValueList[
														attributeItem.groupId
													] || []"
													:key="attributeOptionItem.specValueId"
													:label="attributeOptionItem.specValueName"
													:value="attributeOptionItem.specValueId"
													:disabled="attributeOptionItem.disabled"
													>{{ attributeOptionItem.specValueName }}
												</el-option>
											</el-select>
											<span
												class="close-model"
												@click="
													attributeItem.attributeValueList.splice(
														attributeValueIndex,
														1
													);
													isCanSelect();
												"
												>×</span
											>
											<div
												v-if="attributeIndex === 0 && isAddImg"
												class="upload-img-wrap"
											>
												<div class="arrow" />
												<div
													v-if="!attributeValueItem.imageUrl"
													class="rc-upload"
												>
													<div class="rc-upload-trigger">
														<span>+</span>
													</div>
												</div>
												<div v-else class="upload-img">
													<img
														src="https://perfectdiary-public.oss-cn-shenzhen.aliyuncs.com/image/2019/07/679624aabc6e4e05bf15354db57a8cc4.png?x-oss-process=image/resize,m_fill,h_250,w_250"
													/>
													<span
														class="close-model"
														@click="attributeValueItem.imageUrl = ''"
														>×</span
													>
													<div class="rc-upload">替换</div>
												</div>
											</div>
										</div>
									</div>
									<div class="sku-add-imgs">
										<el-button
											size="small"
											class="goods-sku-group-sort"
											@click="addAttributeValue(attributeItem)"
											>添加规格值
										</el-button>
									</div>
								</div>
								<div
									v-if="attributeIndex === 0 && attributeItem.groupId && isAddImg"
									class="goods-sku-group-list"
								>
									<span style="opacity: 0;">描述：</span>
									<div class="sku-inline">
										<p class="help-block">
											仅支持为第一组规格设置规格图片（最多40张图），买家选择不同规格会看到对应规格图片，建议尺寸：800
											x 800像素
										</p>
									</div>
								</div>
							</div>
							<div class="goods-sku-group-btn">
								<el-button
									v-if="formObj.attribute.length < 3"
									size="small"
									@click="addAttributeList"
								>
									添加规格项目
								</el-button>
								<el-tooltip
									v-else
									size="small"
									effect="dark"
									content="最多支持 3 组规格"
									placement="top"
								>
									<div class="goods-sku-group-btn-disable">添加规格项目</div>
								</el-tooltip>
								<el-button
									size="small"
									class="goods-sku-group-sort"
									@click="dialogVisibleSlickSort = true"
									>自定义排序
								</el-button>
							</div>
						</div>
					</template>
					<!--规格明细-->
					<template v-slot:goodsSkuDetail>
						<div v-if="isShowGoodsSkuDetail" class="goodsSkuDetail">
							<el-table
								:data="formObj.skuList"
								:span-method="objectSpanMethod"
								:highlight-current-row="false"
								:border="true"
								style="width: 100%; margin-bottom: 20px"
							>
								<el-table-column
									v-for="(item, index) in formObj.attribute"
									:key="index"
									:prop="'specValue' + (index + 1)"
									:label="item.groupName"
								>
									<!--33= {{'specValue' + (index+1) }}-->
								</el-table-column>
								<el-table-column>
									<template slot="header">
										<span class="goods-must">价格(元)</span>
									</template>
									<template slot-scope="scope">
										<div class="goods-sku-list-box">
											<el-input
												v-model.trim="scope.row.price"
												size="mini"
												type="text"
												class="sku-opts-item"
												oninput="value=value.replace(/[^0-9]/g,'')"
											/>
										</div>
									</template>
								</el-table-column>
								<el-table-column>
									<template slot="header">
										<span
											>库存
											<el-tooltip
												class="item"
												effect="dark"
												content="此处库存仍然为商品的可售卖库存，可以直接进行修改，修改后，买家看到的商品可售库存数量会进行变化"
												placement="top"
											>
												<i class="el-icon-question" />
											</el-tooltip>
										</span>
									</template>
									<template slot-scope="scope">
										<div class="goods-sku-list-box">
											<el-input
												v-model.trim="scope.row.activeNum"
												size="mini"
												type="text"
												class="sku-opts-item"
												oninput="value=value.replace(/[^0-9]/g,'')"
											/>
										</div>
									</template>
								</el-table-column>
								<el-table-column>
									<template slot="header">
										<span
											>规格编码
											<el-tooltip
												class="item"
												effect="dark"
												content="为方便管理，可以自定规格编码，比如货号"
												placement="top"
											>
												<i class="el-icon-question" />
											</el-tooltip>
										</span>
									</template>
									<template slot-scope="scope">
										<div class="goods-sku-list-box">
											<el-input
												v-model.trim="scope.row.skuCode"
												size="mini"
												type="text"
												class="sku-opts-item"
												oninput="value=value.replace(/[^0-9]/g,'')"
											/>
										</div>
									</template>
								</el-table-column>
								<el-table-column>
									<template slot="header">
										<span
											>成本价
											<el-tooltip
												class="item"
												effect="dark"
												content="成本价未来会用于营销建议，利润分析等"
												placement="top"
											>
												<i class="el-icon-question" />
											</el-tooltip>
										</span>
									</template>
									<template slot-scope="scope">
										<div class="goods-sku-list-box">
											<el-input
												v-model.trim="scope.row.originPrice"
												size="mini"
												type="text"
												class="sku-opts-item"
												oninput="value=value.replace(/[^0-9]/g,'')"
											/>
										</div>
									</template>
								</el-table-column>
								<el-table-column label="销量">
									<template slot-scope="scope">
										<div class="goods-sku-list-box">
											<el-input
												v-model.trim="scope.row.total_sold_num"
												size="mini"
												type="text"
												class="sku-opts-item"
												oninput="value=value.replace(/[^0-9]/g,'')"
											/>
										</div>
									</template>
								</el-table-column>
							</el-table>
							<div class="table-footer">
								<div class="table-footer-content">
									批量设置：
									<span
										:class="['table-footer-span', { active: !isShowSetPrice }]"
									>
										<el-button type="text" @click="isShowSetPrice = true"
											>价格</el-button
										>
										<el-button type="text" @click="isShowSetPrice = true"
											>库存</el-button
										>
									</span>
									<span
										:class="['table-footer-span', { active: isShowSetPrice }]"
									>
										<el-input
											style="width: 80px;margin-right: 10px"
											size="mini"
										/>
										<el-button type="text" @click="setAllPrice">保存</el-button>
										<el-button type="text" @click="isShowSetPrice = false"
											>取消</el-button
										>
									</span>
								</div>
							</div>
						</div>
					</template>

					<!--会员折扣-->
					<template v-slot:memberDiscount>
						<el-button style="display: inline-block;margin-left:10px;" type="text"
							>管理权益卡</el-button
						>
					</template>

					<!--无现货，下单后需过段时间才能发货-->
					<template v-slot:preSaleSlot>
						<div v-if="formObj.preSale">
							<div>
								<el-radio v-model="deliveryType" :label="1">
									<el-date-picker
										v-model="formObj.deliveryTime"
										style="width: 220px"
										type="date"
										:disabled="deliveryType === 2"
										placeholder="请选择发货时间"
									></el-date-picker>
									<span style="margin-left: 10px">开始发货</span>
								</el-radio>
							</div>
							<div style="margin-top: 10px">
								<el-radio v-model="deliveryType" :label="2"
									>付款成功
									<el-input
										v-model="formObj.deliveryDay"
										oninput="value=value.replace(/[^1-9]/g,'')"
										maxlength="90"
										:disabled="deliveryType === 1"
										style="width:80px;margin: 0 10px"
									></el-input>
									天后发货
								</el-radio>
							</div>
							<p class="goods-p">
								只允许设置90天内的发货时间
								，请务必按照约定时间发货以免引起客户投诉。
							</p>
						</div>
					</template>

					<!--限购-->
					<template v-slot:restrictionNumberSlot>
						<div v-if="formObj.restrictionNumber">
							<div>
								<el-radio v-model="restrictionNumberType" :label="1"
									>终身限购
									<el-input
										v-model="formObj.onlyNumberForever"
										maxlength="90"
										:disabled="restrictionNumberType === 2"
										oninput="value=value.replace(/[^1-9]/g,'')"
										style="width:80px;margin: 0 10px"
									></el-input>
									<span style="margin-left: 10px">件</span>
								</el-radio>
							</div>
							<div class="restrictionNumber">
								<el-radio v-model="restrictionNumberType" :label="2"
									>按周期限购
									<el-select
										v-model="formObj.weekType"
										style="width: 100px"
										:disabled="restrictionNumberType === 1"
									>
										<el-option
											v-for="item in onlyNumberWeekList"
											:key="item.value"
											:label="item.label"
											:value="item.value"
										></el-option>
									</el-select>
									<el-input
										v-model="formObj.onlyNumberWeek"
										maxlength="90"
										:disabled="restrictionNumberType === 1"
										oninput="value=value.replace(/[^1-9]/g,'')"
										style="width:80px;margin: 0 10px"
									></el-input>
									件
								</el-radio>
							</div>
						</div>
					</template>
				</el-form>
			</div>
		</div>
		<div class="create-save-btn app-ele-border-radius-0">
			<div class="create-save-btn-box">
				<el-button size="mini">保存</el-button>
				<el-button size="mini" type="primary">下一步</el-button>
			</div>
		</div>
		<!--拖拽排序SKU-->
		<el-dialog
			custom-class="dialog-common app-ele-border-radius-0"
			:close-on-click-modal="false"
			top="20vh"
			title="自定义排序"
			:visible.sync="dialogVisibleSlickSort"
			width="50%"
		>
			<div class="dialog-common-content">
				<div class="at-list">
					<div v-for="(item, index) in slickData" :key="index">
						<div class="group__title">{{ item.groupName }}：</div>
						<div class="group__content">
							<draggable :list="item.attributeValueList">
								<div
									class="preview-item myPre_for_create"
									v-for="(attItem, attIndex) in item.attributeValueList"
									:key="attIndex"
									:index="attIndex"
								>
									{{ attItem.specValueName }}
								</div>
							</draggable>
						</div>
					</div>
				</div>
			</div>
			<el-row slot="footer" style="text-align: right;">
				<!--@click="dialogVisibleSlickSort=!dialogVisibleSlickSort"-->
				<el-button type="primary" size="small" @click="slickFun(1)">确定</el-button>
				<el-button size="small" @click="slickFun(2)">取消</el-button>
			</el-row>
		</el-dialog>
	</div>
</template>

<script>
import Create from "./create";

export default Create;
</script>

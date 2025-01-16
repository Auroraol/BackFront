/*
 * # 此文件版权属于ASO.DESIGN
 * # 文件：create.js  项目：aso-zero
 * # 作用：
 * # 当前修改时间：2021年07月18日 01:05:05
 * # 上次修改时间：2021年07月18日 00:36:58
 * # 作者：thunur
 * # 此文件不可非法传播、倒卖、共享，否则我们将追究相应的法律责任。
 * # 您如果已获得ASO.DESIGN授权可在原有基础上进行修改使用
 * # 如果您还没获得授权请联系我们 thunur@qq.com
 * # Copyright (c) 2021 aso.design
 */

import "./create.scss";
import draggable from "vuedraggable";

export default {
	name: "GoodsCreate",
	components: {
		draggable
	},
	data() {
		return {
			slickData: [], // 用于自定义排序
			formObj: {
				skuList: [], // 处理完后SKU列表
				attribute: [],
				deductionType: 1, // 扣减库存的方式
				logisticsInfo: [1], // 物流信息
				expressFreight: 0, // 快递运费
				shelfType: 1, // 上架时间类型
				shelfTime: "", // 上架时间
				preSale: 0, // 无现货，下单后需过段时间才能发货
				deliveryTime: "", // 发货时间 开始发货
				deliveryDay: "", // 发货时间 天后发货
				restrictionNumber: 0, // 是否限购
				onlyNumberForever: "", // 终身限购
				onlyNumberWeek: "", // 按周期限购
				weekType: "" // 周期类型
			},
			deliveryType: 1, // 无现货，下单后需过段时间才能发货 发货时间段、发货天数
			restrictionNumberType: 1, // 限购类型
			onlyNumberWeekList: [
				{
					value: "week",
					label: "每周"
				},
				{
					value: "month",
					label: "每月"
				},
				{
					value: "year",
					label: "每年"
				}
			],
			formOption: {
				itemSize: "mini",
				column: [
					{
						title: "基本信息"
					},
					{
						children: [
							{
								itemLabel: "商品名",
								type: "input",
								model: "goodsName",
								itemProp: "name",
								itemRules: [
									{ required: true, message: "请填写商品名称", trigger: "blur" }
								],
								clearable: true
							}
						]
					},
					{
						children: [
							{
								itemLabel: "分享描述",
								type: "input",
								model: "desc",
								desc: "展示商品卖点，更加能提高用户的购买欲，建议36个字以内",
								clearable: true
							}
						]
					},
					{
						children: [
							{
								itemLabel: "商品图",
								must: true,
								slotName: "goodsImg",
								desc: "建议尺寸：800*800像素，最多上传15张"
							}
						]
					},
					{
						children: [
							{
								itemLabel: "商品类目",
								type: "select",
								model: "category",
								filterable: true,
								data: [
									{
										value: 1,
										label: "女人"
									},
									{
										value: 2,
										label: "男人"
									},
									{
										value: 3,
										label: "食品"
									},
									{
										value: 4,
										label: "美妆"
									},
									{
										value: 5,
										label: "亲子"
									},
									{
										value: 6,
										label: "居家"
									}
								]
							}
						]
					},
					{
						children: [
							{
								itemLabel: "商品分组",
								type: "select",
								model: "tag",
								multiple: true,
								slotName: "addNewGrouping",
								desc: "使用“列表中隐藏”分组，商品将不出现在商品列表中",
								data: [
									{
										value: 1,
										label: "分组1"
									},
									{
										value: 2,
										label: "分组2"
									},
									{
										value: 3,
										label: "分组3"
									}
								]
							}
						]
					},
					{
						children: [
							{
								itemLabel: "商品卖点",
								type: "input",
								model: "sellingPoint",
								desc: "在商品详情页标题下面展示卖点信息，建议60字以内",
								clearable: true
							}
						]
					},
					{
						children: [
							{
								itemLabel: "主图视频",
								slotName: "goodsVideo",
								desc: "建议时长9-30秒，建议视频宽高比16:9"
							}
						]
					},
					{
						title: "价格库存"
					},
					{
						children: [
							{
								itemLabel: "商品规格",
								slotName: "goodsSku",
								desc: "如有颜色、尺码等多种规格，请添加商品规格"
							}
						]
					},
					{
						children: [
							{
								itemLabel: "规格明细",
								slotName: "goodsSkuDetail"
							}
						]
					},
					{
						children: [
							{
								itemLabel: "价格",
								type: "input",
								model: "price",
								must: true,
								placeholder: "0.00",
								prepend: "￥",
								disabled: true
							}
						]
					},
					{
						children: [
							{
								itemLabel: "划线格",
								type: "input",
								model: "originPrice",
								placeholder: ""
							}
						]
					},
					{
						children: [
							{
								itemLabel: "库存扣减方式",
								type: "radio",
								model: "deductionType",
								must: true,
								desc:
									"商品参加“多人拼团”、“降价拍”活动时，默认为付款减库存，参加“秒杀”活动时，默认为拍下减库存",
								data: [
									{
										value: 1,
										label: "库存扣减方式"
									},
									{
										value: 2,
										label: "付款减库存"
									}
								]
							}
						]
					},
					{
						children: [
							{
								itemLabel: "库存",
								type: "input",
								model: "activeNum",
								must: true,
								placeholder: "0",
								disabled: true
							}
						]
					},
					{
						children: [
							{
								itemLabel: "剩余件数",
								label: "商品详情不显示剩余件数",
								type: "checkbox",
								model: "value"
							}
						]
					},
					{
						children: [
							{
								itemLabel: "会员折扣",
								label: "参加会员折扣",
								type: "checkbox",
								model: "memberDiscount",
								slotName: "memberDiscount",
								desc: "是否勾选不影响自定义会员价生效"
							}
						]
					},
					{
						title: "物流信息"
					},
					{
						children: [
							{
								type: "checkbox",
								itemLabel: "配送方式",
								model: "logisticsInfo",
								must: true,
								isGroup: true,
								desc: "",
								data: [
									{
										value: 1,
										label: "快递发货"
									},
									{
										value: 2,
										label: "同城配送"
									},
									{
										value: 3,
										label: "到店自提"
									}
								]
							}
						]
					},
					{
						children: [
							{
								itemLabel: "快递运费",
								type: "radio",
								model: "expressFreight",
								must: true,
								desc:
									"运费模板支持按地区设置运费，按购买件数计算运费，按重量计算运费等",
								data: [
									{
										value: 0,
										label: "统一邮费",
										children: [
											// 子选项
											{
												type: "input",
												model: "freightPrice",
												placeholder: "",
												prepend: "￥"
											}
										]
									},
									{
										value: 2,
										label: "运费模板",
										children: [
											// 子选项
											{
												type: "select",
												model: "category",
												filterable: true,
												placeholder: "请选择运费模板",
												data: [
													{
														value: 1,
														label: "广东"
													},
													{
														value: 2,
														label: "北京"
													},
													{
														value: 3,
														label: "海外"
													},
													{
														value: 4,
														label: "台湾"
													},
													{
														value: 5,
														label: "香港"
													},
													{
														value: 6,
														label: "澳门"
													}
												]
											}
										]
									}
								]
							}
						]
					},
					{
						title: "其他信息"
					},
					{
						children: [
							{
								itemLabel: "上架时间",
								type: "radio",
								model: "shelfType",
								data: [
									{
										value: 1,
										label: "立即上架售卖"
									},
									{
										value: 2,
										label: "自定义上架时间",
										children: [
											{
												type: "datePicker",
												model: "shelfTime",
												placeholder: "请选择上架售卖时间",
												pickerOptions: {
													shortcuts: [
														{
															text: "今天",
															onClick(picker) {
																picker.$emit("pick", new Date());
															}
														},
														{
															text: "昨天",
															onClick(picker) {
																const date = new Date();
																date.setTime(
																	date.getTime() -
																		3600 * 1000 * 24
																);
																picker.$emit("pick", date);
															}
														},
														{
															text: "一周前",
															onClick(picker) {
																const date = new Date();
																date.setTime(
																	date.getTime() -
																		3600 * 1000 * 24 * 7
																);
																picker.$emit("pick", date);
															}
														}
													]
												}
											}
										]
									},
									{
										value: 3,
										label: "暂不售卖，放入仓库"
									}
								]
							}
						]
					},
					{
						children: [
							{
								itemLabel: "预售",
								label: "无现货，下单后需过段时间才能发货",
								type: "checkbox",
								model: "preSale",
								trueLabel: 1,
								falseLabel: 0,
								slotName: "preSaleSlot"
							}
						]
					},
					{
						children: [
							{
								itemLabel: "限购",
								label: "限制每人可购买数量",
								type: "checkbox",
								model: "restrictionNumber",
								trueLabel: 1,
								falseLabel: 0,
								slotName: "restrictionNumberSlot"
							}
						]
					}
				]
			},
			isAddImg: false, // 是否添加规格图片
			isShowSetPrice: false, // 是否显示批量设置价格和库存
			dialogVisibleSlickSort: false, // 自定义排序弹窗
			groupList: [
				// 所有的规格组
				{
					id: 1,
					name: "颜色"
				},
				{
					id: 2,
					name: "内存"
				},
				{
					id: 3,
					name: "系列"
				}
			],
			attributeValueList: {
				1: [
					// 以规格组为key
					{
						specValueId: "1-1",
						specValueName: "白色"
					},
					{
						specValueId: "1-2",
						specValueName: "黑色"
					},
					{
						specValueId: "1-3",
						specValueName: "绿色"
					},
					{
						specValueId: "1-4",
						specValueName: "黄色"
					},
					{
						specValueId: "1-5",
						specValueName: "紫色"
					},
					{
						specValueId: "1-6",
						specValueName: "红色"
					}
				],
				2: [
					{
						specValueId: "2-1",
						specValueName: "64G"
					},
					{
						specValueId: "2-2",
						specValueName: "128G"
					},
					{
						specValueId: "2-3",
						specValueName: "256G"
					}
				],
				3: [
					{
						specValueId: "3-1",
						specValueName: "iPhone11"
					},
					{
						specValueId: "3-2",
						specValueName: "iPhone11 pro"
					}
				]
			} // 规格值列表
		};
	},
	computed: {
		isShowGoodsSkuDetail() {
			// 是否显示SKU明细
			let isShow = false;
			if (this.formObj.attribute.length) {
				this.formObj.attribute.map(at => {
					if (at.attributeValueList.length) {
						isShow = true;
					}
				});
			}
			return isShow;
		},
		skuListDeal() {
			return this.formObj.attribute;
		},
		logisticsInfo() {
			return this.formObj.logisticsInfo;
		}
	},
	watch: {
		logisticsInfo: {
			// 处理物流信息的描述
			deep: true,
			handler(val) {
				let desc = "";
				if (val.find(x => x === 2)) {
					desc = "“同城配送”";
				}
				if (val.find(x => x === 3)) {
					if (desc) {
						desc = desc + "、";
					}
					desc = desc + "“到店自提”";
				}
				if (desc) {
					desc = desc + "需在店铺设置开启后生效";
				}
				this.formOption.column.map(res => {
					if (res.children) {
						res.children.map(li => {
							if (li.model === "logisticsInfo") {
								li.desc = desc;
							}
						});
					}
				});
			}
		},
		skuListDeal: {
			// 梳理SKU 笛卡尔积算法
			deep: true,
			handler(val) {
				if (val.length) {
					//定义拖拽排序的数据
					this.slickData = JSON.parse(JSON.stringify(val));
				} else {
					this.slickData = [];
				}
				console.log("this.slickData==", this.slickData);
				if (val.length) {
					const calc = [];
					val.map((res, index) => {
						calc[index] = [];
						res.attributeValueList.map(at => {
							if (at.specValueName) {
								calc[index].push({
									specValueId: at.specValueId,
									specValueName: at.specValueName
								});
							}
						});
					});
					const currentSku = this.calcDescartes(calc);
					if (currentSku.length) {
						console.log("calc", currentSku);
						this.formObj.skuList = [];
						currentSku.map((list, listIndex) => {
							console.log("list==", typeof list, list, listIndex);
							const obj = {
								sortNum: listIndex + 1, // 排序
								skuComb: "", // comb
								total_sold_num: 0, // 销量
								price: "", // SKU价格
								originPrice: "", // SKU价格成本价
								skuCode: "", // sku编码
								activeNum: "" // 库存
							};
							if (list.length) {
								list.map((cu, index) => {
									const key = "specValue" + (index + 1);
									const value = "specValueId" + (index + 1);
									obj[key] = cu.specValueName;
									obj[value] = cu.specValueId;
								});
							} else {
								console.log("00", list);
								obj.specValue1 = list.specValueName;
								obj.specValueId1 = list.specValueId;
							}
							this.formObj.skuList.push(obj);
						});
						console.log(this.formObj.skuList);
					}
				}
			}
		}
	},
	methods: {
		/** 笛卡尔积算法*/
		calcDescartes(array) {
			if (array.length < 2) return array[0] || [];
			return [].reduce.call(array, function(col, set) {
				const res = [];
				col.forEach(function(c) {
					set.forEach(function(s) {
						const t = [].concat(Array.isArray(c) ? c : [c]);
						t.push(s);
						res.push(t);
					});
				});
				return res;
			});
		},
		/** 添加新的规格*/
		addAttributeList() {
			if (this.formObj.attribute.length >= 3) {
				this.$message.error("最多支持 3 组规格");
				return;
			}
			this.formObj.attribute.push({
				attributeValueList: [],
				groupId: "", // 规格组id
				groupName: "", // 规格组名称
				sortNum: this.formObj.attribute.length + 1 // 排序号，用于判断第几属性
			});
		},
		/** 添加规格值*/
		addAttributeValue($item) {
			// console.log('$item==', $item)
			$item.attributeValueList.push({
				imageUrl: "", // sku 主图
				specValueId: "", // 规格值ID
				specValueName: "" // 规格值名称
			});
		},
		/** 选择规格、规格值失焦、回车事件
		 * @param item[当前规格]
		 * @param type [类型：1=>规格组 2=>规格值]
		 * @param $even 事件
		 * */
		selectBlur(item, type, $even) {
			/** 获取当前失焦的值*/
			// console.log(item, type, $even)
			// let text = $even.target.value
			if ($even.type === "blur") {
				// 失焦事件 该事件没有进去selectChange事件中，需要额外处理
			}
		},
		/** 选择规格、规格值失焦、回车事件
		 * @param item[当前规格]
		 * @param type [类型：1=>规格组 2=>规格值]
		 * @param attributeItem
		 * */
		selectChange(item, type, attributeItem) {
			// console.log(item, type, attributeItem)
			if (type === 1) {
				// 规格组
				const currentAttribute = this.groupList.find(x => x.id === item.groupId); // 获取当前的规格组信息
				const isInList = this.formObj.attribute.filter(x => x.groupId === item.groupId);
				if (isInList.length >= 2) {
					// 已经存在
					this.$message.error(`已经存在${currentAttribute.name}规格`);
					item.groupId = ""; // 存在
					return;
				}
				item.groupName = currentAttribute.name; // 设置规格名称
				this.isCanSelect();
				return;
			}
			/** 规格值*/
			const isInValue = attributeItem.attributeValueList.filter(
				x => x.specValueId === item.specValueId
			);
			if (isInValue.length >= 2) {
				// 已经存在
				this.$message.error(`该组存在${item.specValueName}规格值`);
				item.specValueId = ""; // 存在
				return;
			}
			item.specValueName = this.attributeValueList[attributeItem.groupId].find(
				x => x.specValueId === item.specValueId
			)["specValueName"];
			this.isCanSelect();
		},
		/**
		 * 是否可以选择
		 * */
		isCanSelect() {
			// 规格组
			this.groupList.map(res => {
				this.$set(res, "disabled", false); // 默认可以选择
			});
			for (const ol in this.attributeValueList) {
				const list = this.attributeValueList[ol];
				if (list.length) {
					this.attributeValueList[ol].map(res => {
						this.$set(res, "disabled", false); // 默认可以选择
					});
				}
			}
			this.formObj.attribute.map(att => {
				this.groupList.map(res => {
					if (res.id === att.groupId) {
						this.$set(res, "disabled", true); // 将已选择的设为禁用
					}
					att.attributeValueList.map(cli => {
						// 设置规格值的是否可选属性
						const attList = this.attributeValueList[att.groupId];
						if (attList) {
							attList.map(li => {
								if (cli.specValueId === li.specValueId) {
									// 存在
									this.$set(li, "disabled", true); // 将已选择的设为禁用
								}
							});
						}
					});
				});
			});
		},
		/** table span method*/
		objectSpanMethod({ row, column, rowIndex, columnIndex }) {
			let tableSpanMethod = [1, 1];
			const $attribute = this.formObj.attribute;
			if ($attribute.length >= 2) {
				// 选择的规格值是否大于2
				// console.log('[2]===', $attribute)
				const att_2 = $attribute[1].attributeValueList.length || 1;
				let att_3 = 1;
				if ($attribute.length === 3) {
					att_3 = $attribute[2].attributeValueList.length || 1;
				}
				tableSpanMethod = [att_2 * att_3, att_3, 1];
			}
			// console.log('tableSpanMethod==', tableSpanMethod);
			if (columnIndex === 0) {
				if (rowIndex % tableSpanMethod[0] === 0) {
					return {
						rowspan: tableSpanMethod[0],
						colspan: 1
					};
				} else {
					return {
						rowspan: 0,
						colspan: 0
					};
				}
			}
			if (columnIndex === 1) {
				if (rowIndex % tableSpanMethod[1] === 0) {
					return {
						rowspan: tableSpanMethod[1],
						colspan: 1
					};
				} else {
					return {
						rowspan: 0,
						colspan: 0
					};
				}
			}
		},
		/** 批量设置库存、价格*/
		setAllPrice() {
			this.isShowSetPrice = false;
		},
		/**拖拽排序*/
		slickFun($type) {
			if ($type === 1) {
				//确定
				this.formObj.attribute = JSON.parse(JSON.stringify(this.slickData));
			} else {
				//取消
				let sli = JSON.stringify(this.formObj.attribute);
				this.slickData = JSON.parse(sli);
			}
			this.dialogVisibleSlickSort = false;
		}
	}
};

import "./list.scss";
export default {
	name: "GoodsList",
	components: {},
	data() {
		return {
			lookBigImgCover: false, // 查看大图
			imagesUrl: "", // 需要查看大图的地址
			list: [],
			option: {
				menuDeleteOption: {
					type: "text"
				},
				menuEditOption: {
					type: "text"
				},
				isPagination: true,
				paginationTotal: 0,
				paginationCurrent: 1,
				paginationSize: 20,
				menuWidth: 200,
				column: [
					{
						label: "商品 价格",
						prop: "title",
						slotName: "title"
					},
					{
						label: "访问量",
						prop: "visitCountUv",
						slotName: "visitCountUv",
						width: 200,
						sortable: true
					},
					{
						label: "库存",
						prop: "totalStock",
						width: 200,
						sortable: true
					},
					{
						label: "总销量",
						prop: "totalSoldNum",
						width: 200,
						sortable: true
					},
					{
						label: "创建时间",
						prop: "createdTime",
						width: 200,
						sortable: true
					}
				]
			},
			/** 搜索*/
			formOption: {
				itemSize: "small",
				labelWidth: "150px",
				column: [
					{
						span: 7,
						children: [
							{
								itemLabel: "商品名称或编码: ",
								type: "input",
								model: "keyWord"
							}
						]
					},
					{
						span: 12,
						children: [
							{
								itemLabel: "商品分组: ",
								type: "select",
								model: "ground",
								clearable: true,
								filterable: true,
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
						span: 7,
						children: [
							{
								itemLabel: "商品类型: ",
								type: "select",
								model: "type",
								clearable: true,
								filterable: true,
								data: [
									{
										value: 1,
										label: "类型1"
									},
									{
										value: 2,
										label: "类型2"
									},
									{
										value: 3,
										label: "类型3"
									}
								]
							}
						]
					},
					{
						span: 7,
						children: [
							{
								itemLabel: "总销量: ",
								type: "input",
								model: "OrderTimesMin",
								style: "width:50px;",
								clearable: true
							},
							{
								type: "input",
								model: "OrderTimesMax",
								style: "width:50px;",
								clearable: true,
								addPre: "-",
								addPreStyle: "margin-right:5px;margin-left:5px;"
							}
						]
					},
					{
						span: 7,
						children: [
							{
								itemLabel: "价格: ",
								type: "input",
								model: "priceMin",
								style: "width:50px;",
								clearable: true
							},
							{
								type: "input",
								model: "priceMax",
								style: "width:50px;",
								clearable: true,
								addPre: "-",
								addPreStyle: "margin-right:5px;margin-left:5px;"
							}
						]
					},
					{
						span: 24,
						slotName: "btnList"
					}
				]
			},
			formObj: {},
			listQuery: {
				categoryId: "" // 当前分组ID
			}
		};
	},
	computed: {},
	mounted() {
		this.getList();
	},
	methods: {
		/** 跳转编辑页面*/
		goCreatePage() {
			this.$router.push({
				name: "goodsCreate"
			});
		},
		/** 获取列表*/
		getList() {
			const par = this.listQuery;
			par.page = this.option.paginationCurrent;
			par.limit = this.option.paginationSize;
			// goodsList(par).then(res => {
			// 	if (res.code === 20000) {
			// 		this.list = res.data.items;
			// 		this.option.paginationTotal = res.data.total;
			// 	}
			// });
		},
		/** 分页回调*/
		paginationChance(ev) {
			let current = ev.data.current;
			if (ev.data.size !== this.option.paginationSize) {
				current = 1;
			}
			this.option.paginationSize = ev.data.size;
			this.option.paginationCurrent = current;
			this.getList();
		}
	}
};

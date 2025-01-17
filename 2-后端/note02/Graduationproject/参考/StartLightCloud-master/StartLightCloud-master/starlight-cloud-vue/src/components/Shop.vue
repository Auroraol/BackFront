<template>
    <div class="shop-information">
        <!-- <div class="shop-title" style="font-size: large;color: #211E34;">
            恭喜发现彩蛋2-FxShdadowTG
        </div> -->
        <!-- <canvas id="QRCode_header"></canvas> -->
        <el-dialog
            :custom-class="'my-buyGoodsPaymentDialog-class'"
            v-model="rechargeDialogVisible"
            title="请扫码或复制链接进行支付"
            width="40%"
        >
        <span>
            
            <canvas id="QRCode_header"></canvas>
            <p></p>
            <div>
                <el-button color="#626aef" @click="this.copyAlipayURL(this.alipayURL)" >点击复制链接</el-button>
            </div>
        </span>

        <p></p>

        <div>
            <el-image style="width: 35px; height: 35px" :src="alipayIconUrl" :fit="fit" />
        </div>

        </el-dialog>

        <el-tabs type="border-shop">
            <el-tab-pane label="商品" style="color: #FBBC4D;">

                <p v-for="(goods) in goodsList" :key="goods.id" class="scrollbar-shop-goods">
                    <el-row style="background-color:#312C4D;color: white;border-radius: 5px;">
                        <el-col>
                            <el-container>
                                <el-aside width="200px" style="padding-left: 5px;padding-top: 5px;">
                                    <!-- <el-image style="border-radius: 5px;" :src="sCoinIconUrl" fit="fill" /> -->
                                    <el-image style="border-radius: 5px;" :src="getGoodsTypeIcon(goods.goods_type)" fit="fill" />
                                </el-aside>
                                <el-container>
                                    <el-header>
                                        <span  style="font-size: large;float: left;padding-top: 15px;">
                                            {{ goods.goods_name }} ( {{ goods.display_day }} )
                                        </span>


                                        <span style="font-size: 15px;font-weight: normal;padding-top: 15px;margin-left: 100px;position: auto;float:right">
                                            剩余：{{ goods.storage_count }}
                                            </span>

                                            <el-dialog
                                                :custom-class="'my-buyGoodsConfirmDialog-class'"
                                                v-model="buyGoodsConfirmDialogVisible"
                                                width="25%"
                                            >

                                            <span style="color:azure;font-size: 15px;">确定购买 {{ this.currentGoodsSelectName }} 吗？</span>
                                            <template #footer>
                                            <span class="dialog-footer">
                                                <el-button @click="buyGoodsConfirmDialogVisible = false" style="background-color: #312D4C;color: aliceblue;border-color:#312D4C;">取消</el-button>
                                                <el-button type="primary" @click="this.handleBuyGoods(this.currentGoodsSelectId, this.currentGoodsSelectMoneyType)" style="background-color: #FCBC52;color: black;border-color:#FCBC52;">
                                                确定
                                                </el-button>
                                            </span>
                                            </template>
                                        </el-dialog>

                                    </el-header>
                                    <el-main class="el-main">
                                        <span style="color: gray;float:left;">
                                            {{ goods.description }}
                                        </span>
                                    </el-main>
                                    <el-footer style="color: #FBBC4D;float: right;">
                                        <span style="font-size: 35px;font-weight: bolder;float: left;">
                                            仅售: {{ parseFloat(goods.price) }} {{ goods.money_type == "SCoin" ? "星币" : "元" }}
                                        </span>


                                        <span>
                                            <el-button @click="this.buyGoodsConfirmDialog(goods.id, goods.money_type)" type="primary"
                                                style="float:right;color:black;background-color: #FBBC4D;margin-top: 15px;">
                                                购买
                                            </el-button>
                                        </span>
                                    </el-footer>
                                </el-container>
                            </el-container>

                        </el-col>
                    </el-row>

                </p>

            </el-tab-pane>

            <el-tab-pane label="订单" style="color: #FBBC4D;">

                <p v-for="(order) in this.user.orderList" :key="order.id" class="scrollbar-shop-order">
                    <el-row style="background-color:#312C4D;color: white;border-radius: 5px;">
                        <el-col>
                            <el-container>
                                <el-aside width="200px" style="padding-left: 5px;padding-top: 5px;">
                                    <el-image style="border-radius: 5px;" :src="orderIconUrl" fit="fill" />
                                </el-aside>
                                <el-container>
                                    <el-header>
                                        <span  style="font-size: large;float: left;padding-top: 15px;">
                                            {{ order.name }}
                                        </span>

                                            <span v-if="order.pay_status == 0" style="font-size: 15px;font-weight: normal;padding-top: 15px;margin-left: 100px;position: auto;float:right">
                                            结束时间：{{ order.end_time }}
                                            </span>

                                            <span v-if="order.pay_status == 1" style="color: #FCBC52;font-size: 15px;font-weight: normal;padding-top: 15px;margin-left: 100px;position: auto;float:right">
                                            订单已完成
                                            </span>

                                            <span v-if="order.pay_status == 2" style="color: rgb(252, 116, 190);font-size: 15px;font-weight: normal;padding-top: 15px;margin-left: 100px;position: auto;float:right">
                                            订单已超时
                                            </span>

                                            <span v-if="order.pay_status == 3" style="color: gray;font-size: 15px;font-weight: normal;padding-top: 15px;margin-left: 100px;position: auto;float:right">
                                            订单已取消
                                            </span>
                                            

                                            <el-dialog
                                                :custom-class="'my-buyGoodsConfirmDialog-class'"
                                                v-model="buyGoodsConfirmDialogVisible"
                                                width="25%"
                                            >

                                            <span style="color:azure;font-size: 15px;">确定购买 {{ this.currentGoodsSelectName }} 吗？</span>
                                            <template #footer>
                                            <span class="dialog-footer">
                                                <el-button @click="buyGoodsConfirmDialogVisible = false" style="background-color: #312D4C;color: aliceblue;border-color:#312D4C;">取消</el-button>
                                                <el-button type="primary" @click="this.handleBuyGoods(this.currentGoodsSelectId, this.currentGoodsSelectMoneyType)" style="background-color: #FCBC52;color: black;border-color:#FCBC52;">
                                                确定
                                                </el-button>
                                            </span>
                                            </template>
                                        </el-dialog>

                                    </el-header>
                                    <el-main class="el-main">
                                    <span style="color: gray;float:left;">
                                        <div style="padding-right: 5px;">
                                            订单编号：{{ order.order_id }}
                                        </div>
                                        <div>
                                            创建时间：{{ order.CreatedAt }}
                                        </div>
                                        <div style="float: left;">
                                            订单金额：{{ parseFloat(order.payment) }} {{ order.money_type == "SCoin" ? "星币 " : "元" }}
                                        </div>
                                    </span>
                                    </el-main>
                                    <el-footer style="color: #FBBC4D;float: right;">
                                        <span style="font-size: 35px;font-weight: bolder;float: left;">
                                            {{ order.pay_status == 0 ? "等待支付" : "订单结束" }}
                                        </span>


                                        <span>
                                            <el-button v-if="order.pay_status == 0" @click="this.getOrderPayUrlFromRedis(order.order_id)" type="primary"
                                                style="float:right;color:black;background-color: #FBBC4D;border-color: #FBBC4D;margin-top: 15px;">
                                                支付
                                            </el-button>
                                            <el-button v-if="order.pay_status == 0" @click="this.cancelOrder(order.order_id)" type="primary"
                                                style="float:right;color:white;background-color: #211E34;border-color: #211E34;margin-top: 15px;margin-right: 15px;">
                                                取消订单
                                            </el-button>
                                        </span>
                                    </el-footer>
                                </el-container>
                            </el-container>

                        </el-col>
                    </el-row>

                </p>

            </el-tab-pane>   
        </el-tabs>

    </div>
</template>

<script>
import { ElMessage } from 'element-plus'
import { getAllGoodsInfoAPI,buyGoodsBySCoinAPI,buyGoodsByCNYAPI } from "../api/goods.js"
import { getUserOrderListAPI,cancelOrderAPI,getOrderPayUrlFromRedisAPI } from "../api/order.js"
import QRCode from 'qrcode'
import alipayIconUrl from '../assets/alipayIcon.png';
import sCoinIconUrl from '../assets/sCoinIcon.png';
import ramIconUrl from '../assets/ramIcon.png';
import orderIconUrl from '../assets/orderIcon.png';

export default {
    name: 'shop',
    data() {
        return {
            //支付宝框显示
            rechargeDialogVisible: false,

            //图标
            alipayIconUrl: alipayIconUrl,
            sCoinIconUrl: sCoinIconUrl,
            ramIconUrl: ramIconUrl,
            orderIconUrl: orderIconUrl,

            alipayURL: "",
            currentGoodsSelectId: 0,    //记录当前选择过的商品id
            currentGoodsSelectMoneyType: '',    //记录当前选择过的商品价格类型
            currentGoodsSelectName: null, //记录当前选择过的商品名
            buyGoodsConfirmDialogVisible: false,
            QRCodeMsg: '',
            user: {
                userId: window.localStorage.getItem('user_id'),
                money: "",
                orderList : [],
            },
            goodsList: [],
        }
    },
    props: {
        user: {
            type: Object,
            required: true,
            default: () => ({})
        },
    },
    methods: {
        //通过商品类型显示不同icon
        getGoodsTypeIcon(goodsType){
            if(goodsType == 1){
                return ramIconUrl
            }else if(goodsType == 2){
                return sCoinIconUrl
            }
        },
        //打开支付对话框并从redis中获取支付链接
        getOrderPayUrlFromRedis(orderId){
            
            let order = {
                orderId: orderId
            }

            getOrderPayUrlFromRedisAPI(order).then(res => {
                console.log(res.data)
                if (res.data.data == "null"){
                    this.$message.error("暂时无法支付");
                    return
                }

                this.rechargeDialogVisible = true

                setTimeout(() => {
                this.getQRCode(res.data.data)
                }, 10);

                this.alipayURL = res.data.data
                return

            }).catch(err => {
                console.log("从redis中获取支付链接出错")
                console.log(err)
            })
        },
        //复制支付链接
        copyAlipayURL(url){
            this.$copyText(url).then(() => {
            this.$message.success("复制成功！");

        })
        },
        //取消订单
        cancelOrder(orderId){
            let order = {
                orderId: orderId,
            }
            cancelOrderAPI(order).then(res => {
                console.log(res.data)

                if(res.data.code == 214){
                    this.$message.success("取消订单成功");
                    this.getUserOrderList()
                    return
                }else if(res.data.code == 426){
                    this.$message.error("订单已超时，取消订单失败");
                    //this.getUserOrderList()
                    return
                }
                return

            }).catch(err => {
                console.log("取消订单出错")
                console.log(err)
            })
        },
        //获取二维码
        getQRCode(url) {
                let opts = {
                    errorCorrectionLevel: "L",//容错级别
                    type: "image/png",//生成的二维码类型
                    quality: 0.3,//二维码质量
                    margin: 2,//二维码留白边距
                    width: 200,//宽
                    height: 180,//高
                    text: "充值",//二维码内容
                    color: {
                        dark: "#333333",//前景色
                        light: "#fff"//背景色
                    }
                };
                this.QRCodeMsg = url; //生成的二维码为URL地址
                let msg = document.getElementById("QRCode_header");
                // 将获取到的数据（val）画到msg（canvas）上
                QRCode.toCanvas(msg, this.QRCodeMsg, opts, function (error) {
                    console.log(error)
                });
            },
        //弹出确认购买框
        buyGoodsConfirmDialog(goodsId,goodsMoneyType){
            this.buyGoodsConfirmDialogVisible = true
            //记录选择的商品id
            this.currentGoodsSelectId = goodsId
            //记录选择的商品价格类型
            this.currentGoodsSelectMoneyType = goodsMoneyType

            //通过id取商品名
            for(let i = 0; i < this.goodsList.length; i++){
                if(this.goodsList[i].id == goodsId){
                    this.currentGoodsSelectName = this.goodsList[i].goods_name
                    break
                }
            }
        },
        //获取全部商品数据
        getGoodsList() {
            getAllGoodsInfoAPI().then(res => {
                //解析
                let tempGoodsList = JSON.parse(res.data.data)
                this.goodsList = tempGoodsList

            }).catch(err => {
                console.log("获取全部商品数据出错")
                console.log(err)
            })
        },
        //获取用户最新10条订单数据
        getUserOrderList() {

            let user = {
                userId: this.user.userId
            }
            getUserOrderListAPI(user).then(res => {
                //解析
                let tempUserOrderList = JSON.parse(res.data.data)

                //处理
                tempUserOrderList.forEach(element => {
                    element.end_time = element.end_time.replace("T", " ").replace("+08:00", "")
                    element.CreatedAt = element.CreatedAt.replace("T", " ").replace("+08:00", "")
                })
                this.user.orderList = tempUserOrderList
                console.log(this.user.orderList)

            }).catch(err => {
                console.log("获取用户最新10条订单数据出错")
                console.log(err)
            })
        },
        //购买分发
        handleBuyGoods(goodsId,goodsMoneyType){
            if(goodsMoneyType == 'SCoin'){
                console.log("SCoin")
                this.buyGoodsBySCoin(goodsId)
            }
            else if(goodsMoneyType == 'CNY'){
                console.log("CNY")
                this.buyGoodsByCNY(goodsId)
            }
            return
        },
        //CNY购买商品
        buyGoodsByCNY(goodsId){
            this.buyGoodsConfirmDialogVisible = false,
            // 购买商品
            console.log("buyGoodsByCNY")

            let data = {
                userId: this.user.userId,
                goodsId: goodsId,
            }

            buyGoodsByCNYAPI(data).then(res => {
                console.log(res.data)
                //弹出QR二维码显示框
                this.rechargeDialogVisible = true
                //延迟加载二维码，不然会不显示
                const self = this
                setTimeout(function() {
                    self.getQRCode(res.data)
                }, 10)
                //加载链接
                this.alipayURL = res.data

            }).catch(err => {
                console.log("购买商品出错")
                console.log(err)
            })
        },
        //星币购买商品
        buyGoodsBySCoin(goodsId){
            this.buyGoodsConfirmDialogVisible = false,
            // 购买商品
            console.log("buyGoodsBySCoin")

            let data = {
                userId: this.user.userId,
                goodsId: goodsId,
            }
            buyGoodsBySCoinAPI(data).then(res => {
                console.log(res.data)
                if(res.data.code == 416){
                    ElMessage.error("购买失败，星币余额不足")
                }else if(res.data.code == 418){
                    ElMessage.error("创建订单失败")
                }else if(res.data.code == 419){
                    ElMessage.error("更改订单失败")
                }else if(res.data.code == 415){
                    ElMessage.error("购买出现异常（暂不支持CNY）")
                }else if(res.data.code == 421){
                    ElMessage.error("存货不足，无法购买")
                }else if(res.data.code == 210){
                    ElMessage.success("购买成功，2秒后自动重新加载页面")

                    //购买成功后刷新网页
                    const timer = setTimeout(function() {
                        location.reload(); // 2秒之后，再执行这段代码
                        clearTimeout(timer);
                    }, 2000);

                }


            }).catch(err => {
                console.log("购买商品出错")
                console.log(err)
            })
            
        }

    },
    mounted(){
        this.getGoodsList()
        this.getUserOrderList()
    }
}
</script>

<style>

/*滚动条样式*/
textarea::-webkit-scrollbar {
    width: 4px;    
}
textarea::-webkit-scrollbar-thumb {
    border-radius: 10px;
    box-shadow: inset 0 0 5px rgba(0,0,0,0.2);
    background: rgba(0,0,0,0.2);
}
textarea::-webkit-scrollbar-track {
    box-shadow: inset 0 0 5px rgba(0,0,0,0.2);
    border-radius: 0;
    background: rgba(0,0,0,0.1);

}

.el-main::-webkit-scrollbar {
  width: 6px;    
  height: 3px;
}
::-webkit-scrollbar-corner{
  background-color: transparent;
}
::-webkit-scrollbar-thumb {
  border-radius: 5px;
  background: #2f2952;
}
::-webkit-scrollbar-track {
  border-radius: 0;
  background: #211E34;
}

.el-tabs__nav-wrap::after {
    display: none;
}

.el-tabs__item.is-active {
    color: #FCBC52;
}

.shop-information {
    background-color: #211E34;
    height: 100%;
}

.border-shop {
    background-color: #5945db;
    height: 100%;
}

.my-buyGoodsPaymentDialog-class .el-dialog__header {
    background-color: #201C30;
    padding-bottom: 10px;
    margin-right: 0px;
    --el-bg-color: #201c30 !important;
}

.my-buyGoodsPaymentDialog-class .el-dialog__body {
    padding: calc(var(--el-dialog-padding-primary) + 10px) var(--el-dialog-padding-primary);
    color: var(--el-text-color-regular);
    font-size: var(--el-dialog-content-font-size);
    background-color: #201C30;
    height: 285px;
}

.my-buyGoodsPaymentDialog-class .el-dialog__footer {
    padding: var(--el-dialog-padding-primary);
    padding-top: 10px;
    text-align: right;
    box-sizing: border-box;
    background-color: #201C30;
}

.my-buyGoodsPaymentDialog-class .el-dialog__headerbtn {
    position: absolute;
    top: 6px;
    right: 0;
    padding: 0;
    width: 54px;
    height: 54px;
    background: 0 0;
    outline: 0;
    cursor: pointer;
    font-size: var(--el-message-close-size,16px);
    background-color: #201C30;
}

.my-buyGoodsConfirmDialog-class .el-dialog__header {
    background-color: #201C30;
    padding-bottom: 10px;
    margin-right: 0px;
    --el-bg-color: #201c30 !important;
}

.my-buyGoodsConfirmDialog-class .el-dialog__body {
    padding: calc(var(--el-dialog-padding-primary) + 10px) var(--el-dialog-padding-primary);
    color: var(--el-text-color-regular);
    font-size: var(--el-dialog-content-font-size);
    background-color: #201C30;
}

.my-buyGoodsConfirmDialog-class .el-dialog__footer {
    padding: var(--el-dialog-padding-primary);
    padding-top: 10px;
    text-align: right;
    box-sizing: border-box;
    background-color: #201C30;
}

.my-buyGoodsConfirmDialog-class .el-dialog__headerbtn {
    position: absolute;
    top: 6px;
    right: 0;
    padding: 0;
    width: 54px;
    height: 54px;
    background: 0 0;
    outline: 0;
    cursor: pointer;
    font-size: var(--el-message-close-size,16px);
    background-color: #201C30;
}

</style>

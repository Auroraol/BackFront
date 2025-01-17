/**
 * @Author: FxShadow
 * @Description:
 * @Date: 2023/05/09 16:18
 */

package util

import (
	"fmt"
	"github.com/gin-gonic/gin"
	"github.com/smartwalle/alipay/v3"
	"log"
)

var (
	client          *alipay.Client
	alipayNotifyURL = ""
	alipayReturnURL = ""
)

func InitAlipay(appId string, privateKey string, appPublicCertPath string, alipayRootCertPath string, alipayPublicCertPath string, ConfigAlipayNotifyURL string, ConfigAlipayReturnURL string) {
	fmt.Println(appId, privateKey)
	client, _ = alipay.New(appId, privateKey, false)

	err := client.LoadAppPublicCertFromFile(appPublicCertPath)
	if err != nil {
		log.Println("加载应用公钥证书出错")
		return
	} // 加载应用公钥证书
	err2 := client.LoadAliPayRootCertFromFile(alipayRootCertPath)
	if err2 != nil {
		log.Println("加载支付宝根证书出错")
		return
	} // 加载支付宝根证书
	err3 := client.LoadAliPayPublicCertFromFile(alipayPublicCertPath)
	if err3 != nil {
		log.Println("加载支付宝公钥证书出错")
		return
	} // 加载支付宝公钥证书

	//加载支付载体
	alipayNotifyURL = ConfigAlipayNotifyURL
	alipayReturnURL = ConfigAlipayReturnURL
}

func Alipay(c *gin.Context, outTradeNo string, subject string, totalAmount string) (payUrl string) {

	var p = alipay.TradePagePay{}
	// 支付成功后，支付宝会发送一个POST消息到该地址
	p.NotifyURL = alipayNotifyURL
	// 支付成功后，浏览器将会重定向到该 URL
	p.ReturnURL = alipayReturnURL
	//载入订单号
	p.OutTradeNo = outTradeNo
	//限时五分钟
	p.TimeoutExpress = "5m"
	//标题
	p.Subject = subject
	//金额
	p.TotalAmount = totalAmount
	p.ProductCode = "FAST_INSTANT_TRADE_PAY"

	var url, err = client.TradePagePay(p)
	if err != nil {
		fmt.Println(err)
	}

	payUrl = url.String()
	return
}

// AlipayCancel 取消支付宝订单
func AlipayCancel(outTradeNo string) (result bool) {
	var pay = alipay.TradeCancel{OutTradeNo: outTradeNo}

	tradeCancel, err := client.TradeCancel(pay)
	if err != nil {
		return false
	}
	log.Printf("返回值: %+v\n", tradeCancel)

	if tradeCancel.Content.Code == "10000" && tradeCancel.Content.Msg == "Success" {
		return true
	}
	return false
}

func AlipayNotify(c *gin.Context) (result bool) {
	fmt.Print("进入校验支付宝回调...")

	req := c.Request
	err := req.ParseForm()
	if err != nil {
		return
	}

	//验签
	ok, err := client.VerifySign(req.Form)
	fmt.Println(req.Form)

	status := req.Form.Get("trade_status")
	if status != "TRADE_SUCCESS" && ok != true {
		fmt.Println("验签失败")
		return false
	}

	fmt.Println("验签成功")
	return true

}

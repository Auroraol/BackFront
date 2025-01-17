/**
 * @Author: FxShadow
 * @Description:
 * @Date: 2023/06/26 18:20
 */

package util

import (
	"fmt"
	"gopkg.in/gomail.v2"
	"log"
	"strconv"
)

var (
	senderEmail   = ""
	SMTPServer    = ""
	emailSMTPPort int
	emailSMTPKey  = ""
)

func InitEmail(ConfigSenderEmail string, ConfigSMTPServer string, ConfigEmailSMTPPort string, ConfigEmailSMTPKey string) {
	senderEmail = ConfigSenderEmail
	SMTPServer = ConfigSMTPServer
	emailSMTPPort, _ = strconv.Atoi(ConfigEmailSMTPPort)
	emailSMTPKey = ConfigEmailSMTPKey
	fmt.Println("初始化成功中...", ConfigSenderEmail, ConfigSMTPServer, ConfigEmailSMTPPort, ConfigEmailSMTPKey)

}

func SendEmail(title string, body string, mailTo ...string) (result bool) {
	fmt.Print(":::::::::::", SMTPServer, emailSMTPPort, senderEmail, emailSMTPKey)
	message := gomail.NewMessage()
	message.SetHeader("From", senderEmail) //发送者腾讯企业邮箱账号
	message.SetHeader("To", mailTo...)     //接收者邮箱列表

	message.SetHeader("Subject", title) //邮件标题
	message.SetBody("text/html", body)  //邮件内容

	d := gomail.NewDialer(SMTPServer, emailSMTPPort, senderEmail, emailSMTPKey)
	if err := d.DialAndSend(message); err != nil {
		log.Println("send mail failed", err)
		return false
	}

	return true
}

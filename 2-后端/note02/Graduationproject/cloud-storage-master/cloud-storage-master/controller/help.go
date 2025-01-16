package c

import (
	"encoding/json"
	"errors"
	"gitee.com/phpdi/cloud-storage/entitys"
	"gitee.com/phpdi/cloud-storage/s"
	"gitee.com/phpdi/cloud-storage/utils"
	"github.com/gin-gonic/gin"
	"net/http"
	"net/url"
)

var StopFunc = errors.New("user stop fun")

//接口返回
type Response struct {
	Code int         //错误码
	Msg  string      //错误消息
	Data interface{} //输出数据
}

//控制层辅助函数

//表单数据转换为结构体数据
func parseForm(this *gin.Context, req interface{}, params ...interface{}) {

	var (
		err           error
		requiredFiled []string
		fieldTag      map[string]string
	)
	//参数输入
	for _, v := range params {
		switch tmp := v.(type) {
		case string:
			requiredFiled = append(requiredFiled, tmp)
		case map[string]string:
			fieldTag = tmp
		}
	}

	json.NewDecoder(this.Request.Body).Decode(req)
	//验证表单数据
	valid := utils.NewValidate().Require(requiredFiled...).FieldTag(fieldTag)
	if err = valid.Valid(req); err != nil {
		checkErr(this, err)
	}
}

//检查错误
func checkErr(this *gin.Context, err error) {
	if err != nil {
		this.AbortWithStatusJSON(http.StatusOK, entitys.Response{Code: 1, Msg: err.Error()})
		panic(StopFunc)
	}
}

//输出结果
func result(this *gin.Context, obj ...interface{}) {
	var (
		resp entitys.Response
		err  error
	)

	for _, v := range obj {
		if v == nil {
			continue
		}
		switch tmp := v.(type) {
		case error:
			err = tmp
		default:
			resp.Data = tmp
		}
	}

	//检查错误
	checkErr(this, err)

	//输出
	this.AbortWithStatusJSON(http.StatusOK, resp)
	panic(StopFunc)

}

//文件下载
func downloadStream(this *gin.Context, data []byte, fileName string) {
	this.Header("Content-Disposition", "attachment; filename="+url.PathEscape(fileName))
	this.Header("Content-Description", "File Transfer")
	this.Header("Content-Type", "application/octet-stream")
	this.Header("Content-Transfer-Encoding", "binary")
	this.Header("Expires", "0")
	this.Header("Cache-Control", "must-revalidate")
	this.Header("Pragma", "public")

	this.Writer.Write(data)

}

//登录用户
func authUser(this *gin.Context) (user entitys.User, err error) {
	token := this.Request.Header.Get("Token")
	user, err = s.AuthService.AuthToken(token)
	return
}

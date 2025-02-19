package c

import (
	"bufio"
	"bytes"
	"encoding/json"
	"fmt"
	"github.com/gin-gonic/gin"
	"io/ioutil"
	"net"
	"net/http"
	"net/url"
)

type testResponseWriter struct{}

func (f *testResponseWriter) Hijack() (net.Conn, *bufio.ReadWriter, error) {
	panic("implement me")
}

func (f *testResponseWriter) Flush() {
	panic("implement me")
}

func (f *testResponseWriter) CloseNotify() <-chan bool {
	panic("implement me")
}

func (f *testResponseWriter) Status() int {
	panic("implement me")
}

func (f *testResponseWriter) Size() int {
	panic("implement me")
}

func (f *testResponseWriter) WriteString(string) (int, error) {
	panic("implement me")
}

func (f *testResponseWriter) Written() bool {
	panic("implement me")
}

func (f *testResponseWriter) WriteHeaderNow() {
	panic("implement me")
}

func (f *testResponseWriter) Pusher() http.Pusher {
	panic("implement me")
}

func (f *testResponseWriter) Header() http.Header {
	return http.Header{}
}
func (f *testResponseWriter) Write(b []byte) (int, error) {

	v := make(map[string]interface{})
	json.Unmarshal(b, &v)

	tmp, _ := json.MarshalIndent(v, "", "     ")
	fmt.Println(string(tmp))
	return 0, nil
}
func (f *testResponseWriter) WriteHeader(n int) {}

var ctx *gin.Context

func init() {
	ctx = makeCtx()
}

//构造控制器
func makeCtx() *gin.Context {

	this := new(gin.Context)

	this.Request = &http.Request{URL: &url.URL{Scheme: "http", Host: "localhost", Path: "/"}, Header: http.Header{}}

	this.Request.Header.Set("Content-Type", "application/x-www-form-urlencoded")

	this.Writer = &testResponseWriter{}
	return this
}

//设置post请求参数
func setPost(this *gin.Context, formData interface{}) *gin.Context {
	//post
	if formData != nil {
		this.Request.Method = "POST"
		var bu bytes.Buffer

		json.NewEncoder(&bu).Encode(formData)

		this.Request.Body = ioutil.NopCloser(&bu)

	}

	return this

}

//设置url请求参数
func setGet(this *gin.Context, querys ...string) *gin.Context {
	if len(querys) > 0 && len(querys)%2 == 0 {
		for i := 0; i < len(querys); i++ {
			k := querys[i]
			v := querys[i+1]
			this.Request.Form.Set(k, v)
			i++
		}

	}

	return this
}

func stopPanic() {
	//用户主动推出
	if err := recover(); err == StopFunc {
		return
	}

}

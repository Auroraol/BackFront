package main

import (
	"bufio"
	"bytes"
	"encoding/json"
	"fmt"
	"gitee.com/phpdi/cloud-storage/entitys"
	"io"
	"io/ioutil"
	"net/http"
	"os"
	"os/exec"
	"path/filepath"
	"strconv"
)

func multipartUpload(filename string, targetURL string, chunkSize int) error {
	f, err := os.Open(filename)
	if err != nil {
		fmt.Println(err)
		return err
	}
	defer f.Close()

	bfRd := bufio.NewReader(f)
	index := 0

	ch := make(chan int, 1)
	buf := make([]byte, chunkSize) //每次读取chunkSize大小的内容
	for {
		n, err := bfRd.Read(buf)
		if n <= 0 {
			break
		}
		index++

		bufCopied := make([]byte, 5*1048576)
		copy(bufCopied, buf)

		go func(b []byte, curIdx int) {
			fmt.Printf("upload_size: %d\n", len(b))

			resp, err := http.Post(
				targetURL+"&Index="+strconv.Itoa(curIdx),
				"multipart/form-data",
				bytes.NewReader(b))
			if err != nil {
				fmt.Println(err)
			}

			body, _ := ioutil.ReadAll(resp.Body)
			fmt.Println(string(body))
			resp.Body.Close()

			ch <- curIdx
		}(bufCopied[:n], index)

		//遇到任何错误立即返回，并忽略 EOF 错误信息
		if err != nil {
			if err == io.EOF {
				break
			} else {
				fmt.Println(err.Error())
			}
		}
	}

	for idx := 0; idx < index; idx++ {
		select {
		case res := <-ch:
			fmt.Println(res)
		}
	}

	return nil
}

func main() {
	//fmt.Println(getFileSize("/home/yu/下载/go1.13.8.linux-amd64.tar.gz"))
	token := "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJVc2VySWQiOjEsImV4cCI6MTU4MzIwNTkyOH0.8Cq1JiA3nCAexwVZRDYZKloNEuJSNs5lpTh3w2vbVH4"

	uploadID := "A32480FC-5B6A-00B1-835B-E61F2A146D2E"
	chunkSize := 5242880

	// 3. 请求分块上传接口
	// 要上传的文件路径
	filepath := "/home/yu/下载/go1.13.8.linux-amd64.tar.gz"

	tURL := fmt.Sprintf("http://localhost:8123/blockupload?Token=%s&UploadId=%s", token, uploadID)
	multipartUpload(filepath, tURL, chunkSize)

}

//合并文件
func mergeAllPartFile(partFileStorePath, fileStorePath string) (bool, error) {
	var cmd *exec.Cmd
	cmd = exec.Command(entitys.MyAppConfig.MergeAllShell, partFileStorePath, fileStorePath)

	if _, err := cmd.Output(); err != nil {
		fmt.Println(err)
		return false, err
	}
	fmt.Println(fileStorePath, " has been merge complete")
	return true, nil
}

//json方式打印结构体
func JsonPrint(obj interface{}) {
	tmp, _ := json.MarshalIndent(obj, "", "     ")
	fmt.Println(string(tmp))
}

func getFileSize(filename string) int64 {
	var result int64
	filepath.Walk(filename, func(path string, f os.FileInfo, err error) error {
		result = f.Size()
		return nil
	})
	return result
}

/**
 * @Author: FxShadow
 * @Description:
 * @Date: 2023/03/15 22:28
 */

package main

import (
	"os"
	"system-admin/starlight-cloud-admin/config"
)

func main() {
	//初始化配置
	config.InitSystem()

	os.Exit(1)
}

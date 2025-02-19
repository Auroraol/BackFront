/**
 * @Author: FxShadow
 * @Description:	此为改良过后的md5加密器
 * @Date: 2023/03/22 11:04
 */

package util

import (
	"crypto/md5"
	"encoding/hex"
)

const Md5Key = "starLight"

func MD5Generate(data string) (md5String string) {
	md5New := md5.New()
	md5New.Write([]byte(data + Md5Key))
	md5String = hex.EncodeToString(md5New.Sum(nil))

	return
}

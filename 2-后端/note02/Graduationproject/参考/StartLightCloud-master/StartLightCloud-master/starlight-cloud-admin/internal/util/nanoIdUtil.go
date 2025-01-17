/**
 * @Author: FxShadow
 * @Description:此为经过改造后的nanoID生成器
 * @Date: 2023/03/21 21:40
 */

package util

import (
	"fmt"
	gonanoid "github.com/matoous/go-nanoid"
)

const RANDOM_VALUE = "1234567890"
const SIZE = 16

func NanoIdGenerate() (id string) {
	id, err := gonanoid.Generate(RANDOM_VALUE, SIZE)
	if err != nil {
		fmt.Println(err)
	}
	return id
}

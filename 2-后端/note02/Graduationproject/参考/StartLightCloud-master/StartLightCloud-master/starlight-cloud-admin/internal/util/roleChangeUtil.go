/**
 * @Author: FxShadow
 * @Description: 角色权限等级转换器，自动将数字等级转换为角色字符串
 * @Date: 2023/04/04 16:14
 */

package util

func LevelChangeRole(level int) (role string) {
	if level == 0 {
		role = "user"
		return
	} else if level == 1 {
		role = "admin"
	}
	return
}

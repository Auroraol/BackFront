/*
 * # 此文件版权属于ASO.DESIGN
 * # 文件：prefix.go  项目：aso-zero
 * # 作用：
 * # 当前修改时间：2021年07月18日 01:05:05
 * # 上次修改时间：2021年07月16日 15:48:47
 * # 作者：thunur
 * # 此文件不可非法传播、倒卖、共享，否则我们将追究相应的法律责任。
 * # 您如果已获得ASO.DESIGN授权可在原有基础上进行修改使用
 * # 如果您还没获得授权请联系我们 thunur@qq.com
 * # Copyright (c) 2021 aso.design
 */

package prefix

const (

	// CacheSysUserTokenPrefix 用户token缓存
	CacheSysUserTokenPrefix = "token:cache:id:" // 用户token令牌缓存key
	// CacheSysMenuIdPrefix 菜单主键缓存
	CacheSysMenuIdPrefix = "cache:sysMenu:id:" // 菜单缓存key
	// CacheSysMenuUidPrefix 后台用户菜单缓存
	CacheSysMenuUidPrefix = "cache:sysMenu:uid:" // uid缓存菜单key
	// CacheSysMenuAllPrefix 后台所有菜单缓存
	CacheSysMenuAllPrefix = "cache:sysMenu:all" // 所有菜单缓存key
	// CacheSysPermsUidPrefix 用户按钮权限uid缓存
	CacheSysPermsUidPrefix = "cache:sysPerms:uid:" // uid按钮权限缓存key
	// CacheSysDepartmentIdPrefix 部门id缓存
	CacheSysDepartmentIdPrefix = "cache:sysDepartment:id:"
	// CacheSysDepartmentAllPrefix 后台所有部门缓存
	CacheSysDepartmentAllPrefix = "cache:sysDepartment:all"

	// CacheSysUserMenuAllPrefix 用户所有菜单
	CacheSysUserMenuAllPrefix = "cache:sysMenu:User:all" // 所有菜单缓存key
	// CacheSysUserPermsAllPrefix 用户所有按钮权限
	CacheSysUserPermsAllPrefix = "cache:sysPerms:User:all" // 所有按钮权限缓存key

	// CacheSysRoleIdPrefix 角色主键缓存key
	CacheSysRoleIdPrefix = "cache:sysRole:id:"
	// CacheSysRoleAllPrefix 角色列表缓存key
	CacheSysRoleAllPrefix = "cache:sysRole:all"
	// CacheSysRoleMenuIdPrefix 角色菜单关联表缓存key
	CacheSysRoleMenuIdPrefix = "cache:sysRoleMenu:id:"

	CacheWechatConfigIdPrefix = "cache:wechatConfig:type:"

	CacheWechatMaterialItemKeyPrefix = "cache:wechatMaterialItem:key:"

	// 静态资源管理

	// CacheAppTypeIdPrefix ID主键缓存
	CacheAppTypeIdPrefix = "cache:appType:id:"
	// CacheAppTypeAllPrefix 全部列表
	CacheAppTypeAllPrefix = "cache:appType:all"

	CacheAppItemIdPrefix = "cache:appItem:id:"

	// CacheWechatMenuIdPrefix 菜单主键缓存key
	CacheWechatMenuIdPrefix = "cache:wechatMenu:id:"
	// CacheWechatMenuListPrefix 菜单列表缓存key
	CacheWechatMenuListPrefix = "cache:wechatMenu:all"
	// CacheWechatMenuPidPrefix 菜单上级菜单缓存key
	CacheWechatMenuPidPrefix = "cache:wechatMenu:pid:"

	CacheWechatKeyReplayIdPrefix = "cache:wechatKeyReplay:id:"
)

/**
 * @Author: FxShadow
 * @Description:
 * @Date: 2023/03/16 16:48
 */

package util

// 状态码
const (
	UserLoginOK               = iota + 200 //用户登录成功
	UserRegisterOK            = iota + 200 //用户注册成功
	FileUploadOK              = iota + 200 //文件上传成功
	FileUpdateOK              = iota + 200 //更新数据库文件成功
	GetFileListOK             = iota + 200 //获取文件列表成功
	DeleteFileOK              = iota + 200 //删除文件成功
	CreateFolderOK            = iota + 200 //创建文件夹成功
	GetSearchFileListOK       = iota + 200 //获取查询文件列表成功
	GetUserInfoOK             = iota + 200 //获取用户信息成功
	GetGoodsInfoOK            = iota + 200 //获取商品信息成功
	PaymentWithSCoinOK        = iota + 200 //购买商品成功
	VerifyPaymentWithSCoinOK  = iota + 200 //充值星币验签成功
	RechargeSCoinOK           = iota + 200 //充值星币成功
	GetUserOrderListOK        = iota + 200 //获取用户所有订单成功
	CancelOrderOK             = iota + 200 //取消订单成功
	GetOrderPayUrlFromRedisOK = iota + 200 //从redis中获取订单的支付链接成功
	UploadProfileOK           = iota + 200 //上传用户头像成功
	GetProfileOK              = iota + 200 //获取用户头像成功
	RedisDoEmailCodeOK        = iota + 200 //验证码加入redis成功
	LimitRequestCountOK       = iota + 200 //接口频率限制通过
)

// 失败状态码
const (
	UserLoginByUsernameAndPasswordFailed = iota + 400 //用户登录失败，账号或密码错误
	UserRegisterUsernameExisted          = iota + 400 //用户注册失败，用户名已存在
	UserLoginOvertime                    = iota + 400 //账号登录频繁，请稍后再试
	UserRegisterUnusual                  = iota + 400 //用户注册异常
	UserRegisterByPhoneNumberFailed      = iota + 400 //用户注册失败，该手机号已被注册
	TokenInvalid                         = iota + 400 //token失效
	FileUploadFailed                     = iota + 400 //文件上传失败
	FileUpdateFailed                     = iota + 400 //更新数据库文件失败
	FileUploadUnusual                    = iota + 400 //上传文件异常
	GetFileListFailed                    = iota + 400 //获取文件列表失败
	DeleteFileFailed                     = iota + 400 //删除文件失败
	CreateFolderFailed                   = iota + 400 //创建文件夹成功
	GetSearchFileListFailed              = iota + 400 //获取查询文件列表成功
	GetUserInfoFailed                    = iota + 400 //获取用户信息失败
	GetGoodsInfoFailed                   = iota + 400 //获取商品信息失败
	GetGoodsInfoUnusual                  = iota + 400 //获取商品信息异常
	PaymentWithSCoinMoneyNotEnoughFailed = iota + 400 //购买失败，星币余额不足
	UserLoginUnusual                     = iota + 400 //账号登录异常
	CreateOrderFailed                    = iota + 400 //创建订单失败
	UpdateOrderFailed                    = iota + 400 //更改订单失败
	VerifyPaymentWithSCoinUnusual        = iota + 400 //充值星币验签异常
	StorageCountNotEnough                = iota + 400 //仓库货物不足
	ReduceStorageCountFailed             = iota + 400 //减少仓库货物失败
	GetUserOrderListFailed               = iota + 400 //获取用户所有订单失败
	CancelOrderFailed                    = iota + 400 //取消订单失败
	GetOrderPayUrlFromRedisFailed        = iota + 400 //从redis中获取订单的支付链接失败
	OrderoverTimeCancelOrderFailed       = iota + 400 //取消订单失败，订单已超时
	GetProfileVersionFailed              = iota + 400 //获取用户头像版本失败
	UploadProfileUnusual                 = iota + 400 //上传用户头像异常
	GetProfileFailed                     = iota + 400 //获取用户头像失败
	UsernameExisted                      = iota + 400 //用户名已存在
	RedisDoEmailCodeFailed               = iota + 400 //验证码加入redis失败
	GetRedisEmailCodeUnusual             = iota + 400 //获取邮箱注册验证码异常
	RedisEmailCodeExisted                = iota + 400 //邮箱注册验证码已存在
	VerifyCodeError                      = iota + 400 //验证码错误
	UserRegisterEmailExisted             = iota + 400 //用户注册失败，邮箱已存在
	LimitRequestCountUnusual             = iota + 400 //接口频率限制异常
	LimitRequestCountMAX                 = iota + 400 //接口频率限制已超出
)

/**
 * @Author: FxShadow
 * @Description:
 * @Date: 2023/03/16 13:25
 */

package service

import (
	"fmt"
	"github.com/gin-gonic/gin"
	"github.com/gomodule/redigo/redis"
	"math/rand"
	"strconv"
	"system-admin/starlight-cloud-admin/internal/dao"
	"system-admin/starlight-cloud-admin/internal/model/common"
	"system-admin/starlight-cloud-admin/internal/model/request"
	"system-admin/starlight-cloud-admin/internal/model/response"
	"system-admin/starlight-cloud-admin/internal/util"
	"time"
)

type UserService struct{}

func GetUserService() (userService UserService) {
	return userService
}

// UserLogin todo
// UserLogin 用户登录
func (us *UserService) UserLogin(c *gin.Context) (message response.UserLoginResponse) {
	var userLoginRequest request.UserLoginRequest

	userLoginRequest.Username = c.Query("username")
	userLoginRequest.Password = c.Query("password")

	userLoginRequest.IP = c.ClientIP()

	////限制同一ip只能5秒内登录一次
	//conn := util.GetRedis()
	//defer conn.Close()
	//
	////查询redis是否有该IP
	//redisIP, _ := redis.String(conn.Do("get", "ip"))
	//
	////如果查不到redisIP就添加进去
	//if redisIP == "" {
	//	OK, _ := conn.Do("setex", "ip", "5", userLoginRequest.IP)
	//
	//	if OK != "OK" {
	//		fmt.Println("redisIPUnusual")
	//		message = response.UserLoginResponse{
	//			Code:      util.UserLoginUnusual,
	//			UserId:    "null",
	//			Token:     "null",
	//			Role:      "null",
	//			LastLogin: "null",
	//		}
	//		return
	//	}
	//
	//} else if redisIP == userLoginRequest.IP {
	//	fmt.Println("redisIP overTime")
	//	message = response.UserLoginResponse{
	//		Code:      util.UserLoginOvertime,
	//		UserId:    "null",
	//		Token:     "null",
	//		Role:      "null",
	//		LastLogin: "null",
	//	}
	//	return
	//}

	//校验用户名
	ok := util.CheckInfo("username", userLoginRequest.Username)
	if ok == false {
		message = response.UserLoginResponse{
			Code:      util.UserLoginByUsernameAndPasswordFailed,
			UserId:    "null",
			Token:     "null",
			Role:      "null",
			LastLogin: "null",
		}
		return
	}

	//校验密码
	ok = util.CheckInfo("password", userLoginRequest.Password)
	if ok == false {
		message = response.UserLoginResponse{
			Code:      util.UserLoginByUsernameAndPasswordFailed,
			UserId:    "null",
			Token:     "null",
			Role:      "null",
			LastLogin: "null",
		}
		return
	}

	user := &common.User{
		Username: userLoginRequest.Username,
		Password: userLoginRequest.Password,
	}

	//加密用户密码以校验md5值
	user.Password = util.MD5Generate(user.Password)

	var userDao dao.UserDao
	//校验用户名和密码是否正确
	result := userDao.QueryByUsernameAndPassword(user)

	//如果查询到则允许通过并颁发token
	if result > 0 {

		//记录最后一次登录时间（最后的日期里为格式，不是实际时间）
		lastLogin := time.Now().Format("2006-01-02 15:04:05")
		userDao.Update(*user, "last_login", lastLogin)

		//获取用户的id
		result = int64(userDao.QueryUserId(user))

		//获取用户的role
		level := userDao.QueryLevelByUserId(user)

		message = response.UserLoginResponse{
			Code:      util.UserLoginOK,
			UserId:    user.UserId,
			Token:     util.EncryptionJWT(userLoginRequest.Username),
			Role:      strconv.Itoa(level),
			LastLogin: lastLogin,
		}
	} else {
		message = response.UserLoginResponse{
			Code:      util.UserLoginByUsernameAndPasswordFailed,
			UserId:    "null",
			Token:     "null",
			Role:      "null",
			LastLogin: "null",
		}
	}

	return
}

// UserRegister 用户注册
func (us *UserService) UserRegister(c *gin.Context) (message response.MessageResponse) {
	var userRegisterRequest request.UserRegisterRequest
	userRegisterRequest.Username = c.Query("username")
	userRegisterRequest.Password = c.Query("password")
	userRegisterRequest.Email = c.Query("email")
	userRegisterRequest.VerifyCode = c.Query("emailVerifyCode")

	user := &common.User{
		Username: userRegisterRequest.Username,
		Password: userRegisterRequest.Password,
		Email:    userRegisterRequest.Email,
	}

	//校验验证码是否符合
	conn := util.GetRedis()
	defer conn.Close()

	key := "r:ec:" + userRegisterRequest.Email + ":" + userRegisterRequest.Username
	fmt.Println(key)

	redisVerifyCode, _ := redis.String(conn.Do("get", key))

	if redisVerifyCode != userRegisterRequest.VerifyCode {
		message = response.MessageResponse{
			Code: util.VerifyCodeError,
			Msg:  "验证码错误",
			Data: "null",
		}
		return
	}

	var userDao dao.UserDao
	//查询用户是否存在
	result := userDao.QueryByData(*user, "username", user.Username)

	//如果查询到则不通过
	if result > 0 {
		message = response.MessageResponse{
			Code: util.UserRegisterUsernameExisted,
			Msg:  "注册失败，用户名已存在",
			Data: "null",
		}
		return
	}

	//查询邮箱是否存在
	result = userDao.QueryByData(*user, "email", user.Email)

	//如果查询到则不通过
	if result > 0 {
		message = response.MessageResponse{
			Code: util.UserRegisterEmailExisted,
			Msg:  "注册失败，邮箱已存在",
			Data: "null",
		}
		return
	}

	//分配用户id
	user.UserId = util.NanoIdGenerate()
	//加密用户密码
	user.Password = util.MD5Generate(user.Password)
	//初始化用户名字（默认为用户账号）
	user.DisplayName = user.Username
	//分配用户云盘实际存储RAM(默认5GB)
	user.RAM = "5368709120"
	//分配用户云盘总存储RAM(默认5GB的字节数)
	user.TotalRAM = "5368709120"
	//分配用户云盘总存储RAM(默认5GB)
	user.DisplayRAM = 5
	//初始化用户头像版本
	user.ProfileVersion = "0"
	//分配邮箱
	user.Email = userRegisterRequest.Email
	//分配星币
	user.Money = 0
	//初始化可用星币
	user.CanMoney = 0
	//初始化冻结星币
	user.FrozenMoney = 0

	//创建角色
	result = userDao.Insert(*user)
	if result > 0 {
		message = response.MessageResponse{
			Code: util.UserRegisterOK,
			Msg:  "注册成功",
			Data: "null",
		}
		return
	} else {
		message = response.MessageResponse{
			Code: util.UserRegisterUnusual,
			Msg:  "注册异常",
			Data: "null",
		}
	}

	return
}

// GetUserInfo 获取用户信息
func (us *UserService) GetUserInfo(c *gin.Context) (message response.UserInfoResponse) {
	userId := c.Query("user_id")

	var user common.User
	user.UserId = userId

	var userDao dao.UserDao
	row := userDao.QueryInfoByUserId(&user)

	if row == 0 {
		message = response.UserInfoResponse{
			Code:        util.GetUserInfoFailed,
			Msg:         "获取用户信息失败",
			UserId:      userId,
			RAM:         "null",
			TotalRAM:    "null",
			DisplayRAM:  0,
			CanMoney:    user.CanMoney,
			PhoneNumber: "null",
		}
		return
	}
	message = response.UserInfoResponse{
		Code:        util.GetUserInfoOK,
		Msg:         "获取用户信息成功",
		UserId:      userId,
		RAM:         user.RAM,
		DisplayName: user.DisplayName,
		TotalRAM:    user.TotalRAM,
		DisplayRAM:  user.DisplayRAM,
		CanMoney:    user.CanMoney,
		PhoneNumber: user.PhoneNumber,
	}

	return
}

// UploadProfile 上传头像
func (us *UserService) UploadProfile(c *gin.Context) (message response.MessageResponse) {
	println("收到了上传头像请求")
	userId := c.PostForm("userId")
	fileData, _ := c.FormFile("file") //获取文件原数据

	//用户头像版本+1
	userDao := dao.UserDao{}
	user := &common.User{UserId: userId}
	row := userDao.UpdateWithAutoIncrement(user, "profile_version", "1")
	if row == 0 {
		message = response.MessageResponse{
			Code: util.UploadProfileUnusual,
			Msg:  "上传用户头像异常",
			Data: "null",
		}
		return
	}

	//获取用户头像版本号
	row = userDao.QueryByUserId(user, "profile_version")
	if row == 0 {
		message = response.MessageResponse{
			Code: util.GetProfileVersionFailed,
			Msg:  "获取用户头像版本失败",
			Data: "null",
		}
		return
	}

	//获取当前时间
	currentTime := time.Now()

	//指定日期格式
	dateFormat := "200601021504"

	//格式化日期为指定格式
	formattedDate := currentTime.Format(dateFormat)

	//创建新文件名
	profileName := formattedDate + "_" + userId + "_" + "profile" + "_" + "v" + user.ProfileVersion + ".png"

	//临时头像文件路径
	tempProfileFile := "../fileRepository/temp/userProfile/"

	//临时存储头像在服务端
	err := c.SaveUploadedFile(fileData, tempProfileFile+profileName)
	if err != nil {
		fmt.Println(err)
	}

	//获取头像bucket
	profileBucket := util.GetProfileBucket()

	//文件名+源文件
	err = profileBucket.PutObjectFromFile(userId+"/"+profileName, "../fileRepository/temp/userProfile/"+profileName)
	if err != nil {
		fmt.Println("Error:", err)
	}

	//存储用户头像的文件名
	row = userDao.Update(*user, "profile", profileName)
	if row == 0 {
		message = response.MessageResponse{
			Code: util.UploadProfileUnusual,
			Msg:  "上传用户头像异常",
			Data: "null",
		}
		return
	}

	message = response.MessageResponse{
		Code: util.UploadProfileOK,
		Msg:  "上传头像成功",
		Data: "null",
	}

	return
}

// GetProfile 获取用户头像
func (us *UserService) GetProfile(c *gin.Context) (message response.MessageResponse) {
	println("收到了获取用户头像请求")
	userId := c.Query("user_id")

	user := &common.User{
		UserId: userId,
	}

	//查询用户头像url
	var userDao dao.UserDao
	row := userDao.QueryByUserId(user, "profile")
	if row == 0 {
		message = response.MessageResponse{
			Code: util.GetProfileFailed,
			Msg:  "获取用户头像失败",
			Data: "null",
		}
		return
	}

	OSSClientURL := util.GetOSSClientURL()

	//生成用户头像url
	profile := user.Profile

	message = response.MessageResponse{
		Code: util.GetProfileOK,
		Msg:  "获取用户头像成功",
		Data: OSSClientURL + "/" + userId + "/" + profile,
	}

	return
}

// GetRegisterVerifyCode 获取邮箱注册验证码
func (us *UserService) GetRegisterVerifyCode(c *gin.Context) (message response.MessageResponse) {
	println("收到了获取发送验证码请求")
	username := c.Query("username")
	userEmail := c.Query("email")

	user := common.User{
		Username: username,
		Email:    userEmail,
	}

	//校验用户名是否存在
	userDao := dao.UserDao{}
	result := userDao.QueryByData(user, "username", user.Username)

	//如果查询到则不通过
	if result > 0 {
		message = response.MessageResponse{
			Code: util.UsernameExisted,
			Msg:  "用户名已存在",
			Data: "null",
		}
		return
	}

	//查询邮箱是否存在
	result = userDao.QueryByData(user, "email", user.Email)

	//如果查询到则不通过
	if result > 0 {
		message = response.MessageResponse{
			Code: util.UserRegisterEmailExisted,
			Msg:  "注册失败，邮箱已存在",
			Data: "null",
		}
		return
	}

	//验证码加入缓存
	conn := util.GetRedis()
	defer conn.Close()

	//随机生成验证码
	rand.Seed(time.Now().UnixNano())
	min := 100000                          // 最小值（六位数的最小值为100000）
	max := 999999                          // 最大值（六位数的最大值为999999）
	num := rand.Intn(max-min+1) + min      // 生成 min 到 max 之间的随机数
	randNumber := fmt.Sprintf("%06d", num) // 将随机数转换为六位数字字符串

	key := "r:ec:" + userEmail + ":" + username

	//查询redis是否有该username
	redisUsername, _ := redis.String(conn.Do("get", key))

	//如果查不到redisUsername就添加进去
	if redisUsername == "" {
		OK, _ := conn.Do("SETEX", key, "180", randNumber)
		fmt.Println(OK)

		if OK != "OK" {
			message = response.MessageResponse{
				Code: util.RedisDoEmailCodeFailed,
				Msg:  "验证码加入redis失败",
				Data: "null",
			}
			return
		}

		// 执行 TTL 命令获取键的剩余生存时间
		ttl, err := redis.Int(conn.Do("TTL", key))
		if err != nil {
			fmt.Println("Failed to get TTL:", err)
			return
		}

		switch ttl {
		case -1:
			message = response.MessageResponse{
				Code: util.GetRedisEmailCodeUnusual,
				Msg:  "获取验证码异常",
				Data: "null",
			}
		case -2:
			message = response.MessageResponse{
				Code: util.GetRedisEmailCodeUnusual,
				Msg:  "获取验证码异常",
				Data: "null",
			}
		default:
			message = response.MessageResponse{
				Code: util.RedisDoEmailCodeOK,
				Msg:  "获取验证码成功",
				Data: strconv.Itoa(ttl),
			}
		}

	} else {

		// 执行 TTL 命令获取键的剩余生存时间
		ttl, err := redis.Int(conn.Do("TTL", key))
		if err != nil {
			fmt.Println("Failed to get TTL:", err)
			return
		}

		switch ttl {
		case -1:
			message = response.MessageResponse{
				Code: util.GetRedisEmailCodeUnusual,
				Msg:  "验证码不存在",
				Data: "null",
			}
		case -2:
			message = response.MessageResponse{
				Code: util.GetRedisEmailCodeUnusual,
				Msg:  "验证码已过期",
				Data: "null",
			}
		default:
			message = response.MessageResponse{
				Code: util.RedisEmailCodeExisted,
				Msg:  "验证码已存在",
				Data: strconv.Itoa(ttl),
			}
		}
		return
	}

	//发送验证码邮件
	OK := util.SendEmail("星灯云盘账号注册", "【星灯云盘】您正在注册账号，验证码："+randNumber+"，请勿泄露给他人，3分钟内有效。", userEmail)
	fmt.Println(OK)

	return
}

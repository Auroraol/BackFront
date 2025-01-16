package logic

import (
	"aso/utils/cache"
	"aso/utils/common"
	"aso/utils/encryption"
	"aso/utils/gconv"
	"context"
	"errors"
	"github.com/tal-tech/go-zero/core/stores/sqlc"
	"time"

	"aso/rpc/sys/internal/svc"
	"aso/rpc/sys/sys"

	"github.com/tal-tech/go-zero/core/logx"
)

const UserPassTime string = "user_pass_"
const UserLock string = "user_lock_"

type LoginLogic struct {
	ctx    context.Context
	svcCtx *svc.ServiceContext
	logx.Logger
}

func NewLoginLogic(ctx context.Context, svcCtx *svc.ServiceContext) *LoginLogic {
	return &LoginLogic{
		ctx:    ctx,
		svcCtx: svcCtx,
		Logger: logx.WithContext(ctx),
	}
}

// Login 登录
func (l *LoginLogic) Login(in *sys.LoginRequest) (*sys.LoginResp, error) {
	if checkLock(in.Username) {
		return nil, errors.New("输入次数过多，账号已被锁定，请30分钟后再试")
	}
	userInfo, err := l.svcCtx.UserModel.FindOneByUsername(in.Username)
	switch err {
	case nil:
	case sqlc.ErrNotFound:
		return nil, errors.New("用户名不存在")
	default:
		return nil, err
	}
	// 检查验证码
	if !verifyString(in) {
		return nil, errors.New("验证码错误")
	}
	// 检查用户状态
	if userInfo.Status != true {
		return nil, errors.New("用户已被禁用")
	}

	// 检查用户密码
	if userInfo.Password != l.encryptData(in.Password) {
		// 增加密码错误统计
		errTimes := setPasswordCounts(in.Username)
		having := 5 - errTimes
		return nil, errors.New("账号或密码不正确,还有" + gconv.String(having) + "次之后账号将锁定")
	}

	// 创建token
	now := time.Now().Unix()
	jwtToken, err := common.GetJwtToken(l.svcCtx.Config.JWT.AccessSecret, now, l.svcCtx.Config.JWT.AccessExpire, userInfo.Id)

	if err != nil {
		return nil, err
	}
	removePasswordCounts(in.Username)
	return &sys.LoginResp{
		Token: jwtToken,
		Uid:   userInfo.Id,
	}, nil
}

// encryptData 数据加密方法
func (l *LoginLogic) encryptData(plainText string) string {
	return encryption.AESEncrypt(plainText, l.svcCtx.Config.EncryptKey)
}

// setPasswordCounts 记录密码尝试次数
func setPasswordCounts(loginName string) int {
	curTimes := 0
	curTimeObj, _ := cache.Instance().Get(UserPassTime + loginName)
	if curTimeObj != nil {
		curTimes = gconv.Int(curTimeObj)
	}
	curTimes = curTimes + 1
	cache.Instance().Set(UserPassTime+loginName, curTimes, 1*time.Minute)

	if curTimes >= 5 {
		lock(loginName)
	}
	return curTimes
}

// removePasswordCounts 移除密码错误次数
func removePasswordCounts(loginName string) {
	cache.Instance().Delete(UserPassTime + loginName)
}

// lock 锁定账号
func lock(loginName string) {
	cache.Instance().Set(UserLock+loginName, true, 30*time.Minute)
}

// Unlock 解除锁定
func unlock(loginName string) {
	cache.Instance().Delete(UserLock + loginName)
}

// checkLock 检查账号是否锁定
func checkLock(loginName string) bool {
	result := false
	rs, _ := cache.Instance().Get(UserLock + loginName)
	if rs != nil {
		result = true
	}
	return result
}

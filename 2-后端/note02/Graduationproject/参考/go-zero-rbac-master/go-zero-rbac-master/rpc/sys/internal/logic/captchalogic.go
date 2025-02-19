package logic

import (
	"aso/rpc/sys/internal/svc"
	"aso/rpc/sys/sys"
	"aso/utils/gconv"
	"context"
	"errors"
	"github.com/mojocn/base64Captcha"
	"github.com/tal-tech/go-zero/core/logx"
)

type CaptchaLogic struct {
	ctx    context.Context
	svcCtx *svc.ServiceContext
	logx.Logger
}

func NewCaptchaLogic(ctx context.Context, svcCtx *svc.ServiceContext) *CaptchaLogic {
	return &CaptchaLogic{
		ctx:    ctx,
		svcCtx: svcCtx,
		Logger: logx.WithContext(ctx),
	}
}

// Captcha 验证码
func (l *CaptchaLogic) Captcha(in *sys.CaptchaRequest) (*sys.CaptchaResp, error) {
	id, data := getVerifyImgString(in)
	return &sys.CaptchaResp{
		CaptchaId: id,
		Data:      data,
	}, nil
}

// 创建验证码
func getVerifyImgString(in *sys.CaptchaRequest) (idKeyC string, base64stringC string) {
	driver := &base64Captcha.DriverString{
		Height:     gconv.Int(in.Height),
		Width:      gconv.Int(in.Width),
		NoiseCount: 30,
		Length:     4,
		Source:     "abcdefghjkmnpqrstuvwxyz23456789",
		Fonts:      []string{"chromohv.ttf"},
	}
	driver = driver.ConvertFonts()
	store := base64Captcha.DefaultMemStore
	c := base64Captcha.NewCaptcha(driver, store)
	idKeyC, base64stringC, err := c.Generate()
	if err != nil {
		_ = errors.New("验证码生成失败，原因：" + err.Error())
	}
	return
}

// 检查验证码
func verifyString(in *sys.LoginRequest) bool {
	driver := new(base64Captcha.DriverString)
	store := base64Captcha.DefaultMemStore
	c := base64Captcha.NewCaptcha(driver, store)
	return c.Verify(in.CaptchaId, in.VerifyCode, true)
}

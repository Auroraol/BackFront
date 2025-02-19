/**
 * @Author: FxShadow
 * @Description:
 * @Date: 2023/05/10 0:00
 */

package common

// todo 暂时无用
type AliConfig struct {
	AppID        string `mapstructure:"app_id"`
	PrivateKey   string `mapstructure:"private_key"`
	AliPublicKey string `mapstructure:"ali_public_key"`
	NotifyURL    string `mapstructure:"notify_url"`
	ReturnURL    string `mapstructure:"return_url"`
	ProductCode  string `mapstructure:"product_code"`
	Subject      string `mapstructure:"sub_ject"`
}

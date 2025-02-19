package aixiya.framework.backend.platform.foundation.service;


import aixiya.framework.backend.platform.foundation.api.model.BaiduSmsVo;
import aixiya.framework.backend.platform.foundation.api.model.VerificationCodeResponse;
import aixiya.framework.backend.platform.foundation.api.model.VerificationCodeVo;

/**
 * @Author wangqun865@163.com
 */
public interface BaiduSmsService {

    /**
     * 百度统一发送短信
     * @param BaiduSmsVo
     * @return
     */
    boolean simpleSend(BaiduSmsVo BaiduSmsVo);

    /**
     * 统一验证码发送
     * @param verificationCodeVo
     * @return
     */
    VerificationCodeResponse verificationCodeSend(VerificationCodeVo verificationCodeVo);

    /**
     * 统一校验验证码
     * @param verificationCodeVo
     * @return
     */
    VerificationCodeResponse validateCode(VerificationCodeVo verificationCodeVo);

}

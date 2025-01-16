package aixiya.framework.backend.platform.foundation.api.feign;


import aixiya.framework.backend.platform.foundation.api.fallback.SmsServiceFallback;
import aixiya.framework.backend.platform.foundation.api.model.BaiduSmsVo;
import aixiya.framework.backend.platform.foundation.api.model.VerificationCodeVo;
import com.aixiya.framework.backend.common.api.AixiyaFwResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author wangqun865@163.com
 */
@FeignClient(value = "foundation-center",fallback = SmsServiceFallback.class)
public interface ISmsService {
    /**
     * 发送短信（百度云）
     * @param baiduSmsVo
     * @return
     */
    @RequestMapping(value="/public/api/sms/simple/send",method = RequestMethod.POST)
    AixiyaFwResponse simpleSend(@RequestBody BaiduSmsVo baiduSmsVo);

    /**
     * 发送验证码
     * @param verificationCodeVo
     * @return
     */
    @RequestMapping(value = "/public/api/sms/verificationCode/send", method = RequestMethod.POST)
    @ResponseBody
    AixiyaFwResponse verificationCodeSend(@RequestBody VerificationCodeVo verificationCodeVo);

    /**
     * 校验验证码
     * @param verificationCodeVo
     * @return
     */
    @RequestMapping(value = "/public/api/sms/validateCode/send", method = RequestMethod.POST)
    @ResponseBody
    AixiyaFwResponse validateCode(@RequestBody VerificationCodeVo verificationCodeVo);


}

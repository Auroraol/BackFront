package aixiya.framework.backend.platform.foundation.api.fallback;

import aixiya.framework.backend.platform.foundation.api.feign.ISmsService;
import aixiya.framework.backend.platform.foundation.api.model.BaiduSmsVo;
import aixiya.framework.backend.platform.foundation.api.model.VerificationCodeVo;
import com.aixiya.framework.backend.common.api.AixiyaFwResponse;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @Author wangqun865@163.com
 */
@Component
@Slf4j
public class SmsServiceFallback implements ISmsService {
    @Override
    public AixiyaFwResponse simpleSend(BaiduSmsVo baiduSmsVo) {
        String request = JSON.toJSONString(baiduSmsVo);
        log.error("调用API{}异常{}","simpleSend",request);
        return new AixiyaFwResponse().fail("接口调用失败!");
    }

    @Override
    public AixiyaFwResponse verificationCodeSend(VerificationCodeVo verificationCodeVo) {
        String request = JSON.toJSONString(verificationCodeVo);
        log.error("调用API{}异常{}","verificationCodeSend",request);
        return new AixiyaFwResponse().fail("接口调用失败!");
    }

    @Override
    public AixiyaFwResponse validateCode(VerificationCodeVo verificationCodeVo) {
        String request = JSON.toJSONString(verificationCodeVo);
        log.error("调用API{}异常{}","validateCode",request);
        return new AixiyaFwResponse().fail("接口调用失败!");
    }
}

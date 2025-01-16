package aixiya.framework.backend.platform.foundation.api.fallback;

import aixiya.framework.backend.platform.foundation.api.feign.IOcrService;
import aixiya.framework.backend.platform.foundation.api.model.BaseOcrRequest;
import com.aixiya.framework.backend.common.api.AixiyaFwResponse;
import com.alibaba.fastjson.JSON;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @Author wangqun865@163.com
 */
@Component
@Slf4j
public class OcrServiceFallback implements IOcrService {

    @Override
    public AixiyaFwResponse simpleOcr(BaseOcrRequest baseOcrRequest) {
        String request = JSON.toJSONString(baseOcrRequest);
        log.error("调用API{}异常{}","simpleSend",request);
        return new AixiyaFwResponse().fail("接口调用失败!");
    }
}

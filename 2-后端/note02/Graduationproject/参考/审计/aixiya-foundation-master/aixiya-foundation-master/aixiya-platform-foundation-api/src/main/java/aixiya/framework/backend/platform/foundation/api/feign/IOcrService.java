package aixiya.framework.backend.platform.foundation.api.feign;


import aixiya.framework.backend.platform.foundation.api.fallback.OcrServiceFallback;
import aixiya.framework.backend.platform.foundation.api.model.BaseOcrRequest;
import com.aixiya.framework.backend.common.api.AixiyaFwResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author wangqun865@163.com
 */
@FeignClient(value = "foundation-center",fallback = OcrServiceFallback.class)
public interface IOcrService {
    /**
     * 根据storage File ID 进行OCR 识别 （可扩展为根据其他类型进行OCR识别)
     * @param baseOcrRequest 参照API中 OcrByStroageVo
     * @return
     */
    @RequestMapping(value="public/api/ocr/simple/ocr",method = RequestMethod.POST)
    @ResponseBody
    AixiyaFwResponse simpleOcr(@RequestBody BaseOcrRequest baseOcrRequest);

}

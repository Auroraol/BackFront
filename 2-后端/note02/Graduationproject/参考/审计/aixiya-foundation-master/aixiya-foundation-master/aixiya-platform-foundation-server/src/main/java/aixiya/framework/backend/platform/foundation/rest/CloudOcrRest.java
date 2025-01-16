package aixiya.framework.backend.platform.foundation.rest;

import aixiya.framework.backend.platform.foundation.api.common.exception.OcrBaiduException;
import aixiya.framework.backend.platform.foundation.api.common.exception.OcrDownloadException;
import aixiya.framework.backend.platform.foundation.api.model.BaseOcrRequest;
import aixiya.framework.backend.platform.foundation.api.model.BaseOcrResponse;
import aixiya.framework.backend.platform.foundation.api.model.OcrByStroageVo;
import aixiya.framework.backend.platform.foundation.api.utils.constants.OcrApiConstants;
import aixiya.framework.backend.platform.foundation.service.BaiduOcrService;
import aixiya.framework.backend.platform.foundation.service.ObsService;
import com.aixiya.framework.backend.common.api.AixiyaFwResponse;
import com.alibaba.fastjson.JSON;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @Author wangqun865@163.com
 */

@Slf4j
@Validated
@RestController
@RequestMapping("public/api/ocr")
public class CloudOcrRest {
    @Autowired
    private ObsService obsService;

    @Autowired
    private BaiduOcrService baiduOcrService;

    /**
     * 通过storage服务 OCR识别
     * @param baseOcrRequest
     * @return
     */
    @RequestMapping(value = "/simple/ocr", method = RequestMethod.POST)
    @ResponseBody
    public AixiyaFwResponse simpleOcr(@RequestBody @Validated BaseOcrRequest baseOcrRequest ) {

        String request = JSON.toJSONString(baseOcrRequest);
        log.info("调用API{}请求{}","/simple/ocr",request);
        OcrByStroageVo ocrByStroageVo = baseOcrRequest.getOcrByStroageVo();

        byte[] fileByte = obsService.downloadFileForOcr(ocrByStroageVo.getFileId());
        if (fileByte == null) {
            throw new OcrDownloadException("byte is empty! 请联系系统管理员");
        }

        BaseOcrResponse baseOcrResponse = null;
        String type = baseOcrRequest.getType();
        if (!type.equals(OcrApiConstants.OCR_ID_CARD_FRONT) && !type.equals(OcrApiConstants.OCR_ID_CARD_BACK)
                && !type.equals(OcrApiConstants.OCR_BUSINESS_LICENSE)) {
            throw new OcrBaiduException("type is illegal!");
        }
        baseOcrResponse = baiduOcrService.ocrIdCardFront(
                ocrByStroageVo.getFileId() ,fileByte ,baseOcrRequest.getType() );

        if (baseOcrResponse == null) {
            return new AixiyaFwResponse().fail("baseOcrResponse is null!");
        }
        if (baseOcrResponse.isSuccess()) {
            return new AixiyaFwResponse().data(baseOcrResponse);
        } else {
            return new AixiyaFwResponse().fail(baseOcrResponse.getErrorMsg());
        }
    }


}

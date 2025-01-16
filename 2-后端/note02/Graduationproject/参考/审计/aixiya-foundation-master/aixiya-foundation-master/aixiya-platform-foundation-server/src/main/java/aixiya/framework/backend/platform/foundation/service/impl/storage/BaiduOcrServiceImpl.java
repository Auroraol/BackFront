package aixiya.framework.backend.platform.foundation.service.impl.storage;

import aixiya.framework.backend.platform.foundation.api.common.exception.OcrBaiduException;
import aixiya.framework.backend.platform.foundation.api.model.BaseOcrResponse;
import aixiya.framework.backend.platform.foundation.api.model.BusinessLicenseVo;
import aixiya.framework.backend.platform.foundation.api.model.IdCardBackVo;
import aixiya.framework.backend.platform.foundation.api.model.IdCardFrontVo;
import aixiya.framework.backend.platform.foundation.api.utils.constants.OcrApiConstants;
import aixiya.framework.backend.platform.foundation.common.util.OcrClientSingleton;
import aixiya.framework.backend.platform.foundation.service.BaiduOcrService;
import com.baidu.aip.ocr.AipOcr;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.HashMap;

/**
 * @Author wangqun865@163.com
 */
@Service
@Slf4j
public class BaiduOcrServiceImpl implements BaiduOcrService {



    @Override
    public BaseOcrResponse ocrIdCardFront(String fileId , byte[] file , String type) {
        log.info("begin ocrIdCard file{},type{}" , fileId , type);
        if (file == null) {
            throw new OcrBaiduException("ocr : file is empty!");
        }
        AipOcr client = OcrClientSingleton.getInstance();
        BaseOcrResponse baseOcrResponse = null;

        HashMap<String, String> options = new HashMap<>();

        JSONObject ocrResponse = null;
        String frontOrBack = null;

        try {
            if (type.equals(OcrApiConstants.OCR_ID_CARD_FRONT)) {
                options.put("detect_direction", "true");
                options.put("detect_risk", "false");
                frontOrBack = "front";
                ocrResponse = client.idcard(file, frontOrBack, options);
            } else if (type.equals(OcrApiConstants.OCR_ID_CARD_BACK)) {
                options.put("detect_direction", "true");
                options.put("detect_risk", "false");
                frontOrBack = "back";
                ocrResponse = client.idcard(file, frontOrBack, options);
            } else if(type.equals(OcrApiConstants.OCR_BUSINESS_LICENSE)) {
                options.put("detect_direction", "true");
                options.put("detect_risk", "false");
                ocrResponse = client.businessLicense(file, options);
            }else {
                throw new OcrBaiduException("type is illegal!");
            }
        } catch (Exception e) {
            log.error("ocr baidu request error! fileId {}" , fileId);
            log.error(e.getMessage(),e);
            throw new OcrBaiduException("ocr interface error!");
        }
        if(ocrResponse == null || StringUtils.isEmpty(ocrResponse.toString())) {
            log.error("ocr baidu response is empty! fileId {}" , fileId);
            throw new OcrBaiduException("ocr response is empty!");
        }
        log.info("BaiduOcrServiceImpl --> ocrIdCardFront get file:{} ocrResponse:{}",fileId,ocrResponse.toString());

        Integer logId = ocrResponse.getInt("log_id");
        log.info("ocr response logId : {}; fileId: {}",logId.toString(),fileId);

        baseOcrResponse = parseBaseOcrResponse(ocrResponse , type);
        return baseOcrResponse;
    }


    private BaseOcrResponse parseBaseOcrResponse(JSONObject ocrResponse , String type) {
        BaseOcrResponse baseOcrResponse = null;
        try {
            JSONObject wordsResult = ocrResponse.getJSONObject("words_result");
            //身份证正面
            if (type.equals(OcrApiConstants.OCR_ID_CARD_FRONT)) {
                baseOcrResponse = new IdCardFrontVo();
                baseOcrResponse = initResponse(baseOcrResponse ,ocrResponse,type);
                JSONObject addressObj =  wordsResult.getJSONObject("住址");
                if (addressObj != null) {
                    ((IdCardFrontVo) baseOcrResponse).setAddress(addressObj.getString("words"));
                }
                JSONObject cardNoObj =  wordsResult.getJSONObject("公民身份号码");
                if (cardNoObj != null) {
                    ((IdCardFrontVo) baseOcrResponse).setCardNo(cardNoObj.getString("words"));
                }
                JSONObject birthdayObj =  wordsResult.getJSONObject("出生");
                if(birthdayObj != null) {
                    ((IdCardFrontVo) baseOcrResponse).setBirthday(birthdayObj.getString("words"));
                }
                JSONObject nameObj =  wordsResult.getJSONObject("姓名");
                if (nameObj != null) {
                    ((IdCardFrontVo) baseOcrResponse).setName(nameObj.getString("words"));
                }
                JSONObject sexObj =  wordsResult.getJSONObject("性别");
                if (sexObj != null) {
                    ((IdCardFrontVo) baseOcrResponse).setSex(sexObj.getString("words"));
                }
                JSONObject nationalityObj =  wordsResult.getJSONObject("民族");
                if (nationalityObj != null) {
                    ((IdCardFrontVo) baseOcrResponse).setNationality(nationalityObj.getString("words"));
                }
            } else if (type.equals(OcrApiConstants.OCR_ID_CARD_BACK)) {
                baseOcrResponse = new IdCardBackVo();
                baseOcrResponse = initResponse(baseOcrResponse ,ocrResponse,type);
                JSONObject expirydayObj =  wordsResult.getJSONObject("失效日期");
                if (expirydayObj != null) {
                    ((IdCardBackVo) baseOcrResponse).setExpiryDay(expirydayObj.getString("words"));
                }
                JSONObject signAgentObj =  wordsResult.getJSONObject("签发机关");
                if (signAgentObj != null) {
                    ((IdCardBackVo) baseOcrResponse).setSignAgent(signAgentObj.getString("words"));
                }
                JSONObject signDayObj =  wordsResult.getJSONObject("签发日期");
                if (signDayObj != null) {
                    ((IdCardBackVo) baseOcrResponse).setSignDay(signDayObj.getString("words"));
                }
            } else if(type.equals(OcrApiConstants.OCR_BUSINESS_LICENSE)) {
                baseOcrResponse = new BusinessLicenseVo();
                baseOcrResponse = initResponse(baseOcrResponse ,ocrResponse,type);
                JSONObject orgNameObj =  wordsResult.getJSONObject("单位名称");
                if (orgNameObj != null) {
                    ((BusinessLicenseVo) baseOcrResponse).setOrgName(orgNameObj.getString("words"));
                }
                JSONObject legalPersonObj =  wordsResult.getJSONObject("法人");
                if (legalPersonObj != null) {
                    ((BusinessLicenseVo) baseOcrResponse).setLegalPerson(legalPersonObj.getString("words"));
                }
                JSONObject addressObj =  wordsResult.getJSONObject("地址");
                if (addressObj != null) {
                    ((BusinessLicenseVo) baseOcrResponse).setAddress(addressObj.getString("words"));
                }
                JSONObject expiryDayObj =  wordsResult.getJSONObject("有效期");
                if (expiryDayObj != null) {
                    ((BusinessLicenseVo) baseOcrResponse).setExpiryDay(expiryDayObj.getString("words"));
                }
                JSONObject cardNoObj =  wordsResult.getJSONObject("证件编号");
                if (cardNoObj != null) {
                    ((BusinessLicenseVo) baseOcrResponse).setCardNo(cardNoObj.getString("words"));
                }
                JSONObject socialCreditCodeObj =  wordsResult.getJSONObject("社会信用代码");
                if (socialCreditCodeObj != null) {
                    ((BusinessLicenseVo) baseOcrResponse).setSocialCreditCode(socialCreditCodeObj.getString("words"));
                }
            }else {
                throw new OcrBaiduException("type is illegal!");
            }
        }catch (Exception e) {
            log.error("parseBaseOcrResponse error!");
            log.error(e.getMessage(),e);
            baseOcrResponse.setSuccess(false);
            baseOcrResponse.setErrorMsg("ocr response json转换错误");
        }
        return baseOcrResponse;
    }

    private BaseOcrResponse initResponse(BaseOcrResponse baseOcrResponse , JSONObject ocrResponse , String type) {
        baseOcrResponse.setSuccess(true);
        baseOcrResponse.setOcrJson(ocrResponse.toString());
        baseOcrResponse.setType(type);
        baseOcrResponse.setLogId(ocrResponse.getInt("log_id"));
        return baseOcrResponse;
    }



}
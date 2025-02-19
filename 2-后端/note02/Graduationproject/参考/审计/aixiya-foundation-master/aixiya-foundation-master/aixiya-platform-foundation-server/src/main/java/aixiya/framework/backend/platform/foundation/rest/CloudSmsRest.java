package aixiya.framework.backend.platform.foundation.rest;

import aixiya.framework.backend.platform.foundation.api.model.BaiduSmsVo;
import aixiya.framework.backend.platform.foundation.api.model.VerificationCodeResponse;
import aixiya.framework.backend.platform.foundation.api.model.VerificationCodeVo;
import aixiya.framework.backend.platform.foundation.service.BaiduSmsService;
import com.aixiya.framework.backend.common.api.AixiyaFwResponse;
import com.alibaba.fastjson.JSONObject;

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
@RequestMapping("public/api/sms")
public class CloudSmsRest {
    @Autowired
    private BaiduSmsService baiduSmsService;

    @RequestMapping(value = "simple/send", method = RequestMethod.POST)
    @ResponseBody
    public AixiyaFwResponse simpleSend(@RequestBody @Validated BaiduSmsVo baiduSmsVo) {
        String requestJson = JSONObject.toJSONString(baiduSmsVo);
        log.info("/simple/send request:" + requestJson);
        return new AixiyaFwResponse().data(baiduSmsService.simpleSend(baiduSmsVo));
    }

    @RequestMapping(value = "verificationCode/send", method = RequestMethod.POST)
    @ResponseBody
    public AixiyaFwResponse verificationCodeSend(@RequestBody @Validated VerificationCodeVo verificationCodeVo) {
        String requestJson = JSONObject.toJSONString(verificationCodeVo);
        log.info("/verificationCode/send request:" + requestJson);
        VerificationCodeResponse response = baiduSmsService.verificationCodeSend(verificationCodeVo);
        return new AixiyaFwResponse().data(response);
    }

    @RequestMapping(value = "validateCode/send", method = RequestMethod.POST)
    @ResponseBody
    public AixiyaFwResponse validateCode(@RequestBody @Validated VerificationCodeVo verificationCodeVo) {
        String requestJson = JSONObject.toJSONString(verificationCodeVo);
        log.info("/verificationCode/send request:" + requestJson);
        VerificationCodeResponse response = baiduSmsService.validateCode(verificationCodeVo);
        return new AixiyaFwResponse().data(response);
    }


}

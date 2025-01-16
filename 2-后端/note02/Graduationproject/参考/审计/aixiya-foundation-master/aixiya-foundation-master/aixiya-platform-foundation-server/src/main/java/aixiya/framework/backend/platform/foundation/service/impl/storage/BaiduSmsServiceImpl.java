package aixiya.framework.backend.platform.foundation.service.impl.storage;

import aixiya.framework.backend.platform.foundation.api.model.BaiduSmsVo;
import aixiya.framework.backend.platform.foundation.api.model.VerificationCodeResponse;
import aixiya.framework.backend.platform.foundation.api.model.VerificationCodeVo;
import aixiya.framework.backend.platform.foundation.common.constants.RedisConstants;
import aixiya.framework.backend.platform.foundation.common.util.SmsUtil;
import aixiya.framework.backend.platform.foundation.configure.BaiduSmsConfig;
import aixiya.framework.backend.platform.foundation.configure.VerificationCodeConfig;
import aixiya.framework.backend.platform.foundation.entity.SmsSendLog;
import aixiya.framework.backend.platform.foundation.mapper.SmsSendLogMapper;
import aixiya.framework.backend.platform.foundation.service.BaiduSmsService;
import com.aixiya.framework.backend.redis.starter.service.RedisService;
import com.alibaba.fastjson.JSONObject;
import com.baidubce.auth.DefaultBceCredentials;
import com.baidubce.services.sms.SmsClient;
import com.baidubce.services.sms.SmsClientConfiguration;
import com.baidubce.services.sms.model.SendMessageV3Request;
import com.baidubce.services.sms.model.SendMessageV3Response;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author wangqun865@163.com
 */
@Service
@Slf4j
@RequiredArgsConstructor
//@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class BaiduSmsServiceImpl  implements BaiduSmsService {

    private final SmsSendLogMapper smsSendLogMapper;

    @Autowired
    private BaiduSmsConfig baiduSmsConfig;

    @Autowired
    private VerificationCodeConfig verificationCodeConfig;

    @Autowired
    private RedisService redisService;

    private SmsClient client = null;

    private SmsClient getSmsClient() {
        if (this.client == null) {
            SmsClientConfiguration config = new SmsClientConfiguration();
            config.setCredentials(new DefaultBceCredentials(baiduSmsConfig.getAk(), baiduSmsConfig.getSk()));
            config.setEndpoint(baiduSmsConfig.getEndpoint());
            config.setSocketTimeoutInMillis(baiduSmsConfig.getSocketTimeout());
            config.setConnectionTimeoutInMillis(baiduSmsConfig.getConnectionTimeout());
            this.client = new SmsClient(config);
        }
        return this.client;
    }

    @Override
    public boolean simpleSend(BaiduSmsVo baiduSmsVo) {
        String requestJson = JSONObject.toJSONString(baiduSmsVo);
        log.info("BaiduSmsServiceImpl --> simpleSend--> reuqestJson:" + requestJson);
        boolean returnFlag ;
        SendMessageV3Request request = new SendMessageV3Request();
        request.setMobile(baiduSmsVo.getMobile());
        request.setSignatureId(baiduSmsVo.getSignatureId());
        request.setTemplate(baiduSmsVo.getTemplate());
        request.setContentVar(baiduSmsVo.getContentVar());
        SendMessageV3Response response = this.getSmsClient().sendMessage(request);

        //构建日志
        SmsSendLog smsSendLog = new SmsSendLog();
        String contentValue = "";
        if (baiduSmsVo.getContentVar() != null && baiduSmsVo.getContentVar().size() != 0) {
            contentValue = JSONObject.toJSONString(baiduSmsVo.getContentVar());
            if (contentValue != null && (contentValue.length() > 499)) {
                contentValue = contentValue.substring(0,499);
            }
        }
        smsSendLog.setContent(contentValue);
        smsSendLog.setMobile(baiduSmsVo.getMobile());
        smsSendLog.setSendType("0");
        smsSendLog.setSignature(baiduSmsVo.getSignatureId());
        smsSendLog.setTemplate(baiduSmsVo.getTemplate());

        if (response != null && response.isSuccess()) {
            smsSendLog.setResponseStatus("0");
            returnFlag = true;
        } else {
            smsSendLog.setResponseStatus("1");
            returnFlag = false;
            if (response == null) {
                log.error("BaiduSmsServiceImpl-->simpleSend-->baidu response null Error!");
            } else {
                log.error("BaiduSmsServiceImpl-->simpleSend-->baidu responseError:" + response.getCode() + response.getMessage() );
                if (response.getMessage() != null && (response.getMessage().length() >499)) {
                    smsSendLog.setResponseContent(response.getMessage().substring(0,499));
                }
            }
        }

        baiduSmsVo.setSendUserName(baiduSmsVo.getSendUserName());
        baiduSmsVo.setSendUserId(baiduSmsVo.getSendUserId());
        /**
         * public 方法，不保存操作人，根据手机号获取操作记录
         */
        /*smsSendLog.setSendUserId(999999999);
        smsSendLog.setSendUserName("public");
        try {
            CurrentUser currentUser = AixiyaFwUtil.getCurrentUser();
            if (currentUser != null && currentUser.getDid() != null
                    && currentUser.getUsername() != null) {
                smsSendLog.setSendUserId(currentUser.getDid().intValue());
                smsSendLog.setSendUserName(currentUser.getUsername());
            }
        } catch (ClassCastException e) {
            smsSendLog.setSendUserId(999999999);
            smsSendLog.setSendUserName("public");
        }*/

        try {
            smsSendLogMapper.insert(smsSendLog);
        } catch (Exception e) {
            log.error("BaiduSmsServiceImpl-->simpleSend-->insert Error!");
            log.error(e.getMessage(),e);
        }
        return  returnFlag;
    }

    @Override
    public VerificationCodeResponse verificationCodeSend(VerificationCodeVo verificationCodeVo) {
        VerificationCodeResponse response = new VerificationCodeResponse();
        //组装业务
        //验证码保留时间
        Long validityTime = null;
        //发送间隔时间
        Long intervalTime = null;
        if (verificationCodeVo.getValidityTime() == null) {
            validityTime = verificationCodeConfig.getValidityTime();
        } else {
            validityTime = verificationCodeVo.getValidityTime();
        }
        if (verificationCodeVo.getIntervalTime() == null) {
            intervalTime = verificationCodeConfig.getIntervalTime();
        } else {
            intervalTime = verificationCodeVo.getIntervalTime();
        }
        String mobile = verificationCodeVo.getMobile();
        String uniqueBussiness = verificationCodeVo.getCenterId() + verificationCodeVo.getBusinessId();
        String uniqueKey = RedisConstants.SMS_VERIFICATION_UNIQUE_KEY + mobile + uniqueBussiness;
        String intervalUniqueKey = RedisConstants.SMS_INTERVAL_QNIQUE_KEY + mobile + uniqueBussiness;

        Object object = redisService.get(intervalUniqueKey);
        if (object != null) {
            response.setErrorMessage("获取频繁,请稍后再获取验证码!");
            response.setSuccess(false);
            return response;
        }

        String verificationCode = SmsUtil.getRandom(verificationCodeVo.getRandomNum() == null ? verificationCodeConfig.getRandomNum() : verificationCodeVo.getRandomNum());

        //发送短信
        boolean sendFlag = true;
        try {
            BaiduSmsVo baiduSmsVo = parseBaiduSmsVo(verificationCodeVo , verificationCode ,verificationCodeConfig);
            sendFlag = this.simpleSend(baiduSmsVo);
        } catch (Exception e) {
            log.error("BaiduSmsServiceImpl-->verificationCodeSend 调用短信服务，发送验证码失败");
            log.error(e.getMessage(),e);
            sendFlag = false;
        }
        if (sendFlag) {
            redisService.set(uniqueKey , verificationCode ,validityTime);
            redisService.set(intervalUniqueKey ,new Date() , intervalTime);
            response.setSuccess(true);
            response.setVerificationCode(verificationCode);
            return response;
        } else {
            //redisService.del(uniqueKey);
            //redisService.del(intervalUniqueKey);
            response.setSuccess(false);
            response.setErrorMessage("发送验证码失败，请重新获取");
            return response;
        }
    }

    @Override
    public VerificationCodeResponse validateCode(VerificationCodeVo verificationCodeVo) {
        //todo 是否有需求要做验证码错误N次即锁定
        VerificationCodeResponse response = new VerificationCodeResponse();
        response.setSuccess(false);
        String mobile = verificationCodeVo.getMobile();
        String uniqueBussiness = verificationCodeVo.getCenterId() + verificationCodeVo.getBusinessId();
        String uniqueKey = RedisConstants.SMS_VERIFICATION_UNIQUE_KEY + mobile + uniqueBussiness;
        Object o = redisService.get(uniqueKey);
        if (o != null) {
            String verificationCode = (String) o;
            if (verificationCode.equals(verificationCodeVo.getVilidateCode())) {
                response.setSuccess(true);
            } else {
                response.setErrorMessage("验证码无效,请重新输入");
            }
        } else {
            response.setErrorMessage("验证码无效,请重新获取");
        }
        return response;
    }

    private BaiduSmsVo parseBaiduSmsVo(VerificationCodeVo verificationCodeVo ,String verificationCode ,VerificationCodeConfig verificationCodeConfig ) {
        BaiduSmsVo baiduSmsVo = new BaiduSmsVo();
        baiduSmsVo.setSendUserId(verificationCodeVo.getSendUserId());
        baiduSmsVo.setSendUserName(verificationCodeVo.getSendUserName());
        baiduSmsVo.setMobile(verificationCodeVo.getMobile());
        baiduSmsVo.setSignatureId(verificationCodeVo.getSignatureId() == null ? verificationCodeConfig.getSignatureId() : verificationCodeVo.getSignatureId());
        baiduSmsVo.setTemplate(verificationCodeVo.getTemplate() == null ? verificationCodeConfig.getTemplate() : verificationCodeVo.getTemplate());
        Map<String , String> contentVar = new HashMap<>();
        contentVar.put(verificationCodeConfig.getVerificationCodeKey(),verificationCode);
        if (verificationCodeVo.isConsistBusinessName()) {
            contentVar.put(verificationCodeConfig.getBusinessKey() , verificationCodeVo.getBusinessName());
        } else {
            contentVar.put(verificationCodeConfig.getBusinessKey() , "");
        }
        baiduSmsVo.setContentVar(contentVar);
        return baiduSmsVo;
    }




}

package aixiya.framework.backend.platform.foundation.configure;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.io.Serializable;

/**
 * @Author wangqun865@163.com
 */
@Configuration
@ConfigurationProperties(prefix = "sms.verification")
@Data
public class VerificationCodeConfig implements Serializable {

    private static final long serialVersionUID = -1764060024645802671L;

    /**
     * 验证码保留时间 (默认3分钟)
     */
    private final Long validityTime = 60L*3;

    /**
     * 验证码发送间隔时间
     */
    private final Long intervalTime = 60L*1;

    /**
     *  随机数长度 (默认为4)
     */
    private final Integer randomNum = 4;

    /**
     * 验证码百度模板Id
     */
    private String template;

    /**
     * 验证码签名Id
     */
    private String signatureId;

    /**
     * 短信模板默认${BUSINESS_TYPE}值
     * 如果业务系统传了则有
     */
    private String businessKey;

    /**
     * 短信模板默认${VERIFICATION_CODE}
     */
    private String verificationCodeKey;
}

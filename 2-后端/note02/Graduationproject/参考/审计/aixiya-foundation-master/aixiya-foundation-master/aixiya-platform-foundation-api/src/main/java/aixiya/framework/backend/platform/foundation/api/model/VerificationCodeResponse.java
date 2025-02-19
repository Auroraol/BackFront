package aixiya.framework.backend.platform.foundation.api.model;

import lombok.Data;

/**
 * @Author wangqun865@163.com
 */
@Data
public class VerificationCodeResponse {
    private boolean success;

    private String verificationCode;

    private String errorMessage;
}

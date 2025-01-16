package aixiya.framework.backend.platform.foundation.api.model;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.Map;

/**
 * @Author wangqun865@163.com
 */
@Data
public class BaiduSmsVo {
    /**
     * 手机号
     */
    @NotBlank(message = "手机号不能为空")
    private String mobile;
    /**
     * 短信模板ID
     */
    @NotBlank(message = "模板不能为空")
    private String template;
    /**
     * 短信签名ID
     */
    @NotBlank(message = "签名不能为空")
    private String signatureId;
    /**
     * 短信填充变量
     */
    private Map<String , String> contentVar;
    private String custom;
    private String userExtId;


    //@NotBlank(message = "发送人不能为空")
    private String sendUserId;

    //@NotBlank(message = "发送人不能为空")
    private String sendUserName;

}

package aixiya.framework.backend.platform.foundation.configure;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.io.Serializable;

/**
 * @Author wangqun865@163.com
 */
@Configuration
@ConfigurationProperties(prefix = "ocr.baidu")
@Data
public class BaiduOcrConfig implements Serializable {
    private static final long serialVersionUID = 1898253232240763876L;

    private String aipAppId;

    private String aipApiKey;

    private String aipSecretKey;

    private Integer connectTimeOut = 10000;

    private Integer socketTimeOut = 60000;

}

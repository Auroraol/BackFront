package aixiya.framework.backend.platform.foundation.configure;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.io.Serializable;

/**
 * @Author wangqun865@163.com
 */
@Configuration
@ConfigurationProperties(prefix = "sms.baidu")
@Data
public class BaiduSmsConfig implements Serializable {
    private static final long serialVersionUID = -3399823656490008958L;
    private String ak;

    private String sk;

    private String endpoint;

    private final Integer connectionTimeout = 10000;

    private final Integer socketTimeout = 20000;
}

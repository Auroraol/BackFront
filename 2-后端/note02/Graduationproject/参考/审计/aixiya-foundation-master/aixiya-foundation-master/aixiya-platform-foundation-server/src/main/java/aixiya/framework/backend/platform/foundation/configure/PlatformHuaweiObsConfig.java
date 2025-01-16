package aixiya.framework.backend.platform.foundation.configure;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.io.Serializable;

/**
 * @Author wangqun865@163.com
 */
@Configuration
@ConfigurationProperties(prefix = "csc.platform.huawei")
@Data
public class PlatformHuaweiObsConfig extends PlatformStorageConfig implements Serializable {
    private static final long serialVersionUID = 1898243232240763876L;

    //华为云Endponit
    private String endPoint;
    //华为云AK
    private String ak;
    //华为云SK
    private String sk;
    //华为匿名桶
    private String publicBucker;
    //华为非匿名桶
    private String privateBucker;
    //华为云路径前缀
    private String prefix;
    //文件访问有效期
    private Long expireSeconds = 300L;
    //图片样式
    private String imgStyle = "style/style-zoom";

}

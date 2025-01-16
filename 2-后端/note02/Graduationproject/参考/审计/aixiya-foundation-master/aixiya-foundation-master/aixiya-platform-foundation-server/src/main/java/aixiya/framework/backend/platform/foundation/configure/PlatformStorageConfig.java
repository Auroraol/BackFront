package aixiya.framework.backend.platform.foundation.configure;

import lombok.Data;

/**
 * @Author wangqun865@163.com
 */
@Data
public class PlatformStorageConfig implements BaseStorageConfig{
    private Integer socketTimeout;
    private Integer connectionTimeout;
}

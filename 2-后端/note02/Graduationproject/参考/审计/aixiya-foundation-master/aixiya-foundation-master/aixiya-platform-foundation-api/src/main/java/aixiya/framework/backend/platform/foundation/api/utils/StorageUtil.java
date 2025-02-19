package aixiya.framework.backend.platform.foundation.api.utils;

import org.springframework.core.env.Environment;

/**
 * @Author wangqun865@163.com
 */
public class StorageUtil {
    public static String getApplicationName(Environment environment) {
        return environment.getProperty("spring.application.name");
    }
}

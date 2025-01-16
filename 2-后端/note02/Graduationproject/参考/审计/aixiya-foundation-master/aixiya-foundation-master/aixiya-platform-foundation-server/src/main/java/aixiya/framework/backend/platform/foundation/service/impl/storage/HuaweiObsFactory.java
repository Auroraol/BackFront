package aixiya.framework.backend.platform.foundation.service.impl.storage;


import aixiya.framework.backend.platform.foundation.configure.PlatformHuaweiObsConfig;
import aixiya.framework.backend.platform.foundation.service.StroageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author wangqun865@163.com
 */
@Component
public class HuaweiObsFactory extends StroageFactory{
    @Autowired
    protected PlatformHuaweiObsConfig platformHuaweiObsConfig;

    @Override
    public StroageService buildPlatformStorageService() {
        return new PlatformHuaweiObsService(platformHuaweiObsConfig);
    }
}

package aixiya.framework.backend.platform.foundation.service.impl.storage;


import aixiya.framework.backend.platform.foundation.service.StroageService;

/**
 * @Author wangqun865@163.com
 */
public abstract class StroageFactory {
    public abstract StroageService buildPlatformStorageService();

}

package aixiya.framework.backend.platform.foundation.service.impl.storage;

import aixiya.framework.backend.platform.foundation.api.model.UploadVo;
import aixiya.framework.backend.platform.foundation.configure.PlatformHuaweiObsConfig;
import aixiya.framework.backend.platform.foundation.entity.FileStorage;
import aixiya.framework.backend.platform.foundation.service.StroageService;
import com.obs.services.model.ObsObject;

import org.springframework.util.StringUtils;

import java.util.List;
import java.util.UUID;


/**
 * @Author wangqun865@163.com
 */

public abstract class PlatformStorageService implements StroageService {
    /**
     * 平台桶1 epc 华为云存储配置信息
     */
    protected PlatformHuaweiObsConfig platformHuaweiObsConfig;


    /**
     * 获取文件全名路径
     * @param prefix
     * @return
     */
    public String getPath(String prefix) {
        //文件路径
        String path = UUID.randomUUID().toString().replaceAll("-", "");
        //文件路径
        if (!StringUtils.isEmpty(prefix)) {
            if (prefix.endsWith("/")) {
                path = prefix + path;
            } else {
                path = prefix + "/" + path;
            }
        }
        return path;
    }



    /**
     * 简单下载文件
     * @param uploadVo
     * @return
     */
    @Override
    public abstract List<FileStorage> SimpleUpload(UploadVo uploadVo);


    /**
     * 获取文件流
     * 通过request 模拟API 下载文件 （可拓展，style、分段等）
     * @param path
     * @param isAnonymous
     * @param withStyle
     * @return
     */
    @Override
    public abstract ObsObject getObject(String path, boolean isAnonymous, String withStyle);

    @Override
    public abstract void deleteObject(String id ,boolean isAnonymous);


}

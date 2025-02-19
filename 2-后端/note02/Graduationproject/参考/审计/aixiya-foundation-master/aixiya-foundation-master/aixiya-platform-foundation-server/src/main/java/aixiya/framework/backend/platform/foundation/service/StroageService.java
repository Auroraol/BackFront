package aixiya.framework.backend.platform.foundation.service;

import aixiya.framework.backend.platform.foundation.api.model.UploadVo;
import aixiya.framework.backend.platform.foundation.entity.FileStorage;
import com.obs.services.model.ObsObject;

import java.util.List;

/**
 * @Author wangqun865@163.com
 */
public interface StroageService {

    public List<FileStorage> SimpleUpload(UploadVo uploadVo);

    public ObsObject getObject(String path, boolean isAnonymous, String withStyle);

    public void deleteObject(String id, boolean isAnonymous);
}

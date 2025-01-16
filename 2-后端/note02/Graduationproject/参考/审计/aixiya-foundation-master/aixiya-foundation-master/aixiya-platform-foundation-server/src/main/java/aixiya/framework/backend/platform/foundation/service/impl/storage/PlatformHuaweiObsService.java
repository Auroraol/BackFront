package aixiya.framework.backend.platform.foundation.service.impl.storage;

import aixiya.framework.backend.platform.foundation.api.model.UploadVo;
import aixiya.framework.backend.platform.foundation.configure.PlatformHuaweiObsConfig;
import aixiya.framework.backend.platform.foundation.entity.FileStorage;

import com.aixiya.framework.backend.common.exception.AixiyaFwRuntimeException;
import com.obs.services.ObsClient;
import com.obs.services.ObsConfiguration;
import com.obs.services.model.GetObjectRequest;
import com.obs.services.model.ObsObject;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author wangqun865@163.com
 */
@Slf4j
public class PlatformHuaweiObsService extends PlatformStorageService{

    private static ObsConfiguration obsConfiguration ;

    private static ObsClient client = null;

    public PlatformHuaweiObsService(PlatformHuaweiObsConfig platformHuaweiObsConfig) {
        this.platformHuaweiObsConfig = platformHuaweiObsConfig;
        getInstance();
    }

    private void getInstance() {
        if (client == null) {
            obsConfiguration = new ObsConfiguration();
            obsConfiguration.setSocketTimeout(platformHuaweiObsConfig.getSocketTimeout());
            obsConfiguration.setConnectionTimeout(platformHuaweiObsConfig.getConnectionTimeout());
            obsConfiguration.setEndPoint(this.platformHuaweiObsConfig.getEndPoint());
            client = new ObsClient(this.platformHuaweiObsConfig.getAk(), this.platformHuaweiObsConfig.getSk(), obsConfiguration);
        }
    }

    @Override
    public List<FileStorage> SimpleUpload(UploadVo uploadVo){

        if (uploadVo.getFile() == null || uploadVo.getFile().length < 0) {
            throw new AixiyaFwRuntimeException("上传文件不能为空");
        }
        List<FileStorage> fileStorages = new ArrayList<>();
        //上传文件
        for (int i = 0; i < uploadVo.getFile().length; i++) {
            MultipartFile fileContent = uploadVo.getFile()[i];
            int i1 = fileContent.getOriginalFilename().lastIndexOf(".");
            String suffix = i1 > -1 ? fileContent.getOriginalFilename().substring(i1) : "";
            String fileId = getPath(uploadVo.getPrefix());
            String fileName = fileId + suffix;
            try {
                if (uploadVo.isAnonymous()) {
                    client.putObject(platformHuaweiObsConfig.getPublicBucker(), fileName, fileContent.getInputStream());
                } else {
                    client.putObject(platformHuaweiObsConfig.getPrivateBucker(), fileName, fileContent.getInputStream());
                }
                FileStorage fileStorage = new FileStorage();
                fileStorage.setId(fileId);
                fileStorage.setSuffix(suffix);
                fileStorage.setAnonymous(uploadVo.isAnonymous() ? "1" : "0");
                fileStorage.setBusinessId(uploadVo.getBusinessId());
                fileStorage.setClientId(uploadVo.getClientId());
                fileStorage.setName(fileContent.getOriginalFilename());
                fileStorage.setPrefix(uploadVo.getPrefix());
                fileStorage.setRemark(uploadVo.getRemark());
                fileStorage.setSystemName(uploadVo.getSystemName());
                fileStorage.setType(uploadVo.getType());
                fileStorage.setTypeName(uploadVo.getTypeName());
                fileStorage.setSourceType(uploadVo.getSourceType());
                fileStorage.setCenterId(uploadVo.getCenterId());
                fileStorages.add(fileStorage);
            } catch (Exception e) {
                log.error(e.getMessage(),e);
                throw new AixiyaFwRuntimeException("上传文件失败，请联系文件系统管理员");
            }
        }
        return fileStorages;
    }

    @Override
    public ObsObject getObject(String filePath, boolean isAnonymous, String withStyle) {
        GetObjectRequest request = new GetObjectRequest(isAnonymous ? platformHuaweiObsConfig.getPublicBucker() : platformHuaweiObsConfig.getPrivateBucker(), filePath);
        if (StringUtils.isNotEmpty(withStyle)) {
            Map<String, String> hashMap = new HashMap<>();
            hashMap.put("x-image-process-guyicuode", withStyle);
            request.setRequestParameters(hashMap);
        }
        return client.getObject(request);
    }

    @Override
    public void deleteObject(String id , boolean isAnonymous) {
        client.deleteObject(isAnonymous ? platformHuaweiObsConfig.getPublicBucker() : platformHuaweiObsConfig.getPrivateBucker() , id);
    }


}

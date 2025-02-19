package aixiya.framework.backend.platform.foundation.api.fallback;

import aixiya.framework.backend.platform.foundation.api.feign.IStorageService;
import aixiya.framework.backend.platform.foundation.api.model.DownloadUrlVo;
import aixiya.framework.backend.platform.foundation.api.model.DownloadVo;
import com.aixiya.framework.backend.common.api.AixiyaFwResponse;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author wangqun865@163.com
 */
@Component
@Slf4j
public class StorageServiceFallback implements IStorageService {
    @Override
    public AixiyaFwResponse simpleConfirm(List<DownloadVo> files) {
        String request = JSON.toJSONString(files);
        log.error("调用API{}异常{}","simpleConfirm",request);
        return new AixiyaFwResponse().fail("接口调用失败");
    }

    @Override
    public AixiyaFwResponse downloadUrls(DownloadUrlVo downloadUrlVo) {
        String request = JSON.toJSONString(downloadUrlVo);
        log.error("调用API{}异常{}","downloadUrls",request);
        return new AixiyaFwResponse().fail("接口调用失败");
    }
}

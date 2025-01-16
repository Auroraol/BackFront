package aixiya.framework.backend.platform.foundation.api.model;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @Author wangqun865@163.com
 */
@Data
public class OcrByStroageVo{

    /**
     * 文件id  OBS服务中获取的文件Id
     */
    @NotBlank
    private String fileId;
}

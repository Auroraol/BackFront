package aixiya.framework.backend.platform.foundation.api.model;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @Author wangqun865@163.com
 */
@Data
public class BaseOcrRequest {

    /**
     * 识别类型
     * 定义参照OcrApiConstants
     */
    @NotBlank
    private String type;

    private OcrByStroageVo ocrByStroageVo;


}

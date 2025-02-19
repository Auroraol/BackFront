package aixiya.framework.backend.platform.foundation.api.model;

import lombok.Data;

/**
 * @Author wangqun865@163.com
 */
@Data
public class BaseOcrResponse {

    private boolean success;

    private String errorMsg;

    /**
     * 识别证件类型
     */
    private String type;

    /**
     * json格式
     */
    private String ocrJson;

    /**
     * logid
     */
    private Integer logId;



}

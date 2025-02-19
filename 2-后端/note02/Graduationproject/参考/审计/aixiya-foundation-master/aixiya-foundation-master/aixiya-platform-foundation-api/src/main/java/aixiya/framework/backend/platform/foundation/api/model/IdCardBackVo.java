package aixiya.framework.backend.platform.foundation.api.model;

import lombok.Data;

/**
 * @Author wangqun865@163.com
 */
@Data
public class IdCardBackVo extends BaseOcrResponse{

    /**
     * 失效日期
     * 格式YYYYMMDD
     */
    private String expiryDay;

    /**
     * 签发机关
     */
    private String signAgent;

    /**
     * 签发日期
     * 格式YYYYMMDD
     */
    private String signDay;
}

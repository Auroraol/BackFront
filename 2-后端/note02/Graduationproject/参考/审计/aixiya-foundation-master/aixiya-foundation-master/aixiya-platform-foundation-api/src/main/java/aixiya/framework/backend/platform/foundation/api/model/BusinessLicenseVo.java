package aixiya.framework.backend.platform.foundation.api.model;

import lombok.Data;

/**
 * @Author wangqun865@163.com
 */
@Data
public class BusinessLicenseVo extends BaseOcrResponse{

    /**
     * 单位名称
     */
    private String orgName;

    /**
     * 法人
     */
    private String legalPerson;

    /**
     * 地址
     */
    private String address;

    /**
     * 有效期
     * 格式YYYYMMDD 20201010
     */
    private String expiryDay;

    /**
     * 证件编号
     */
    private String cardNo;

    /**
     * 社会信用代码
     */
    private String socialCreditCode;
}

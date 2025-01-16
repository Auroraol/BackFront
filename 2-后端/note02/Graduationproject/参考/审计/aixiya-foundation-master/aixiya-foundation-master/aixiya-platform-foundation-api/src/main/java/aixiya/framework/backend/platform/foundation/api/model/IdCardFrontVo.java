package aixiya.framework.backend.platform.foundation.api.model;

import lombok.Data;

/**
 * @Author wangqun865@163.com
 */
@Data
public class IdCardFrontVo extends BaseOcrResponse{

    /**
     * 姓名
     */
    private String name;

    /**
     * 性别  男-女
     */
    private String sex;

    /**
     * 地址
     */
    private String address;

    /**
     * 身份证号
     */
    private String cardNo;

    /**
     * 出生日期
     */
    private String Birthday;

    /**
     * 民族
     */
    private String nationality;

}

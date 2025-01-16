package aixiya.framework.backend.platform.foundation.api.model;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @Author wangqun865@163.com
 */
@Data
public class VerificationCodeVo {

    /**
     * 业务id（what business id）
     * 业务系统自定义(与businessName对应)
     */
    @NotBlank(message = "业务id不能为空")
    private String businessId;

    /**
     * 业务name（what business CN）
     * 业务系统自定义(与businessId对应)
     */
    @NotBlank(message = "业务名称不能为空")
    private String businessName;

    /**
     * 中心id（发起方）
     * 1:用户中心
     * 2:船舶中心
     */
    @NotBlank(message = "发起方不能为空")
    private String centerId;

    /**
     * 验证码模板是否包含发送业务名称
     * 默认不包含，包含则验证码信息中包括businessName内容
     */

    private boolean consistBusinessName = false;

    /**
     * 手机号
     */
    @NotBlank(message = "手机号不能为空")
    private String mobile;

    /**
     * 短信模板ID
     * for支持不同类型验证码扩展
     * 空表示使用默认模板
     * PS:""也表示要用自己的为""的模板
     */
    private String template;

    /**
     * 短信签名ID
     * for支持不同类型签名扩展
     * 空表示使用默认签名
     * PS:""也表示要用自己的为""的签名
     */
    private String signatureId;

    /**
     * 验证码有效期，不配置默认1000*60*3    3分钟
     * 单位ms
     */
    private Long validityTime;

    /**
     * 下次发送间隔，不配置默认1000*60*1    1分钟
     * 单位ms
     */
    private Long intervalTime;

    /**
     * 验证码位数 不配置默认4位
     * 单位不为0整数
     */
    private Integer randomNum;

    /**
     * 验证CODE （即验证码）
     * 校验验证码时使用
     */
    private String vilidateCode;

    private String sendUserId;

    private String sendUserName;

}

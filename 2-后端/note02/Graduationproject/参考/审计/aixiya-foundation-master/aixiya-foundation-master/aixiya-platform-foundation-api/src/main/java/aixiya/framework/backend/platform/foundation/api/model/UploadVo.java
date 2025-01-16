package aixiya.framework.backend.platform.foundation.api.model;


import aixiya.framework.backend.platform.foundation.api.annotation.SourceTypeValid;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * @Author wangqun865@163.com
 */
@Data
@SourceTypeValid
public class UploadVo implements Serializable {
    private static final long serialVersionUID = 6702718890145466250L;

    @NotEmpty(message = "上传文件不能为空")
    private MultipartFile[] file;

    /**
     * 使用的产品客户端唯一标识，需功能中心规划，可为空
     */
    private String clientId;

    /**
     * 使用的中心客户端唯一标识，需功能中心规划，可为空
     */
    private String centerId;

    /**
     *  0：public-功能中心规划-都不可用  1:产品规划-只产品查询 2:中心规划-只中心查询 3：产品中心同时规划-只同时查询（需同时满足）
     *  4：产品中心同时规划-全部可用（无需满足任何条件）
     */
    @NotBlank(message = "文件来源不能为空")
    private String sourceType;

    /**
     * API提供，审计使用
     */
    private String systemName;

    /**
     * //业务系统业务id，唯一标识
     */
    private String businessId;

    /**
     * //文件类型 业务系统自定义
     */
    @NotBlank(message = "文件类型不能为空")
    private String type;

    /**
     *  //文件类型名称 业务系统自定义
     */
    @NotBlank(message = "文件类型名称不能为空")
    private String typeName;

    private boolean anonymous;

    /**
     * //文件前缀(文件夹） 业务系统如果使用，需文件中心规划
     */
    private String prefix;

    /**
     * //文件备注说明
     */
    private String remark;

    /**
     * //预留字段 上传同时删除原有
     */
    private String preId;

    /**
     * 上传用户id
     */
    private String upUserId;

    /**
     * 上传用户名称
     */
    private String upUserName;

    /**
     * 确认用户id
     */
    private String confirmUserId;

    /**
     * 确认用户名称
     */
    private String confirmUserName;
}

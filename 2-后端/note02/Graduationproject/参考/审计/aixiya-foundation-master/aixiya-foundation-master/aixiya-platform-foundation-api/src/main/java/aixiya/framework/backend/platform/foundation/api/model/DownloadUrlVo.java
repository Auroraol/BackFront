package aixiya.framework.backend.platform.foundation.api.model;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * @Author wangqun865@163.com
 */
@Data
public class DownloadUrlVo {
    /**
     * 使用的客户端唯一标识，需与文件中心规划，空表示public
     */
    private String clientId;

    /**
     * 使用的中心客户端唯一标识，需文件中心规划，可为空
     */
    private String centerId;

    /**
     *  0：public-功能中心规划-都不可用  1:产品规划-只产品查询 2:中心规划-只中心查询 3：产品中心同时规划-只同时查询（需同时满足）
     *  4：产品中心同时规划-全部可用（无需满足任何条件）
     */
    @NotBlank(message = "文件来源不能为空")
    private String sourceType;

    /**
     * 业务系统业务id，唯一标识
     */
    @NotBlank(message = "业务标识不能为空")
    private String businessId;

    /**
     * 文件类型 业务系统自定义
     */
    private List<String> typeList;

}

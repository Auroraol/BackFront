package aixiya.framework.backend.platform.foundation.api.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author wangqun865@163.com
 */
@Data
public class DownloadVo implements Serializable {
    private String id;

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
    private String sourceType;

    /**
     * 业务系统业务id，唯一标识
     */
    private String businessId;

    /**
     * 文件类型 业务系统自定义
     */
    private String type;

    /**
     * 文件类型名称 业务系统自定义
     */
    private String typeName;

    //文件名称
    private String name;

    //文件后缀
    private String suffix;

    //文件前缀(文件夹） 业务系统如果使用，需文件中心规划
    private String prefix;

    // api提供 系统名称，审计使用
    private String systemName;

    //文件备注说明
    private String remark;

    //下载URL
    private String downloadUrl;

    //缩略图URL（暂未提供 保留字段）
    private String imgStyleUrl;

    //上传用户id
    private String upUserId;

    //上传用户名称
    private String upUserName;

    //上传时间
    private String upDate;

    //确认用户id
    private String confirmUserId;

    //确认用户名称
    private String confirmUserName;

    //确认时间
    private String confirmDate;
}

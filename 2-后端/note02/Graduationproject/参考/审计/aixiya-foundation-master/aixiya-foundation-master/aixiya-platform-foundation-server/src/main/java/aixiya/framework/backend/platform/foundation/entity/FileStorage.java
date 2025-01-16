package aixiya.framework.backend.platform.foundation.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author wangqun865@163.com
 */
@Data
@TableName("t_file_storage")
public class FileStorage implements Serializable {

    private static final long serialVersionUID = -2392984752263669824L;

    @TableId(value = "id")
    private String id;

    @TableField("client_id")
    private String clientId;

    /**
     * 使用的中心客户端唯一标识，需文件中心规划，可为空
     */
    @TableField("center_id")
    private String centerId;

    /**
     *  0：public-功能中心规划-都不可用  1:产品规划-只产品查询 2:中心规划-只中心查询 3：产品中心同时规划-只同时查询（需同时满足）
     *  4：其他规划-全部可用（无需满足任何条件）
     */
    @TableField("source_type")
    private String sourceType;

    @TableField("system_name")
    private String systemName;


    @TableField("business_id")
    private String businessId;

    @TableField("type")
    private String type;

    @TableField("type_name")
    private String typeName;

    @TableField("anonymous")
    private String anonymous;

    @TableField("name")
    private String name;

    @TableField("suffix")
    private String suffix;

    @TableField("prefix")
    private String prefix;

    @TableField("del_flag")
    private Integer delFlag;

    @TableField("remark")
    private String remark;

    @TableField("up_user_id")
    private String upUserId;

    @TableField("up_user_name")
    private String upUserName;

    @TableField("up_date")
    private Date upDate;

    @TableField("confirm_user_id")
    private String confirmUserId;

    @TableField("confirm_user_name")
    private String confirmUserName;

    @TableField("confirm_date")
    private Date confirmDate;












}

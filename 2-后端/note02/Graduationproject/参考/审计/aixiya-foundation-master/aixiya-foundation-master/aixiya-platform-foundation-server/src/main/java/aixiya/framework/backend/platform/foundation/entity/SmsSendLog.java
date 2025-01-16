package aixiya.framework.backend.platform.foundation.entity;

import com.baomidou.mybatisplus.annotation.IdType;
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
@TableName("t_sms_send_log")
public class SmsSendLog implements Serializable {

    private static final long serialVersionUID = 763923281072874963L;

    @TableId(value = "id", type = IdType.AUTO)
    private String id;

    @TableField("send_type")
    private String sendType;

    @TableField("mobile")
    private String mobile;

    @TableField("template")
    private String template;

    @TableField("signature")
    private String signature;

    @TableField("content")
    private String content;

    @TableField("response_status")
    private String responseStatus;

    @TableField("response_content")
    private String responseContent;

    @TableField("send_user_id")
    private Integer sendUserId;

    @TableField("send_user_name")
    private String sendUserName;

    @TableField("send_date")
    private Date sendDate;


}

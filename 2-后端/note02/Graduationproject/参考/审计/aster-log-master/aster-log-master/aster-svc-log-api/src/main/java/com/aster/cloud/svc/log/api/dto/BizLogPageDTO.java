package com.aster.cloud.svc.log.api.dto;

import com.aster.cloud.svc.log.api.entity.BizLog;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author 王骞
 * @date 2021-01-12
 */
@EqualsAndHashCode(callSuper = true)
@ApiModel(description = "业务日志查询对象")
@Data
public class BizLogPageDTO extends BizLog{

    /**
     * 页码
     */
    @ApiModelProperty(value = "页码，默认第1页")
    private Integer pageNum = 1;

    /**
     * 每页数量
     */
    @ApiModelProperty(value = "分页数目，默认10条")
    private Integer pageSize = 10;

}

/*
 *
 *      Copyright (c) 2018-2025, lmp All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without
 *  modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice,
 *  this list of conditions and the following disclaimer.
 *  Redistributions in binary form must reproduce the above copyright
 *  notice, this list of conditions and the following disclaimer in the
 *  documentation and/or other materials provided with the distribution.
 *  Neither the name of the pig4cloud.com developer nor the names of its
 *  contributors may be used to endorse or promote products derived from
 *  this software without specific prior written permission.
 *  Author: lmp
 *
 */

package com.aster.cloud.svc.log.api.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 业务日志表
 * </p>
 *
 * @author lmp
 * @since 2017-11-20
 */
@Data
@ApiModel(value = "业务日志")
public class BizLog implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 编号
	 */
	@TableId(type = IdType.ASSIGN_ID)
	@ApiModelProperty(value = "id")
	private Long id;

	/**
	 * 业务主体代表性id，可重复
	 */
	@ApiModelProperty(value = "业务id（业务主体代表性id，可重复）")
	private String bizId;

	/**
	 * 日志类型
	 */
	@NotBlank(message = "业务类型不能为空")
	@ApiModelProperty(value = "业务类型")
	private String bizType;

	/**
	 * 日志标题
	 */
	@NotBlank(message = "日志标题不能为空")
	@ApiModelProperty(value = "日志标题")
	private String title;

	/**
	 * 日志内容
	 */
	@ApiModelProperty(value = "日志内容")
	private String content;


	/**
	 * 操作IP地址
	 */
	@ApiModelProperty(value = "操作ip地址")
	private String remoteAddr;

	/**
	 * 请求URI
	 */
	@ApiModelProperty(value = "请求uri")
	private String requestUri;

	/**
	 * 操作方式
	 */
	@ApiModelProperty(value = "操作方式")
	private String method;

	/**
	 * 操作提交的数据
	 */
	@ApiModelProperty(value = "请求参数")
	private String params;

	/**
	 * 创建者
	 */
	@ApiModelProperty(value = "创建人")
	private String createBy;

	/**
	 * 创建时间
	 */
	@ApiModelProperty(value = "创建时间")
	private LocalDateTime createTime;

	/**
	 * 租户ID
	 */
	private Integer tenantId;

}

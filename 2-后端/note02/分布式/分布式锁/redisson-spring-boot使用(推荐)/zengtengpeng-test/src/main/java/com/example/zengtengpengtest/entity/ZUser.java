package com.example.zengtengpengtest.entity;

import lombok.Data;
import org.redisson.api.annotation.REntity;
import org.redisson.api.annotation.RId;
import org.redisson.api.annotation.RIndex;

import java.io.Serializable;

/**
 * @Author: LFJ
 * @Date: 2024-01-23 14:49
 */
//
//@Data
//public class ZUser implements Serializable {
//	String name;
//	String age;
//}

@REntity   //Redis的基础上实现的
@Data
public class ZUser {

	@RId
	private Integer id;

	@RIndex
	private String name;

	@RIndex
	private String age;

}
package com.youzhirong.fbslock.dao;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import tk.mybatis.mapper.annotation.Version;

@Getter
@Setter
@ToString
public class BaseDomain implements Serializable {

	@Version
    private Long objectVersionNumber;
}

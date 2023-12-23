package com.example.jwttest.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Bean;

/**
 * @Author: LFJ
 * @Date: 2023-09-26 13:50
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserVo {

	int id;
	String name;
}

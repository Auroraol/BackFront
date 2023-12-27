package com.example.formtest.dao;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @Author: LFJ
 * @Date: 2023-12-27 17:40
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
	private long userId;
	private String userName;
	private String userPassword;
	Integer sex;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private LocalDateTime birthDate1;  // 保持为 LocalDateTime 类型

	private LocalDate birthDate2;  // 修改为 LocalDate 类型

}

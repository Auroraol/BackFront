package com.youzhirong.fbslock.emun;

import org.springframework.util.StringUtils;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * [枚举]考察维度【SUP_INVEST_DIMENSIONS】
 * @author youzhirong
 * @version 创建时间： 2018-12-15
 */
@Getter
@AllArgsConstructor
public enum SupInvestDimensionsDefinition {
	
	COMPANY_PROFILE("COMPANY_PROFILE", "公司概况"),
	CONSTRUCTION_EQUIPMENT("CONSTRUCTION_EQUIPMENT", "施工机械设备"),
	CONSTRUCTION_MANAGEMENT("CONSTRUCTION_MANAGEMENT", "施工管理"),
	COOPERATION_INTENTION("COOPERATION_INTENTION", "合作意向"),
	COOPERATION_UNITS("COOPERATION_UNITS", "合作单位"),
	DESIGN_CAPABILITY("DESIGN_CAPABILITY", "设计能力"),
	ENGINEERING_LEVEL_("ENGINEERING_LEVEL_", "技术水平"),
	FACTORY_CONDITIONS("FACTORY_CONDITIONS", "加工厂情况"),
	MANAGEMENT_SYSTEM("MANAGEMENT_SYSTEM", "管理体制"),
	MATERIAL_SUPPLIES("MATERIAL_SUPPLIES", "材料供应"),
	OVERALL_ENVIRONMENT("OVERALL_ENVIRONMENT", "整体环境"),
	PRODUCTION_AND_SUPPLY("PRODUCTION_AND_SUPPLY", "生产及供货"),
	PROJECT_OVERVIEW("PROJECT_OVERVIEW", "项目概况"),
	PROJECT_PERFORMANCE("PROJECT_PERFORMANCE", "项目履约情况"),
	PROJECT_PERFORMANCE_CAPACITY("PROJECT_PERFORMANCE_CAPACITY", "项目履约能力"),
	PROJECT_TEAM("PROJECT_TEAM", "项目团队情况"),
	SPOT_EXAMINATION("SPOT_EXAMINATION", "现场考察情况"),
	WAREHOUSE_DISTRIBUTION("WAREHOUSE_DISTRIBUTION", "仓储及配送"); 

	private String code;
	private String name;

	public static Boolean isCodeExist(String code) {
		if (StringUtils.isEmpty(code)) {
			return Boolean.FALSE;
		}
		for (SupInvestDimensionsDefinition tempEnum : SupInvestDimensionsDefinition.values()) {
			if (tempEnum.getCode().equals(code)) {
				return Boolean.TRUE;
			}
		}
		return Boolean.FALSE;
	}

	public static SupInvestDimensionsDefinition getMethodByCode(String code) {
		SupInvestDimensionsDefinition type = COMPANY_PROFILE;
		for (SupInvestDimensionsDefinition tempEnum : SupInvestDimensionsDefinition.values()) {
			if (tempEnum.getCode().equals(code)) {
				type = tempEnum;
				break;
			}
		}
		return type;
	}
	
	public static String getNameByCode(String code) {
		if(StringUtils.isEmpty(code)) {
			return null;
		}
		for (SupInvestDimensionsDefinition tempEnum : SupInvestDimensionsDefinition.values()) {
			if (tempEnum.getCode().equals(code)) {
				return tempEnum.getName();
			}
		}
		return null;
	}
	
	public static String getCodeByName(String name) {
		if(StringUtils.isEmpty(name)) {
			return null;
		}
		for (SupInvestDimensionsDefinition tempEnum : SupInvestDimensionsDefinition.values()) {
			if (tempEnum.getName().equals(name)) {
				return tempEnum.getCode();
			}
		}
		return null;
	}

}

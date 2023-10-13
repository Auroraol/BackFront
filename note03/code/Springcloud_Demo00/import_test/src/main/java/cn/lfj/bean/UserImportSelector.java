package cn.lfj.bean;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

public class UserImportSelector implements ImportSelector {

	//@Configuration
	public String[] selectImports(AnnotationMetadata annotationMetadata) {
		//获取配置类的名称
		return new String[]{UserConfiguration.class.getName()};
	}
}

package com.lfj.wms.config;

/**
 * @Author: LFJ
 * @Date: 2023-11-22 22:17
 */

import com.lfj.wms.config.Properties.SwaggerProperties;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiUiConfig {
	@Autowired
	private SwaggerProperties swaggerProperties;

	@Bean
	public GroupedOpenApi empApi() {
		return GroupedOpenApi.builder()
				.group("员工管理")
				.pathsToMatch("/emp/**","/emps")
				.build();
	}
	@Bean
	public GroupedOpenApi deptApi() {
		return GroupedOpenApi.builder()
				.group("部门管理")
				.pathsToMatch("/dept/**","/depts")
				.build();
	}

	@Bean
	public OpenAPI docsOpenAPI() {
		return new OpenAPI()
				.info(new Info()
						.title(swaggerProperties.getTitle())
						.description(swaggerProperties.getDescription())
						.contact(new Contact()
								.name(swaggerProperties.getAuthor())
								.url(swaggerProperties.getUrl())
								.email(swaggerProperties.getEmail()))
						.version(swaggerProperties.getVersion())
						.license(new License().name("Apache 2.0").url("http://springdoc.org")))
				.externalDocs(new ExternalDocumentation()
						.description("")
						.url("https://springshop.wiki.github.org/docs"));
	}
}

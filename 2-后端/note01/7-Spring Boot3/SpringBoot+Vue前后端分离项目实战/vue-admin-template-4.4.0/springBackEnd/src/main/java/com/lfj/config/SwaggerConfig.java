//package com.lfj.config;
//
//import com.lfj.config.Properties.SwaggerProperties;
//import io.swagger.annotations.Api;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import springfox.documentation.builders.ApiInfoBuilder;
//import springfox.documentation.builders.PathSelectors;
//import springfox.documentation.builders.RequestHandlerSelectors;
//import springfox.documentation.oas.annotations.EnableOpenApi;
//import springfox.documentation.service.ApiInfo;
//import springfox.documentation.service.Contact;
//import springfox.documentation.spi.DocumentationType;
//import springfox.documentation.spring.web.plugins.Docket;
//
//@EnableOpenApi
//@Configuration
//public class SwaggerConfig {
//
//	@Autowired
//	private SwaggerProperties swaggerProperties;
//
//	@Bean
//	public Docket createRestApi() {
//		return new Docket(DocumentationType.OAS_30)
//				.pathMapping("/")
//				.enable(swaggerProperties.getEnable())//生产禁用
//				.apiInfo(apiInfo())
//				.select()
//				.apis(RequestHandlerSelectors.withClassAnnotation(Api.class))//方法一、扫描类上有@Api的，推荐，不会显示basic-error-controller
////                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))//方法二、扫描方法上有@ApiOperation，但缺少类信息，不会显示basic-error-controller
////                .apis(RequestHandlerSelectors.basePackage("com.alian.swagger.controller"))//按包扫描,也可以扫描共同的父包，不会显示basic-error-controller
////                .paths(PathSelectors.regex("/.*"))// 对根下所有路径进行监控
//				.paths(PathSelectors.any())
//				.build();
//	}
//
//	/**
//	 * API 页面上半部分展示信息
//	 */
//	private ApiInfo apiInfo() {
//		return new ApiInfoBuilder()
//				.title(swaggerProperties.getTitle())//标题
//				.description(swaggerProperties.getDescription())//描述
//				.contact(new Contact(swaggerProperties.getAuthor(), swaggerProperties.getUrl(), swaggerProperties.getEmail()))//作者信息
//				.version(swaggerProperties.getVersion())//版本号
//				.build();
//	}
//
//}

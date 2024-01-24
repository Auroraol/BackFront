package com.example.springbootredissontest1.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

/**
 * @author Janwes
 * @version 1.0
 * @package com.janwes.config
 * @date 2021/7/1 17:20
 * @description
 */
@Configuration
@EnableSwagger2WebMvc
public class Swagger2Configuration {

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(new ApiInfoBuilder()
                        // 标题
                        .title("电子商城 API")
                        // 描述
                        .description("电子商城应用程序接口文档api")
                        .termsOfServiceUrl("http://www.xx.com/")
                        .contact(new Contact("janwes", "http://xxxaaa.com", "xxx@qq.com"))
                        .version("1.0")
                        .build())
                // 分组名称
                .groupName("2.X版本")
                .select()
                // 这里指定Controller扫描包路径
                .apis(RequestHandlerSelectors.basePackage("com.janwes"))
                .paths(PathSelectors.any())
                .build();
    }
}

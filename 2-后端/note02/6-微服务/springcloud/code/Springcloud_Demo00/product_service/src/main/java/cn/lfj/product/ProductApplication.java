package cn.lfj.product;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EntityScan("cn.lfj.product.entity")
//激活eurekaClient
//@EnableEurekaClient
//@EnableDiscoveryClient
/*
从Spring Cloud Edgware版本开始， @EnableDiscoveryClient 或@EnableEurekaClient 可省略。
只需加上相关依赖，并进行相应配置，即可将微服务注册到服务发现组件上。
*/
public class ProductApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductApplication.class,args);
	}
}

package cn.lfj;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @Author: LFJ
 * @Date: 2023-10-11 22:57
 */

@EnableEurekaClient
@SpringBootApplication
public class ServiceApplication {
	public static void main(String[] args){
		SpringApplication.run(ServiceApplication.class,args);
	}

}

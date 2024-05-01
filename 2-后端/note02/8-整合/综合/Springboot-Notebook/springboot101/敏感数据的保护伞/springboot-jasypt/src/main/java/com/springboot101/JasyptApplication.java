package com.springboot101;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableEncryptableProperties  //开启自动解密功能
public class JasyptApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(JasyptApplication.class, args);
    }
}

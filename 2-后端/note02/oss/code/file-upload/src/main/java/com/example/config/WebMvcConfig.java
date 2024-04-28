package com.example.config;

import com.example.service.FileServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Autowired
    FileServiceImpl fileService;

    /**
     * 文件上传到服务器某个目录，然后SpringBoot配置虚拟路径，映射到此目录
     * pathPattern  虚拟路径
     * filePath 文件保存根路径
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //将匹配上/files/**虚拟路径的url映射到文件上传到服务器的绝对路径，获取静态资源
        registry.addResourceHandler("/" + fileService.pathPattern + "/**")
                .addResourceLocations("file:" + fileService.filePath);
        WebMvcConfigurer.super.addResourceHandlers(registry);
    }
}

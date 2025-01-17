package com.zhang.musicrs;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.util.StringUtils;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author ZHANG
 */
@SpringBootApplication
// @EnableOpenApi
@Slf4j
public class MusicRSApplication {
    public static void main(String[] args) throws UnknownHostException {
        // 入口函数
        ConfigurableApplicationContext application = SpringApplication.run(MusicRSApplication.class, args);
        Environment env = application.getBean(Environment.class);
        String ip = InetAddress.getLocalHost().getHostAddress();
        String port = env.getProperty("server.port");
        String path = env.getProperty("server.servlet.context-path");
        assert path != null;
        if (StringUtils.isEmpty(path)) {
            path = "";
        }
        log.info("\n\t----------------------------------------------------------\n\t" +
                "Application  is running! Access URLs:\n\t" +
                "Local访问网址: \t\thttp://localhost:" + port + path + "\n\t" +
                "External访问网址: \thttp://" + ip + ":" + port + path + "\n\t" +
                "----------------------------------------------------------");
    }
}

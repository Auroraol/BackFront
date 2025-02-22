package net.javadog.pulsar.framework.banner.core;

import cn.hutool.core.thread.ThreadUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.env.Environment;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;

/**
 * 项目启动成功后，提供文档相关的地址
 *
 * @author hdx
 */
@Slf4j
public class PulsarBannerApplicationRunner implements ApplicationRunner {

    @Autowired
    private Environment env;

    @Override
    public void run(ApplicationArguments args) throws UnknownHostException {
        // 获取ip
        String ip = InetAddress.getLocalHost().getHostAddress();
        // 获取YAML中的配置
        String port = env.getProperty("server.port");
        String active = env.getProperty("spring.profiles.active");
        String contextPath = env.getProperty("server.servlet.context-path");
        ThreadUtil.execute(() -> {
            // 延迟 1 秒，保证输出到结尾
            ThreadUtil.sleep(1, TimeUnit.SECONDS);
            log.info("\n----------------------------------------------------------\n\t" +
                    "欢迎访问  \thttps://www.javadog.net\n\t" +
                    "当前程序【" + active + "】环境已启动! 地址如下:\n\t" +
                    "Local: \t\thttp://localhost:" + port + contextPath + "\n\t" +
                    "External: \thttp://" + ip + ":" + port + contextPath + "\n\t" +
                    "Knife4j文档: http://" + ip + ":" + port + contextPath + "/doc.html" + "\n\t" +
                    "Swagger文档: http://" + ip + ":" + port + contextPath + "/swagger-ui\n" +
                    "----------------------------------------------------------");
        });
    }
}

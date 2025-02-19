package aixiya.framework.backend.platform.foundation;

import com.aixiya.framework.backend.security.starter.annotation.EnablePlatformCloudResourceServer;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Author wangqun865@163.com
 */
@SpringBootApplication
@EnablePlatformCloudResourceServer
@MapperScan("aixiya.framework.backend.platform.foundation.mapper")
//@EnableFeignClients({"", ""})
public class FoundationApplication {
    public static void main(String[] args) {
        SpringApplication.run(FoundationApplication.class, args);
    }
}

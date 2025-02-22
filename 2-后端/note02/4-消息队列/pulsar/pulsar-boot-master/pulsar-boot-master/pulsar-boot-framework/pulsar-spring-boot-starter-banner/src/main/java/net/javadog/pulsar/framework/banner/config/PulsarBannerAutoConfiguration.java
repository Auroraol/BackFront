package net.javadog.pulsar.framework.banner.config;

import net.javadog.pulsar.framework.banner.core.PulsarBannerApplicationRunner;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Banner 自动配置类
 *
 * @author javadog
 */
@AutoConfiguration
public class PulsarBannerAutoConfiguration {
    @Bean
    public PulsarBannerApplicationRunner bannerApplicationRunner() {
        return new PulsarBannerApplicationRunner();
    }
}

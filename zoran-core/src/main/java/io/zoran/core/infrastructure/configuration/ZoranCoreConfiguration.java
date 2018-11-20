package io.zoran.core.infrastructure.configuration;

import io.zoran.application.common.mappers.MapperFactory;
import io.zoran.core.application.user.UserStore;
import io.zoran.core.application.user.ZoranUserService;
import io.zoran.core.application.user.ZoranUserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * @author Michal Sadowski (michal.sadowski@roche.com) on 16.11.2018
 */
@Configuration
@EnableMongoRepositories("io.zoran.core")
@Import(WebSecurityConfiguration.class)
public class ZoranCoreConfiguration {

    private static final String CONFIG_FINALIZED = "Web Security Configured ";

    @Slf4j
    @Configuration
    @ConditionalOnProperty(value = "application.config.security.enabled", havingValue = "true")
    static class EnabledSecurityConfiguration implements InitializingBean {

        @Override
        public void afterPropertiesSet() {
            log.info(CONFIG_FINALIZED + "with ENABLED Security!");
        }

        @Bean
        ZoranUserService userService(@Autowired MapperFactory mapperFactory, @Autowired UserStore userStore) {
            return new ZoranUserServiceImpl(mapperFactory, userStore);
        }
    }

    @Slf4j
    @ConditionalOnMissingBean(EnabledSecurityConfiguration.class)
    @Configuration
    static class DisabledSecurityConfiguration implements InitializingBean {

        @Override
        public void afterPropertiesSet() {
            log.info(CONFIG_FINALIZED + "with DISABLED security!");
        }
    }
}

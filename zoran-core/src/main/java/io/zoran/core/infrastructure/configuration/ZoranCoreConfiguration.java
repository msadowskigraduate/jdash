package io.zoran.core.infrastructure.configuration;

import io.zoran.core.infrastructure.SecuredBlock;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
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
    @SecuredBlock
    static class EnabledSecurityConfiguration implements InitializingBean {

        @Override
        public void afterPropertiesSet() {
            log.info(CONFIG_FINALIZED + "with ENABLED Security!");
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

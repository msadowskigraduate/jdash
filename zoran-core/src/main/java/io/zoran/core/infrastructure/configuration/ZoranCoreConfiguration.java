package io.zoran.core.infrastructure.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;

/**
 * @author Michal Sadowski (michal.sadowski@roche.com) on 16.11.2018
 */
@Configuration
@EnableMongoRepositories("io.zoran.core")
@ComponentScan(value = "io.zoran.core")
class ZoranCoreConfiguration {

    private static final String CONFIG_FINALIZED = "Application Configured ";
    @Slf4j
    @Configuration
    @ConditionalOnProperty(value = "application.config.security.enabled", havingValue = "false")
    @EnableOAuth2Client
    @EnableAuthorizationServer
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

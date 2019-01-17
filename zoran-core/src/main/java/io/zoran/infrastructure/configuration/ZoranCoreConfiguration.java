package io.zoran.infrastructure.configuration;

import io.zoran.application.resource.ResourceService;
import io.zoran.application.resource.SharingGroupService;
import io.zoran.application.security.DisabledSecurityResourceServiceImpl;
import io.zoran.application.security.SecurityResourceService;
import io.zoran.application.user.ZoranUserService;
import io.zoran.infrastructure.SecuredBlock;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * @author Michal Sadowski (michal.sadowski@roche.com) on 16.11.2018
 */
@Configuration
@EnableMongoRepositories("io.zoran")
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

        @Bean
        SecurityResourceService resourceService(@Autowired ResourceService service,
                                                @Autowired SharingGroupService sharingGroupService,
                                                @Autowired ZoranUserService userService) {
            return new DisabledSecurityResourceServiceImpl(service, sharingGroupService, userService);
        }
    }
}

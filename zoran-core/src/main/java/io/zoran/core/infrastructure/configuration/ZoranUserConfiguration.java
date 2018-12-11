package io.zoran.core.infrastructure.configuration;

import io.zoran.core.application.user.DisabledSecurityUserService;
import io.zoran.core.application.user.UserStore;
import io.zoran.core.application.user.ZoranUserService;
import io.zoran.core.application.user.ZoranUserServiceImpl;
import io.zoran.core.infrastructure.NoSecurity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Michal Sadowski (sadochasee@gmail.com) on 19/11/2018.
 */
@Configuration
class ZoranUserConfiguration {

    @Bean
    @ConditionalOnBean(ZoranCoreConfiguration.EnabledSecurityConfiguration.class)
    ZoranUserService userService(@Autowired UserStore userStore) {
        return new ZoranUserServiceImpl(userStore);
    }

    @Bean
    @NoSecurity
    ZoranUserService disabledSecurityUserService() {
        return new DisabledSecurityUserService();
    }
}
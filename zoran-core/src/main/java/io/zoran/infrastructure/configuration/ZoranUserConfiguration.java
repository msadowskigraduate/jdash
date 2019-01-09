package io.zoran.infrastructure.configuration;

import io.zoran.application.user.DisabledSecurityUserService;
import io.zoran.application.user.UserStore;
import io.zoran.application.user.ZoranUserService;
import io.zoran.application.user.ZoranUserServiceImpl;
import io.zoran.infrastructure.NoSecurity;
import io.zoran.infrastructure.SecuredBlock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Michal Sadowski (sadochasee@gmail.com) on 19/11/2018.
 */
@Configuration
class ZoranUserConfiguration {

    @Bean
    @SecuredBlock
    ZoranUserService userService(@Autowired UserStore userStore) {
        return new ZoranUserServiceImpl(userStore);
    }

    @Bean
    @NoSecurity
    ZoranUserService disabledSecurityUserService() {
        return new DisabledSecurityUserService();
    }
}
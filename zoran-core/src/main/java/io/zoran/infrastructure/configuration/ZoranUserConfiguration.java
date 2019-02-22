package io.zoran.infrastructure.configuration;

import io.zoran.application.user.DisabledSecurityUserService;
import io.zoran.application.user.UserStore;
import io.zoran.application.user.ZoranUserService;
import io.zoran.application.user.ZoranUserServiceImpl;
import io.zoran.infrastructure.SecurityDisabled;
import io.zoran.infrastructure.SecurityEnabled;
import io.zoran.infrastructure.integrations.git.OAuthAuthorizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Michal Sadowski (sadochasee@gmail.com) on 19/11/2018.
 */
@Configuration
class ZoranUserConfiguration {

    @Bean
    @SecurityEnabled
    ZoranUserService userService(@Autowired UserStore userStore) {
        return new ZoranUserServiceImpl(userStore);
    }

    @Bean
    @SecurityDisabled
    ZoranUserService disabledSecurityUserService(@Autowired OAuthAuthorizationService service) {
        return new DisabledSecurityUserService(service);
    }
}
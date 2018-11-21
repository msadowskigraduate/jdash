package io.zoran.core.infrastructure.configuration;

import io.zoran.application.common.mappers.MapperFactory;
import io.zoran.core.application.user.UserStore;
import io.zoran.core.application.user.ZoranUserService;
import io.zoran.core.application.user.ZoranUserServiceImpl;
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
    ZoranUserService userService(@Autowired MapperFactory mapperFactory, @Autowired UserStore userStore) {
        return new ZoranUserServiceImpl(mapperFactory, userStore);
    }

}
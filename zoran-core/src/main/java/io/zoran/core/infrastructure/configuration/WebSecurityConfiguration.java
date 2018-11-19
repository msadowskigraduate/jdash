package io.zoran.core.infrastructure.configuration;

import io.zoran.core.application.user.ZoranUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @author Michal Sadowski (michal.sadowski@roche.com) on 16.11.2018
 */
class WebSecurityConfiguration {

    @Configuration
    @EnableWebSecurity
    @Order(Ordered.HIGHEST_PRECEDENCE)
    @RequiredArgsConstructor
    @ConditionalOnProperty(value = "application.config.security.enabled", havingValue = "true")
    static class EnabledWebSecurityConfiguration extends WebSecurityConfigurerAdapter {

        private final ZoranUserService userService;

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                    .authorizeRequests()
                    .anyRequest().authenticated()
                    .and()
                        .oauth2Login()
                            .userInfoEndpoint()
                                .userService(userService);
        }
    }

    @Configuration
    @ConditionalOnMissingBean(EnabledWebSecurityConfiguration.class)
    static class DisabledWebSecurityConfiguration extends WebSecurityConfigurerAdapter {
        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.cors()
                    .disable();
        }
    }
}

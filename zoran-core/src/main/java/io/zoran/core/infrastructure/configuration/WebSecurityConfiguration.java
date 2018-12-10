package io.zoran.core.infrastructure.configuration;

import io.zoran.core.application.user.ZoranUserService;
import io.zoran.core.infrastructure.SecuredBlock;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import static org.springframework.core.Ordered.HIGHEST_PRECEDENCE;

/**
 * @author Michal Sadowski (michal.sadowski@roche.com) on 16.11.2018
 */
class WebSecurityConfiguration {

    @SecuredBlock
    @Configuration
    @EnableWebSecurity
    @Order(HIGHEST_PRECEDENCE)
    @RequiredArgsConstructor
    static class EnabledWebSecurityConfiguration extends WebSecurityConfigurerAdapter {

        private final ZoranUserService userService;

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                    .authorizeRequests()
                    .antMatchers("/build-info",
                            "/swagger-ui.html",
                            "/swagger-resources").permitAll()
                    .anyRequest().authenticated()
                    .and()
                    .oauth2Login()
                    .userInfoEndpoint()
                    .userService(userService)
                    .and().and()
                    .exceptionHandling()
                    .accessDeniedPage("/401");
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

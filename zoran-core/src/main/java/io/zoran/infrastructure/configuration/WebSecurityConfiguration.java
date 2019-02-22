package io.zoran.infrastructure.configuration;

import io.zoran.application.user.ZoranUserService;
import io.zoran.infrastructure.SecurityEnabled;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

import static org.springframework.core.Ordered.HIGHEST_PRECEDENCE;

/**
 * @author Michal Sadowski (michal.sadowski@roche.com) on 16.11.2018
 */
class WebSecurityConfiguration {

    @SecurityEnabled
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
                            "/swagger-resources", "/userinfo").permitAll()
                    .anyRequest().authenticated()
                    .and()
                    .oauth2Login()
                    .userInfoEndpoint()
                    .userService(userService)
                    .and()
                    .failureUrl("/401")
                    .and()
                    .exceptionHandling()
                    .accessDeniedPage("/401");
        }
    }

    @Configuration
    @ConditionalOnMissingBean(EnabledWebSecurityConfiguration.class)
    static class DisabledWebSecurityConfiguration extends WebSecurityConfigurerAdapter {
        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.cors().and().csrf()
                    .disable();
        }

        @Bean
        CorsConfigurationSource corsConfigurationSource() {
            CorsConfiguration configuration = new CorsConfiguration();
            configuration.setAllowedOrigins(Arrays.asList("*"));
            configuration.setAllowedMethods(Arrays.asList("*"));
            configuration.setAllowedHeaders(Arrays.asList("*"));
            configuration.setAllowCredentials(true);
            UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
            source.registerCorsConfiguration("/**", configuration);
            return source;
        }
    }
}

package io.zoran.core.infrastructure.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoTokenServices;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.filter.CompositeFilter;

import javax.servlet.Filter;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Michal Sadowski (michal.sadowski@roche.com) on 16.11.2018
 */
@Configuration
class WebSecurityConfiguration {

    @Configuration
    @RequiredArgsConstructor
    @ConditionalOnProperty(value = "application.config.security.enabled", havingValue = "false")
    static class EnabledWebSecurityConfiguration extends WebSecurityConfigurerAdapter {

        private final OAuth2ClientContext clientContext;

        @Bean
        @ConfigurationProperties("github.client")
        AuthorizationCodeResourceDetails gitHub() {
            return new AuthorizationCodeResourceDetails();
        }

        @Bean
        @ConfigurationProperties("github.resource")
        ResourceServerProperties githubResource() {
            return new ResourceServerProperties();
        }

        @Bean
        @Scope(scopeName = "singleton")
        Filter ssoFilter() {
            CompositeFilter filter = new CompositeFilter();
            List<Filter> filters = new ArrayList<>();
            OAuth2ClientAuthenticationProcessingFilter githubFilter = new OAuth2ClientAuthenticationProcessingFilter("/login/github");
            OAuth2RestTemplate githubTemplate = new OAuth2RestTemplate(gitHub(), clientContext);
            githubFilter.setRestTemplate(githubTemplate);
            UserInfoTokenServices tokenServices = new UserInfoTokenServices(githubResource().getUserInfoUri(), gitHub()
                    .getClientId());
            tokenServices.setRestTemplate(githubTemplate);
            githubFilter.setTokenServices(tokenServices);
            filters.add(githubFilter);
            filter.setFilters(filters);
            return filter;
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.antMatcher("/**")
                .addFilterBefore(ssoFilter(), BasicAuthenticationFilter.class)
                .authorizeRequests()
                    .antMatchers("/login/**").permitAll()
                    .anyRequest().authenticated()
                .and()
                    .exceptionHandling().authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/"));
        }
    }

    @Configuration
    @ConditionalOnMissingBean(EnabledWebSecurityConfiguration.class)
    static class DisabledWebSecurityConfiguration extends WebSecurityConfigurerAdapter {
        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.cors()
                .disable()
                .antMatcher("/**")
                .anonymous();
        }
    }
}

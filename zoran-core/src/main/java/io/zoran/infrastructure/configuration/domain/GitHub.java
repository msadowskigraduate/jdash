package io.zoran.infrastructure.configuration.domain;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author Michal Sadowski (sadochasee@gmail.com) on 29/12/2018.
 */
@Data
@Component
@ConfigurationProperties(value = "spring.security.oauth2.client.registration.github")
public class GitHub {
    private String client_id;
    private String client_secret;
    private String scopes;
}

package io.zoran.configuration.domain;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author Michal Sadowski (sadochasee@gmail.com) on 29/07/2018.
 */
@Data
@Component
@RequiredArgsConstructor
@ConfigurationProperties()
public class Git {
    private final char[] git_password;
    private final String git_username;
}

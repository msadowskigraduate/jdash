package io.zoran.infrastructure.configuration.domain;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

/**
 * @author Michal Sadowski (sadochasee@gmail.com) on 29/07/2018.
 */
@Data
@Component
@Validated
@RequiredArgsConstructor
@ConfigurationProperties()
public class Git {
    @NonNull
    private final char[] git_password;
    @NonNull
    private final String git_username;
}

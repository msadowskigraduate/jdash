package io.zoran.infrastructure.configuration.domain;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

/**
 * @author Michal Sadowski (sadochasee@gmail.com) on 29/07/2018.
 */
@Data
@Validated
@ConfigurationProperties("application.config")
@Component
public class Zoran {

    @NestedConfigurationProperty
    private StorageProperties properties;

    @NestedConfigurationProperty
    private GeneratorProperties generator;
}

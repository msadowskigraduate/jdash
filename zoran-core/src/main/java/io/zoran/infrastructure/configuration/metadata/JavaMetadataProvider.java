package io.zoran.infrastructure.configuration.metadata;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Michal Sadowski (michal.sadowski@roche.com) on 16.06.2019
 */
@Data
@Component
@ConfigurationProperties("initializr")
class JavaMetadataProvider implements MetadataProvider {
    public static final String JAVA_LOADER_ID = "java";

    @NestedConfigurationProperty
    public List<SingleCapability> javaVersions;

    @Override
    public String getLoaderId() {
        return JAVA_LOADER_ID;
    }

    @Override
    public List<SingleCapability> loadCapabilities() {
        return javaVersions;
    }
}

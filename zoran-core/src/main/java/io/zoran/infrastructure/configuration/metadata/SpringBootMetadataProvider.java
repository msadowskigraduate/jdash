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
class SpringBootMetadataProvider implements MetadataProvider {
    private static final String SPRINGBOOT_LOADER_ID = "spring-boot";

    @NestedConfigurationProperty
    private List<SingleCapability> bootVersions;

    @Override
    public String getLoaderId() {
        return SPRINGBOOT_LOADER_ID;
    }

    @Override
    public List<SingleCapability> loadCapabilities() {
        return this.bootVersions;
    }
}

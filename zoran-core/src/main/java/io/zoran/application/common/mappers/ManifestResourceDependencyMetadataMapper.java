package io.zoran.application.common.mappers;

import io.zoran.api.domain.ResourceDependencyMetadata;
import io.zoran.api.domain.ResourceDependencyMetadataModel;
import io.zoran.domain.manifest.Manifest;
import lombok.experimental.UtilityClass;

/**
 * @author Michal Sadowski (sadochasee@gmail.com) on 07/01/2019.
 */
@UtilityClass
public class ManifestResourceDependencyMetadataMapper {
    public ResourceDependencyMetadata map(String parentIdentifier, Manifest manifest) {
        return ResourceDependencyMetadataModel.of(
                parentIdentifier,
                manifest.getId(),
                manifest.getName(),
                manifest.getDescription(),
                null,
                manifest.getVersion()
        );
    }
}

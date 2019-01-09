package io.zoran.application.common.mappers;

import io.zoran.api.domain.ResourceDependencyMetadata;
import io.zoran.api.domain.ResourceDependencyMetadataModel;
import io.zoran.domain.resource.Resource;
import lombok.experimental.UtilityClass;

/**
 * @author Michal Sadowski (sadochasee@gmail.com) on 07/01/2019.
 */
@UtilityClass
public class ResourceDependencyMetadataMapper {
    public ResourceDependencyMetadata map(String parentIdentifier, Resource resource) {
        return ResourceDependencyMetadataModel.of(
                parentIdentifier,
                resource.getId(),
                resource.getName(),
                resource.getProjectDetails().getDescription(),
                resource.getProjectDetails().getGroupId(),
                resource.getProjectDetails().getVersion()
        );
    }
}

package io.zoran.application.common.mappers;

import io.zoran.api.domain.ResourceDependencyMetadata;
import io.zoran.api.domain.ResourceDependencyMetadataModel;
import io.zoran.domain.generator.DependencyItem;
import io.zoran.domain.manifest.ResourceType;

/**
 * @author Michal Sadowski (michal.sadowski@roche.com) on 11.12.2018
 */
public class DependencyItemToModelMapper {

    public ResourceDependencyMetadata map(DependencyItem dependencyItem) {
        return ResourceDependencyMetadataModel.of(
                "spring",
                dependencyItem.getDependency().getId(),
                dependencyItem.getDependency().getName(),
                dependencyItem.getDependency().getDescription(),
                dependencyItem.getGroup(),
                dependencyItem.getDependency().getVersion(),
                ResourceType.DEPENDENCY
        );
    }
}

package io.zoran.core.infrastructure.resource;

import io.zoran.core.domain.resource.Resource;
import io.zoran.core.domain.resource.ResourceVisibility;
import io.zoran.core.domain.resource.dto.ProjectResourceDto;
import io.zoran.core.domain.resource.project.ProjectResourceImpl;
import lombok.experimental.UtilityClass;

/**
 * @author Michal Sadowski (michal.sadowski@roche.com) on 23.11.2018
 */
@UtilityClass
public class ResourceConverter {
    public static ProjectResourceImpl convert(ProjectResourceDto dto) {
        return ProjectResourceImpl.builder()
                                  .resourceName(dto.getProjectName())
                                  .resourceVisibility(ResourceVisibility.valueOf(dto.getResourceVisibility()))
                                  .build();
    }

    public static ProjectResourceDto convert(Resource pojo) {
        return new ProjectResourceDto(pojo.getId(), pojo.getName(), pojo.getVisibility().name(), pojo.getOwner(), pojo.isProject() ? "Project" : "Other");
    }
}
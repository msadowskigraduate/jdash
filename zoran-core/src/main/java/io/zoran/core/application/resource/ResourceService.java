package io.zoran.core.application.resource;

import io.zoran.core.domain.resource.Resource;
import io.zoran.core.domain.resource.ResourceVisibility;
import io.zoran.core.domain.resource.dto.ProjectResourceDto;
import io.zoran.core.infrastructure.exception.ResourceNotFoundException;

import java.util.List;

/**
 * @author Michal Sadowski (sadochasee@gmail.com) on 18/11/2018.
 */
public interface ResourceService {
    List<Resource> getAllResourcesOwnedBy(String userId);
    List<String> getAllSharedResources(String userId);
    Resource getResourceById(String resourceId) throws ResourceNotFoundException;
    Resource createNewResource(ProjectResourceDto resource, String owner);
    Resource transferOwnership(String resourceId, String newOwnerId);
    List<Resource> getAllResources(ResourceVisibility visibility);
    Resource deleteResource(String resourceId);
}

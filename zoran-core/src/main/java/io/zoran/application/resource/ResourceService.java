package io.zoran.application.resource;

import io.zoran.api.domain.ProjectResourceRequest;
import io.zoran.domain.resource.Resource;
import io.zoran.domain.resource.ResourceVisibility;
import io.zoran.infrastructure.exception.ResourceNotFoundException;

import java.util.List;

/**
 * @author Michal Sadowski (sadochasee@gmail.com) on 18/11/2018.
 */
public interface ResourceService {
    List<Resource> getAllResourcesOwnedBy(String userId);
    List<String> getAllSharedResources(String userId);
    Resource getResourceById(String resourceId) throws ResourceNotFoundException;
    Resource createNewResource(ProjectResourceRequest resource, String owner);
    Resource createNewResource(Resource resource, String owner);
    Resource transferOwnership(String resourceId, String newOwnerId);
    List<Resource> getAllResources(ResourceVisibility visibility);
    Resource deleteResource(String resourceId);
}

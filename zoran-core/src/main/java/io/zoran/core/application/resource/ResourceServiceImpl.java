package io.zoran.core.application.resource;

import io.zoran.core.domain.resource.Resource;
import io.zoran.core.domain.resource.shared.SharedProjectResource;
import io.zoran.core.infrastructure.exception.ResourceNotFoundException;
import io.zoran.core.infrastructure.resource.ResourceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * @author Michal Sadowski (michal.sadowski@roche.com) on 20.11.2018
 */
@Service
@RequiredArgsConstructor
public class ResourceServiceImpl implements ResourceService {
    private final ResourceRepository resourceRepository;
    private final SharedResourceService sharedResourceService;

    @Override
    public List<Resource> getAllResourcesOwnedBy(String userId) {
        List<Resource> resourceList = resourceRepository.findAllByOwner(userId);
        return resourceList;
    }

    @Override
    public List<String> getAllSharedResources(String userId) {
        List<SharedProjectResource> resources = sharedResourceService.getAllForUser(userId);
        return resources.stream()
                        .map(SharedProjectResource::getProjectId)
                        .collect(toList());
    }

    @Override
    public Resource getResourceById(String resourceId) {
        return resourceRepository.findById(resourceId).orElseThrow(() -> new ResourceNotFoundException(""));
    }
}
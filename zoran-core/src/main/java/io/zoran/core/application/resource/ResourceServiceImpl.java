package io.zoran.core.application.resource;

import io.zoran.core.application.audit.Audited;
import io.zoran.core.domain.resource.Resource;
import io.zoran.core.domain.resource.ResourceVisibility;
import io.zoran.core.domain.resource.dto.ProjectResourceDto;
import io.zoran.core.domain.resource.project.ProjectResourceImpl;
import io.zoran.core.domain.resource.shared.SharingGroup;
import io.zoran.core.infrastructure.exception.ResourceNotFoundException;
import io.zoran.core.infrastructure.resource.ResourceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static io.zoran.core.domain.audit.AuditAction.NEW_RESOURCE_CREATED;
import static io.zoran.core.domain.audit.AuditAction.RESOURCE_OWNERSHIP_MODIFIED;
import static java.util.stream.Collectors.toList;

/**
 * @author Michal Sadowski (michal.sadowski@roche.com) on 20.11.2018
 */
@Service
@RequiredArgsConstructor
public class ResourceServiceImpl implements ResourceService {
    private final ResourceRepository resourceRepository;
    private final SharingGroupService sharingGroupService;

    @Override
    public List<Resource> getAllResourcesOwnedBy(String userId) {
        return resourceRepository.findAllByOwner(userId);
    }

    @Override
    public List<String> getAllSharedResources(String userId) {
        List<SharingGroup> resources = sharingGroupService.getAllForUser(userId);
        return resources.stream()
                        .map(SharingGroup::getProjectId)
                        .collect(toList());
    }

    @Override
    public Resource getResourceById(String resourceId) {
        return resourceRepository.findById(resourceId).orElseThrow(() -> new ResourceNotFoundException(""));
    }

    //TODO
    @Audited(NEW_RESOURCE_CREATED)
    @Override
    public Resource createNewResource(ProjectResourceDto dto, String ownerId) {
        ProjectResourceImpl resource = ProjectResourceImpl.builder()
                                                          .resourceName(dto.getProjectName())
                                                          .ownerId(ownerId)
                                                          .resourceVisibility(ResourceVisibility.valueOf(dto.getResourceVisibility()))
                                                          .creationDate(LocalDateTime.now())
                                                          .build();
        return resourceRepository.save(resource);
    }

    @Audited(RESOURCE_OWNERSHIP_MODIFIED)
    @Override
    public Resource transferOwnership(String resourceId, String newOwnerId) {
        Resource resource = resourceRepository.findById(resourceId)
                                  .orElseThrow(() -> new ResourceNotFoundException(resourceId));
        return upSert(resource.transferOwnership(newOwnerId));
    }

    @Override
    public List<Resource> getAllResources(ResourceVisibility visibility) {
        if(visibility.equals(ResourceVisibility.ALL)) {
            return resourceRepository.findAll();
        }
        return resourceRepository.findAllByVisibility(visibility);
    }

    @Override
    public Resource deleteResource(String resourceId) {
        Resource r = resourceRepository.findById(resourceId).orElseThrow(() -> new ResourceNotFoundException(resourceId));
        resourceRepository.deleteById(r.getId());
        return r;
    }

    private Resource upSert(Resource resource) {
        resourceRepository.deleteById(resource.getId());
        return resourceRepository.save(resource);
    }
}
package io.zoran.application.resource;

import io.zoran.api.domain.ProjectResourceRequest;
import io.zoran.application.audit.Audited;
import io.zoran.application.indexer.TemplateFactory;
import io.zoran.domain.git.License;
import io.zoran.domain.manifest.Template;
import io.zoran.domain.resource.Resource;
import io.zoran.domain.resource.ResourceVisibility;
import io.zoran.domain.resource.project.ProjectResource;
import io.zoran.domain.resource.shared.SharingGroup;
import io.zoran.infrastructure.exception.ResourceNotFoundException;
import io.zoran.infrastructure.integrations.license.LicenseService;
import io.zoran.infrastructure.resource.ResourceConverter;
import io.zoran.infrastructure.resource.ResourceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static io.zoran.domain.audit.AuditAction.NEW_RESOURCE_CREATED;
import static io.zoran.domain.audit.AuditAction.RESOURCE_OWNERSHIP_MODIFIED;
import static java.util.stream.Collectors.toList;

/**
 * @author Michal Sadowski (michal.sadowski@roche.com) on 20.11.2018
 */
@Service
@RequiredArgsConstructor
public class ResourceServiceImpl implements ResourceService {
    private final ResourceRepository resourceRepository;
    private final LicenseService licenseService;
    private final SharingGroupService sharingGroupService;
    private final TemplateFactory templateFactory;

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

    @Audited(NEW_RESOURCE_CREATED)
    @Override
    public Resource createNewResource(ProjectResourceRequest dto, String ownerId) {
        License license = licenseService.getOrDefault(dto.getLicenseKey());
        ProjectResource resource = ResourceConverter.convert(dto, license, ownerId);
        resource.setTemplateData(dto.getTemplateTuples()
                                    .stream()
                                    .map(x -> {
                                        Template t = templateFactory.getTemplateForSlug(x.getTemplateSlug());
                                        t.setContext(x.getContexts());
                                        return t;
                                    })
                                    .collect(toList()));
        resource.setCreationDate(LocalDateTime.now());
        return resourceRepository.save(resource);
    }

    @Audited(NEW_RESOURCE_CREATED)
    @Override
    public Resource createNewResource(Resource resource, String owner) {
        if(resource instanceof ProjectResource) {
            ((ProjectResource) resource).setCreationDate(LocalDateTime.now());
        }
        return resourceRepository.findByUrl(resource.getUrl())
                                 .orElseGet(() -> resourceRepository.save(resource));
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
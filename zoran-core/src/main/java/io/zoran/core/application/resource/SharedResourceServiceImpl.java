package io.zoran.core.application.resource;

import io.zoran.core.application.audit.Audited;
import io.zoran.core.domain.audit.AuditAction;
import io.zoran.core.domain.resource.ResourcePrivileges;
import io.zoran.core.domain.resource.shared.SharedProjectResource;
import io.zoran.core.infrastructure.exception.ResourceNotFoundException;
import io.zoran.core.infrastructure.resource.SharedResourceRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Michal Sadowski (michal.sadowski@roche.com) on 20.11.2018
 */
@Service
@RequiredArgsConstructor
public class SharedResourceServiceImpl implements SharedResourceService {

    private final SharedResourceRepository repository;

    //TODO Security consideration -> possibly exposing internal data structure
    @Override
    public SharedProjectResource getSharedProjectResourceFor(@NonNull String projectId) {
        return repository.findByProjectId(projectId).orElseThrow(() -> new ResourceNotFoundException(projectId));
    }

    @Override
    public void giveAccess(@NonNull String projectId, @NonNull String userId, @NonNull String access) {
        ResourcePrivileges accessPrivilege = ResourcePrivileges.valueOf(access);
        SharedProjectResource resource = this.getSharedProjectResourceFor(projectId);
        upSert(resource.giveAccess(userId, accessPrivilege));
    }

    @Override
    public void revokeAccessFor(@NonNull String projectId, @NonNull String userId) {
        SharedProjectResource resource = this.getSharedProjectResourceFor(projectId);
        upSert(resource.revokeAccessFor(userId));
    }

    @Override
    public String getAccessPrivilegeFor(@NonNull String projectId, @NonNull String userId) {
        SharedProjectResource resource = this.getSharedProjectResourceFor(projectId);
        return resource.getAccessFor(userId).name();
    }

    @Override
    public Map<String, String> getAuthorizedUsersList(@NonNull String projectId) {
        SharedProjectResource resource = this.getSharedProjectResourceFor(projectId);
        return resource.getPriviligesMap().entrySet()
                .stream()
                .collect(Collectors.toMap(Map.Entry::getKey, x -> x.getValue().name()));
    }

    //TODO Security consideration -> possibly exposing internal data structure
    @Override
    public List<SharedProjectResource> getAllForUser(@NonNull String userId) {
        return repository.findAllResourcesWhereUserIsAMember(userId);
    }

    @Audited(value = AuditAction.SHARED_RESOURCE_PRIVILEGE_CHANGED)
    private void upSert(SharedProjectResource resource) {
        repository.deleteById(resource.getSharedResourceId());
        repository.save(resource);
    }
}

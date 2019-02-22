package io.zoran.application.resource;

import io.zoran.application.audit.Audited;
import io.zoran.domain.resource.ResourcePrivileges;
import io.zoran.domain.resource.shared.SharingGroup;
import io.zoran.infrastructure.resource.SharingGroupRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static io.zoran.domain.audit.AuditAction.RESOURCE_OWNERSHIP_MODIFIED;

/**
 * @author Michal Sadowski (michal.sadowski@roche.com) on 20.11.2018
 */
@Service
@RequiredArgsConstructor
public class SharingGroupServiceImpl implements SharingGroupService {

    private final SharingGroupRepository repository;

    //TODO Security consideration -> possibly exposing internal data structure
    @Override
    public SharingGroup getSharingGroupForProject(@NonNull String projectId) {
        return repository.findByProjectId(projectId).orElseGet(() -> this.createNewSharingGroup(projectId));
    }

    @Override
    @Audited(RESOURCE_OWNERSHIP_MODIFIED)
    public void giveAccess(@NonNull String projectId, @NonNull String userId, @NonNull String access) {
        ResourcePrivileges accessPrivilege = ResourcePrivileges.valueOf(access);
        SharingGroup resource = this.getSharingGroupForProject(projectId);
        upSert(resource.giveAccess(userId, accessPrivilege));
    }

    @Override
    @Audited(RESOURCE_OWNERSHIP_MODIFIED)
    public void revokeAccessFor(@NonNull String projectId, @NonNull String userId) {
        SharingGroup resource = this.getSharingGroupForProject(projectId);
        upSert(resource.revokeAccessFor(userId));
    }

    @Override
    public String getAccessPrivilegeFor(@NonNull String projectId, @NonNull String userId) {
        SharingGroup resource = this.getSharingGroupForProject(projectId);
        return resource.getAccessFor(userId).name();
    }

    @Override
    public Map<String, String> getAuthorizedUsersList(@NonNull String projectId) {
        SharingGroup resource = this.getSharingGroupForProject(projectId);
        return resource.getPriviligesMap().entrySet()
                .stream()
                .collect(Collectors.toMap(Map.Entry::getKey, x -> x.getValue().name()));
    }

    //TODO Security consideration -> possibly exposing internal data structure
    @Override
    public List<SharingGroup> getAllForUser(@NonNull String userId) {
        List<SharingGroup> list = repository.findAllResourcesWhereUserIsAMember(userId);
        if(list == null) {
            return Collections.EMPTY_LIST;
        }
        return list;
    }

    @Override
    public void deleteGroupForResource(String resourceId) {
        Optional<SharingGroup> sG = repository.findByProjectId(resourceId);
        sG.ifPresent(sharingGroup -> repository.deleteById(sharingGroup.getSharedResourceId()));
    }

    @Override
    public SharingGroup createNewSharingGroup(String projectId) {
        SharingGroup sharingGroup = SharingGroup.builder()
                .projectId(projectId)
                .build();
        return repository.save(sharingGroup);
    }

    private void upSert(SharingGroup resource) {
        repository.deleteById(resource.getSharedResourceId());
        repository.save(resource);
    }
}

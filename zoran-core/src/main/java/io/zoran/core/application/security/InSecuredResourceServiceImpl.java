package io.zoran.core.application.security;

import io.zoran.core.application.resource.ResourceService;
import io.zoran.core.application.resource.SharingGroupService;
import io.zoran.core.application.user.ZoranUserService;
import io.zoran.core.domain.resource.Resource;
import io.zoran.core.domain.resource.dto.ProjectResourceDto;
import io.zoran.core.domain.resource.shared.SharingGroup;
import io.zoran.core.infrastructure.NoSecurity;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;

import static io.zoran.core.infrastructure.resource.ResourceConverter.convert;
import static java.util.stream.Collectors.toList;

/**
 * @author Michal Sadowski (michal.sadowski@roche.com) on 20.11.2018
 */
@NoSecurity
@RequiredArgsConstructor
class InSecuredResourceServiceImpl implements SecurityResourceService {

    private final ResourceService resourceService;
    private final SharingGroupService sharingGroupService;
    private final ZoranUserService zoranUserService;

    @Override
    public Resource authoriseResourceRequest(String resourceId) {
        return resourceService.getResourceById(resourceId);
    }

    @Override
    public List<Resource> authoriseAllResourcesOwnedByRequest() {
        return resourceService.getAllResources();
    }

    @Override
    public List<Resource> authorizedGetAllResourcesConnectedWithPrincipal() {
        return resourceService.getAllResources();
    }

    @Override
    public SharingGroup authorizedGetSharingGroupForProject(String projectId) {
        return sharingGroupService.getSharingGroupForProject(projectId);
    }

    @Override
    public void authoriseGiveAccessRequest(String projectId, String userId, String access) {
        sharingGroupService.giveAccess(projectId, userId, access);
    }

    @Override
    public void authoriseRevokeAccessForRequest(String projectId, String userId) {
        sharingGroupService.revokeAccessFor(projectId, userId);
    }

    @Override
    public Map<String, String> authoriseGetAuthorizedUsersListRequest(String projectId) {
        return sharingGroupService.getAuthorizedUsersList(projectId);
    }

    @Override
    public List<SharingGroup> authoriseGetAllForUserRequest() {
        return authorizedGetAllResourcesConnectedWithPrincipal()
                .stream()
                .map(x -> sharingGroupService.getSharingGroupForProject(x.getId()))
                .collect( toList());
    }

    @Override
    public ProjectResourceDto newResource(ProjectResourceDto dto) {
        Resource resource = resourceService.createNewResource(dto, zoranUserService.authenticateAndGetUserId());
        sharingGroupService.createNewSharingGroup(resource.getId());
        return convert(resource);
    }

    @Override
    public ProjectResourceDto deleteResource(String resourceId) {
        return convert(resourceService.deleteResource(resourceId));
    }

    @Override
    public ProjectResourceDto transferOwnership(String resourceId, String newOwnerId) {
        return convert(resourceService.transferOwnership(resourceId, newOwnerId));
    }
}

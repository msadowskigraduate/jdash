package io.zoran.application.security;

import io.zoran.api.domain.ProjectResourceRequest;
import io.zoran.application.resource.ResourceService;
import io.zoran.application.resource.SharingGroupService;
import io.zoran.application.user.ZoranUserService;
import io.zoran.domain.resource.Resource;
import io.zoran.api.domain.ResourceResponse;
import io.zoran.domain.resource.shared.SharingGroup;
import io.zoran.infrastructure.SecurityDisabled;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;

import static io.zoran.domain.resource.ResourceVisibility.ALL;
import static io.zoran.infrastructure.resource.ResourceConverter.convert;
import static java.util.stream.Collectors.toList;

/**
 * @author Michal Sadowski (michal.sadowski@roche.com) on 20.11.2018
 */
@SecurityDisabled
@RequiredArgsConstructor
public class DisabledSecurityResourceServiceImpl implements SecurityResourceService {

    private final ResourceService resourceService;
    private final SharingGroupService sharingGroupService;
    private final ZoranUserService zoranUserService;

    @Override
    public Resource authoriseResourceRequest(String resourceId) {
        return resourceService.getResourceById(resourceId);
    }

    @Override
    public List<Resource> authoriseAllResourcesOwnedByRequest() {
        return resourceService.getAllResources(ALL);
    }

    @Override
    public List<Resource> authorizedGetAllResourcesConnectedWithPrincipal() {
        return resourceService.getAllResources(ALL);
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
    public ResourceResponse newResource(ProjectResourceRequest dto) {
        Resource resource = resourceService.createNewResource(dto, zoranUserService.authenticateAndGetUserId());
        sharingGroupService.createNewSharingGroup(resource.getId());
        return convert(resource);
    }

    @Override
    public ResourceResponse deleteResource(String resourceId) {
        return convert(resourceService.deleteResource(resourceId));
    }

    @Override
    public ResourceResponse transferOwnership(String resourceId, String newOwnerId) {
        return convert(resourceService.transferOwnership(resourceId, newOwnerId));
    }
}

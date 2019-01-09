package io.zoran.application.security;

import io.zoran.api.domain.ProjectResourceRequest;
import io.zoran.application.resource.ResourceService;
import io.zoran.application.resource.SharingGroupService;
import io.zoran.application.user.ZoranUserService;
import io.zoran.domain.resource.Resource;
import io.zoran.domain.resource.ResourceVisibility;
import io.zoran.api.domain.ResourceResponse;
import io.zoran.domain.resource.shared.SharingGroup;
import io.zoran.infrastructure.SecuredBlock;
import io.zoran.infrastructure.exception.ResourceNotFoundException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.common.exceptions.UnauthorizedUserException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Stream;

import static io.zoran.domain.resource.ResourcePrivileges.*;
import static io.zoran.infrastructure.resource.ResourceConverter.convert;
import static java.util.stream.Collectors.toList;

/**
 * @author Michal Sadowski (michal.sadowski@roche.com) on 20.11.2018
 *
 * Validates if current user is allowed to to the requested resources. Serves them if necessary.
 *
 */
@SecuredBlock
@Service
@RequiredArgsConstructor
public class SecuredResourceServiceImpl implements SecurityResourceService {
    private final ZoranUserService zoranUserService;
    private final ResourceService resourceService;
    private final SharingGroupService sharingGroupService;

    private Predicate<SharingGroup> filterRevoked(String ownerUserId) {
        return x -> !x.getPriviligesMap().get(ownerUserId).equals(REVOKED);
    }

    private Predicate<SharingGroup> canReadOrWrite(String ownerUserId) {
        return x -> x.getAccessFor(ownerUserId).equals(READ) || x.getAccessFor(ownerUserId).equals(WRITE);
    }

    private String getAuthenticatedUserId() {
        return zoranUserService.authenticateAndGetUserId();
    }

    @Override
    public Resource authoriseResourceRequest(@NonNull String projectId) {
        String ownerUserId = this.getAuthenticatedUserId();
        Resource resource = resourceService.getResourceById(projectId);
        if(resource.getOwner().equalsIgnoreCase(ownerUserId)) {
            return resource;
        }

        SharingGroup shared = sharingGroupService.getAllForUser(ownerUserId)
                                                 .stream()
                                                 .filter(canReadOrWrite(ownerUserId))
                                                 .findFirst().orElseThrow(() -> new ResourceNotFoundException(projectId));
        return resourceService.getResourceById(shared.getProjectId());
    }

    @Override
    public List<Resource> authoriseAllResourcesOwnedByRequest() {
        String ownerUserId = this.getAuthenticatedUserId();
        return resourceService.getAllResourcesOwnedBy(ownerUserId);
    }

    @Override
    public List<Resource> authorizedGetAllResourcesConnectedWithPrincipal() {
        String ownerUserId = this.getAuthenticatedUserId();
        List<SharingGroup> sharingGroupList = sharingGroupService.getAllForUser(ownerUserId);
        return Stream.concat(sharingGroupList
                .stream()
                .filter(filterRevoked(ownerUserId))
                .map(SharingGroup::getProjectId)
                .map(resourceService::getResourceById),
                resourceService.getAllResources(ResourceVisibility.PUBLIC).stream())
                .collect(toList());
    }

    @Override
    public SharingGroup authorizedGetSharingGroupForProject(@NonNull String projectId) {
        String ownerUserId = this.getAuthenticatedUserId();
        Resource resource = resourceService.getResourceById(projectId);
        if(resource.getOwner().equalsIgnoreCase(ownerUserId)) {
            return sharingGroupService.getSharingGroupForProject(projectId);
        } else {
            throw new UnauthorizedUserException(OAuth2Exception.INSUFFICIENT_SCOPE);
        }
    }

    @Override
    public void authoriseGiveAccessRequest(@NonNull String projectId, @NonNull String userId, @NonNull String access) {
        String ownerUserId = this.getAuthenticatedUserId();
        Resource resource = resourceService.getResourceById(projectId);
        if(resource.getOwner().equalsIgnoreCase(ownerUserId)) {
            sharingGroupService.giveAccess(projectId, userId, access);
        } else {
            throw new UnauthorizedUserException(OAuth2Exception.INSUFFICIENT_SCOPE);
        }
    }

    @Override
    public void authoriseRevokeAccessForRequest(@NonNull String projectId, @NonNull String userId) {
        String ownerUserId = this.getAuthenticatedUserId();
        Resource resource = resourceService.getResourceById(projectId);
        if(resource.getOwner().equalsIgnoreCase(ownerUserId)) {
            sharingGroupService.revokeAccessFor(projectId, userId);
        } else {
            throw new UnauthorizedUserException(OAuth2Exception.INSUFFICIENT_SCOPE);
        }
    }

    @Override
    public Map<String, String> authoriseGetAuthorizedUsersListRequest(@NonNull String projectId) {
        String ownerUserId = this.getAuthenticatedUserId();
        Resource resource = resourceService.getResourceById(projectId);
        if(resource.getOwner().equalsIgnoreCase(ownerUserId)) {
            return sharingGroupService.getAuthorizedUsersList(projectId);
        }
        throw new UnauthorizedUserException(OAuth2Exception.INSUFFICIENT_SCOPE);
    }

    @Override
    public List<SharingGroup> authoriseGetAllForUserRequest() {
        String ownerUserId = this.getAuthenticatedUserId();
        return sharingGroupService.getAllForUser(ownerUserId).stream()
                                  .filter(filterRevoked(ownerUserId))
                                  .collect(toList());
    }

    @Override
    public ResourceResponse newResource(ProjectResourceRequest dto) {
        Resource resource = resourceService.createNewResource(dto, getAuthenticatedUserId());
        sharingGroupService.createNewSharingGroup(resource.getId());
        return convert(resource);
    }

    @Override
    public ResourceResponse deleteResource(String resourceId) {
        String ownerId = getAuthenticatedUserId();
        Resource resource = resourceService.getResourceById(resourceId);
        if(resource.getOwner().equalsIgnoreCase(ownerId)) {
            return convert(resourceService.deleteResource(resourceId));
        }
        throw new UnauthorizedUserException(OAuth2Exception.INSUFFICIENT_SCOPE);
    }

    @Override
    public ResourceResponse transferOwnership(String resourceId, String newOwnerId) {
        String ownerId = getAuthenticatedUserId();
        Resource resource = resourceService.getResourceById(resourceId);
        if(resource.getOwner().equalsIgnoreCase(ownerId)) {
            return convert(resourceService.transferOwnership(resourceId, newOwnerId));
        }
        throw new UnauthorizedUserException(OAuth2Exception.INSUFFICIENT_SCOPE);
    }
}
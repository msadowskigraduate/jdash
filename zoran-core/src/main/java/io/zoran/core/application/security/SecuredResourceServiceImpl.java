package io.zoran.core.application.security;

import io.zoran.core.application.resource.ResourceService;
import io.zoran.core.application.resource.SharedResourceService;
import io.zoran.core.application.user.ZoranUserService;
import io.zoran.core.domain.resource.Resource;
import io.zoran.core.domain.resource.ResourcePrivileges;
import io.zoran.core.domain.resource.shared.SharedProjectResource;
import io.zoran.core.infrastructure.exception.ResourceNotFoundException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.common.exceptions.UnauthorizedUserException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

/**
 * @author Michal Sadowski (michal.sadowski@roche.com) on 20.11.2018
 *
 * Validates if current user is allowed to to the requested resources. Serves them if necessary.
 *
 */
@Service
@RequiredArgsConstructor
public class SecuredResourceServiceImpl implements SecurityResourceService {
    private final ZoranUserService zoranUserService;
    private final ResourceService resourceService;
    private final SharedResourceService sharedResourceService;

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

        SharedProjectResource shared = sharedResourceService.getAllForUser(ownerUserId)
                .stream()
                .filter(x -> x.getAccessFor(ownerUserId).equals(ResourcePrivileges.WRITE) ||
                x.getAccessFor(ownerUserId).equals(ResourcePrivileges.READ))
                .findFirst().orElseThrow(() -> new ResourceNotFoundException(projectId));
        return resourceService.getResourceById(shared.getProjectId());
    }

    @Override
    public List<Resource> authoriseAllResourcesOwnedByRequest() {
        String ownerUserId = this.getAuthenticatedUserId();
        return resourceService.getAllResourcesOwnedBy(ownerUserId);
    }

    @Override
    public List<String> authoriseAllSharedResourcesRequest() {
        String ownerUserId = this.getAuthenticatedUserId();
        List<SharedProjectResource> sharedProjectResourceList = sharedResourceService.getAllForUser(ownerUserId);
        return sharedProjectResourceList.stream().map(SharedProjectResource::getProjectId).collect(toList());
    }

    @Override
    public SharedProjectResource authoriseSharedProjectResourceForRequest(@NonNull String projectId) {
        String ownerUserId = this.getAuthenticatedUserId();
        Resource resource = resourceService.getResourceById(projectId);
        if(resource.getOwner().equalsIgnoreCase(ownerUserId)) {
            sharedResourceService.getSharedProjectResourceFor(projectId);
        } else {
            throw new UnauthorizedUserException(OAuth2Exception.INSUFFICIENT_SCOPE);
        }
    }

    @Override
    public void authoriseGiveAccessRequest(@NonNull String projectId, @NonNull String userId, @NonNull String access) {
        String ownerUserId = this.getAuthenticatedUserId();
        Resource resource = resourceService.getResourceById(projectId);
        if(resource.getOwner().equalsIgnoreCase(ownerUserId)) {
            sharedResourceService.giveAccess(projectId, userId, access);
        } else {
            throw new UnauthorizedUserException(OAuth2Exception.INSUFFICIENT_SCOPE);
        }
    }

    @Override
    public void authoriseRevokeAccessForRequest(@NonNull String projectId, @NonNull String userId) {
        String ownerUserId = this.getAuthenticatedUserId();
        Resource resource = resourceService.getResourceById(projectId);
        if(resource.getOwner().equalsIgnoreCase(ownerUserId)) {
            sharedResourceService.revokeAccessFor(projectId, userId);
        } else {
            throw new UnauthorizedUserException(OAuth2Exception.INSUFFICIENT_SCOPE);
        }
    }

    @Override
    public Map<String, String> authoriseGetAuthorizedUsersListRequest(@NonNull String projectId) {
        String ownerUserId = this.getAuthenticatedUserId();
        Resource resource = resourceService.getResourceById(projectId);
        if(resource.getOwner().equalsIgnoreCase(ownerUserId)) {
            return sharedResourceService.getAuthorizedUsersList(projectId);
        }
        throw new UnauthorizedUserException(OAuth2Exception.INSUFFICIENT_SCOPE);
    }

    @Override
    public List<SharedProjectResource> authoriseGetAllForUserRequest() {
        return null;
    }
}
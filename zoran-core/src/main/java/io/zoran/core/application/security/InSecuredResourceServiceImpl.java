package io.zoran.core.application.security;

import io.zoran.core.application.resource.SharedResourceService;
import io.zoran.core.domain.resource.Resource;
import io.zoran.core.domain.resource.shared.SharedProjectResource;

import java.util.List;
import java.util.Map;

/**
 * @author Michal Sadowski (michal.sadowski@roche.com) on 20.11.2018
 */
class InSecuredResourceServiceImpl implements SecurityResourceService {
    @Override
    public Resource authoriseResourceRequest(String resourceId) {
        return null;
    }

    @Override
    public SharedResourceService authoriseSharedResourceRequest(String sharedResourceId) {
        return null;
    }

    @Override
    public List<Resource> authoriseAllResourcesOwnedByRequest(String userId) {
        return null;
    }

    @Override
    public List<String> authoriseAllSharedResourcesRequest(String userId) {
        return null;
    }

    @Override
    public SharedProjectResource authoriseSharedProjectResourceForRequest(String projectId) {
        return null;
    }

    @Override
    public void authoriseGiveAccessRequest(String projectId, String userId, String access) {

    }

    @Override
    public void authoriseRevokeAccessForRequest(String projectId, String userId) {

    }

    @Override
    public Map<String, String> authoriseGetAuthorizedUsersListRequest(String projectId) {
        return null;
    }

    @Override
    public List<SharedProjectResource> authoriseGetAllForUserRequest(String userId) {
        return null;
    }
}

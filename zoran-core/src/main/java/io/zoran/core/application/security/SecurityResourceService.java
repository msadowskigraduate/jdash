package io.zoran.core.application.security;

import io.zoran.core.application.resource.SharedResourceService;
import io.zoran.core.domain.resource.Resource;
import io.zoran.core.domain.resource.shared.SharedProjectResource;

import java.util.List;
import java.util.Map;

/**
 * @author Michal Sadowski (michal.sadowski@roche.com) on 20.11.2018
 */
interface SecurityResourceService {
    /**
     * Check if user is authorised to that Resource and return it, if positive.
     * @return Resource
     */
    Resource authoriseResourceRequest(String resourceId);

    /**
     * Check if user is authorised to given SharedResource and return it, if positive.
     * @return SharedResourceService
     */
    SharedResourceService authoriseSharedResourceRequest(String sharedResourceId);

    /**
     * Get all Resources current user is Owner of.
     * @param userId
     * @return
     */
    List<Resource> authoriseAllResourcesOwnedByRequest(String userId);

    /**
     * Get all shared Resources, current user is Collaborating in.
     * @param userId
     * @return
     */
    List<String> authoriseAllSharedResourcesRequest(String userId);

    /**
     * Check if user is authorised to given SharedResource for given project and return it, if positive.
     * @param projectId
     * @return
     */
    SharedProjectResource authoriseSharedProjectResourceForRequest(String projectId);
    void authoriseGiveAccessRequest(String projectId, String userId, String access);
    void authoriseRevokeAccessForRequest(String projectId, String userId);
    String authoriseGetAccessPrivilegeForRequest(String projectId, String userId);
    Map<String, String> authoriseGetAuthorizedUsersListRequest(String projectId);
    List<SharedProjectResource> authoriseGetAllForUserRequest(String userId);

}

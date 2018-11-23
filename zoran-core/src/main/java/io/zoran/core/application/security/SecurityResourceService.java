package io.zoran.core.application.security;

import io.zoran.core.domain.resource.Resource;
import io.zoran.core.domain.resource.dto.ProjectResourceDto;
import io.zoran.core.domain.resource.shared.SharingGroup;

import java.util.List;
import java.util.Map;

/**
 * @author Michal Sadowski (michal.sadowski@roche.com) on 20.11.2018
 */
public interface SecurityResourceService {
    /**
     * Check if user is authorised to that Resource and return it, if positive.
     * @return Resource
     */
    Resource authoriseResourceRequest(String resourceId);

    /**
     * Get all Resources current user is Owner of.
     * @return
     */
    List<Resource> authoriseAllResourcesOwnedByRequest();

    /**
     * Get all shared Resources, current user is Collaborating in.
     * @return
     */
    List<Resource> authorizedGetAllResourcesConnectedWithPrincipal();

    /**
     * Check if user is authorised to given SharedResource for given project and return it, if positive.
     * @param projectId
     * @return
     */
    SharingGroup authorizedGetSharingGroupForProject(String projectId);

    /**
     * Check if user is authorized and grant access to resource for user.
     * @param projectId
     * @param userId
     * @param access
     */
    void authoriseGiveAccessRequest(String projectId, String userId, String access);

    /**
     * Check if user is authorized and revoke access for user.
     * @param projectId
     * @param userId
     */
    void authoriseRevokeAccessForRequest(String projectId, String userId);

//    /**
//     * Check if current user can read access list and get access for user.
//     * @param projectId
//     * @param userId
//     * @return
//     */
//    String authoriseGetAccessPrivilegeForRequest(String projectId, String userId);

    /**
     * Check if current user can read access list and get access list for given project.
     * @param projectId
     * @return
     */
    Map<String, String> authoriseGetAuthorizedUsersListRequest(String projectId);

    /**
     * Get all Shared resources for current user.
     * @return
     */
    List<SharingGroup> authoriseGetAllForUserRequest();

    /**
     * Creates new resource assigned to current user.
     */
    ProjectResourceDto newResource(ProjectResourceDto dto);

    /**
     * Deletes irreversibly a resource identified by its {@param resourceId}
     * @param resourceId
     * @return
     */
    ProjectResourceDto deleteResource(String resourceId);

    /**
     * Transfers ownership of a resource characterized by {@param resourceId} to user of id {@param newOwnerId}.
     * @param resourceId
     * @param newOwnerId
     * @return
     */
    ProjectResourceDto transferOwnership(String resourceId, String newOwnerId);
}

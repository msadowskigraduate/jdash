package io.zoran.core.application.resource;

import io.zoran.core.domain.resource.shared.SharingGroup;

import java.util.List;
import java.util.Map;

/**
 * @author Michal Sadowski (michal.sadowski@roche.com) on 20.11.2018
 */
public interface SharingGroupService {
    SharingGroup getSharingGroupForProject(String projectId);
    void giveAccess(String projectId, String userId, String access);
    void revokeAccessFor(String projectId, String userId);
    String getAccessPrivilegeFor(String projectId, String userId);
    Map<String, String> getAuthorizedUsersList(String projectId);
    List<SharingGroup> getAllForUser(String userId);
    void deleteGroupForResource(String resourceId);
    SharingGroup createNewSharingGroup(String projectId);
}

package io.zoran.core.application.resource;

import io.zoran.core.domain.resource.shared.SharedProjectResource;

import java.util.List;
import java.util.Map;

/**
 * @author Michal Sadowski (michal.sadowski@roche.com) on 20.11.2018
 */
public interface SharedResourceService {
    SharedProjectResource getSharedProjectResourceFor(String projectId);
    void giveAccess(String projectId, String userId, String access);
    void revokeAccessFor(String projectId, String userId);
    String getAccessPrivilegeFor(String projectId, String userId);
    Map<String, String> getAuthorizedUsersList(String projectId);
    List<SharedProjectResource> getAllForUser(String userId);
}

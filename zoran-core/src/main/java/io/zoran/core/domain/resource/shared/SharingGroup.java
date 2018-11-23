package io.zoran.core.domain.resource.shared;

import io.zoran.core.domain.resource.ResourcePrivileges;
import io.zoran.core.infrastructure.exception.ResourceAccessPriviligesException;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashMap;
import java.util.Map;

import static io.zoran.core.infrastructure.exception.ExceptionMessageConstants.RESOURCE_CANNOT_ASSIGN_PRIVILIGES_EXCEEDING;

/**
 * @author Michal Sadowski (michal.sadowski@roche.com) on 20.11.2018
 */
@Document
@Data
@Builder
public final class SharingGroup {
    @Id
    private final String sharedResourceId;
    private final String projectId;
    private Map<String, ResourcePrivileges> priviligesMap;

    public SharingGroup giveAccess(String userId, ResourcePrivileges access) {
        switch (access) {
            case REVOKED: revokeAccessFor(userId); break;
            case OWNER: throw new ResourceAccessPriviligesException(RESOURCE_CANNOT_ASSIGN_PRIVILIGES_EXCEEDING);
            default: getPriviligesMap().put(userId, access); break;
        }
        return this;
    }

    public SharingGroup revokeAccessFor(String userId) {
        getPriviligesMap().remove(userId);
        return this;
    }

    public ResourcePrivileges getAccessFor(String userId) {
        return priviligesMap.getOrDefault(userId, ResourcePrivileges.REVOKED);
    }

    public Map<String, ResourcePrivileges> getPriviligesMap() {
        if(this.priviligesMap == null) {
            this.priviligesMap = new HashMap<>();
            return this.priviligesMap;
        }
        return this.priviligesMap;
    }
}

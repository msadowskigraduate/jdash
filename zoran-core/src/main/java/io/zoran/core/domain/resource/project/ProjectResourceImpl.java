package io.zoran.core.domain.resource.project;

import io.zoran.core.domain.resource.Resource;
import io.zoran.core.domain.resource.ResourceVisibility;
import io.zoran.domain.manifest.Manifest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import static io.zoran.core.domain.resource.ResourceVisibility.PRIVATE;
import static io.zoran.core.domain.resource.ResourceVisibility.PUBLIC;
import static io.zoran.core.domain.resource.ResourceVisibility.SHARED_FRIENDS;

/**
 * @author Michal Sadowski (michal.sadowski@roche.com) on 20.11.2018
 */
@Document
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
final class ProjectResourceImpl implements Resource, Cloneable {
    @Id
    private final String resourceId;

    @DBRef
    private final ProjectDetails details;
    private String ownerId;
    private ResourceVisibility resourceVisibility = PUBLIC;

    @Override
    public String getOwner() {
        return this.ownerId;
    }

    @Override
    public boolean isProject() {
        return true;
    }

    @Override
    public Manifest getManifest() {
        return details.getBillOfMaterials();
    }

    @Override
    public ResourceVisibility getVisibility() {
        return resourceVisibility;
    }

    @Override
    public Resource transferOwnership(String recipientId) {
        this.ownerId = recipientId;
        return this;
    }

    private Resource state(ResourceVisibility resourceVisibility) {
        this.resourceVisibility = resourceVisibility;
        return this;
    }

    public Resource makePrivate() {
        return state(PRIVATE);
    }

    public Resource makePublic() {
        return state(PUBLIC);
    }

    public Resource share() {
        return state(SHARED_FRIENDS);
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}

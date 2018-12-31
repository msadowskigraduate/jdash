package io.zoran.core.domain.resource.project;

import io.zoran.core.domain.resource.Resource;
import io.zoran.core.domain.resource.ResourceVisibility;
import io.zoran.domain.manifest.ResourceType;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.time.LocalDateTime;

import static io.zoran.core.domain.resource.ResourceVisibility.*;

/**
 * @author Michal Sadowski (michal.sadowski@roche.com) on 20.11.2018
 */
//TODO Prawa autorskie
@Document
@Builder
@Data
public final class ProjectResourceImpl implements Resource {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String resourceId;
    private String resourceName;
    @DBRef
    private ProjectDetails details;
    private String ownerId;
    private LocalDateTime creationDate;
    @Builder.Default private ResourceType type;
    @Builder.Default private ResourceVisibility resourceVisibility = PUBLIC;
    @Builder.Default private String licenseAbbrv;

    public String getId() {
        return this.resourceId;
    }

    @Override
    public String getOwner() {
        return this.ownerId;
    }

    @Override
    public String getName() {
        return this.resourceName;
    }

    @Override
    public boolean isProject() {
        return this.type != null && this.type.equals(ResourceType.PROJECT);
    }

    @Override
    public String getManifest() {
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


}

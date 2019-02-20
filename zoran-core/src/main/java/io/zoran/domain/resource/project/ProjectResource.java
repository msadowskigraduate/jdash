package io.zoran.domain.resource.project;

import io.zoran.domain.git.License;
import io.zoran.domain.manifest.ResourceType;
import io.zoran.domain.manifest.Template;
import io.zoran.domain.resource.Resource;
import io.zoran.domain.resource.ResourceVisibility;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.time.LocalDateTime;
import java.util.List;

import static io.zoran.domain.resource.ResourceVisibility.*;

/**
 * @author Michal Sadowski (michal.sadowski@roche.com) on 20.11.2018
 */
@Document
@Builder
@Data
public final class ProjectResource implements Resource {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;
    private String name;
    private String owner;
    //List of dependencies required by this resource coded in format artId:groupId:version
    private List<String> dependencies;
    private LocalDateTime creationDate;
    private ResourceType resourceType;
    private List<Template> templateData;
    @Builder.Default private ResourceVisibility visibility = PUBLIC;

    @DBRef(db = "")
    private License license;
    private ProjectDetails projectDetails;

    @Override
    public Resource transferOwnership(String recipientId) {
        this.owner = recipientId;
        return this;
    }

    @Override
    public String getUrl() {
        return this.projectDetails.getGitUrl();
    }

    @Override
    public boolean isProject() {
        return this.resourceType.equals(ResourceType.PROJECT);
    }

    @Override
    public String toManifest() {
        return null;
    }

    private Resource state(ResourceVisibility resourceVisibility) {
        this.visibility = resourceVisibility;
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
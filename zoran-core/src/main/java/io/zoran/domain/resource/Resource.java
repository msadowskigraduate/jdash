package io.zoran.domain.resource;

import io.zoran.domain.git.License;
import io.zoran.domain.manifest.ResourceType;
import io.zoran.domain.manifest.Template;
import io.zoran.domain.resource.project.ProjectDetails;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Michal Sadowski (michal.sadowski@roche.com) on 20.11.2018
 */
public interface Resource {
    String getId();
    String getName();
    String getOwner();
    String getUrl();
    boolean isProject();
    String toManifest();
    LocalDateTime getCreationDate();
    ResourceType getResourceType();
    ResourceVisibility getVisibility();
    ProjectDetails getProjectDetails();
    License getLicense();
    List<Template> getTemplateData();
    List<String> getDependencies();
    Resource transferOwnership(String recipientId);
}
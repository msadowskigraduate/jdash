package io.zoran.domain.manifest;

import io.zoran.domain.resource.ResourceVisibility;

import java.util.List;

/**
 * @author Michal Sadowski (sadochasee@gmail.com) on 17/07/2018.
 */
public interface Manifest {
    String getName();
    String getPath();
    String getOwner();
    String getVersion();
    String getLead();
    String getDescription();
    String getProjectLanguage();
    String[] getTags();
    Location getPreferredLocation();
    ResourceVisibility getVisibility();
    ResourceType getType();
    List<String> getDependencies();
    String getLicense();
}
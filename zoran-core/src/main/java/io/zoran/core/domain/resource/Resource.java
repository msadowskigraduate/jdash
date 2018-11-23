package io.zoran.core.domain.resource;

/**
 * @author Michal Sadowski (michal.sadowski@roche.com) on 20.11.2018
 */
public interface Resource {
    String getId();
    String getOwner();
    String getName();
    boolean isProject();
    String getManifest();
    ResourceVisibility getVisibility();
    Resource transferOwnership(String recipientId);
}

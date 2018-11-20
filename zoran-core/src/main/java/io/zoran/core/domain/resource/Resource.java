package io.zoran.core.domain.resource;

import io.zoran.domain.manifest.Manifest;

/**
 * @author Michal Sadowski (michal.sadowski@roche.com) on 20.11.2018
 */
public interface Resource {
    String getOwner();
    boolean isProject();
    Manifest getManifest();
    ResourceVisibility getVisibility();
    Resource transferOwnership(String recipientId);
}

package io.zoran.infrastructure.configuration.metadata;

import java.util.List;

/**
 * @author Michal Sadowski (michal.sadowski@roche.com) on 16.06.2019
 */
public interface MetadataProvider {
    String getLoaderId();
    List<SingleCapability> loadCapabilities();
}

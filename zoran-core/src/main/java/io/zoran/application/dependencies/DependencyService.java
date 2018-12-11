package io.zoran.application.dependencies;

import io.zoran.api.domain.ResourceDependencyMetadata;

import java.util.List;

/**
 * @author Michal Sadowski (michal.sadowski@roche.com) on 10.12.2018
 */
public interface DependencyService {

    /**
     * Identifies the service.
     * @return
     */
    String getIdentifier();

    /**
     * Get all available dependencies for given version.
     * @param version
     * @return
     */
    List<ResourceDependencyMetadata> getDependenciesForVersion(String version);
}

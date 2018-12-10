package io.zoran.application.dependencies;

import java.util.List;

/**
 * @author Michal Sadowski (michal.sadowski@roche.com) on 10.12.2018
 */
public interface DependencyService {

    /**
     * Get all available dependencies for given version.
     * @param version
     * @return
     */
    List<String> getDependenciesForVersion(String version);
}

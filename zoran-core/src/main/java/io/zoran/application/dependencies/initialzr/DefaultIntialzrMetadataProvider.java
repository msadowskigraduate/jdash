package io.zoran.application.dependencies.initialzr;

import io.spring.initializr.generator.version.Version;
import io.spring.initializr.metadata.*;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Michal Sadowski (michal.sadowski@roche.com) on 10.12.2018
 */
public class DefaultIntialzrMetadataProvider implements DependencyMetadataProvider {

    public DependencyMetadata get(InitializrMetadata metadata, Version bootVersion) {
        Map<String, Dependency> dependencies = new LinkedHashMap<>();
        for (Dependency dependency : metadata.getDependencies().getAll()) {
            if (dependency.match(bootVersion)) {
                dependencies.put(dependency.getId(), dependency.resolve(bootVersion));
            }
        }

        Map<String, Repository> repositories = new LinkedHashMap<>();
        for (Dependency dependency : dependencies.values()) {
            if (dependency.getRepository() != null) {
                repositories.put(dependency.getRepository(), metadata.getConfiguration()
                                                                     .getEnv().getRepositories().get(dependency.getRepository()));
            }
        }

        Map<String, BillOfMaterials> boms = new LinkedHashMap<>();
        for (Dependency dependency : dependencies.values()) {
            if (dependency.getBom() != null) {
                boms.put(dependency.getBom(), metadata.getConfiguration().getEnv()
                                                      .getBoms().get(dependency.getBom()).resolve(bootVersion));
            }
        }
        // Each resolved bom may require additional repositories
        for (BillOfMaterials bom : boms.values()) {
            for (String id : bom.getRepositories()) {
                repositories.put(id,
                        metadata.getConfiguration().getEnv().getRepositories().get(id));
            }
        }

        return new DependencyMetadata(bootVersion, dependencies, repositories, boms);
    }
}

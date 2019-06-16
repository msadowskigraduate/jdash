package io.zoran.infrastructure.resource;

import io.zoran.application.resource.ResourceService;
import io.zoran.domain.manifest.Manifest;
import io.zoran.domain.resource.Resource;
import lombok.RequiredArgsConstructor;

/**
 * @author Michal Sadowski (michal.sadowski@roche.com) on 09.01.2019
 *
 * This loader allows to place manifest resources (template metadata) be put in resources repository.
 */
@Deprecated
//@Component
@RequiredArgsConstructor
public class ManifestResourceLoader {
    private final ResourceService resourceService;
    private final ResourceMapper resourceMapper;

    public Resource saveManifestAsResource(Manifest manifest) {
        Resource parsedManifest = resourceMapper.map(manifest);
        return resourceService.createNewResource(parsedManifest, manifest.getOwner());
    }
}

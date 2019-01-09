package io.zoran.infrastructure.resource;

import io.zoran.domain.manifest.Manifest;
import io.zoran.domain.resource.project.ProjectDetails;
import io.zoran.domain.resource.project.ProjectResource;
import io.zoran.infrastructure.integrations.license.LicenseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author Michal Sadowski (michal.sadowski@roche.com) on 09.01.2019
 */
@Service
@RequiredArgsConstructor
public class ResourceMapper {
    private final LicenseService licenseService;

    public ProjectResource map(Manifest manifest) {
        return ProjectResource.builder()
                              .name(manifest.getName())
                              .projectDetails(mapDetails(manifest))
                              .resourceType(manifest.getType())
                              .dependencies(manifest.getDependencies())
                              .license(licenseService.getOrDefault(manifest.getLicense()))
                              .build();
    }

    private ProjectDetails mapDetails(Manifest manifest) {
        return ProjectDetails.builder()
                             .version(manifest.getVersion())
                             .lead(manifest.getLead())
                             .description(manifest.getDescription())
                             .projectLanguage(manifest.getProjectLanguage())
                             .name(manifest.getName())
                             .tags(manifest.getTags())
                             .gitUrl(manifest.getPath())
                             .build();
    }
}
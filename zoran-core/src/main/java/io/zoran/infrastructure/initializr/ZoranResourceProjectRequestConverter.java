package io.zoran.infrastructure.initializr;

import io.zoran.application.common.mappers.Mapper;
import io.zoran.domain.initializr.ProjectRequest;
import io.zoran.domain.resource.Resource;
import org.springframework.stereotype.Service;

/**
 * @author Michal Sadowski (michal.sadowski@roche.com) on 21.02.2019
 */
@Service
public class ZoranResourceProjectRequestConverter implements Mapper<Resource, ProjectRequest> {
    private static final String DEFAULT_PACKAGING = "jar";

    @Override
    public ProjectRequest map(Resource resource) {
        return ProjectRequest.builder()
                             .applicationName(resource.getProjectDetails().getProjectName())
                             .artifactId(resource.getProjectDetails().getArtifactId())
                             .groupId(resource.getProjectDetails().getGroupId())
                             .dependencies(resource.getDependencies())
                             .description(resource.getProjectDetails().getDescription())
                             .javaVersion(resource.getProjectDetails().getJavaVersion())
                             .bootVersion(resource.getProjectDetails().getBootVersion())
                             .language(resource.getProjectDetails().getProjectLanguage())
                             .name(resource.getName())
                             .version(resource.getProjectDetails().getVersion())
                             .type(resource.getResourceType().getValue())
                             .packaging(DEFAULT_PACKAGING)
                             .baseDir(resource.getProjectDetails().getProjectName())
                             .build();
    }
}

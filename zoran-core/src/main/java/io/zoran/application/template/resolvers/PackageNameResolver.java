package io.zoran.application.template.resolvers;

import io.zoran.domain.manifest.Template;
import io.zoran.domain.resource.Resource;
import lombok.experimental.UtilityClass;

import static io.zoran.application.template.resolvers.OutputPathResolver.resolveLocation;

/**
 * @author Michal Sadowski (michal.sadowski@roche.com) on 19.02.2019
 */
@UtilityClass
public class PackageNameResolver {
    private static final String DEFAULT_SEPARATOR = ".";

    public static String resolve(Resource resource, Template template) {
        String locationTag = resolveLocation(template.getPreferredLocation());
        return resource.getProjectDetails().getGroupId() +
                DEFAULT_SEPARATOR +
                resource.getProjectDetails().getArtifactId() +
                DEFAULT_SEPARATOR +
                locationTag;
    }
}

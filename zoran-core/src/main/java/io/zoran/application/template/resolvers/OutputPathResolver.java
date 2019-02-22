package io.zoran.application.template.resolvers;

import io.zoran.domain.manifest.Location;
import io.zoran.domain.resource.Resource;
import lombok.experimental.UtilityClass;

/**
 * @author Michal Sadowski (michal.sadowski@roche.com) on 19.02.2019
 */
@UtilityClass
public class OutputPathResolver {

    public static String resolve(String packageName, Resource resource, String language, Location loc) {
        String location = resolveLocation(loc);
        return resource.getProjectDetails().getProjectName() + "/src/main/" + language + "/" + packageName +  "/" + location;
    }

    public static String resolveLocation(Location loc) {
        String location;

        switch (loc) {
            case API: location = "api/"; break;
            case DOMAIN: location = "domain/"; break;
            case INFRASTRUCTURE: location = "infrastructure/"; break;
            case SERVICES: location = "application/"; break;
            case DATA: location = "domain/application"; break;
            default: location = ""; break;
        }

        return location;
    }
}

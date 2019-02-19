package io.zoran.infrastructure.resource;

import io.zoran.api.domain.ProjectResourceRequest;
import io.zoran.api.domain.ResourceResponse;
import io.zoran.domain.git.License;
import io.zoran.domain.resource.Resource;
import io.zoran.domain.resource.project.ProjectDetails;
import io.zoran.domain.resource.project.ProjectResource;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang.StringUtils;

/**
 * @author Michal Sadowski (michal.sadowski@roche.com) on 23.11.2018
 */
@UtilityClass
public class ResourceConverter {
    public static ResourceResponse convert(Resource pojo) {
        return ResourceResponse.builder()
                               .id(pojo.getId())
                               .name(pojo.getName())
                               .type(pojo.getResourceType())
                               .owner(pojo.getOwner())
                               .resourceVisibility(pojo.getVisibility())
                               .projectLanguage(pojo.getProjectDetails().getProjectLanguage())
                               .groupId(pojo.getProjectDetails().getGroupId())
                               .artifactId(pojo.getProjectDetails().getArtifactId())
                               .version(pojo.getProjectDetails().getVersion())
                               .lead(pojo.getProjectDetails().getLead())
                               .description(pojo.getProjectDetails().getDescription())
                               .gitUrl(pojo.getProjectDetails().getGitUrl())
                               .tags(StringUtils.join(pojo.getProjectDetails().getTags(), ","))
                               .license(pojo.getLicense())
                               .build();
    }

    public static ProjectResource convert(ProjectResourceRequest resourceRequest, License license, String ownerId) {
        return ProjectResource.builder()
                       .name(resourceRequest.getName())
                       .owner(ownerId)
                       .visibility(resourceRequest.getResourceVisibility())
                       .license(license)
                       .resourceType(resourceRequest.getType())
                       .dependencies(resourceRequest.getTemplatesUsed())
                       .projectDetails(
                               ProjectDetails.builder()
                                             .name(resourceRequest.getName())
                                             .projectLanguage(resourceRequest.getProjectLanguage())
                                             .projectName(resourceRequest.getName())
                                             .artifactId(resourceRequest.getArtifactId())
                                             .groupId(resourceRequest.getGroupId())
                                             .description(resourceRequest.getDescription())
                                             .lead(resourceRequest.getLead())
                                             .gitUrl(resourceRequest.getGitUrl())
                                             .version(resourceRequest.getVersion())
                                             .tags(resourceRequest.getTags().split(" "))
                                             .build())
                       .build();
    }
}
package io.zoran.api.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.zoran.domain.git.License;
import io.zoran.domain.manifest.ResourceType;
import io.zoran.domain.resource.ResourceVisibility;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

/**
 * @author Michal Sadowski (michal.sadowski@roche.com) on 22.11.2018
 */
@Builder
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResourceResponse {
    private String id;
    private String name;
    private String projectLanguage;
    private String groupId;
    private String artifactId;
    private ResourceType type;
    private ResourceVisibility resourceVisibility;
    private String owner;
    private String version;
    private String lead;
    private String description;
    private String tags;
    private String gitUrl;
    private License license;
    private List<String> dependencies;
    private List<String> templates;
}
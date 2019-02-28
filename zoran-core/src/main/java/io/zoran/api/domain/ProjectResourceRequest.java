package io.zoran.api.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.zoran.domain.manifest.ResourceType;
import io.zoran.domain.resource.ResourceVisibility;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Michal Sadowski (michal.sadowski@roche.com) on 22.11.2018
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProjectResourceRequest {
    private String name;
    private String projectLanguage;
    private String groupId;
    private String artifactId;
    private ResourceType type;
    private ResourceVisibility resourceVisibility;
    private String version;
    private String lead;
    private String description;
    private String tags;
    @JsonProperty("templatesUsed")
    private List<String> dependencies;
    private List<String> templateData;
    private String licenseKey;
    private String gitUrl;
    private String bootVersion;
    private String javaVersion;
}
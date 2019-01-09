package io.zoran.api.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.zoran.domain.manifest.ResourceType;
import io.zoran.domain.resource.ResourceVisibility;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * @author Michal Sadowski (michal.sadowski@roche.com) on 22.11.2018
 */
@Data
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
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
    private List<String> templatesUsed;
    private String licenseKey;
    private String gitUrl;
}
package io.zoran.domain.manifest;

import io.zoran.domain.resource.ResourceVisibility;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * @author Michal Sadowski (sadochasee@gmail.com) on 22/07/2018.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class YMLManifest implements Manifest {
    private String name;
    private String lead;
    private String version;
    private String owner;
    private String path;
    private String description;
    private String license;
    private String projectLanguage;
    private String[] tags;
    private List<String> dependencies;
    private ResourceType type;
    private ResourceVisibility visibility;
    private List<Template> template;
    private Map<String, Object> additionalProperties;
}
package io.zoran.api.domain;

import io.zoran.domain.manifest.ResourceType;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class ResourceDependencyMetadataModel extends ResourceDependencyMetadata {
    private String id;
    private String name;
    private String description;
    private String group;
    private String version;
    private ResourceType type;

    private ResourceDependencyMetadataModel(String parentIdentifier, String id, String name, String description,
                                            String group, String version, ResourceType type) {
        super(parentIdentifier);
        this.id = id;
        this.name = name;
        this.description = description;
        this.group = group;
        this.version = version;
        this.type = type;
    }

    public static ResourceDependencyMetadataModel of(String parentIdentifier, String id, String name,
                                                     String description, String group, String version, ResourceType type) {
        return new ResourceDependencyMetadataModel(parentIdentifier, id, name, description, group, version, type);
    }
}

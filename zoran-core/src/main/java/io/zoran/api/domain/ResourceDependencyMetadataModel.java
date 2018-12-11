package io.zoran.api.domain;

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

    private ResourceDependencyMetadataModel(String parentIdentifier, String id, String name, String description, String group, String version) {
        super(parentIdentifier);
        this.id = id;
        this.name = name;
        this.description = description;
        this.group = group;
        this.version = version;
    }

    public static ResourceDependencyMetadataModel of(String parentIdentifier, String id, String name, String description, String group, String version) {
        return new ResourceDependencyMetadataModel(parentIdentifier, id, name, description, group, version);
    }
}

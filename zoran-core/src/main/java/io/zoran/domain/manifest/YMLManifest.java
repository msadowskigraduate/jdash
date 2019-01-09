package io.zoran.domain.manifest;

import io.zoran.core.domain.resource.ResourceVisibility;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Michal Sadowski (sadochasee@gmail.com) on 22/07/2018.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class YMLManifest implements Manifest {
    private String id;
    private String name; //filename
    private String slug;
    private String lead;
    private String version;
    private String owner;
    private String path;
    private String description;
    private ResourceVisibility visibility;
    private ResourceType type;
    private Location preferredLocation;
    private List<String> dependencies;
}
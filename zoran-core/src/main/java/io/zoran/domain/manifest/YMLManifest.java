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
    String name; //filename
    String slug;
    String lead;
    String version;
    String owner;
    String path;
    ResourceVisibility visibility;
    ResourceType type;
    Location preferredLocation;
    List<String> dependencies;
}

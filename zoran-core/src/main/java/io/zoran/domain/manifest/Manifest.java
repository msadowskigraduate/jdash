package io.zoran.domain.manifest;

import java.util.List;

/**
 * @author Michal Sadowski (sadochasee@gmail.com) on 17/07/2018.
 */
public interface Manifest {
    String getId();
    String getName();
    String getPath();
    String getOwner();
    String getVersion();
    String getDescription();
    Location getPreferredLocation();
    ResourceType getType();
    List<String> getDependencies();
}

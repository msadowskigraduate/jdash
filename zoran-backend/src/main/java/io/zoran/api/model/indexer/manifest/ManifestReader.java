package io.zoran.api.model.indexer.manifest;

import java.nio.file.Path;

/**
 * @author Michal Sadowski (sadochasee@gmail.com) on 22/07/2018.
 */
public interface ManifestReader {
    boolean canRead();

    Manifest read(Path path);
}

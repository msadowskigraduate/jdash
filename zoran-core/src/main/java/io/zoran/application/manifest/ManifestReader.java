package io.zoran.application.manifest;

import io.zoran.domain.manifest.Manifest;

import java.io.InputStream;
import java.nio.file.Path;

/**
 * @author Michal Sadowski (sadochasee@gmail.com) on 22/07/2018.
 */
public interface ManifestReader {
    boolean canRead(Path path);

    Manifest read(Path path);

    Manifest read(String input);

    Manifest read(InputStream input);
}

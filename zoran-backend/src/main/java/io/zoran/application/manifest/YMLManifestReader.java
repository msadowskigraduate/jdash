package io.zoran.application.manifest;

import io.zoran.domain.manifest.Manifest;

import java.nio.file.Path;

/**
 * @author Michal Sadowski (sadochasee@gmail.com) on 22/07/2018.
 */
public class YMLManifestReader implements ManifestReader{

    @Override
    public boolean canRead() {
        return false;
    }

    @Override
    public Manifest read(Path path) {
        return null;
    }
}

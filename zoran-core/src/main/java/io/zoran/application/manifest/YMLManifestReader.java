package io.zoran.application.manifest;

import io.zoran.domain.manifest.Manifest;
import org.springframework.stereotype.Component;

import java.nio.file.Path;

/**
 * @author Michal Sadowski (sadochasee@gmail.com) on 22/07/2018.
 */

@Component
public class YMLManifestReader implements ManifestReader {

    @Override
    public boolean canRead() {
        return false;
    }

    @Override
    public Manifest read(Path path) {
        return null;
    }
}

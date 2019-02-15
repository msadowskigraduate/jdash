package io.zoran.infrastructure.services;

import lombok.experimental.UtilityClass;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

/**
 * @author Michal Sadowski (michal.sadowski@roche.com) on 15.02.2019
 */
@UtilityClass
public class PathUtils {

    public boolean isValidPath(Path path) {
        return path != null && path.toFile().exists();
    }

    public boolean isValidNonEmpty(Path path) {
        if (isValidPath(path) && Files.isDirectory(path)) {
            if (Objects.requireNonNull(path.toFile().list()).length > 0) {
                return true;
            }
            return false;
        }
        return false;
    }
}

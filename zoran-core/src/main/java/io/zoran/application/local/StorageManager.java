package io.zoran.application.local;

import io.zoran.infrastructure.configuration.domain.Zoran;
import io.zoran.infrastructure.exception.CreateDirectoryException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static io.zoran.application.local.StorageConst.LOCAL_PREFIX;
import static io.zoran.application.local.StorageConst.MODEL_PREFIX;

/**
 * @author Michal Sadowski (sadochasee@gmail.com) on 09/12/2018.
 */
@Scope(scopeName = "singleton")
@Component
@RequiredArgsConstructor
public class StorageManager {
    private final Zoran properties;
    private Path localStoragePath;
    private Path modelStoragePath;

    public Path getLocalStoragePath() {
        if (localStoragePath == null) {
            String p = properties.getStorage().getLocal();
            localStoragePath = getOrCreateNew(p, LOCAL_PREFIX);
        }
        return localStoragePath;
    }

    public Path getModelStoragePath() {
        if (modelStoragePath == null) {
            String p = properties.getStorage().getPath();
            modelStoragePath = getOrCreateNew(p, MODEL_PREFIX);
        }
        return modelStoragePath;
    }

    private Path getOrCreateNew(String path, String prefix) {
        Path currentRelativePath = Paths.get(path, prefix);
        Path p = currentRelativePath.toAbsolutePath();
        try {
            if (Files.exists(p)) {
                return p;
            }
            return Files.createDirectories(currentRelativePath);
        } catch (IOException e) {
            throw new CreateDirectoryException();
        }
    }
}

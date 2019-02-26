package io.zoran.application.project.impl;

import io.zoran.application.local.StorageManager;
import io.zoran.application.project.ProjectRetrievalService;
import io.zoran.domain.resource.Resource;
import io.zoran.infrastructure.exception.ZoranDownloadException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

/**
 * @author Michal Sadowski (michal.sadowski@roche.com) on 26.02.2019
 */
@Service
@RequiredArgsConstructor
class DefaultProjectRetrievalService implements ProjectRetrievalService {

    private final StorageManager storageManager;

    @Override
    public Path getLocalPath(Resource resource) throws IOException {
        Path local = storageManager.getLocalStoragePath();
        final Path[] path = new Path[1];
        Files.walkFileTree(local, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                if(dir.getFileName().toString().equals(resource.getName())) {
                  path[0] = dir;
                  return FileVisitResult.CONTINUE;
                }
                return FileVisitResult.CONTINUE;
            }
        });

        if(path[0] != null) {
            return path[0];
        }
        throw new ZoranDownloadException("Cannot find files for: " + resource.getName());
    }
}
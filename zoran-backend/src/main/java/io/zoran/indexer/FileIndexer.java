package io.zoran.indexer;

import io.zoran.indexer.manifest.Manifest;
import io.zoran.indexer.manifest.ManifestReader;
import io.zoran.indexer.tree.Node;
import io.zoran.indexer.tree.Tree;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

/**
 * @author Michal Sadowski (sadochasee@gmail.com) on 22/07/2018.
 */
@RequiredArgsConstructor
public class FileIndexer implements Indexer<Tree> {

    private final ManifestReader reader;

    @Override
    public Tree index(Path rootDirectoryPath) throws IOException {
        Tree tree = new Tree();

        Files.walkFileTree(rootDirectoryPath, new SimpleFileVisitor<Path>() {

            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                addNodeToTree(dir, tree);
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                if(reader.canRead()) {
                    read(reader, file);
                    return FileVisitResult.CONTINUE;
                }
                addNodeToTree(file, tree);
                return FileVisitResult.CONTINUE;
            }


            private FileVisitResult read(ManifestReader reader, Path path) {
                Boolean isSuccess = addManifestToDirectory(reader.read(path), path, tree);
                if(isSuccess)
                    return FileVisitResult.CONTINUE;
                return FileVisitResult.TERMINATE;
            }

        });

        return tree;
    }

    private void addNodeToTree(Path dir, Tree tree) {
        Node node = Node.builder()
                .path(dir)
                .id(dir.getFileName().toString())
                .build();
        Path parentPath = dir.getParent();
        tree.addNode(node, tree.getNodeByPath(parentPath));
    }

    private boolean addManifestToDirectory(Manifest manifest, Path directoryPath, Tree tree) {
        Node node = tree.getNodeByPath(directoryPath);
        if(node != null) {
            node.setManifest(manifest);
            return true;
        }
        return false;
    }
}


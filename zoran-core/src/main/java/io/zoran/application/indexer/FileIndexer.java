package io.zoran.application.indexer;

import io.zoran.application.manifest.ManifestReader;
import io.zoran.domain.manifest.Manifest;
import io.zoran.domain.indexer.Indexer;
import io.zoran.domain.indexer.Node;
import io.zoran.domain.indexer.Tree;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

/**
 * @author Michal Sadowski (sadochasee@gmail.com) on 22/07/2018.
 */
//TODO to be rewritten
@Component
@RequiredArgsConstructor
public class FileIndexer implements Indexer<Tree> {

    private final ManifestReader reader;

    @Override
    public Tree index(Path rootDirectoryPath) throws IOException {
        Tree tree = Tree.newTree();

        Files.walkFileTree(rootDirectoryPath, new SimpleFileVisitor<Path>() {

            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                addNodeToTree(dir, tree);
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                if(reader.canRead(file)) {
                    return read(reader, file);
                }
                addNodeToTree(file, tree);
                return FileVisitResult.CONTINUE;
            }


            private FileVisitResult read(ManifestReader reader, Path path) {
                Path parentDirectoryPath = path.getParent();
                Boolean isSuccess = addManifestToDirectory(reader.read(path), parentDirectoryPath, tree);
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
                .id(dir.toString())
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


package io.zoran.api.model.indexer.domain;

import io.zoran.api.model.indexer.Index;
import io.zoran.api.model.indexer.manifest.Manifest;
import lombok.*;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Michal Sadowski (sadochasee@gmail.com) on 17/07/2018.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Node implements Index<Node>{

    private String id;
    private Manifest manifest;
    @Builder.Default private List<Node> children = new ArrayList<>();
    private Path path;
    private boolean isDirectory;
    private Node parent;

    @Override
    public boolean isRoot() {
        return getParent() == null;
    }

    @Override
    public List<Path> traverse(List<Path> pathList) {
        pathList.add(getPath());
        if(!isLeaf()) {
            children.forEach(child -> pathList.addAll(child.traverse(pathList)));
        }

        return pathList;
    }

    @Override
    public List<Node> getDecendants() {
        return children;
    }

    @Override
    public boolean isDirectory() {
        return isDirectory;
    }

    @Override
    public Manifest getManifest() {
        return manifest;
    }

    @Override
    public boolean isLeaf() {
        return getChildren().isEmpty();
    }

    public void addChild(Node node) {
        this.getChildren().add(node);
    }
}

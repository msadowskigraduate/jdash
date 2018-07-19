package io.zoran.repository.tree;

import io.zoran.repository.Index;
import io.zoran.repository.Manifest;
import lombok.*;

import java.nio.file.Path;
import java.util.List;

/**
 * @author Michal Sadowski (sadochasee@gmail.com) on 17/07/2018.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Node implements Index<Node>{

    private Manifest manifest;
    private List<Node> children;
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
}

package io.zoran.indexer;

import io.zoran.indexer.manifest.Manifest;

import java.nio.file.Path;
import java.util.List;

/**
 * @author Michal Sadowski (sadochasee@gmail.com) on 17/07/2018.
 */
public interface Index<T> {

    boolean isRoot();

    List<Path> traverse(List<Path> pathList);

    List<T> getDecendants();

    boolean isDirectory();

    Manifest getManifest();

    boolean isLeaf();
}

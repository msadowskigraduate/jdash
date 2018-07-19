package io.zoran.repository.tree;

import io.zoran.repository.Index;
import io.zoran.repository.Indexer;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Michal Sadowski (sadochasee@gmail.com) on 17/07/2018.
 */
public class Tree implements Indexer {

    private Node rootNode;

    @Override
    public void index() {
        List<Path> paths = new ArrayList<>();
        rootNode.traverse(paths);
    }

    @Override
    public Object getPathForIndex() {
        return null;
    }

    @Override
    public Index getRootIndex() {
        return null;
    }
}

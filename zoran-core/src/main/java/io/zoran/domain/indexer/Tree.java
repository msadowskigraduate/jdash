package io.zoran.domain.indexer;

import io.zoran.domain.manifest.Manifest;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang.StringUtils;
import org.springframework.lang.Nullable;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Predicate;

import static java.util.stream.Collectors.toList;

/**
 * @author Michal Sadowski (sadochasee@gmail.com) on 17/07/2018.
 */

public class Tree implements Model {

    private String treeIdentifier;

    @Setter(AccessLevel.PRIVATE)
    @Getter(AccessLevel.PRIVATE)
    private Node rootNode;
    private List<Node> nodeList;

    public static Tree newTree() {
        return new Tree();
    }

    public static Tree newTree(Path rootPath) {
        Tree t = new Tree();
        Node rootNode = Node.builder()
                .path(rootPath)
                .id(rootPath.toString())
                .isDirectory(rootPath.toFile().isDirectory())
                .build();
        t.setRootNode(rootNode);
        return t;
    }

    public static Tree emptyTree() {
        return new EmptyTree();
    }

    private Tree() {
        this.treeIdentifier = UUID.randomUUID().toString();
        this.nodeList = new ArrayList<>();
    }


    public Node addNode(Node node, Node parentNode) {
        if(parentNode == null && rootNode == null) {
            return addRootNode(node);
        } else if(parentNode != null && parentNode.getPath() != null) {
            nodeList.add(node);
            parentNode.addChild(node);
            parentNode.setParent(parentNode);
            return node;
        }
        return null;
    }

    private Node addRootNode(Node node) {
        setRootNode(node);
        return node;
    }

    @Nullable
    public Node getNodeByPath(Path path) {
        if(this.hasRoot() && rootNode.getPath().equals(path)) {
            return rootNode;
        }

        if(nodeList.isEmpty()) {
            return null;
        }

        return nodeList.stream().filter(x -> x.getPath().equals(path)).findFirst().orElse(null);
    }

    private boolean hasRoot() {
        return this.getRootNode() != null;
    }

    public List<Manifest> getAllManifests(Predicate<Manifest> filter) {
        return nodeList.stream()
                .map(Node::getManifest)
                .filter(Objects::nonNull)
                .filter(filter)
                .collect(toList());
    }

    public String toString() {
        return rootNode.toString() + ", children: [" +
                StringUtils.join(nodeList.stream().map(Node::toString).collect(toList()), " ")
                + " ];";
    }

    @Override
    public String getId() {
        return this.treeIdentifier;
    }

    @ToString
    private static class EmptyTree extends Tree implements Model {
        private String treeIdentifier;

        private EmptyTree() {
            this.treeIdentifier = UUID.randomUUID().toString();
        }

        public String getId() {
            return this.treeIdentifier;
        }

        public Node getNodeByPath() {
            return null;
        }

        public Node addNode(Node node, Node parentNode) {
            return null;
        }
    }
}
package io.zoran.api.model.indexer.domain;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.lang.Nullable;

import java.nio.file.Path;
import java.util.List;

/**
 * @author Michal Sadowski (sadochasee@gmail.com) on 17/07/2018.
 */

@Data
@Document
public class Tree {

    private String treeIdentifier;
    private Node rootNode;
    private List<Node> nodeList;

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

    public Node addRootNode(Node node) {
        setRootNode(node);
        return node;
    }

    @Nullable
    public Node getNodeByPath(Path path) {
        if(nodeList.isEmpty()) {
            return null;
        }
        return nodeList.stream().filter(x -> x.getPath().equals(path)).findFirst().orElse(null);
    }
}

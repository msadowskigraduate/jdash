package io.zoran.indexer.model;

import io.zoran.indexer.tree.Tree;
import org.springframework.stereotype.Service;

/**
 * @author Michal Sadowski (sadochasee@gmail.com) on 22/07/2018.
 */

@Service
public class ModelRepositoryService {

    private ModelRepository<Tree> treeModelRepository;

    public Tree saveTreeModel(Tree tree) {
        return treeModelRepository.save(tree);
    }

}

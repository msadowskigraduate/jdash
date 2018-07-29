package io.zoran.api.model.indexer.domain.internal;

import io.zoran.api.model.indexer.domain.Tree;
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

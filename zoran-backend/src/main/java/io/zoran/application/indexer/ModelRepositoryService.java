package io.zoran.application.indexer;

import io.zoran.domain.indexer.Tree;
import org.springframework.stereotype.Service;

/**
 * @author Michal Sadowski (sadochasee@gmail.com) on 22/07/2018.
 */

@Service
public class ModelRepositoryService {

    private ModelRepository treeModelRepository;

    public Tree saveTreeModel(Tree tree) {
        return treeModelRepository.save(tree);
    }

}

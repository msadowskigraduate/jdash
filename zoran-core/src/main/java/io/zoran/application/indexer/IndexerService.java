package io.zoran.application.indexer;

import io.zoran.application.local.StorageManager;
import io.zoran.domain.indexer.Indexer;
import io.zoran.domain.indexer.Tree;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.List;

/**
 * @author Michal Sadowski (sadochasee@gmail.com) on 29/07/2018.
 */
@Service
@RequiredArgsConstructor
public class IndexerService {

    private final Indexer<Tree> indexer;
    private final ModelFactory modelFactory;
    private final StorageManager manager;

    public Tree indexTree() throws URISyntaxException, IOException {
        ModelRepository<Tree> repository = modelFactory.getDefaultStore();
        Tree t = indexer.index(manager.getModelStoragePath());
        return repository.addNewTree(t);
    }

    public List<Tree> getIndexedResults() {
        ModelRepository<Tree> repository = modelFactory.getDefaultStore();
        if(repository != null && !repository.isEmpty()) {
            return repository.getAll();
        }
        return Collections.emptyList();
    }

    public Tree getIndexedResults(String id) {
        ModelRepository<Tree> repository = modelFactory.getDefaultStore();
        if(repository != null && !repository.isEmpty()) {
            return repository.getTreeById(id);
        }
        return Tree.emptyTree();
    }
}
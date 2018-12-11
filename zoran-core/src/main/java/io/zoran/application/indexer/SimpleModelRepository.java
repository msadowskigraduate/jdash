package io.zoran.application.indexer;

import io.zoran.domain.indexer.Tree;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static java.util.stream.Collectors.toList;

/**
 * @author Michal Sadowski (sadochasee@gmail.com) on 09/12/2018.
 */
public class SimpleModelRepository implements ModelRepository<Tree>, AutoCloseable {

    private Map<String, Tree> repository;

    private SimpleModelRepository() {
        repository = new ConcurrentHashMap<>();
    }

    static SimpleModelRepository newSimpleStore() {
        return new SimpleModelRepository();
    }

    @Override
    public Tree addNewTree(Tree model) {
        return repository.put(model.getId(), model);
    }

    @Override
    public Tree getTreeById(String id) {
        return repository.get(id);
    }

    @Override
    public Tree evict(String id) {
        return repository.remove(id);
    }

    @Override
    public void evict() {
        repository.clear();
    }

    @Override
    public List<Tree> getAll() {
        return repository.entrySet().stream()
                .map(Map.Entry::getValue).collect(toList());
    }

    @Override
    public void close() throws Exception {
        evict();
    }
}

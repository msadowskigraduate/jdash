package io.zoran.application.dependencies;

import io.zoran.api.domain.ResourceDependencyMetadata;
import io.zoran.application.indexer.IndexerService;
import io.zoran.domain.indexer.Tree;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static io.zoran.application.dependencies.DependencyConstants.INDEXER;

/**
 * @author Michal Sadowski (sadochasee@gmail.com) on 16/12/2018.
 */
@Service
@RequiredArgsConstructor
public class IndexedDependencyService implements DependencyService {

    private final IndexerService indexer;

    @Override
    public String getIdentifier() {
        return INDEXER;
    }

    @Override
    public List<ResourceDependencyMetadata> getDependenciesForVersion(String version) {
        return null;
    }

    private void getIndexedData() {
        List<Tree> results = indexer.getIndexedResults();
    }
}

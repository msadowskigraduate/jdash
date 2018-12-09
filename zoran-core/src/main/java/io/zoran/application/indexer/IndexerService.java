package io.zoran.application.indexer;

import io.zoran.domain.indexer.Indexer;
import io.zoran.domain.indexer.Tree;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Paths;

/**
 * @author Michal Sadowski (sadochasee@gmail.com) on 29/07/2018.
 */
@Service
@RequiredArgsConstructor
public class IndexerService {

    private final Indexer<Tree> indexer;
    private final ModelRepository<Tree> repository;

    private Tree indexTree() throws URISyntaxException, IOException {
        return indexer.index(Paths.get(new URI("")));
    }
}

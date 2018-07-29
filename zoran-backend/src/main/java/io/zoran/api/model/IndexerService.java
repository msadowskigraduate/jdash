package io.zoran.api.model;

import io.zoran.api.model.indexer.Indexer;
import io.zoran.api.model.indexer.domain.Tree;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
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

    @PostConstruct
    @Scheduled(fixedRate = 5000)
    private Tree indexTree() throws URISyntaxException, IOException {
        return indexer.index(Paths.get(new URI("")));
    }
}

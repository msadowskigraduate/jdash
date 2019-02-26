package io.zoran.infrastructure.indexer;

import io.zoran.application.indexer.IndexerService;
import io.zoran.application.local.StorageManager;
import io.zoran.infrastructure.configuration.ProductionOnly;
import io.zoran.infrastructure.integrations.git.GitService;
import lombok.RequiredArgsConstructor;
import org.eclipse.jgit.api.errors.GitAPIException;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.URISyntaxException;

/**
 * @author Michal Sadowski (michal.sadowski@roche.com) on 18.02.2019
 *
 * Clones model (template) repository and initializes indexing.
 */
@ProductionOnly
@RequiredArgsConstructor
class IndexModelPopulator {
    private final StorageManager manager;
    private final GitService service;
    private final IndexerService indexerService;

    @PostConstruct
    void populateModel() throws GitAPIException, IOException, URISyntaxException {
        service.cloneModelRepository(manager.getModelStoragePath().toFile());
        indexerService.indexTree();
    }
}

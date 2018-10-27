package io.zoran.application.indexer;

import io.zoran.domain.indexer.Indexer;
import io.zoran.domain.indexer.Tree;
import io.zoran.infrastructure.git.GitService;
import lombok.RequiredArgsConstructor;
import org.eclipse.jgit.api.errors.GitAPIException;
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
    private final GitService gitService;

//    @PostConstruct
//    @Scheduled(fixedRate = 5000)
    private Tree indexTree() throws URISyntaxException, IOException, GitAPIException {
        return indexer.index(Paths.get(new URI(gitService.cloneTemplateRepository())));
    }
}

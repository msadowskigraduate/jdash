package io.zoran.application.pipelines.handlers.impl;

import io.zoran.application.audit.Audited;
import io.zoran.application.pipelines.domain.Artifact;
import io.zoran.application.pipelines.handlers.AbstractPipelineTask;
import io.zoran.infrastructure.exception.ZoranHandlerException;
import io.zoran.infrastructure.integrations.git.GitClientFactory;
import io.zoran.infrastructure.services.PathUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.egit.github.core.Repository;
import org.eclipse.egit.github.core.service.RepositoryService;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.dircache.DirCache;
import org.eclipse.jgit.revwalk.RevCommit;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static io.zoran.application.pipelines.handlers.impl.HandlerParamConst.CLONED_LOCAL_PATH;
import static io.zoran.application.pipelines.handlers.impl.HandlerParamConst.GIT_URL;
import static io.zoran.domain.audit.AuditAction.NEW_REPOSITORY_CREATED;

/**
 * @author Michal Sadowski (sadochasee@gmail.com) on 03/02/2019.
 * <p>
 * Pipeline handler that creates repository AND commits code there.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class GitCommitHandler extends AbstractPipelineTask {
    private final GitClientFactory gitFactory;
    private Artifact tempArtifact;

    @Override
    @Audited(NEW_REPOSITORY_CREATED)
    public void handle() {
        try {
            //Get Git path registered in handler context
            String gitUrl = this.map.get(GIT_URL);

            //if does not exist create new repository and use it
            if (gitUrl == null) {
                gitUrl = createNewRepository();
            }

            Path localFiles = Paths.get(gitUrl);
            //Create temp directory to hold cloned data
            Path tempFile = Files.createTempDirectory(Paths.get(""), null);
            Git instance = cloneRepositoryToLocalTemp(gitUrl, tempFile.toFile());

            //If there are some local files copy them to temp folder
            if(PathUtils.isValidNonEmpty(localFiles)) {
                Files.move(localFiles, tempFile);
            }

            //register cloned repository path
            this.tempArtifact = Artifact.instance();
            this.tempArtifact.register(CLONED_LOCAL_PATH, tempFile);
            commit(instance);
            push(instance);
        } catch (IOException | GitAPIException e) {
            throw new ZoranHandlerException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public Artifact getArtifact() {
        return this.tempArtifact;
    }

    private String createNewRepository() throws IOException {
        RepositoryService repository = gitFactory.getRepositoryClient();
        Repository newRepository = repository.createRepository(initRepo());
        log.info("New repository created! " + newRepository.getGitUrl());
        return newRepository.getGitUrl();
    }

    private Repository initRepo() {
        Repository repository = new Repository();
        repository.setName(this.resource.getName());
        repository.setDescription(this.resource.getProjectDetails().getDescription());
        return repository;
    }

    private Git cloneRepositoryToLocalTemp(String gitUrl, File filePath) throws GitAPIException {
        return Git.cloneRepository()
           .setURI(gitUrl)
           .setDirectory(filePath)
           .call();
    }

    private RevCommit commit(Git instance) throws GitAPIException {
        DirCache cache = instance.add()
                .call();

        return instance.commit().setMessage("test message").call();
    }

    private void push(Git instance) throws GitAPIException {
        instance.push().call();
    }
}

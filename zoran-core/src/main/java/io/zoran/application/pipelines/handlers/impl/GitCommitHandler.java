package io.zoran.application.pipelines.handlers.impl;

import io.zoran.application.audit.Audited;
import io.zoran.application.pipelines.domain.Artifact;
import io.zoran.application.pipelines.handlers.AbstractPipelineTask;
import io.zoran.infrastructure.exception.ZoranHandlerException;
import io.zoran.infrastructure.integrations.git.GitService;
import io.zoran.infrastructure.services.PathUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.egit.github.core.Repository;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.revwalk.RevCommit;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static io.zoran.application.pipelines.handlers.impl.HandlerParamConst.*;
import static io.zoran.domain.audit.AuditAction.NEW_REPOSITORY_CREATED;
import static io.zoran.infrastructure.services.MessageGenerator.processMessage;

/**
 * @author Michal Sadowski (sadochasee@gmail.com) on 03/02/2019.
 * <p>
 * Pipeline handler that creates repository AND commits code there.
 */
@Slf4j
@Handler
@RequiredArgsConstructor
public class GitCommitHandler extends AbstractPipelineTask {
    private final GitService gitService;
    private Artifact tempArtifact;
    private String message;

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    @Audited(NEW_REPOSITORY_CREATED)
    public void handle() throws ZoranHandlerException {
        try {
            //Get Git path registered in handler context
            String gitUrl = this.map.get(GIT_URL);
            String localPath = this.map.get(CLONED_LOCAL_PATH);
            Path localFiles = null;

            //if does not exist create new repository and use it
            if (gitUrl == null) {
                gitUrl = gitService.createNewRepository(initRepo());
            }

            if(localPath != null) {
                localFiles = Paths.get(localPath);
            }
            //Create temp directory to hold cloned data
            Path tempFile = Files.createTempDirectory(Paths.get(""), null);
            Git instance = gitService.cloneRepositoryToLocalTemp(gitUrl, tempFile.toFile());

            //If there are some local files copy them to temp folder
            if(PathUtils.isValidNonEmpty(localFiles)) {
                Path movedItems = Files.move(localFiles, tempFile);
                this.tempArtifact.register(LOCAL_ITEMS_MOVED, movedItems);
            }

            //register cloned repository path
            this.tempArtifact = Artifact.instance();
            this.tempArtifact.register(CLONED_LOCAL_PATH, tempFile);
            commitFiles(instance, tempFile);
            push(instance);
            this.message = processMessage("Created new repository: " + instance.toString());
        } catch (IOException | GitAPIException e) {
            throw new ZoranHandlerException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public Artifact getArtifact() {
        return this.tempArtifact;
    }

    //TODO This code should not belong to Handler class. This is against single class responsibility paradigm
    private Repository initRepo() {
        Repository repository = new Repository();
        repository.setName(this.resource.getName());
        repository.setDescription(this.resource.getProjectDetails().getDescription());
        return repository;
    }

    private RevCommit commitFiles(Git instance, Path tempFile) throws GitAPIException, IOException {
        if(!PathUtils.isValidNonEmpty(tempFile)) {
            addDummyFile(tempFile);
        }

        instance.add()
                .addFilepattern(tempFile.toString())
                .call();
        return instance
                .commit()
                .setMessage(DEFAULT_COMMIT_MESSAGE)
                .call();
    }

    private void push(Git instance) throws GitAPIException {
        instance
                .push()
                .setCredentialsProvider(this.gitService.getDefaultProvider())
                .call();
    }

    private void addDummyFile(Path tempFile) throws IOException {
        Path testFile = tempFile.resolve("README.md");
        Files.createFile(testFile).toFile();
    }
}

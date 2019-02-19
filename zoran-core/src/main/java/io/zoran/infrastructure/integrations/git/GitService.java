package io.zoran.infrastructure.integrations.git;

import io.zoran.application.user.ZoranUserService;
import io.zoran.domain.git.License;
import io.zoran.infrastructure.configuration.domain.GitHub;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.egit.github.core.Repository;
import org.eclipse.egit.github.core.service.RepositoryService;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @author Michal Sadowski (michal.sadowski@roche.com) on 09.01.2019
 *
 * Wraps Feign Client for Git, adding security.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class GitService {

    private final GitProxyService proxyService;
    private final ZoranUserService userService;
    private final GitClientFactory gitFactory;
    private final GitHub gitHub;

    public List<License> getLicenses() {
        return proxyService.getLicenses(gitHub.getClient_id(), gitHub.getClient_secret());
    }
    public String getAuthResponse() {
        return proxyService.getAuthResponse(gitHub.getClient_id(), gitHub.getClient_secret());
    }

    public Git cloneRepositoryToLocalTemp(String gitUrl, File localFilePath) throws GitAPIException {
        return Git.cloneRepository()
                  .setURI(gitUrl)
                  .setDirectory(localFilePath)
                  .call();
    }

    public Git cloneModelRepository(File targetLocal) throws GitAPIException {
        return cloneRepositoryToLocalTemp(gitHub.getModel_git_url(), targetLocal);
    }

    public String createNewRepository(Repository newRepositoryModel) throws IOException {
        RepositoryService repository = gitFactory.getRepositoryClient();
        Repository newRepository = repository.createRepository(newRepositoryModel);
        log.info("New repository created! " + newRepository.getGitUrl());
        return newRepository.getGitUrl();
    }

    public UsernamePasswordCredentialsProvider getDefaultProvider() {
        return new UsernamePasswordCredentialsProvider(
                this.userService.getCurrentUser().getName(),
                this.userService.getAccessToken());
    }
}

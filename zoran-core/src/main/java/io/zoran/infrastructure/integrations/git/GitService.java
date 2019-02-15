package io.zoran.infrastructure.integrations.git;

import io.zoran.domain.git.License;
import io.zoran.domain.git.NewRepositoryRequest;
import io.zoran.domain.git.RepositoryResponse;
import io.zoran.infrastructure.configuration.domain.GitHub;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Michal Sadowski (michal.sadowski@roche.com) on 09.01.2019
 *
 * Wraps Feign Client for Git, adding security.
 */
@Service
@RequiredArgsConstructor
public class GitService {

    private final GitProxyService proxyService;
    private final GitHub gitHub;

    public List<License> getLicenses() {
        return proxyService.getLicenses(gitHub.getClient_id(), gitHub.getClient_secret());
    }

    public String getAuthResponse() {
        return proxyService.getAuthResponse(gitHub.getClient_id(), gitHub.getClient_secret());
    }

    public RepositoryResponse createNewRepository(NewRepositoryRequest repositoryRequest) {
        return proxyService.createNewRepository(repositoryRequest);
    }
}

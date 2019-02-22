package io.zoran.infrastructure.integrations.git;

import io.zoran.application.user.ZoranUserService;
import lombok.RequiredArgsConstructor;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.service.RepositoryService;
import org.springframework.stereotype.Service;

import static io.zoran.infrastructure.integrations.git.GitConsts.GIT_HUB_API_HOST;
import static io.zoran.infrastructure.integrations.git.GitConsts.PORT;
import static io.zoran.infrastructure.integrations.git.GitConsts.SCHEME;

/**
 * @author Michal Sadowski (michal.sadowski@roche.com) on 15.02.2019
 */
@Service
@RequiredArgsConstructor
public class GitClientFactory {
    private final ZoranUserService userService;

    RepositoryService getRepositoryClient() {
        GitHubClient client = createClient();
        RepositoryService service = new RepositoryService(client);
        return service;
    }

    private GitHubClient createClient() {
        GitHubClient client = new GitHubClient(GIT_HUB_API_HOST, PORT, SCHEME);
        client.setOAuth2Token(userService.getAccessToken());
        return client;
    }
}
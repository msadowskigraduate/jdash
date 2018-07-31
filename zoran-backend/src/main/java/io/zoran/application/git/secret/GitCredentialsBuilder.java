package io.zoran.application.git.secret;

import io.zoran.infrastructure.configuration.domain.Git;
import lombok.RequiredArgsConstructor;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.springframework.stereotype.Service;

/**
 * @author Michal Sadowski (michal.sadowski@roche.com) on 30.07.2018
 */
@Service
@RequiredArgsConstructor
public final class GitCredentialsBuilder {

    private final Git git;

    public static UsernamePasswordCredentialsProvider getCredentials(String username, char[] password) {
        return new UsernamePasswordCredentialsProvider(username, password);
    }

    public UsernamePasswordCredentialsProvider getRootGitCredentials() {
        return getCredentials(git.getGit_username(), git.getGit_password());
    }
}

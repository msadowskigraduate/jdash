package io.zoran.git;

import io.zoran.configuration.domain.Git;
import lombok.RequiredArgsConstructor;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.springframework.stereotype.Service;

/**
 * @author Michal Sadowski (michal.sadowski@roche.com) on 30.07.2018
 */
@Service
@RequiredArgsConstructor
public class GitCredentialsBuilder {

    private final Git git;

    public UsernamePasswordCredentialsProvider getCredentials() {
        return new UsernamePasswordCredentialsProvider(git.getGit_username(), git.getGit_password());
    }
}

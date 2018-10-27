package io.zoran.infrastructure.git;

import io.zoran.application.git.secret.GitCredentialsBuilder;
import io.zoran.infrastructure.configuration.domain.Zoran;
import io.zoran.domain.commands.GitCommand;
import io.zoran.domain.commands.internal.GitCloneCommand;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.springframework.stereotype.Service;

/**
 * @author Michal Sadowski (michal.sadowski@roche.com) on 31.07.2018
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class GitService {

    private final GitCredentialsBuilder credentialsBuilder;
    private final Zoran zoran;

    public String cloneTemplateRepository() throws GitAPIException {
        UsernamePasswordCredentialsProvider credentials = credentialsBuilder.getRootGitCredentials();

        GitCommand<String> command = new GitCloneCommand.Builder()
                .remoteURI(zoran.getGit_repository())
                .credentials(credentialsBuilder.getRootGitCredentials())
                .directory(zoran.getZoran_directory())
                .build();

        return command.apply();
    }
}

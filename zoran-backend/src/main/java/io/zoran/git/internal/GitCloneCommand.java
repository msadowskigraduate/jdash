package io.zoran.git.internal;

import io.zoran.git.JGitCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;

import java.io.File;

/**
 * @author Michal Sadowski (sadochasee@gmail.com) on 17/07/2018.
 */
public final class GitCloneCommand extends JGitCommand<String> {

    private final String directory;

    public GitCloneCommand(String URI, UsernamePasswordCredentialsProvider credentials, String directory) {
        super(URI, credentials);
        this.directory = directory;
    }

    @Override
    public String apply() throws GitAPIException {
            Git git = Git.cloneRepository()
                    .setURI(this.URI)
                    .setDirectory(new File(directory))
                    .setCredentialsProvider(this.credentials)
                    .call();
            return directory;
    }
}

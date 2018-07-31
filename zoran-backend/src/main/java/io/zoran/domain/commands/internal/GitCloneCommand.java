package io.zoran.domain.commands.internal;

import io.zoran.domain.commands.AbstractGitCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;

import java.io.File;

/**
 * @author Michal Sadowski (sadochasee@gmail.com) on 17/07/2018.
 */
public final class GitCloneCommand extends AbstractGitCommand<String> {

    private String directory;

    public GitCloneCommand(Builder builder) {
        super(builder);
        directory = builder.directory;
    }

    @Override
    public String apply() throws GitAPIException {
            Git git = Git.cloneRepository()
                    .setURI(this.URI)
                    .setDirectory(new File(directory))
                    .setCredentialsProvider(this.credentials)
                    .call();
            return git.getRepository().getDirectory().getAbsolutePath();
    }


    public static class Builder extends AbstractGitCommand.Builder<Builder> {
        private String directory;

        public Builder directory(String directory) {
            this.directory = directory;
            return self();
        }

        @Override
        protected Builder self() {
            return this;
        }

        @Override
        public AbstractGitCommand build() {
            return new GitCloneCommand(this);
        }
    }
}

package io.zoran.domain.commands.internal;

import io.zoran.domain.commands.AbstractGitCommand;

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
    public String apply() {
        throw new UnsupportedOperationException();
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

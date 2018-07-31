package io.zoran.domain.commands;

import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;

/**
 * @author Michal Sadowski (michal.sadowski@roche.com) on 30.07.2018
 */
public abstract class AbstractGitCommand<T> implements GitCommand<T> {
    protected final String URI;
    protected final UsernamePasswordCredentialsProvider credentials;

    protected abstract static class Builder< T extends Builder<T>> {

        private String remoteURI;
        private UsernamePasswordCredentialsProvider gitCredentials;

        public T remoteURI(String URI) {
            this.remoteURI = URI;
            return self();
        }

        public T credentials(UsernamePasswordCredentialsProvider gitCredentials) {
            this.gitCredentials = gitCredentials;
            return self();
        }

        protected abstract AbstractGitCommand build();

        protected abstract T self();
    }

    public AbstractGitCommand(Builder<?> builder) {
        this.URI = builder.remoteURI;
        this.credentials = builder.gitCredentials;
    }
}

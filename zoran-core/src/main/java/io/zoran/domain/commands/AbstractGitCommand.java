package io.zoran.domain.commands;

/**
 * @author Michal Sadowski (michal.sadowski@roche.com) on 30.07.2018
 */
public abstract class AbstractGitCommand<T> implements GitCommand<T> {
    protected final String URI;

    protected abstract static class Builder< T extends Builder<T>> {

        private String remoteURI;

        public T remoteURI(String URI) {
            this.remoteURI = URI;
            return self();
        }


        protected abstract AbstractGitCommand build();

        protected abstract T self();
    }

    public AbstractGitCommand(Builder<?> builder) {
        this.URI = builder.remoteURI;
    }
}

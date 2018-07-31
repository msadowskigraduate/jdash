package io.zoran.git;

import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;

/**
 * @author Michal Sadowski (michal.sadowski@roche.com) on 30.07.2018
 */
public abstract class JGitCommand<T> implements GitCommand<T> {
    protected String URI;
    protected UsernamePasswordCredentialsProvider credentials;

    public JGitCommand(String URI, UsernamePasswordCredentialsProvider credentials) {
        this.URI = URI;
        this.credentials = credentials;
    }
}

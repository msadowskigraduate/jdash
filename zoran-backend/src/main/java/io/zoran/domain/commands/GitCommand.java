package io.zoran.domain.commands;

import org.eclipse.jgit.api.errors.GitAPIException;

/**
 * @author Michal Sadowski (michal.sadowski@roche.com) on 30.07.2018
 */
public interface GitCommand<T> {
    T apply() throws GitAPIException;
}

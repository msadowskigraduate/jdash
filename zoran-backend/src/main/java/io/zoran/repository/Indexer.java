package io.zoran.repository;

/**
 * @author Michal Sadowski (sadochasee@gmail.com) on 16/07/2018.
 *
 * @since 1.0
 */
public interface Indexer<T> {

    void index();

    T getPathForIndex();

    Index getRootIndex();
}

package io.zoran.api.model.indexer;

import java.io.IOException;
import java.nio.file.Path;

/**
 * @author Michal Sadowski (sadochasee@gmail.com) on 16/07/2018.
 *
 * @since 1.0
 */
public interface Indexer<T> {

    T index(Path path) throws IOException;

}

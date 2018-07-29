package io.zoran.api.model.indexer.domain.internal;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author Michal Sadowski (sadochasee@gmail.com) on 22/07/2018.
 */
public interface ModelRepository<T> extends MongoRepository<T, String> {
    
}

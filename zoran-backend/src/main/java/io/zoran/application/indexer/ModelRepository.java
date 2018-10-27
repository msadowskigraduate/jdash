package io.zoran.application.indexer;

import io.zoran.domain.indexer.Tree;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author Michal Sadowski (sadochasee@gmail.com) on 22/07/2018.
 */
interface ModelRepository extends MongoRepository<Tree, String> {
    
}

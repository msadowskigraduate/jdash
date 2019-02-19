package io.zoran.application.indexer;

import io.zoran.domain.indexer.Tree;
import org.springframework.stereotype.Component;

/**
 * @author Michal Sadowski (sadochasee@gmail.com) on 09/12/2018.
 */
@Component
public class ModelFactory {

    private ModelRepository<Tree> defaultInstance;

    private ModelRepository createMemStore(){
        return SimpleModelRepository.newSimpleStore();
    }

    public ModelRepository getDefaultStore() {
        if(this.defaultInstance == null) {
            this.defaultInstance = createMemStore();
        }
        return this.defaultInstance;
    }
}

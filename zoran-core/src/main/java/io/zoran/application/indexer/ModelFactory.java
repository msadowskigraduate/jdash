package io.zoran.application.indexer;

import org.springframework.stereotype.Component;

/**
 * @author Michal Sadowski (sadochasee@gmail.com) on 09/12/2018.
 */
@Component
public class ModelFactory {

    private ModelRepository defaultInstance;

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

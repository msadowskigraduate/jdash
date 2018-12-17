package io.zoran.application.indexer;

import io.zoran.domain.indexer.Model;
import lombok.NonNull;

import java.util.List;

/**
 * @author Michal Sadowski (sadochasee@gmail.com) on 22/07/2018.
 */
interface ModelRepository<T extends Model>{
    /**
     * Adds new Model to repository.
     * @param model Model to be saved
     * @return saved Model
     */
    T addNewTree(T model);

    /**
     * Get saved model by its id. Returns {@code null} if no model of given id can be found.
     * @param id
     * @return tree
     */
    T getTreeById(@NonNull String id);

    /**
     * Fetches and then removes model of given id. Returns {@code null} if no model of given id can be found.
     * @param id Model's id
     * @return model
     */
    T evict(@NonNull String id);

    /**
     * Clears repository of ALL saved models.
     */
    void evict();

    /**
     * Returns all currently saved models. Returns empty list in no models can be found.
     * @return {@code List} of saved models.
     */
    List<T> getAll();

    /**
     * Return true if repository is vacant.
     * @return
     */
    boolean isEmpty();
}

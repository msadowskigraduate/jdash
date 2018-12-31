package io.zoran.core.infrastructure.resource;

import io.zoran.core.domain.resource.Resource;
import io.zoran.core.domain.resource.ResourceVisibility;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * @author Michal Sadowski (michal.sadowski@roche.com) on 20.11.2018
 */
public interface ResourceRepository extends MongoRepository<Resource, String> {
    List<Resource> findAllByOwner(String ownerId);
    List<Resource> findAllByVisibility(ResourceVisibility visibility);
}

package io.zoran.infrastructure.resource;

import io.zoran.domain.resource.Resource;
import io.zoran.domain.resource.ResourceVisibility;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

/**
 * @author Michal Sadowski (michal.sadowski@roche.com) on 20.11.2018
 */
public interface ResourceRepository extends MongoRepository<Resource, String> {
    List<Resource> findAllByOwner(String ownerId);
    List<Resource> findAllByVisibility(ResourceVisibility visibility);
    Optional<Resource> findByUrl(String url);
}

package io.zoran.core.infrastructure.resource;

import io.zoran.core.domain.resource.shared.SharedProjectResource;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

/**
 * @author Michal Sadowski (michal.sadowski@roche.com) on 20.11.2018
 */
public interface SharedResourceRepository extends MongoRepository<SharedProjectResource, String> {
    Optional<SharedProjectResource> findByProjectId(String projectId);

    @Query("{'priviligesMap.?0': {$exists: true}")
    List<SharedProjectResource> findAllResourcesWhereUserIsAMember(String userId);
}

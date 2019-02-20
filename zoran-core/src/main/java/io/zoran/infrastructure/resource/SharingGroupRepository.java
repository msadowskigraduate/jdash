package io.zoran.infrastructure.resource;

import io.zoran.domain.resource.shared.SharingGroup;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

/**
 * @author Michal Sadowski (michal.sadowski@roche.com) on 20.11.2018
 */
public interface SharingGroupRepository extends MongoRepository<SharingGroup, String> {
    Optional<SharingGroup> findByProjectId(String projectId);

    @Query("{'privilegesMap.map': {$elemMatch: {k: ?0} } }")
    List<SharingGroup> findAllResourcesWhereUserIsAMember(String userId);
}

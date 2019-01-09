package io.zoran.infrastructure.integrations.license;

import io.zoran.domain.git.License;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author Michal Sadowski (michal.sadowski@roche.com) on 09.01.2019
 */
@Repository
interface LicenseRepository extends MongoRepository<License, String> {
    Optional<License> findByKey(String key);
}

package io.zoran.domain.audit;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Michal Sadowski (michal.sadowski@roche.com) on 17.11.2018
 */
@Repository
interface AuditRepository extends MongoRepository<AuditEntry, String> {
}

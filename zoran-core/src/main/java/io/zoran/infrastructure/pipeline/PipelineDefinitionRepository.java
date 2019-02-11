package io.zoran.infrastructure.pipeline;

import io.zoran.application.pipelines.domain.PipelineDefinition;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Michal Sadowski (sadochasee@gmail.com) on 23/01/2019.
 */
@Repository
public interface PipelineDefinitionRepository extends MongoRepository<PipelineDefinition, String> {
}

package io.zoran.application.pipelines.service;

import io.zoran.api.domain.PipelineRequest;
import io.zoran.api.domain.PipelineShortResponse;
import io.zoran.application.pipelines.domain.PipelineAsyncTask;
import io.zoran.application.pipelines.domain.PipelineDefinition;
import io.zoran.application.pipelines.handlers.AbstractPipelineTask;

import java.util.List;

/**
 * @author Michal Sadowski (sadochasee@gmail.com) on 26/01/2019.
 */
public interface PipelineService {
    PipelineDefinition createDefinition(PipelineRequest request);
    PipelineDefinition getDefinition(String defId);
    PipelineDefinition editDefinition(PipelineDefinition def);
    PipelineDefinition deleteDefinition(String defId);
    List<PipelineShortResponse> getAll();

    PipelineAsyncTask start(String id);
    PipelineAsyncTask stop(String id);
    PipelineAsyncTask getStatus(String id);
}

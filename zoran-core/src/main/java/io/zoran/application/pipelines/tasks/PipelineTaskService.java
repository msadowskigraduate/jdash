package io.zoran.application.pipelines.tasks;

import io.zoran.application.pipelines.domain.PipelineAsyncTask;
import io.zoran.application.pipelines.domain.PipelineDefinition;

/**
 * @author Michal Sadowski (michal.sadowski@roche.com) on 20.02.2019
 */
public interface PipelineTaskService {
    PipelineAsyncTask run(PipelineDefinition definition, String clientId);
    PipelineAsyncTask getTask(String taskId);
    PipelineAsyncTask addRunningTask(PipelineAsyncTask task);
    PipelineAsyncTask stopTask(String taskId);
    PipelineAsyncTask updateTask(PipelineAsyncTask task);
}

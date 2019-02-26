package io.zoran.application.pipelines.tasks;

import io.zoran.application.pipelines.domain.PipelineAsyncTask;
import io.zoran.application.pipelines.domain.PipelineDefinition;
import io.zoran.application.pipelines.engine.PipelineEngine;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Michal Sadowski (michal.sadowski@roche.com) on 20.02.2019
 */
@Service
@RequiredArgsConstructor
class SimpleTaskService implements PipelineTaskService {
    private final PipelineEngine engine;
    private Map<String, PipelineAsyncTask> taskStore = new ConcurrentHashMap<>();

    @Override
    public PipelineAsyncTask run(PipelineDefinition definition, String clientId) {
        PipelineAsyncTask task = PipelineAsyncTask.builder()
                                                  .id(UUID.randomUUID().toString())
                                                  .idClient(clientId)
                                                  .definition(definition)
                                                  .build();

        engine.start(task, this);
        return task;
    }

    @Override
    public PipelineAsyncTask getTask(String taskId) {
        return taskStore.get(taskId);
    }

    @Override
    public PipelineAsyncTask addRunningTask(PipelineAsyncTask task) {
        String newId = UUID.randomUUID().toString();
        task.setId(newId);
        return taskStore.put(newId, task);
    }

    @Override
    public PipelineAsyncTask stopTask(String taskId) {
        return taskStore.put(taskId, taskStore.get(taskId).failTask());
    }

    @Override
    public PipelineAsyncTask updateTask(PipelineAsyncTask task) {
        taskStore.remove(task.getId());
        return taskStore.put(task.getId(), task);
    }
}

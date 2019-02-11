package io.zoran.application.pipelines.engine;

import io.zoran.application.pipelines.domain.PipelineAsyncTask;
import io.zoran.application.pipelines.domain.PipelineDefinition;
import io.zoran.application.pipelines.domain.PipelineTaskParamMap;
import io.zoran.application.pipelines.handlers.AbstractPipelineTask;
import io.zoran.application.pipelines.service.PipelineService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * @author Michal Sadowski (sadochasee@gmail.com) on 23/01/2019.
 */
@Component
@RequiredArgsConstructor
public class PipelineEngine {
    private final PipelineService service;

    @Async("pipelineProcessorExecutor")
    public CompletableFuture<PipelineAsyncTask> start(PipelineAsyncTask task) {
        PipelineDefinition def = service.getDefinition(task.getDefinitionId());
        Map<Integer, PipelineTaskParamMap> map = def.getOrderTaskMap();

        //Enforce ordering of the tasks.
        for (int i = 0; i < map.keySet().size(); i++) {
            PipelineTaskParamMap paramMap = map.get(i);
            AbstractPipelineTask pTask = service.getTask(paramMap.getClazz());
            pTask.registerInContext(paramMap.getParameters());
            pTask.handle();
        }

        return CompletableFuture.completedFuture(task);
    }
}

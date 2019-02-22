package io.zoran.application.pipelines.engine;

import io.zoran.application.pipelines.domain.PipelineAsyncTask;
import io.zoran.application.pipelines.domain.PipelineDefinition;
import io.zoran.application.pipelines.domain.PipelineProcessingStatus;
import io.zoran.application.pipelines.domain.PipelineTaskParamMap;
import io.zoran.application.pipelines.handlers.AbstractPipelineTask;
import io.zoran.application.pipelines.handlers.HandlerService;
import io.zoran.application.pipelines.tasks.PipelineTaskService;
import io.zoran.application.security.SecurityResourceService;
import io.zoran.domain.resource.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * @author Michal Sadowski (sadochasee@gmail.com) on 23/01/2019.
 */
@Component
@RequiredArgsConstructor
public class PipelineEngine {
    private final HandlerService service;
    private final SecurityResourceService securityResourceService;

    @Async("pipelineProcessorExecutor")
    public CompletableFuture<Void> start(PipelineAsyncTask task, PipelineTaskService taskService) {
        PipelineDefinition def = task.getDefinition();
        Resource resource = securityResourceService.authoriseResourceRequest(def.getTargetResourceId());
        Map<Integer, PipelineTaskParamMap> map = def.getOrderTaskMap();
        task.setDateStart(LocalDateTime.now());
        task.setStatus(PipelineProcessingStatus.IN_PROGRESS);
        taskService.addRunningTask(task);
        //Enforce ordering of the tasks.
        //TODO  This could be done using any implementation of Queue
        for (int i = 0; i < map.keySet().size(); i++) {
            PipelineTaskParamMap paramMap = map.get(i);
            AbstractPipelineTask pTask = service.getTask(paramMap.getClazz());
            pTask.registerInContext(paramMap.getParameters(), resource);
            pTask.handle();
        }

        taskService.updateTask(task.finishTask());
        return CompletableFuture.completedFuture(null);
    }
}

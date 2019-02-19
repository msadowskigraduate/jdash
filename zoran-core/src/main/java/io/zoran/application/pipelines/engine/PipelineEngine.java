package io.zoran.application.pipelines.engine;

import io.zoran.application.pipelines.domain.PipelineAsyncTask;
import io.zoran.application.pipelines.domain.PipelineDefinition;
import io.zoran.application.pipelines.domain.PipelineTaskParamMap;
import io.zoran.application.pipelines.handlers.AbstractPipelineTask;
import io.zoran.application.pipelines.service.PipelineService;
import io.zoran.application.security.SecurityResourceService;
import io.zoran.domain.resource.Resource;
import lombok.RequiredArgsConstructor;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * @author Michal Sadowski (sadochasee@gmail.com) on 23/01/2019.
 */
@Component
@RequiredArgsConstructor
public class PipelineEngine {
    private final PipelineService service;
    private final SecurityResourceService securityResourceService;

    @Async("pipelineProcessorExecutor")
    public CompletableFuture<PipelineAsyncTask> start(PipelineAsyncTask task) throws IOException, GitAPIException {
        PipelineDefinition def = service.getDefinition(task.getDefinitionId());
        Resource resource = securityResourceService.authoriseResourceRequest(def.getTargetResourceId());
        Map<Integer, PipelineTaskParamMap> map = def.getOrderTaskMap();

        //Enforce ordering of the tasks.
        for (int i = 0; i < map.keySet().size(); i++) {
            PipelineTaskParamMap paramMap = map.get(i);
            AbstractPipelineTask pTask = service.getTask(paramMap.getClazz());
            pTask.registerInContext(paramMap.getParameters(), resource);
            pTask.handle();
        }

        return CompletableFuture.completedFuture(task);
    }
}

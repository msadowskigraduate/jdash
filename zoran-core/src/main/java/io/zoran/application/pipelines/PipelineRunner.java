package io.zoran.application.pipelines;

import io.zoran.application.pipelines.domain.PipelineAsyncTask;
import io.zoran.application.pipelines.engine.PipelineEngine;
import io.zoran.application.user.ZoranUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

/**
 * @author Michal Sadowski (sadochasee@gmail.com) on 03/02/2019.
 */
@Component
@RequiredArgsConstructor
public class PipelineRunner {
    private final PipelineEngine engine;
    private final ZoranUserService userService;

    public CompletableFuture<PipelineAsyncTask> run(String defId) {
        PipelineAsyncTask task = PipelineAsyncTask.builder()
                .idClient(userService.getCurrentUser().getId())
                .definition(defId)
                .build();

        return engine.start(task);
    }
}

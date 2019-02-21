package io.zoran.application.pipelines.handlers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Michal Sadowski (michal.sadowski@roche.com) on 20.02.2019
 */
@Service
@RequiredArgsConstructor
class DefaultHandlerService implements HandlerService {

    private final List<AbstractPipelineTask> tasks;

    @Override
    public AbstractPipelineTask getTask(String clazz) {
        return tasks.stream()
                    .filter(x -> x.getClass().getName().equals(clazz))
                    .findFirst()
                    .orElse(null);
    }
}

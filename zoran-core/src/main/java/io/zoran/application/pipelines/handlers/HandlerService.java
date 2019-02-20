package io.zoran.application.pipelines.handlers;

/**
 * @author Michal Sadowski (michal.sadowski@roche.com) on 20.02.2019
 */
public interface HandlerService {
    AbstractPipelineTask getTask(Class clazz);
}

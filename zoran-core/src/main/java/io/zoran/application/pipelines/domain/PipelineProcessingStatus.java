package io.zoran.application.pipelines.domain;

/**
 * @author Michal Sadowski (sadochasee@gmail.com) on 26/01/2019.
 */
public enum PipelineProcessingStatus {
    QUEUED,
    FAILED,
    IN_PROGRESS,
    FINISHED
}

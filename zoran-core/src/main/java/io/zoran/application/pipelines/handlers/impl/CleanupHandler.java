package io.zoran.application.pipelines.handlers.impl;

import io.zoran.application.pipelines.domain.Artifact;
import io.zoran.application.pipelines.handlers.AbstractPipelineTask;
import io.zoran.infrastructure.exception.ZoranHandlerException;

/**
 * @author Michal Sadowski (michal.sadowski@roche.com) on 20.02.2019
 */
public class CleanupHandler extends AbstractPipelineTask {
    @Override
    public String getMessage() {
        return null;
    }

    @Override
    public void handle() throws ZoranHandlerException {

    }

    @Override
    public Artifact getArtifact() {
        return null;
    }
}

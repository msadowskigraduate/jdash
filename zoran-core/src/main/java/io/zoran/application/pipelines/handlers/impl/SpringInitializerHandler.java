package io.zoran.application.pipelines.handlers.impl;

import io.zoran.application.pipelines.domain.Artifact;
import io.zoran.application.pipelines.handlers.AbstractPipelineTask;
import io.zoran.infrastructure.exception.ZoranHandlerException;
import lombok.RequiredArgsConstructor;

/**
 * @author Michal Sadowski (sadochasee@gmail.com) on 20/02/2019.
 */
@Handler
@RequiredArgsConstructor
public class SpringInitializerHandler extends AbstractPipelineTask {

    private Artifact artifact;

    @Override
    public void handle() throws ZoranHandlerException {
        this.artifact = Artifact.instance();
    }

    @Override
    public Artifact getArtifact() {
        return null;
    }
}

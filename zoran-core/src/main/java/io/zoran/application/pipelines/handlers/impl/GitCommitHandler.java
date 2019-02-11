package io.zoran.application.pipelines.handlers.impl;

import io.zoran.application.pipelines.handlers.AbstractPipelineTask;

/**
 * @author Michal Sadowski (sadochasee@gmail.com) on 03/02/2019.
 *
 * Pipeline handler that creates repository AND commits code there.
 */
public class GitCommitHandler extends AbstractPipelineTask {

    @Override
    public void handle() {
        String repoPath = this.map.get(HandlerParamConst.REPO_PATH);

    }
}

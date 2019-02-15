package io.zoran.application.pipelines.handlers;

import io.zoran.application.pipelines.domain.Artifact;
import io.zoran.domain.resource.Resource;
import lombok.NoArgsConstructor;

import java.util.Map;

import static lombok.AccessLevel.PROTECTED;

/**
 * @author Michal Sadowski (sadochasee@gmail.com) on 23/01/2019.
 */
@NoArgsConstructor(access = PROTECTED)
public abstract class AbstractPipelineTask {
    protected Map<String, String> map;
    protected Resource resource;

    //Run handler code
    public abstract void handle();

    /**
     * Artifact is the output, or outcome, of handler code. This can be path to jars, zips etc, or url to remote
     * repositories.
     */
    public abstract Artifact getArtifact();

    public void registerInContext(Map<String, String> map, Resource resource) {
        this.resource = resource;
        this.map = map;
    }
}
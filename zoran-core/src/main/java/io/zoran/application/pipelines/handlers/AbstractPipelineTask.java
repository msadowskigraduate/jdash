package io.zoran.application.pipelines.handlers;

import lombok.NoArgsConstructor;

import java.util.Map;

import static lombok.AccessLevel.PROTECTED;

/**
 * @author Michal Sadowski (sadochasee@gmail.com) on 23/01/2019.
 */
@NoArgsConstructor(access = PROTECTED)
public abstract class AbstractPipelineTask {
    protected Map<String, String> map;

    public abstract void handle();

    public void registerInContext(Map<String, String> map) {
        this.map = map;
    }
}
package io.zoran.application.pipelines.domain;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Michal Sadowski (michal.sadowski@roche.com) on 15.02.2019
 */
public class Artifact {
    private Map<String, Object> context;

    private Artifact() {
        this.context = new ConcurrentHashMap<>();
    }

    public static Artifact instance() {
        return new Artifact();
    }

    public void register(String key, Object value) {
        this.context.put(key, value);
    }

    public Object get(String key) {
        return this.context.get(key);
    }
}
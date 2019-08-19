package io.zoran.application.pipelines.domain;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

/**
 * @author Michal Sadowski (sadochasee@gmail.com) on 26/01/2019.
 */
@Data
@Builder
public class PipelineTaskParamMap {
    private String clazz;
    private Map<String, String> parameters;
}

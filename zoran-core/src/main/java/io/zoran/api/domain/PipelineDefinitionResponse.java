package io.zoran.api.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.zoran.application.pipelines.domain.PipelineStatus;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @author Michal Sadowski (sadochasee@gmail.com) on 07/02/2019.
 */
@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PipelineDefinitionResponse {
    private String idDefinition;
    private String idOwner;
    private String idSharingGroup;
    private String name;
    private String resourceId;
    private Integer noOfRuns;
    private String lastRun;
    private PipelineStatus status;
    private List<HandlerResponse> tasks;
}

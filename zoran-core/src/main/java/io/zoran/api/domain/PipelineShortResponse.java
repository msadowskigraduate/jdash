package io.zoran.api.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.zoran.application.pipelines.domain.PipelineStatus;
import lombok.Builder;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * @author Michal Sadowski (sadochasee@gmail.com) on 06/02/2019.
 */
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PipelineShortResponse {
    @JsonProperty("id")
    private String pipeLineId;
    @JsonProperty("name")
    private String pipelineName;
    @JsonProperty("handlers")
    private Integer noOfHandlers;
    @JsonProperty("noOfRuns")
    private Integer noOfRuns;
    @JsonProperty("lastCompleted")
    private String lastCompleted;
    @JsonProperty("status")
    private PipelineStatus status;
}
package io.zoran.api.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Michal Sadowski (michal.sadowski@roche.com) on 20.02.2019
 */
@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PipelineRequest {
    private String name;
    @JsonProperty("resourceId")
    private String targetResourceId;
    private List<String> listOfSharedUsers;
    @JsonProperty("tasks")
    private List<HandlerResponse> orderTaskMap;
}

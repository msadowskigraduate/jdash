package io.zoran.api.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
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
    private List<HandlerResponse> tasks;
}

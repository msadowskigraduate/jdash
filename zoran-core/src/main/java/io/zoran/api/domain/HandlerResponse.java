package io.zoran.api.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.zoran.infrastructure.configuration.domain.Handler;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * @author Michal Sadowski (sadochasee@gmail.com) on 07/02/2019.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class HandlerResponse {
    @JsonProperty("order")
    private Integer order;
    @JsonProperty("handler")
    private Handler handler;
    @JsonProperty("parameters")
    private Map<String, String> parameters;
}

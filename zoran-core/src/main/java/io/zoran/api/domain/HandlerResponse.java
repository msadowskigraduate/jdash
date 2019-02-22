package io.zoran.api.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.zoran.infrastructure.configuration.domain.Handler;
import lombok.Builder;
import lombok.Data;

import java.util.Map;

/**
 * @author Michal Sadowski (sadochasee@gmail.com) on 07/02/2019.
 */
@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class HandlerResponse {
    private Integer order;
    private Handler handler;
    private Map<String, String> parameters;
}

package io.zoran.api.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Data;

/**
 * @author Michal Sadowski (michal.sadowski@roche.com) on 16.06.2019
 */
@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class SingleCapabilityDto {
    private final String capabilityName;
    private final String name;
    private final String id;
}

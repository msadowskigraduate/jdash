package io.zoran.core.domain.resource.dto;

import lombok.AllArgsConstructor;
import lombok.Value;

import java.util.Map;

/**
 * @author Michal Sadowski (michal.sadowski@roche.com) on 23.11.2018
 */
@Value
@AllArgsConstructor
public class SharingGroupDto {
    private final String projectId;
    private Map<String, String> priviligesMap;
}

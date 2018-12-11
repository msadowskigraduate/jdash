package io.zoran.api.domain;

import lombok.Data;

/**
 * @author Michal Sadowski (michal.sadowski@roche.com) on 11.12.2018
 */
@Data
public class DependencyRequest {
    private final String identifier;
    private final String version;
}

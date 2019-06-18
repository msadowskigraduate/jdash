package io.zoran.infrastructure.configuration.metadata;

import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * @author Michal Sadowski (michal.sadowski@roche.com) on 16.06.2019
 */
@Data
@RequiredArgsConstructor
public class SingleCapability {
    private String name;
    private String id;
    private boolean isDefault;
}

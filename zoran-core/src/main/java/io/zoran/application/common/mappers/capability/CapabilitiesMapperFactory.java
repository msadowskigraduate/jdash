package io.zoran.application.common.mappers.capability;

import io.zoran.api.domain.SingleCapabilityDto;
import io.zoran.infrastructure.configuration.metadata.SingleCapability;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

import static java.util.stream.Collectors.toList;

/**
 * @author Michal Sadowski (michal.sadowski@roche.com) on 16.06.2019
 */
@Component
@RequiredArgsConstructor
public class CapabilitiesMapperFactory {
    private final List<CapabilityMapper> mappers;

    public SingleCapabilityDto map(String parentName, SingleCapability capability) {
        return mappers.stream()
                      .map(x -> x.map(parentName, capability))
                      .filter(Objects::nonNull)
                      .findFirst()
                      .orElseGet(() -> null);
    }

    public List<SingleCapabilityDto> map(String parentName, List<SingleCapability> capability) {
        return capability.stream()
                         .map(x -> map(parentName, x))
                         .filter(Objects::nonNull)
                         .collect(toList());
    }
}
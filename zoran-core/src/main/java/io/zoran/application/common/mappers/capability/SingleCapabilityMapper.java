package io.zoran.application.common.mappers.capability;

import com.google.common.collect.Sets;
import io.zoran.api.domain.SingleCapabilityDto;
import io.zoran.infrastructure.configuration.metadata.SingleCapability;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * @author Michal Sadowski (michal.sadowski@roche.com) on 16.06.2019
 */
@Component
class SingleCapabilityMapper implements CapabilityMapper {
    private static final Set<String> MAPPER_CAPABILITY = Sets.newHashSet("spring-boot", "java", "language");

    @Override
    @Nullable
    public SingleCapabilityDto map(String parentName, SingleCapability t) {
        if(MAPPER_CAPABILITY.contains(parentName))
        return SingleCapabilityDto.builder()
                .id(t.getId())
                .capabilityName(parentName)
                .name(t.getName())
                .build();
        return null;
    }
}

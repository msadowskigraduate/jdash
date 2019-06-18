package io.zoran.application.common.mappers.capability;

import io.zoran.api.domain.SingleCapabilityDto;
import io.zoran.infrastructure.configuration.metadata.SingleCapability;

/**
 * @author Michal Sadowski (michal.sadowski@roche.com) on 16.06.2019
 */
interface CapabilityMapper {
    SingleCapabilityDto map(String parentName, SingleCapability t);
}

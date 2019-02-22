package io.zoran.infrastructure.resource;

import io.zoran.domain.resource.ResourcePrivileges;
import io.zoran.api.domain.SharingGroupDto;
import io.zoran.domain.resource.shared.SharingGroup;
import lombok.experimental.UtilityClass;

import java.util.Map;
import java.util.stream.Collectors;


/**
 * @author Michal Sadowski (sadochasee@gmail.com) on 14/12/2018.
 */
@UtilityClass
public class SharingGroupConverter {
    public SharingGroupDto convert(SharingGroup group) {
        Map<String, String> mappedPrivileges = mapPrivileges(group.getPriviligesMap());
        return SharingGroupDto.of(group.getProjectId(), mappedPrivileges);
    }

    private Map<String, String> mapPrivileges(Map<String, ResourcePrivileges> map) {
        return map.entrySet()
                .stream()
                .collect(Collectors.toMap(Map.Entry::getKey, x -> x.getValue().toString()));
    }
}

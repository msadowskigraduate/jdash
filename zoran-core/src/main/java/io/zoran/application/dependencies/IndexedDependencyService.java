package io.zoran.application.dependencies;

import io.zoran.api.domain.ResourceDependencyMetadata;
import io.zoran.application.common.mappers.ResourceDependencyMetadataMapper;
import io.zoran.application.security.SecurityResourceService;
import io.zoran.domain.resource.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Predicate;

import static io.zoran.application.dependencies.DependencyConstants.INDEXER;
import static java.util.stream.Collectors.toList;

/**
 * @author Michal Sadowski (sadochasee@gmail.com) on 16/12/2018.
 */
@Service
@RequiredArgsConstructor
public class IndexedDependencyService implements DependencyService {

    private final SecurityResourceService resourceService;

    private BiFunction<String, Resource, ResourceDependencyMetadata> map =
            ResourceDependencyMetadataMapper::map;

    @Override
    public String getIdentifier() {
        return INDEXER;
    }

    @Override
    public List<ResourceDependencyMetadata> getDependenciesForVersion(String version) {
        Predicate<Resource> filter = createFilterForVersion(version);
        List<Resource> resources = resourceService.authorizedGetAllResourcesConnectedWithPrincipal();
        return resources.stream()
                .filter(filter)
                .map(resource -> map.apply(getIdentifier(), resource))
                .collect(toList());
    }

    private Predicate<Resource> createFilterForVersion(String version) {
        if(version == null || version.isEmpty()) {
            return Objects::nonNull;
        }

        return (x) -> x.getProjectDetails().getVersion().equals(version);
    }
}

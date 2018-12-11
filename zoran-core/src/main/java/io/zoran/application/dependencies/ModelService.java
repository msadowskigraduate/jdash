package io.zoran.application.dependencies;

import io.zoran.api.domain.DependencyModelResponse;
import io.zoran.api.domain.DependencyRequest;
import io.zoran.api.domain.ResourceDependencyMetadata;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * @author Michal Sadowski (michal.sadowski@roche.com) on 11.12.2018
 */
@Service
@RequiredArgsConstructor
public class ModelService {
    private final List<DependencyService> dependencyService;

    public DependencyModelResponse getAllDependenciesFor(DependencyRequest request) {
        List<DependencyService> filteredServices;
        if (request.getIdentifier() != null) {
            filteredServices = dependencyService.stream()
                                                .filter(service -> service.getIdentifier()
                                                                          .equals(request.getIdentifier()))
                                                .collect(toList());
        } else {
            filteredServices = dependencyService;
        }

        List<ResourceDependencyMetadata> metadataList =
                filteredServices.stream()
                                .map(service -> service.getDependenciesForVersion(request.getVersion()))
                                .flatMap(Collection::stream)
                                .collect(toList());
        return DependencyModelResponse.of(metadataList);
    }
}

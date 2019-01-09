package io.zoran.application.dependencies;

import io.zoran.api.domain.DependencyModelResponse;
import io.zoran.api.domain.DependencyRequest;
import io.zoran.api.domain.LanguageModelResponse;
import io.zoran.api.domain.ResourceDependencyMetadata;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

/**
 * @author Michal Sadowski (michal.sadowski@roche.com) on 11.12.2018
 */
@Service
@RequiredArgsConstructor
public class ModelService {
    private final List<DependencyService> dependencyService;

    private Predicate<DependencyService> filterServices(DependencyRequest id) {
        return x -> x.getIdentifier() != null && x.getIdentifier().equals(id.getIdentifier());
    }

    public DependencyModelResponse getAllDependenciesFor(DependencyRequest request) {
        List<DependencyService> filteredServices;
        if (request.getIdentifier() != null) {
            filteredServices = dependencyService.stream()
                                                .filter(filterServices(request))
                                                .collect(toList());
        } else {
            filteredServices = dependencyService;
        }

        List<ResourceDependencyMetadata> metadataList =
                filteredServices.stream()
                                .map(service -> service.getDependenciesForVersion(request.getVersion()))
                                .filter(Objects::nonNull)
                                .flatMap(Collection::stream)
                                .collect(toList());
        return DependencyModelResponse.of(metadataList);
    }

    public LanguageModelResponse getAllLanguages() {
        return LanguageModelResponse.of(Stream.of("Java", "Kotlin", "Groovy").collect(toList()));
    }
}

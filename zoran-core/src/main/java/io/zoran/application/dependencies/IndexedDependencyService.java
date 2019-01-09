package io.zoran.application.dependencies;

import io.zoran.api.domain.ResourceDependencyMetadata;
import io.zoran.application.common.mappers.ManifestResourceDependencyMetadataMapper;
import io.zoran.application.indexer.IndexerService;
import io.zoran.domain.indexer.Tree;
import io.zoran.domain.manifest.Manifest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
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

    private final IndexerService indexer;

    private BiFunction<String, Manifest, ResourceDependencyMetadata> map =
            ManifestResourceDependencyMetadataMapper::map;

    @Override
    public String getIdentifier() {
        return INDEXER;
    }

    @Override
    public List<ResourceDependencyMetadata> getDependenciesForVersion(String version) {
        Predicate<Manifest> filter = createFilterForVersion(version);
        List<Tree> trees = indexer.getIndexedResults();
        return trees.stream()
                .map(tree -> tree.getAllManifests(filter))
                .flatMap(Collection::stream)
                .map(manifest -> map.apply(getIdentifier(), manifest))
                .collect(toList());
    }

    private Predicate<Manifest> createFilterForVersion(String version) {
        if(version == null || version.isEmpty()) {
            return Objects::nonNull;
        }

        return (x) -> x.getVersion().equals(version);
    }
}

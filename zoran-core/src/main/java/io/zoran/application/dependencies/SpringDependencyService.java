package io.zoran.application.dependencies;

import io.spring.initializr.generator.version.Version;
import io.spring.initializr.metadata.DependencyGroup;
import io.spring.initializr.metadata.InitializrMetadataProvider;
import io.zoran.api.domain.ResourceDependencyMetadata;
import io.zoran.application.common.mappers.DependencyItemToModelMapper;
import io.zoran.domain.generator.DependencyItem;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static io.zoran.application.dependencies.DependencyConstants.SPRING;
import static java.util.stream.Collectors.toList;

/**
 * @author Michal Sadowski (michal.sadowski@roche.com) on 10.12.2018
 */
@Component
@RequiredArgsConstructor
public class SpringDependencyService implements DependencyService {

    private final InitializrMetadataProvider metadataProvider;
    private final DependencyItemToModelMapper modelMapper;

    @Override
    public String getIdentifier() {
        return SPRING;
    }

    @Override
    public List<ResourceDependencyMetadata> getDependenciesForVersion(String version) {
        List<DependencyItem> items = getDependencyItemsForVersion(version);
        return items.stream().map(modelMapper::map).collect(toList());
    }

    private List<DependencyItem> getDependencyItemsForVersion(String version) {
        List<DependencyGroup> dependencyGroups = this.metadataProvider.get()
                                                                      .getDependencies().getContent();
        List<DependencyItem> content = new ArrayList<>();
        Version requestedVersion = (StringUtils.isEmpty(version) ? null
                : Version.parse(version));
        dependencyGroups.forEach((group) -> group.getContent().forEach((dependency) -> {
            if (requestedVersion != null && dependency.getRange() != null) {
                if (dependency.match(requestedVersion)) {
                    content.add(new DependencyItem(group.getName(), dependency));
                }
            }
            else {
                content.add(new DependencyItem(group.getName(), dependency));
            }
        }));
        return content;
    }
}

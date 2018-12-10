package io.zoran.application.dependencies;

import io.spring.initializr.metadata.Dependency;
import io.spring.initializr.metadata.DependencyGroup;
import io.spring.initializr.metadata.InitializrMetadataProvider;
import io.spring.initializr.util.Version;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * @author Michal Sadowski (michal.sadowski@roche.com) on 10.12.2018
 */
@Component
@RequiredArgsConstructor
public class SpringDependencyService implements DependencyService {

    private final InitializrMetadataProvider metadataProvider;

    @Override
    public List<String> getDependenciesForVersion(String version) {
        List<DependencyGroup> dependencyGroups = this.metadataProvider.get()
                                                                      .getDependencies().getContent();
        List<DependencyItem> content = new ArrayList<>();
        Version requestedVersion = (StringUtils.isEmpty(version) ? null
                : Version.parse(version));
        dependencyGroups.forEach((group) -> group.getContent().forEach((dependency) -> {
            if (requestedVersion != null && dependency.getVersionRange() != null) {
                if (dependency.match(requestedVersion)) {
                    content.add(new DependencyItem(group.getName(), dependency));
                }
            }
            else {
                content.add(new DependencyItem(group.getName(), dependency));
            }
        }));
        return content.stream().map(x -> x.dependency.getArtifactId()).collect(toList());
    }

    private static class DependencyItem {

        private final String group;

        private final Dependency dependency;

        DependencyItem(String group, Dependency dependency) {
            this.group = group;
            this.dependency = dependency;
        }

    }
}

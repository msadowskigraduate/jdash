package io.zoran.domain.generator;

import io.spring.initializr.metadata.Dependency;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author Michal Sadowski (michal.sadowski@roche.com) on 11.12.2018
 */
@Getter
@RequiredArgsConstructor
public class DependencyItem {
    private final String group;
    private final Dependency dependency;
}

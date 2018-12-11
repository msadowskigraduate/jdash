package io.zoran.application.dependencies.initialzr;

import io.spring.initializr.metadata.InitializrMetadata;
import io.spring.initializr.metadata.InitializrMetadataProvider;
import lombok.RequiredArgsConstructor;

/**
 * @author Michal Sadowski (michal.sadowski@roche.com) on 10.12.2018
 */
@RequiredArgsConstructor
public class DefaultInitializrMetadataProvider implements InitializrMetadataProvider {

    private final InitializrMetadata metadata;

    @Override
    public InitializrMetadata get() {
        return this.metadata;
    }
}

package io.zoran.infrastructure.configuration;

import io.spring.initializr.generator.ProjectGenerator;
import io.spring.initializr.generator.ProjectRequestPostProcessor;
import io.spring.initializr.generator.ProjectRequestResolver;
import io.spring.initializr.generator.ProjectResourceLocator;
import io.spring.initializr.metadata.*;
import io.zoran.application.dependencies.initialzr.DefaultInitializrMetadataProvider;
import io.zoran.application.dependencies.initialzr.DefaultIntialzrMetadataProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @author Michal Sadowski (michal.sadowski@roche.com) on 10.12.2018
 */
@Configuration
@RequiredArgsConstructor
class InitializrConfiguration {

    private final List<ProjectRequestPostProcessor> postProcessors;

    @Bean
    @ConditionalOnMissingBean
    public ProjectGenerator projectGenerator() {
        return new ProjectGenerator();
    }

    @Bean
    @ConditionalOnMissingBean
    public ProjectRequestResolver projectRequestResolver() {
        return new ProjectRequestResolver(this.postProcessors);
    }

    @Bean
    @ConditionalOnMissingBean
    public ProjectResourceLocator projectResourceLocator() {
        return new ProjectResourceLocator();
    }

    @Bean
    @ConditionalOnMissingBean(InitializrMetadataProvider.class)
    public InitializrMetadataProvider initializrMetadataProvider(InitializrProperties properties) {
        InitializrMetadata metadata = InitializrMetadataBuilder
                .fromInitializrProperties(properties)
                .build();
        return new DefaultInitializrMetadataProvider(metadata);
    }

    @Bean
    @ConditionalOnMissingBean
    public DependencyMetadataProvider dependencyMetadataProvider() {
        return new DefaultIntialzrMetadataProvider();
    }
}

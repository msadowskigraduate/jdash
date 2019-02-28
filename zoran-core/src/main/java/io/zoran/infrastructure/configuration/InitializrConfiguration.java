package io.zoran.infrastructure.configuration;

import io.spring.initializr.generator.io.IndentingWriterFactory;
import io.spring.initializr.generator.io.SimpleIndentStrategy;
import io.spring.initializr.generator.io.template.MustacheTemplateRenderer;
import io.spring.initializr.generator.io.template.TemplateRenderer;
import io.spring.initializr.generator.project.ProjectDirectoryFactory;
import io.spring.initializr.metadata.InitializrMetadata;
import io.spring.initializr.metadata.InitializrMetadataBuilder;
import io.spring.initializr.metadata.InitializrMetadataProvider;
import io.spring.initializr.metadata.InitializrProperties;
import io.zoran.application.dependencies.initialzr.DefaultInitializrMetadataProvider;
import io.zoran.application.local.StorageManager;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.support.NoOpCache;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.io.IOException;


/**
 * @author Michal Sadowski (michal.sadowski@roche.com) on 10.12.2018
 */
@Configuration
@RequiredArgsConstructor
class InitializrConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public ProjectDirectoryFactory projectDirectoryFactory(@Autowired StorageManager sm) throws IOException {
        return (description) -> {
            FileUtils.cleanDirectory(sm.getLocalStoragePath().toFile());
            return sm.getLocalStoragePath();
        };
    }

    @Bean
    public InitializrProperties initializrProperties() {
        return new InitializrProperties();
    }

    @Bean
    @ConditionalOnMissingBean
    public IndentingWriterFactory indentingWriterFactory() {
        return IndentingWriterFactory.create(new SimpleIndentStrategy("\t"));
    }

    @Bean
    @ConditionalOnMissingBean(TemplateRenderer.class)
    public MustacheTemplateRenderer templateRenderer(Environment environment,
                                                     ObjectProvider<CacheManager> cacheManager) {
        return new MustacheTemplateRenderer("classpath:/templates",
                determineCache(environment, cacheManager.getIfAvailable()));
    }

    @Bean
    @ConditionalOnMissingBean(InitializrMetadataProvider.class)
    public InitializrMetadataProvider initializrMetadataProvider(InitializrProperties properties) {
        InitializrMetadata metadata = InitializrMetadataBuilder
                .fromInitializrProperties(properties).build();
        return new DefaultInitializrMetadataProvider(metadata);
    }

    private Cache determineCache(Environment environment, CacheManager cacheManager) {
        if (cacheManager != null) {
            Binder binder = Binder.get(environment);
            boolean cache = binder.bind("spring.mustache.cache", Boolean.class)
                                  .orElse(true);
            if (cache) {
                return cacheManager.getCache("initializr.templates");
            }
        }
        return new NoOpCache("templates");
    }
}

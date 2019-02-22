package io.zoran.infrastructure.configuration;

import io.spring.initializr.generator.io.IndentingWriterFactory;
import io.spring.initializr.generator.io.SimpleIndentStrategy;
import io.spring.initializr.generator.io.template.MustacheTemplateRenderer;
import io.spring.initializr.generator.io.template.TemplateRenderer;
import io.spring.initializr.generator.project.ProjectDirectoryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.support.NoOpCache;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.nio.file.Files;


/**
 * @author Michal Sadowski (michal.sadowski@roche.com) on 10.12.2018
 */
@Configuration
@RequiredArgsConstructor
class InitializrConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public ProjectDirectoryFactory projectDirectoryFactory() {
        return (description) -> Files.createTempDirectory("project-");
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

package io.zoran.application.pipelines.handlers.impl;

import io.spring.initializr.generator.buildsystem.BuildItemResolver;
import io.spring.initializr.generator.project.*;
import io.spring.initializr.metadata.InitializrMetadata;
import io.spring.initializr.metadata.InitializrMetadataProvider;
import io.spring.initializr.metadata.support.MetadataBuildItemResolver;
import io.zoran.application.pipelines.domain.Artifact;
import io.zoran.application.pipelines.handlers.AbstractPipelineTask;
import io.zoran.domain.initializr.ProjectGenerationResult;
import io.zoran.domain.initializr.ProjectRequest;
import io.zoran.infrastructure.exception.ZoranHandlerException;
import io.zoran.infrastructure.initializr.ProjectRequestToDescriptionConverter;
import io.zoran.infrastructure.initializr.ZoranResourceProjectRequestConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Michal Sadowski (sadochasee@gmail.com) on 20/02/2019.
 */
@Slf4j
@Handler
@RequiredArgsConstructor
public class SpringInitializerHandler extends AbstractPipelineTask {
    private final ZoranResourceProjectRequestConverter mapper;
    private final ApplicationContext parentApplicationContext;
    private final ProjectRequestToDescriptionConverter converter;
    private final ZoranResourceProjectRequestConverter zoranResourceConverter;

    private Artifact artifact;
    private transient Map<String, List<File>> temporaryFiles = new LinkedHashMap<>();

    @Override
    public void handle() throws ZoranHandlerException {
        log.info("Generating from Spring Initializr...");
        this.artifact = Artifact.instance();
        InitializrMetadata metadata = this.parentApplicationContext
                .getBean(InitializrMetadataProvider.class).get();

        ProjectRequest request = zoranResourceConverter.map(this.resource);
        ProjectDescription projectDescription = this.converter.convert(request, metadata);

        ProjectGenerator projectGenerator = new ProjectGenerator(
                (projectGenerationContext) -> customizeProjectGenerationContext(
                        projectGenerationContext, metadata));
        ProjectGenerationResult result = projectGenerator.generate(projectDescription, generateProject(request));

        File file = result.getRootDirectory().toFile();
        String name = file.getName();
        addTempFile(name, file);
        log.info("Generating from Spring Initializr... Completed!");
    }

    private void addTempFile(String group, File file) {
        this.temporaryFiles.computeIfAbsent(group, (key) -> new ArrayList<>()).add(file);
    }

    private void customizeProjectGenerationContext(
            AnnotationConfigApplicationContext context, InitializrMetadata metadata) {
        context.setParent(this.parentApplicationContext);
        context.registerBean(InitializrMetadata.class, () -> metadata);
        context.registerBean(BuildItemResolver.class,
                () -> new MetadataBuildItemResolver(
                        metadata,
                        context.getBean(ResolvedProjectDescription.class).getPlatformVersion()));
    }

    private ProjectAssetGenerator<ProjectGenerationResult> generateProject(ProjectRequest request) {
        return (context) -> {
            Path projectDir = new DefaultProjectAssetGenerator().generate(context);
            return new ProjectGenerationResult(context.getBean(ResolvedProjectDescription.class), projectDir);
        };
    }


    @Override
    public Artifact getArtifact() {
        return this.artifact;
    }
}

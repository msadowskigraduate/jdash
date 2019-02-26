package io.zoran.api;

import io.zoran.application.pipelines.handlers.impl.GitCommitHandler;
import io.zoran.application.pipelines.handlers.impl.SpringInitializerHandler;
import io.zoran.application.pipelines.handlers.impl.TemplateGeneratorHandler;
import io.zoran.domain.manifest.ResourceType;
import io.zoran.domain.resource.Resource;
import io.zoran.domain.resource.project.ProjectDetails;
import io.zoran.domain.resource.project.ProjectResource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author Michal Sadowski (michal.sadowski@roche.com) on 15.02.2019
 */
@Slf4j
@Order(Integer.MAX_VALUE)
@RequestMapping("/test")
@RestController
@RequiredArgsConstructor
class TestController {
    private final GitCommitHandler handler;
    private final SpringInitializerHandler springHandler;
    private final TemplateGeneratorHandler templateGeneratorHandler;

    @GetMapping("/newRepo")
    void newRepo() throws IOException, GitAPIException {
        Resource r = ProjectResource.builder().name("test").build();
        ProjectDetails pd = ProjectDetails.builder().description("test desc").build();
        ((ProjectResource) r).setProjectDetails(pd);
        handler.registerInContext(new HashMap<>(), r);
        handler.handle();
        log.info(handler.getArtifact().get("CLONED_LOCAL_PATH").toString());
    }

    @GetMapping("/spring")
    void newSpringApp() throws IOException, GitAPIException {
        Resource r = ProjectResource.builder()
                                    .name("test")
                                    .dependencies(new ArrayList<>())
                                    .resourceType(ResourceType.MAVEN_PROJECT)
                                    .build();
        ProjectDetails pd = ProjectDetails.builder()
                                          .name("test")
                                          .projectName("testname")
                                          .javaVersion("1.8")
                                          .bootVersion("2.0.0.RELEASE")
                                          .projectLanguage("java")
                                          .description("test desc")
                                          .gitUrl("")
                                          .projectName("name")
                                          .artifactId("artifactId")
                                          .groupId("groupId")
                                          .version("1.0")
                                          .build();
        ((ProjectResource) r).setProjectDetails(pd);
        springHandler.registerInContext(new HashMap<>(), r);
        springHandler.handle();

        templateGeneratorHandler.registerInContext(new HashMap<>(), r);
        templateGeneratorHandler.handle();
    }
}

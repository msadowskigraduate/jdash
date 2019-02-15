package io.zoran.api;

import io.zoran.application.pipelines.handlers.impl.GitCommitHandler;
import io.zoran.domain.resource.Resource;
import io.zoran.domain.resource.project.ProjectDetails;
import io.zoran.domain.resource.project.ProjectResource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

/**
 * @author Michal Sadowski (michal.sadowski@roche.com) on 15.02.2019
 */
@Slf4j
@RequestMapping("/test")
@RestController
@RequiredArgsConstructor
class TestController {
    private final GitCommitHandler handler;

    @GetMapping("/newRepo")
    void newRepo() {
        Resource r = ProjectResource.builder().name("test").build();
        ProjectDetails pd = ProjectDetails.builder().description("test desc").build();
        ((ProjectResource) r).setProjectDetails(pd);
        handler.registerInContext(new HashMap<>(), r);
        handler.handle();
        log.info(handler.getArtifact().get("CLONED_LOCAL_PATH").toString());
    }
}

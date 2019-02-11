package io.zoran.api.pipeline;

import io.zoran.api.domain.PipelineDefinitionResponse;
import io.zoran.api.domain.PipelineShortResponse;
import io.zoran.application.pipelines.service.PipelineService;
import io.zoran.infrastructure.pipeline.PipelineResponseConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static io.zoran.api.ApiConst.API_URL;
import static io.zoran.api.ApiConst.UI_URL;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

/**
 * @author Michal Sadowski (sadochasee@gmail.com) on 04/02/2019.
 */
@RestController
@RequestMapping(API_URL + UI_URL)
@RequiredArgsConstructor
public class PipelineController {
    private final static String PIPELINE_URL = "/pipeline";
    private final PipelineService pipelineService;
    private final PipelineResponseConverter converter;

    @GetMapping(value = PIPELINE_URL + "/{id}", produces = APPLICATION_JSON_UTF8_VALUE)
    PipelineDefinitionResponse getDefinitionDetails(@PathVariable("id") String id) {
        return converter.toDefinitionResponse(pipelineService.getDefinition(id));
    }

    @GetMapping(value = PIPELINE_URL, produces = APPLICATION_JSON_UTF8_VALUE)
    List<PipelineShortResponse> getAllPipelines() {
        return pipelineService.getAll();
    }
}

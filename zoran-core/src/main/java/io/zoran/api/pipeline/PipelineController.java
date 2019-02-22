package io.zoran.api.pipeline;

import io.zoran.api.domain.PipelineDefinitionResponse;
import io.zoran.api.domain.PipelineRequest;
import io.zoran.api.domain.PipelineShortResponse;
import io.zoran.application.pipelines.domain.PipelineAsyncTask;
import io.zoran.application.pipelines.service.PipelineService;
import io.zoran.infrastructure.pipeline.PipelineResponseConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    private final static String PIPELINE_TASK_URL = "/task";


    private final PipelineService pipelineService;
    private final PipelineResponseConverter converter;

    @GetMapping(value = PIPELINE_URL, produces = APPLICATION_JSON_UTF8_VALUE)
    List<PipelineShortResponse> getAllPipelines() {
        return pipelineService.getAll();
    }

    @PostMapping(value = PIPELINE_URL, consumes = APPLICATION_JSON_UTF8_VALUE, produces = APPLICATION_JSON_UTF8_VALUE)
    ResponseEntity<PipelineDefinitionResponse> createNewPipeline(@RequestBody PipelineRequest request) {
        PipelineDefinitionResponse response = converter.toDefinitionResponse(pipelineService.createDefinition(request));
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(value = PIPELINE_URL + "/{id}", produces = APPLICATION_JSON_UTF8_VALUE)
    ResponseEntity<PipelineDefinitionResponse> deleteDefinition(@PathVariable("id") String id) {
        return ResponseEntity.ok(converter.toDefinitionResponse(pipelineService.deleteDefinition(id)));
    }

    @GetMapping(value = PIPELINE_URL + "/{id}", produces = APPLICATION_JSON_UTF8_VALUE)
    PipelineDefinitionResponse getDefinitionDetails(@PathVariable("id") String id) {
        return converter.toDefinitionResponse(pipelineService.getDefinition(id));
    }

    @PostMapping(value = PIPELINE_URL + "/{id}" + PIPELINE_TASK_URL, produces = APPLICATION_JSON_UTF8_VALUE)
    ResponseEntity<PipelineAsyncTask> start(@PathVariable("id") String id) {
        return ResponseEntity.ok(pipelineService.start(id));
    }

    @DeleteMapping(value = PIPELINE_URL + "/{id}" + PIPELINE_TASK_URL, produces = APPLICATION_JSON_UTF8_VALUE)
    ResponseEntity<PipelineAsyncTask> stop(@PathVariable("id") String id) {
        return ResponseEntity.ok(pipelineService.stop(id));
    }

    @GetMapping(value = PIPELINE_URL + "/{id}" + PIPELINE_TASK_URL, produces = APPLICATION_JSON_UTF8_VALUE)
    ResponseEntity<PipelineAsyncTask> getStatus(@PathVariable("id") String id) {
        return ResponseEntity.ok(pipelineService.getStatus(id));
    }
}

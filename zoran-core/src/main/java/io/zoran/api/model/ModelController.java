package io.zoran.api.model;

import io.zoran.api.domain.DependencyModelResponse;
import io.zoran.api.domain.DependencyRequest;
import io.zoran.application.dependencies.ModelService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import reactor.core.publisher.Flux;

import static io.zoran.api.ApiConst.API_URL;
import static io.zoran.api.ApiConst.UI_URL;

/**
 * @author Michal Sadowski (sadochasee@gmail.com) on 29/07/2018.
 */
@Controller
@RequestMapping(API_URL + UI_URL)
@RequiredArgsConstructor
class ModelController {
    private final String MODEL_API = "/model";
    private final ModelService modelService;

    @GetMapping(value = MODEL_API + "/dependencies", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    Flux<DependencyModelResponse> getDependencies(@RequestParam(value = "id", required = false) String identifier,
                                                  @RequestParam(value = "version", required = false) String version) {
        return Flux.just(modelService.getAllDependenciesFor(new DependencyRequest(identifier, version)));
    }
}

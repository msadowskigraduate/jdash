package io.zoran.api.model;

import io.zoran.application.dependencies.DependencyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import reactor.core.publisher.Flux;

import java.util.List;

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
    private final DependencyService dependencyService;

    @GetMapping(MODEL_API)
    Flux<String> getModel() {
        return Flux.just("Hello world!");
    }

    @GetMapping(MODEL_API + "/dependencies")
    Flux<List<String>> getDependencies(@RequestParam("version") String version) {
        return Flux.just(dependencyService.getDependenciesForVersion(version));
    }
}

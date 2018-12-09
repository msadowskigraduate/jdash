package io.zoran.api.model;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import reactor.core.publisher.Flux;

import static io.zoran.api.ApiConst.API_URL;

/**
 * @author Michal Sadowski (sadochasee@gmail.com) on 29/07/2018.
 */
@Controller
@RequestMapping(API_URL)
class ModelController {

    @GetMapping("/model")
    Flux<String> getModel() {
        return Flux.just("Hello world!");
    }
}

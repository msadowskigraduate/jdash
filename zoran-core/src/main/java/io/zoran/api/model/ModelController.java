package io.zoran.api.model;

import io.zoran.api.domain.DependencyModelResponse;
import io.zoran.api.domain.DependencyRequest;
import io.zoran.api.domain.LanguageModelResponse;
import io.zoran.application.dependencies.ModelService;
import io.zoran.domain.git.LicenseResponse;
import io.zoran.infrastructure.configuration.domain.GitHub;
import io.zoran.infrastructure.integrations.GitProxyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static io.zoran.api.ApiConst.API_URL;
import static io.zoran.api.ApiConst.APPLICATION_YML;
import static io.zoran.api.ApiConst.UI_URL;

/**
 * @author Michal Sadowski (sadochasee@gmail.com) on 29/07/2018.
 */
@RestController
@RequestMapping(API_URL + UI_URL)
@RequiredArgsConstructor
class ModelController {
    private final static String MODEL_API = "/model";

    private final ModelService modelService;
    private final GitProxyService gitProxyService;
    private final GitHub gitHub;

    @GetMapping(value = MODEL_API + "/dependencies", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    DependencyModelResponse getDependencies(@RequestParam(value = "id", required = false) String identifier,
                                                  @RequestParam(value = "version", required = false) String version) {
        return modelService.getAllDependenciesFor(new DependencyRequest(identifier, version));
    }

    @GetMapping(value = MODEL_API + "/languages", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    LanguageModelResponse getSupportedLanguages() {
        return modelService.getAllLanguages();
    }

    @GetMapping(value = MODEL_API + "/licence", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    LicenseResponse getLicences() {
        return LicenseResponse.of(gitProxyService.getLicenses(gitHub.getClient_id(), gitHub.getClient_secret()));
    }

    @PostMapping(value = MODEL_API + "/sanitize")
    ResponseEntity sanitizeMarkdown(@RequestParam String string) {
        return ResponseEntity.ok(XSSFilterUtils)
    }

}
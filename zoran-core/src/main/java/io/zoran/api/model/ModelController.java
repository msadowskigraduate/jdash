package io.zoran.api.model;

import io.zoran.api.domain.DependencyModelResponse;
import io.zoran.api.domain.DependencyRequest;
import io.zoran.api.domain.LanguageModelResponse;
import io.zoran.api.domain.TemplateMetadata;
import io.zoran.application.dependencies.ModelService;
import io.zoran.application.indexer.TemplateFactory;
import io.zoran.domain.git.LicenseResponse;
import io.zoran.domain.manifest.Manifest;
import io.zoran.infrastructure.configuration.domain.Handler;
import io.zoran.infrastructure.configuration.domain.PipelineMetadataModel;
import io.zoran.infrastructure.integrations.license.LicenseService;
import io.zoran.infrastructure.services.XSSFilterUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import static io.zoran.api.ApiConst.API_URL;
import static io.zoran.api.ApiConst.UI_URL;
import static java.util.stream.Collectors.toList;

/**
 * @author Michal Sadowski (sadochasee@gmail.com) on 29/07/2018.
 */
@RestController
@RequestMapping(API_URL + UI_URL)
@RequiredArgsConstructor
class ModelController {
    private final static String MODEL_API = "/model";

    private final ModelService modelService;
    private final LicenseService licenseService;
    private final PipelineMetadataModel metadataModel;
    private final TemplateFactory templateFactory;

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
        return LicenseResponse.of(licenseService.getAll());
    }

    @PostMapping(value = MODEL_API + "/sanitize")
    ResponseEntity sanitizeMarkdown(@RequestParam String string) {
        return ResponseEntity.ok(XSSFilterUtils.sanitize(string));
    }

    @GetMapping(value = MODEL_API + "/pipeline", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    PipelineMetadataModel getPipelineModel() {
        return this.metadataModel;
    }

    @GetMapping(value = MODEL_API + "/pipeline/{clazz}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    Handler getHandler(@PathVariable("clazz") String clazz) {
        return this.metadataModel.getByClass(clazz);
    }


    @GetMapping(value = MODEL_API + "/template", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    List<TemplateMetadata> getTemplateMetadata(@RequestParam("slugs") String[] slugs) {
        return Stream.of(slugs)
                     .map(templateFactory::getManifestForTemplateUsed)
                     .filter(Objects::nonNull)
                     .map(Manifest::getTemplate)
                     .flatMap(Collection::stream)
                     .map(x -> TemplateMetadata.builder()
                                               .templateSlug(x.getName())
                                               .contexts(x.getContext())
                                               .build())
                     .collect(toList());
    }
}
package io.zoran.api.template;

import io.zoran.api.domain.ResourceResponse;
import io.zoran.application.indexer.TemplateFactory;
import io.zoran.infrastructure.resource.ResourceConverter;
import io.zoran.infrastructure.resource.ResourceMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static io.zoran.api.ApiConst.API_URL;
import static io.zoran.api.ApiConst.UI_URL;
import static java.util.stream.Collectors.toList;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

/**
 * @author Michal Sadowski (michal.sadowski@roche.com) on 05.03.2019
 */
@RestController
@RequestMapping(API_URL + UI_URL)
@RequiredArgsConstructor
class TemplateController {
    static final String TEMPLATE_API_PATH = "/template";
    private final TemplateFactory factory;
    private final ResourceMapper mapper;

    @GetMapping(value = TEMPLATE_API_PATH, produces = APPLICATION_JSON_UTF8_VALUE)
    List<ResourceResponse> getAllTemplates() {
        return factory.getAllTemplateData()
                      .stream()
                      .map(mapper::map)
                      .map(ResourceConverter::convert)
                      .collect(toList());
    }
}

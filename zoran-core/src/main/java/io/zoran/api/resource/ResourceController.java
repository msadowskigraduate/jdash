package io.zoran.api.resource;

import io.zoran.core.application.security.SecurityResourceService;
import io.zoran.core.domain.resource.dto.ProjectResourceDto;
import io.zoran.core.infrastructure.resource.ResourceConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import static io.zoran.api.ApiConst.API_URL;
import static io.zoran.api.ApiConst.UI_URL;
import static java.util.stream.Collectors.toList;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

/**
 * @author Michal Sadowski (sadochasee@gmail.com) on 14/12/2018.
 */
@Controller
@RequestMapping(API_URL + UI_URL)
@RequiredArgsConstructor
class ResourceController {

    final static String RESOURCE_API = "/resource";

    private final SecurityResourceService service;

    @PostMapping(value = RESOURCE_API, produces = APPLICATION_JSON_UTF8_VALUE)
    ResponseEntity<String> newResource(@RequestBody ProjectResourceDto dto) throws URISyntaxException {
        ProjectResourceDto resourceDto = service.newResource(dto);
        return ResponseEntity.created(new URI(resourceDto.getResourceIdentifier())).build();
    }

    @GetMapping(value = RESOURCE_API, produces = APPLICATION_JSON_UTF8_VALUE)
    Flux<List<ProjectResourceDto>> getResources() {
        List<ProjectResourceDto> dtos = service.authoriseAllResourcesOwnedByRequest().stream()
                .map(ResourceConverter::convert)
                .collect(toList());
        return Flux.just(dtos);
    }

    @GetMapping(value = RESOURCE_API + "/{id}", produces = APPLICATION_JSON_UTF8_VALUE)
    Flux<ProjectResourceDto> getResourceByResourceId(@PathVariable("id") String id) {
        ProjectResourceDto dtos = ResourceConverter.convert(service.authoriseResourceRequest(id));
        return Flux.just(dtos);
    }

    @DeleteMapping(value = RESOURCE_API + "/{id}", produces = APPLICATION_JSON_UTF8_VALUE)
    Flux<ProjectResourceDto> deleteResourceByResourceId(@PathVariable("id") String id) {
        ProjectResourceDto dtos = service.deleteResource(id);
        return Flux.just(dtos);
    }
}
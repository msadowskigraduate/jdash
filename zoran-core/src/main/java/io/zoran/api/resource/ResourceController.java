package io.zoran.api.resource;

import io.swagger.annotations.Api;
import io.zoran.api.domain.ProjectResourceRequest;
import io.zoran.application.common.validation.Validator;
import io.zoran.application.security.SecurityResourceService;
import io.zoran.api.domain.ResourceResponse;
import io.zoran.domain.manifest.ResourceType;
import io.zoran.infrastructure.resource.ResourceConverter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static io.zoran.api.ApiConst.*;
import static java.util.stream.Collectors.toList;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

/**
 * @author Michal Sadowski (sadochasee@gmail.com) on 14/12/2018.
 */
@Api
@RestController
@RequestMapping(API_URL + UI_URL)
@RequiredArgsConstructor
public class ResourceController {

    private static final String RESOURCE_TRANSFER_URL = "/owner";
    private static final String RESOURCE_ALL_URL = "/all";

    private final SecurityResourceService service;
    private final Validator<ProjectResourceRequest> validator;

    @GetMapping(value = RESOURCE_API + RESOURCE_ALL_URL, produces = APPLICATION_JSON_UTF8_VALUE)
    List<ResourceResponse> getResources(@RequestParam(name = "type", required = false) ResourceType type) {
        List<ResourceResponse> dtos = service.authorizedGetAllResourcesConnectedWithPrincipal().stream()
                                             .filter(x -> type == null || x.getResourceType() == type)
                                             .map(ResourceConverter::convert)
                                             .collect(toList());
        return dtos;
    }

    @GetMapping(value = RESOURCE_API, produces = APPLICATION_JSON_UTF8_VALUE)
    List<ResourceResponse> getAllResourcesPublicOrRead() {
        List<ResourceResponse> dtos = service.authoriseAllResourcesOwnedByRequest().stream()
                                             .map(ResourceConverter::convert)
                                             .collect(toList());
        return dtos;
    }

    @GetMapping(value = RESOURCE_API + "/{resourceId}", produces = APPLICATION_JSON_UTF8_VALUE)
    ResourceResponse getResourceByResourceId(@PathVariable("resourceId") String id) {
        ResourceResponse dtos = ResourceConverter.convert(service.authoriseResourceRequest(id));
        return dtos;
    }

    @DeleteMapping(value = RESOURCE_API + "/{resourceId}", produces = APPLICATION_JSON_UTF8_VALUE)
    ResourceResponse deleteResourceByResourceId(@PathVariable("resourceId") String id) {
        ResourceResponse dtos = service.deleteResource(id);
        return dtos;
    }

    @CrossOrigin
    @PostMapping(value = RESOURCE_API, consumes = APPLICATION_JSON_UTF8_VALUE, produces = APPLICATION_JSON_UTF8_VALUE)
    ResourceResponse createNewResource(@RequestBody @NonNull ProjectResourceRequest dto) {
        validator.validate(dto);
        return service.newResource(dto);
    }

    @PostMapping(RESOURCE_API + "/{resourceId}" + RESOURCE_TRANSFER_URL)
    ResponseEntity<ResourceResponse> transferOwnership(@RequestParam String newOwner,
                                                       @PathVariable("resourceId") String resourceId) {
        return ResponseEntity.ok(service.transferOwnership(resourceId, newOwner));
    }
}
package io.zoran.api.resource;

import io.zoran.core.application.security.SecurityResourceService;
import io.zoran.core.domain.resource.dto.SharingGroupDto;
import io.zoran.core.infrastructure.exception.ResourceNotFoundException;
import io.zoran.core.infrastructure.resource.SharingGroupConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import static io.zoran.api.ApiConst.API_URL;
import static io.zoran.api.ApiConst.UI_URL;
import static io.zoran.api.resource.ResourceController.RESOURCE_API;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

/**
 * @author Michal Sadowski (sadochasee@gmail.com) on 14/12/2018.
 */
@Controller
@RequestMapping(API_URL + UI_URL + RESOURCE_API)
@RequiredArgsConstructor
class ResourceSharingController {

    private final SecurityResourceService service;

    @GetMapping(value = "/{id}", produces = APPLICATION_JSON_UTF8_VALUE)
    ResponseEntity<SharingGroupDto> getSharingGroup(@PathVariable("id") String id) {
        SharingGroupDto dto = SharingGroupConverter.convert(service.authorizedGetSharingGroupForProject(id));
        return ResponseEntity.ok(dto);
    }

    @PostMapping(value = "/{id}/share")
    ResponseEntity shareResourceWith(@PathVariable("id") String projectId,
                                     @RequestParam("userId") String userId,
                                     @RequestParam("scope") String scope) {
        try {
            service.authoriseGiveAccessRequest(projectId, userId, scope);
            return ResponseEntity.ok().build();
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}

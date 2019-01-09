package io.zoran.core.api;

import io.zoran.core.application.security.SecurityResourceService;
import io.zoran.core.domain.resource.dto.ProjectResourceDto;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Michal Sadowski (michal.sadowski@roche.com) on 23.11.2018
 */
@Controller
@RequiredArgsConstructor
class NewResourceController {
    private final SecurityResourceService resourceService;

    @PostMapping("/resource")
    ResponseEntity<ProjectResourceDto> createNewResource(@NonNull ProjectResourceDto dto) {
       return ResponseEntity.ok(resourceService.newResource(dto));
    }

    @PostMapping("/resource/{resourceId}/transfer")
    ResponseEntity<ProjectResourceDto> transferOwnership(@RequestParam String newOwner,
                                                         @PathVariable("resourceId") String resourceId) {
        return ResponseEntity.ok(resourceService.transferOwnership(resourceId, newOwner));
    }
}

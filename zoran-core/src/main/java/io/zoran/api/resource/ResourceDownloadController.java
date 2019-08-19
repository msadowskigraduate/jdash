package io.zoran.api.resource;

import io.swagger.annotations.Api;
import io.zoran.application.project.ProjectServingService;
import io.zoran.application.security.SecurityResourceService;
import io.zoran.domain.resource.Resource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

import static io.zoran.api.ApiConst.*;

/**
 * @author Michal Sadowski (michal.sadowski@roche.com) on 21.02.2019
 */
@Api
@Slf4j
@RestController
@RequestMapping(API_URL + UI_URL + RESOURCE_API)
@RequiredArgsConstructor
class ResourceDownloadController {
    private final ProjectServingService servingService;
    private final SecurityResourceService resourceService;

    @GetMapping("/{resourceId}/download")
    @ResponseBody
    ResponseEntity<byte[]> download(@PathVariable("resourceId") String resourceId) throws IOException {
        Resource resource = resourceService.authoriseResourceRequest(resourceId);
        byte[] contents = servingService.getArchived(resource);
        return upload(contents, resource.getName() + ".zip", "application/zip");
    }

    private ResponseEntity<byte[]> upload(byte[] bytes, String fileName, String contentType) {
        ResponseEntity<byte[]> result = createResponseEntity(bytes, contentType, fileName);
        return result;
    }

    private ResponseEntity<byte[]> createResponseEntity(byte[] content,
                                                        String contentType, String fileName) {
        String contentDispositionValue = "attachment; filename=\"" + fileName + "\"";
        return ResponseEntity.ok()
                             .header("Content-Type", contentType)
                             .header("Content-Disposition", contentDispositionValue)
                             .body(content);
    }
}

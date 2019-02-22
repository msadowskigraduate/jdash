package io.zoran.application.common.mappers;

import io.zoran.api.domain.PipelineRequest;
import io.zoran.application.pipelines.domain.PipelineDefinition;
import io.zoran.application.pipelines.domain.PipelineStatus;
import io.zoran.application.resource.SharingGroupService;
import io.zoran.application.security.SecurityResourceService;
import io.zoran.application.user.ZoranUserService;
import io.zoran.domain.resource.Resource;
import io.zoran.domain.resource.shared.SharingGroup;
import io.zoran.infrastructure.services.SharingGroupUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * @author Michal Sadowski (michal.sadowski@roche.com) on 20.02.2019
 */
@Service
@RequiredArgsConstructor
public class PipelineMapper implements Mapper<PipelineRequest, PipelineDefinition> {
    private final SharingGroupService sharingGroupService;
    private final ZoranUserService zoranUserService;
    private final SecurityResourceService resourceService;

    @Override
    public PipelineDefinition map(PipelineRequest pipelineRequest) {
        Resource resource = resourceService.authoriseResourceRequest(pipelineRequest.getTargetResourceId());
        SharingGroup sharingGroup = createAndPopulateSharingGroup(resource.getId(), pipelineRequest.getListOfSharedUsers());
        return PipelineDefinition.builder()
                .idOwner(zoranUserService.getCurrentUser().getId())
                .idSharingGroup(sharingGroup.getSharedResourceId())
                .lastRun(null)
                .noOfRuns(0)
                .status(PipelineStatus.IDLE)
                .targetResourceId(resource.getId())
                .name(pipelineRequest.getName())
                .orderTaskMap(pipelineRequest.getOrderTaskMap())
                .build();
    }

    private SharingGroup createAndPopulateSharingGroup(String resourceId, List<String> shared) {
        SharingGroup sharingGroup = sharingGroupService.getSharingGroupForProject(resourceId);
        shared.stream()
              .map(zoranUserService::getUserByNameOrId)
              .filter(Objects::nonNull)
              .forEach(x -> sharingGroup.giveAccess(x.getId(), SharingGroupUtils.getDefaultPrivilege()));

        return sharingGroup;
    }
}

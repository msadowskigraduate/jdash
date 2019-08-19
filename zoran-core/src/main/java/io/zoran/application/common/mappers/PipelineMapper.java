package io.zoran.application.common.mappers;

import io.zoran.api.domain.HandlerResponse;
import io.zoran.api.domain.PipelineRequest;
import io.zoran.application.pipelines.domain.PipelineDefinition;
import io.zoran.application.pipelines.domain.PipelineStatus;
import io.zoran.application.pipelines.domain.PipelineTaskParamMap;
import io.zoran.application.resource.SharingGroupService;
import io.zoran.application.security.SecurityResourceService;
import io.zoran.application.user.ZoranUserService;
import io.zoran.domain.resource.Resource;
import io.zoran.domain.resource.shared.SharingGroup;
import io.zoran.infrastructure.services.SharingGroupUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

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
                .resourceId(resource.getId())
                .name(pipelineRequest.getName())
                .orderTaskMap(taskParamMapMap(pipelineRequest.getOrderTaskMap()))
                .build();
    }

    private SharingGroup createAndPopulateSharingGroup(String resourceId, List<String> shared) {
        SharingGroup sharingGroup = sharingGroupService.getSharingGroupForProject(resourceId);
        if(shared != null) {
            shared.stream()
                  .map(zoranUserService::getUserByNameOrId)
                  .filter(Objects::nonNull)
                  .forEach(x -> sharingGroup.giveAccess(x.getId(), SharingGroupUtils.getDefaultPrivilege()));
        }
        return sharingGroup;
    }

    private Map<Integer, PipelineTaskParamMap> taskParamMapMap(List<HandlerResponse> response) {
        return response.stream()
                .collect(Collectors.toMap(HandlerResponse::getOrder, y -> PipelineTaskParamMap.builder()
                                                                                              .clazz(y.getHandler().getClazz())
                                                                                              .parameters(y.getParameters())
                                                                                              .build()));
    }
}

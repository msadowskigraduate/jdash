package io.zoran.infrastructure.pipeline;

import io.zoran.api.domain.HandlerResponse;
import io.zoran.api.domain.PipelineDefinitionResponse;
import io.zoran.api.domain.PipelineShortResponse;
import io.zoran.application.pipelines.domain.PipelineDefinition;
import io.zoran.infrastructure.configuration.domain.PipelineMetadataModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

import static io.zoran.infrastructure.services.DateTimeUtils.iso;

/**
 * @author Michal Sadowski (sadochasee@gmail.com) on 06/02/2019.
 */
@Component
@RequiredArgsConstructor
public class PipelineResponseConverter {
    private final PipelineMetadataModel metadataModel;

    public static PipelineShortResponse toShort(PipelineDefinition definition) {
        return PipelineShortResponse.builder()
                .pipeLineId(definition.getIdDefinition())
                .pipelineName(definition.getName())
                .noOfHandlers(definition.getOrderTaskMap().size())
                .noOfRuns(definition.getNoOfRuns())
                .status(definition.getStatus())
                .lastCompleted(iso(definition.getLastRun()))
                .resourceId(definition.getTargetResourceId())
                .build();
    }

    public PipelineDefinitionResponse toDefinitionResponse(PipelineDefinition definition) {
        return PipelineDefinitionResponse.builder()
                .idDefinition(definition.getIdDefinition())
                .idOwner(definition.getIdOwner())
                .idSharingGroup(definition.getIdSharingGroup())
                .name(definition.getName())
                .tasks(definition.getOrderTaskMap().entrySet().stream()
                        .map(x -> HandlerResponse.builder()
                                .order(x.getKey())
                                .handler(metadataModel.getByClass(x.getValue().getClazz()))
                                .parameters(x.getValue().getParameters())
                                .build())
                        .collect(Collectors.toList()))
                .lastRun(iso(definition.getLastRun()))
                .noOfRuns(definition.getNoOfRuns())
                .status(definition.getStatus())
                .resourceId(definition.getTargetResourceId())
                .build();
    }
}
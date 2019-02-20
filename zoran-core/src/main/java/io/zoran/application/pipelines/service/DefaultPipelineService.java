package io.zoran.application.pipelines.service;

import io.zoran.api.domain.PipelineRequest;
import io.zoran.api.domain.PipelineShortResponse;
import io.zoran.application.common.mappers.PipelineMapper;
import io.zoran.application.pipelines.domain.PipelineAsyncTask;
import io.zoran.application.pipelines.domain.PipelineDefinition;
import io.zoran.application.pipelines.tasks.PipelineTaskService;
import io.zoran.application.user.ZoranUserService;
import io.zoran.infrastructure.SecurityDisabled;
import io.zoran.infrastructure.pipeline.PipelineDefinitionRepository;
import io.zoran.infrastructure.pipeline.PipelineResponseConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

/**
 * @author Michal Sadowski (sadochasee@gmail.com) on 03/02/2019.
 */
@SecurityDisabled
@Service
@RequiredArgsConstructor
public class DefaultPipelineService implements PipelineService {

    private final PipelineDefinitionRepository repository;
    private final ZoranUserService userService;
    private final PipelineMapper mapper;
    private final PipelineTaskService taskService;

    @Override
    public PipelineDefinition createDefinition(PipelineRequest request) {
        PipelineDefinition definition = mapper.map(request);
        return repository.save(definition);
    }

    @Override
    public PipelineDefinition getDefinition(String defId) {
        return repository.findById(defId).orElse(null);
    }

    @Override
    public PipelineDefinition editDefinition(PipelineDefinition def) {
        repository.deleteById(def.getIdDefinition());
        return repository.save(def);
    }

    @Override
    public PipelineDefinition deleteDefinition(String defId) {
        Optional<PipelineDefinition> definition = repository.findById(defId);
        if(definition.isPresent()) {
            repository.deleteById(defId);
            return definition.get();
        }
        return null;
    }

    @Override
    public List<PipelineShortResponse> getAll() {
        return repository.findAll().stream()
                .map(PipelineResponseConverter::toShort)
                .collect(toList());
    }

    @Override
    public PipelineAsyncTask start(String pipelineDefinition) {
        PipelineDefinition definition = getDefinition(pipelineDefinition);
        return taskService.run(definition, userService.getCurrentUser().getId());
    }

    @Override
    public PipelineAsyncTask stop(String id) {
        return taskService.stopTask(id);
    }

    @Override
    public PipelineAsyncTask getStatus(String id) {
        return taskService.getTask(id);
    }
}

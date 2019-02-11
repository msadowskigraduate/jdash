package io.zoran.application.pipelines.service;

import io.zoran.api.domain.PipelineShortResponse;
import io.zoran.application.pipelines.domain.PipelineDefinition;
import io.zoran.application.pipelines.handlers.AbstractPipelineTask;
import io.zoran.infrastructure.NoSecurity;
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
@NoSecurity
@Service
@RequiredArgsConstructor
public class DefaultPipelineService implements PipelineService {

    private final PipelineDefinitionRepository repository;
    private final List<AbstractPipelineTask> tasks;

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
    public AbstractPipelineTask getTask(Class clazz) {
        return tasks.stream()
                .filter(x -> x.getClass().isAssignableFrom(clazz))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<PipelineShortResponse> getAll() {
        return repository.findAll().stream()
                .map(PipelineResponseConverter::toShort)
                .collect(toList());
    }
}

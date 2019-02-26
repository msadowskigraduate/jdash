package io.zoran.application.pipelines.service;

import io.zoran.api.domain.PipelineRequest;
import io.zoran.api.domain.PipelineShortResponse;
import io.zoran.application.common.mappers.PipelineMapper;
import io.zoran.application.pipelines.domain.PipelineAsyncTask;
import io.zoran.application.pipelines.domain.PipelineDefinition;
import io.zoran.application.pipelines.tasks.PipelineTaskService;
import io.zoran.application.resource.SharingGroupService;
import io.zoran.application.user.ZoranUserService;
import io.zoran.domain.resource.shared.SharingGroup;
import io.zoran.infrastructure.SecurityEnabled;
import io.zoran.infrastructure.pipeline.PipelineDefinitionRepository;
import io.zoran.infrastructure.pipeline.PipelineResponseConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.common.exceptions.UnauthorizedUserException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import static io.zoran.infrastructure.exception.ExceptionMessageConstants.SHARING_GROUP_NOT_FOUND_OR_401;
import static io.zoran.infrastructure.services.SharingGroupUtils.*;
import static java.util.stream.Collectors.toList;

/**
 * @author Michal Sadowski (sadochasee@gmail.com) on 26/01/2019.
 */
@SecurityEnabled
@Service
@RequiredArgsConstructor
public class SecuredPipelineService implements PipelineService {
    private final PipelineDefinitionRepository repository;
    private final ZoranUserService userService;
    private final SharingGroupService groupService;
    private final PipelineMapper mapper;
    private final PipelineTaskService taskService;

    @Override
    public PipelineDefinition getDefinition(String defId) {
        String userId = userService.getCurrentUser().getId();
        return getSecuredOrShared(defId, canRead(userId));
    }

    @Override
    public PipelineDefinition editDefinition(PipelineDefinition def) {
        String userId = userService.getCurrentUser().getId();
        //Check if edit privileges
        getSecuredOrShared(def.getId(), canWrite(userId));
        return upSert(def);
    }

    @Override
    public PipelineDefinition deleteDefinition(String id) {
        return null;
    }

    @Override
    public List<PipelineShortResponse> getAll() {
        String userId = userService.getCurrentUser().getId();
        List<SharingGroup> sharingGroupList = groupService.getAllForUser(userId);
        return sharingGroupList
                .stream()
                .filter(filterRevoked(userId))
                .map(SharingGroup::getProjectId)
                .map(repository::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(PipelineResponseConverter::toShort)
                .collect(toList());
    }

    @Override
    public PipelineAsyncTask start(String id) {
        PipelineDefinition definition = getDefinition(id);
        definition.setLastRun(LocalDateTime.now());
        definition.incrementBuildNo();
        editDefinition(definition);

        return taskService.run(definition, userService.getCurrentUser().getId());
    }

    @Override
    public PipelineAsyncTask stop(String id) {
        PipelineAsyncTask task = getStatus(id);
        return taskService.stopTask(task.getId());
    }

    @Override
    public PipelineAsyncTask getStatus(String id) {
        PipelineAsyncTask task = taskService.getTask(id);
        if (task.getIdClient().equals(userService.getCurrentUser().getId())) {
            return task;
        }
        return null;
    }

    private PipelineDefinition getSecuredOrShared(String defId, Predicate predicate) {
        PipelineDefinition def = repository.findById(defId).orElse(null);
        if (predicate.test(groupService.getSharingGroupForProject(defId))) {
            return def;
        }

        throw new UnauthorizedUserException(SHARING_GROUP_NOT_FOUND_OR_401);
    }

    private PipelineDefinition upSert(PipelineDefinition definition) {
        repository.deleteById(definition.getId());
        return repository.save(definition);
    }

    @Override
    public PipelineDefinition createDefinition(PipelineRequest request) {
        PipelineDefinition definition = mapper.map(request);
        return repository.save(definition);
    }
}

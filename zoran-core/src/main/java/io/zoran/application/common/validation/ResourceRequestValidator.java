package io.zoran.application.common.validation;

import io.spring.initializr.metadata.InitializrMetadata;
import io.spring.initializr.metadata.InitializrMetadataProvider;
import io.zoran.api.domain.ProjectResourceRequest;
import io.zoran.infrastructure.exception.ZoranValidationException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;

/**
 * @author Michal Sadowski (michal.sadowski@roche.com) on 05.03.2019
 */
@ValidatorImpl(ProjectResourceRequest.class)
@RequiredArgsConstructor
public class ResourceRequestValidator implements Validator<ProjectResourceRequest> {

    private final ApplicationContext parentApplicationContext;

    private static final String EMPTY_GROUP_ID = "Group id cannot be empty!";
    private static final String EMPTY_ARTIFACT_ID = "Artifact ID cannot be empty!";
    private static final String INVALID_JAVA_VERSION = "Java version cannot be empty and must be within supported range";
    private static final String INVALID_TYPE = "Invalid or unsupported type.";
    private static final String INVALID_SB_VERSION = "Invalid or unsupported type.";

    @Override
    public void validate(@NonNull ProjectResourceRequest resourceRequest) throws ZoranValidationException {
        InitializrMetadata metadata = this.parentApplicationContext.getBean(InitializrMetadataProvider.class).get();

        if(resourceRequest.getGroupId() == null || resourceRequest.getGroupId().isEmpty()) {
            throw new ZoranValidationException(EMPTY_GROUP_ID);
        }
        if(resourceRequest.getArtifactId() == null || resourceRequest.getArtifactId().isEmpty()) {
            throw new ZoranValidationException(EMPTY_ARTIFACT_ID);
        }
        if(!InitializerValidator.validateLanguage(resourceRequest.getProjectLanguage(), metadata)) {
            throw new ZoranValidationException(INVALID_JAVA_VERSION);
        }
        if(!InitializerValidator.validateType(resourceRequest.getType().getValue(), metadata)) {
            throw new ZoranValidationException(INVALID_TYPE);
        }
        if(!InitializerValidator.validateSpringBootVersion(resourceRequest.getBootVersion())) {
            throw new ZoranValidationException(INVALID_SB_VERSION);
        }
    }
}
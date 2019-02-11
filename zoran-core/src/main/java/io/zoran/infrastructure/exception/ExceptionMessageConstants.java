package io.zoran.infrastructure.exception;

import lombok.experimental.UtilityClass;

/**
 * @author Michal Sadowski (michal.sadowski@roche.com) on 19.11.2018
 */
@UtilityClass
public class ExceptionMessageConstants {
    public static final String UNAUTHORIZED_MESSAGE = "Not authorized.";
    public static final String RESOURCE_CANNOT_ASSIGN_PRIVILIGES_EXCEEDING = "Cannot assign OWNER priviliges for another user!";
    public static final String SHARING_GROUP_NOT_FOUND_OR_401 = "Sharing group not found or you do not have access to view that page.";
    public static final String NO_PIPELINE_DEFINITION = "Definition not found!";
    static final String PARSE_ERROR = "Cannot parse manifest correctly!";
    static final String CREATE_DIRECTORY_FAILED = "Cannot create new directory!";
}

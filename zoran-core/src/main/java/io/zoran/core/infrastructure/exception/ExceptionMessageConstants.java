package io.zoran.core.infrastructure.exception;

import lombok.experimental.UtilityClass;

/**
 * @author Michal Sadowski (michal.sadowski@roche.com) on 19.11.2018
 */
@UtilityClass
public class ExceptionMessageConstants {
    public static final String UNAUTHORIZED_MESSAGE = "Not authorized.";
    public static final String RESOURCE_CANNOT_ASSIGN_PRIVILIGES_EXCEEDING = "Cannot assign OWNER priviliges for another user!";
    static final String PARSE_ERROR = "Cannot parse manifest correctly!";
    static final String CREATE_DIRECTORY_FAILED = "Cannot create new directory!";
}

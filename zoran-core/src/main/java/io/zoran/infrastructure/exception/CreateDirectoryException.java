package io.zoran.infrastructure.exception;

import static io.zoran.infrastructure.exception.ExceptionMessageConstants.CREATE_DIRECTORY_FAILED;

/**
 * @author Michal Sadowski (sadochasee@gmail.com) on 09/12/2018.
 */
public class CreateDirectoryException extends GenericInternalZoranException {
    public CreateDirectoryException() {
        super(CREATE_DIRECTORY_FAILED);
    }
}

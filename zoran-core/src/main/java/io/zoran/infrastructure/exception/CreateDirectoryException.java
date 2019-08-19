package io.zoran.infrastructure.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import static io.zoran.infrastructure.exception.ExceptionMessageConstants.CREATE_DIRECTORY_FAILED;

/**
 * @author Michal Sadowski (sadochasee@gmail.com) on 09/12/2018.
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class CreateDirectoryException extends GenericInternalZoranException {
    public CreateDirectoryException() {
        super(CREATE_DIRECTORY_FAILED);
    }
}

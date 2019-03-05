package io.zoran.infrastructure.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import static io.zoran.infrastructure.exception.ExceptionMessageConstants.PARSE_ERROR;

/**
 * @author Michal Sadowski (sadochasee@gmail.com) on 08/12/2018.
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class ManifestReaderException extends GenericInternalZoranException {
    public ManifestReaderException() {
        super(PARSE_ERROR);
    }
}

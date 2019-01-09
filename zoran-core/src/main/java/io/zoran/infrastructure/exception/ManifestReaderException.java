package io.zoran.infrastructure.exception;

import static io.zoran.infrastructure.exception.ExceptionMessageConstants.PARSE_ERROR;

/**
 * @author Michal Sadowski (sadochasee@gmail.com) on 08/12/2018.
 */
public class ManifestReaderException extends GenericInternalZoranException {
    public ManifestReaderException() {
        super(PARSE_ERROR);
    }
}

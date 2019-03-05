package io.zoran.infrastructure.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Michal Sadowski (michal.sadowski@roche.com) on 20.11.2018
 */
@ResponseStatus(value = HttpStatus.NO_CONTENT)
public class ResourceNotFoundException extends GenericInternalZoranException {
    public ResourceNotFoundException(String message) {
        super("No rosource of given id: " + message);
    }
}

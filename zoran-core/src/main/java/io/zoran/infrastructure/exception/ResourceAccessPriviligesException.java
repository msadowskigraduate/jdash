package io.zoran.infrastructure.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Michal Sadowski (michal.sadowski@roche.com) on 20.11.2018
 */
@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
public class ResourceAccessPriviligesException extends GenericInternalZoranException {
    public ResourceAccessPriviligesException(String message) {
        super(message);
    }
}

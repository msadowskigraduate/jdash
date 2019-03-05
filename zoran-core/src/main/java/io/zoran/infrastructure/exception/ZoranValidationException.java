package io.zoran.infrastructure.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Michal Sadowski (michal.sadowski@roche.com) on 05.03.2019
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class ZoranValidationException extends GenericInternalZoranException {
    public ZoranValidationException(String message) {
        super(message);
    }
}
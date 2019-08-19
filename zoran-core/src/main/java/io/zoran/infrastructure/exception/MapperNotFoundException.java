package io.zoran.infrastructure.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Michal Sadowski (michal.sadowski@roche.com) on 17.11.2018
 */
@ResponseStatus(value = HttpStatus.NOT_IMPLEMENTED)
public class MapperNotFoundException extends GenericInternalZoranException {
    public MapperNotFoundException(Class input, Class output) {
        super("Cannot find mapper for " + input.getCanonicalName() + " " + output.getCanonicalName());
    }
}

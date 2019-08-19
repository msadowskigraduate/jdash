package io.zoran.infrastructure.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Michal Sadowski (michal.sadowski@roche.com) on 19.02.2019
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class ZoranTemplateException extends GenericInternalZoranException {
    public ZoranTemplateException(String message) {
        super(message);
    }
}
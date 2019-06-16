package io.zoran.infrastructure.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Michal Sadowski (michal.sadowski@roche.com) on 15.02.2019
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ZoranHandlerException extends RuntimeException {
    public ZoranHandlerException(String message) {
        super(message);
    }

    public ZoranHandlerException(String message, Throwable cause) {
        super(message, cause);
    }

    public ZoranHandlerException(Throwable cause) {
        super(cause);
    }
}

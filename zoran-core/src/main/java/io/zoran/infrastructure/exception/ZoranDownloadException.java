package io.zoran.infrastructure.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Michal Sadowski (michal.sadowski@roche.com) on 26.02.2019
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ZoranDownloadException extends GenericInternalZoranException {
    public ZoranDownloadException(String message) {
        super(message);
    }
}

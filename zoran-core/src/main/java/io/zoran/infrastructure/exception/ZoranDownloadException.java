package io.zoran.infrastructure.exception;

/**
 * @author Michal Sadowski (michal.sadowski@roche.com) on 26.02.2019
 */
public class ZoranDownloadException extends GenericInternalZoranException {
    public ZoranDownloadException(String message) {
        super(message);
    }
}

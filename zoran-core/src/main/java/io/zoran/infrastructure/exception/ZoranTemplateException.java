package io.zoran.infrastructure.exception;

/**
 * @author Michal Sadowski (michal.sadowski@roche.com) on 19.02.2019
 */
public class ZoranTemplateException extends GenericInternalZoranException {
    public ZoranTemplateException(String message) {
        super(message);
    }
}
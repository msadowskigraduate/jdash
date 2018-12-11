package io.zoran.core.infrastructure.exception;

/**
 * @author Michal Sadowski (michal.sadowski@roche.com) on 20.11.2018
 */
public class ResourceNotFoundException extends GenericInternalZoranException {
    public ResourceNotFoundException(String message) {
        super("No rosource of given id: " + message);
    }
}

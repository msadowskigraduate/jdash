package io.zoran.core.infrastructure.exception;

/**
 * @author Michal Sadowski (michal.sadowski@roche.com) on 17.11.2018
 */
abstract class GenericInternalZoranException extends RuntimeException {
    GenericInternalZoranException(String message) {
        super(message);
    }
}

package io.zoran.infrastructure.exception;

/**
 * @author Michal Sadowski (michal.sadowski@roche.com) on 17.11.2018
 */
public class InvalidAuditActionException extends GenericInternalZoranException {
    public InvalidAuditActionException() {
        super("Null or Invalid Audit Action!");
    }
}

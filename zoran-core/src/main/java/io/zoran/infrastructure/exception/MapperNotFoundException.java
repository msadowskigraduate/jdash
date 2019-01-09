package io.zoran.infrastructure.exception;

/**
 * @author Michal Sadowski (michal.sadowski@roche.com) on 17.11.2018
 */
public class MapperNotFoundException extends GenericInternalZoranException {
    public MapperNotFoundException(Class input, Class output) {
        super("Cannot find mapper for " + input.getCanonicalName() + " " + output.getCanonicalName());
    }
}

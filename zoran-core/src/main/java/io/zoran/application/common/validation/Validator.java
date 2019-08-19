package io.zoran.application.common.validation;

import io.zoran.infrastructure.exception.ZoranValidationException;

/**
 * @author Michal Sadowski (michal.sadowski@roche.com) on 05.03.2019
 */
@FunctionalInterface
public interface Validator<T> {
    void validate(T t) throws ZoranValidationException;
}

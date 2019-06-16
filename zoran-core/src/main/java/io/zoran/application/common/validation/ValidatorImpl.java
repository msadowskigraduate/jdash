package io.zoran.application.common.validation;

import org.springframework.stereotype.Service;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Michal Sadowski (michal.sadowski@roche.com) on 05.03.2019
 */
@Service
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@interface ValidatorImpl {
    Class value();
}

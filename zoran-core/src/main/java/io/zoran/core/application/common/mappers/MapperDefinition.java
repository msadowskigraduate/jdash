package io.zoran.core.application.common.mappers;

import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Michal Sadowski (michal.sadowski@roche.com) on 17.11.2018
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@interface MapperDefinition {

    /**
     * Mapper input Type
     */
    Class input();

    /**
     * Mapper output (result) Type
     */
    Class output();
}

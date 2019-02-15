package io.zoran.infrastructure;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author Michal Sadowski (michal.sadowski@roche.com) on 21.11.2018
 *
 * Types annotated with {@link SecurityEnabled} are only initialized if security measures are enabled.
 */
@Retention(RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@ConditionalOnProperty(value = "application.config.security.enabled", havingValue = "true")
public @interface SecurityEnabled {
}

package io.zoran.core.application.audit;

import io.zoran.core.domain.audit.AuditAction;
import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Michal Sadowski (michal.sadowski@roche.com) on 16.11.2018
 */
@Component
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.CONSTRUCTOR})
public @interface Audited {
    /**
     *  Type of action that will be added to Audit Log.
     */
    AuditAction value();
}

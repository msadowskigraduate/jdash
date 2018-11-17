package io.zoran.core.domain.audit;

import io.zoran.core.application.audit.Audited;
import io.zoran.core.infrastructure.exception.InvalidAuditActionException;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @author Michal Sadowski (michal.sadowski@roche.com) on 17.11.2018
 */
@Aspect
@Component
@RequiredArgsConstructor
class AuditLoggingAspect {

    private final AuditRepository repository;

    @Around("@annotation(io.zoran.core.application.audit.Audited)")
    public Object aroundAudited(ProceedingJoinPoint pjp) throws Throwable {
        MethodSignature method = (MethodSignature) pjp.getSignature();
        Audited audited = method.getMethod().getAnnotation(Audited.class);
        AuditAction auditAction;
        Object obj;
        if(audited != null) {
            auditAction = audited.value();
            obj = pjp.proceed();
            if(obj != null) {
                addNewAuditAction(auditAction);
                return obj;
            }
        }
        throw new InvalidAuditActionException();
    }

    private void addNewAuditAction(AuditAction auditAction) {
        repository.save(AuditEntry.of(LocalDateTime.now(), auditAction));
    }
}

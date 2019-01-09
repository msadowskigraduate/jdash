package io.zoran.domain.audit;

import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.time.LocalDateTime;

/**
 * @author Michal Sadowski (michal.sadowski@roche.com) on 16.11.2018
 */
@Document
@RequiredArgsConstructor(staticName = "of")
class AuditEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;
    private final LocalDateTime currentTime;
    private final AuditAction auditAction;
}

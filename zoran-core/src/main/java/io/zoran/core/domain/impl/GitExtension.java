package io.zoran.core.domain.impl;

import io.zoran.core.domain.Extension;
import io.zoran.core.domain.ExtenstionStatus;
import lombok.Builder;
import lombok.Data;
import lombok.Value;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

/**
 * @author Michal Sadowski (michal.sadowski@roche.com) on 16.11.2018
 */
@Data
@Builder
@Document
class GitExtension implements Extension {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private final String extensionId;
    private final String ownerId;
    private ExtenstionStatus status;
    private String sshKey;
    private String accountName;
    private String accountMail;

    @Override
    public Extension getInstance() {
        return this;
    }

    @Override
    public boolean isDefined() {
        return false;
    }

    @Override
    public boolean isActive() {
        return this.getStatus().equals(ExtenstionStatus.ACTIVE);
    }

    @Override
    public final String getOwnerId() {
        return this.ownerId;
    }

    public void revokeAccess() {
        this.setStatus(ExtenstionStatus.INACTIVE);
        this.setSshKey(null);
    }

    private boolean validateSelf() {
        return (
                this.extensionId != null &&
                        this.ownerId != null
        );
    }
}

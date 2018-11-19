package io.zoran.core.domain.user;

/**
 * @author Michal Sadowski (michal.sadowski@roche.com) on 19.11.2018
 */
public enum UserState {
    INACTIVE,
    ACCESS_REVOKED,
    AUTHENTICATION_PENDING,
    ACTIVE
}

package io.zoran.domain.user;

/**
 * @author Michal Sadowski (michal.sadowski@roche.com) on 19.11.2018
 */
public enum UserState {
    ANONYMOUS,
    INACTIVE, //Registered but not yet accepted TOS and cannot create/edit/view Resources
    ACCESS_REVOKED, //User cannot use system
    ACTIVE //User can normally use system
}

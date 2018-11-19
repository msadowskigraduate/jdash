package io.zoran.core.domain.user;

import org.springframework.security.oauth2.core.user.OAuth2User;

/**
 * @author Michal Sadowski (michal.sadowski@roche.com) on 16.11.2018
 */
public interface User extends OAuth2User {
    String getId();
    UserState getState();
}
